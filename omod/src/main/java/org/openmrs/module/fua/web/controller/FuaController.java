package org.openmrs.module.fua.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UsernamePasswordCredentials;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.FuaEstado;
import org.openmrs.module.fua.api.FuaEstadoService;
import org.openmrs.module.fua.api.FuaService;
import org.openmrs.module.fua.api.FuaVersionService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;


import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;   // ← la excepción
import org.apache.commons.lang3.StringUtils;


@Controller
@RequestMapping(value = "/module/fua")
public class FuaController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private FuaService fuaService;

	@Autowired
	private FuaEstadoService fuaEstadoService;

	@Autowired
	private FuaVersionService fuaVersionService;
	
	/*@Autowired
	private UserService userService;*/
	
	private final String FORM_VIEW = "/module/fua/pages/addFua";
	
	@RequestMapping(method = RequestMethod.GET)
	public String onGet(ModelMap model, @RequestParam(value = "fuaId", required = false) Integer fuaId) {
		Fua fua = (fuaId != null) ? fuaService.getFua(fuaId) : new Fua();
		model.addAttribute("fua", fua);
		model.addAttribute("fuas", fuaService.getAllFuas());
		model.addAttribute("fuaEstados", fuaEstadoService.getAllEstados());
		return FORM_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onPost(HttpSession httpSession, @ModelAttribute("fua") Fua fua, BindingResult errors,
	        @RequestParam(required = false, value = "action") String action) {
		
		MessageSourceService mss = Context.getMessageSourceService();
		
		if (errors.hasErrors()) {
			return FORM_VIEW;
		}
		
		if (!Context.isAuthenticated()) {
			errors.reject("fua.auth.required");
		} else if ("purge".equals(action)) {
			try {
				fuaService.purgeFua(fua);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "fua.delete.success");
			}
			catch (Exception ex) {
				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "fua.delete.failure");
				log.error("Error al eliminar FUA", ex);
			}
		} else {
			fuaService.saveFua(fua);
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "fua.saved");
		}
		return "redirect:/module/fua";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Fua> getAllFuas() {
		log.info("Llamada a /module/fua/list");
		return fuaService.getAllFuas();
	}

	@RequestMapping(value = "/uuid/{uuid}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getFuaByUuid(@PathVariable("uuid") String uuid) {
		Fua fua = fuaService.getFuaByUuid(uuid);

        if (fua == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FUA no encontrado.");
		}

		return ResponseEntity.ok(fua);
	}

	@RequestMapping(
			value    = "/visitInfo/{visitUuid}/generator/{identifierFormat}",
			method   = RequestMethod.GET,
			produces = "text/html")       // devolvemos HTML
	@ResponseBody
	public ResponseEntity<?> renderVisitInfo(
			@PathVariable String visitUuid,
			@PathVariable String identifierFormat) {

		try {
			/* 1. Buscamos el FUA ------------------------------------------------ */
			Fua fua = fuaService.getFuaByVisitUuid(visitUuid);
			if (fua == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("FUA no encontrado para visitUuid: " + visitUuid);
			}

			/* 2. Pasamos payload de String → JSON ------------------------------ */
			ObjectMapper mapper  = new ObjectMapper();
			JsonNode payloadJson;
			try {
				payloadJson = StringUtils.isBlank(fua.getPayload())
						? mapper.createObjectNode()
						: mapper.readTree(fua.getPayload());

			} catch (JsonProcessingException ex) {
				// Si no es JSON válido, lo mandamos como texto
				payloadJson = mapper.getNodeFactory().textNode(fua.getPayload());
			}

			/* 3. Construimos el body para el microservicio --------------------- */
			Map<String, Object> requestBody = new LinkedHashMap<>();
			requestBody.put("payload", payloadJson);
			requestBody.put("token",   "---");
			//requestBody.put("format",  identifierFormat);

			/* 4. Llamamos al microservicio ------------------------------------- */
			String remoteUrl = "http://localhost:3000/ws/FUAFormat/"
					+ UriUtils.encodePath(identifierFormat, StandardCharsets.UTF_8)
					+ "/render";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
			RestTemplate restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory()); // ← permite body en GET

			ResponseEntity<String> remoteResp = restTemplate.exchange(
					remoteUrl, HttpMethod.GET, entity, String.class);
			// (la llamada interna sigue siendo POST porque el microservicio
			//  necesita el JSON; cámbiala si tu microservicio realmente expone GET)

			/* 5. Devolvemos el HTML recibido ----------------------------------- */
			return ResponseEntity.status(remoteResp.getStatusCode())
					.contentType(MediaType.TEXT_HTML)
					.body(remoteResp.getBody());

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body("Error procesando la solicitud: " + ex.getMessage());
		}
	}



	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getFuaById(@PathVariable("id") Integer id) {
		Fua fua = fuaService.getFuaById(id);

		if (fua == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FUA no encontrado por el Id: " + id);
		}

		return ResponseEntity.ok(fua);
	}

	/*@RequestMapping(value = "/visitInfo/{visitUuid}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getPayloadInfoByVisitUuid(@PathVariable("visitUuid") String visitUuid) {
		Fua fua = fuaService.getFuaByVisitUuid(visitUuid);

		if (fua == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("FUA no encontrado para visitUuid: " + visitUuid);
		}

		// Construir el objeto de respuesta
		Map<String, String> response = new HashMap<>();
		response.put("payload", fua.getPayload() != null ? fua.getPayload() : "");
		response.put("token", "---");
		response.put("format", "---");

		return ResponseEntity.ok(response);
	}*/


	@RequestMapping(value = "/solicitudes", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getSolicitudesFUA(
			@RequestParam(value = "status", required = false) String estado,
			@RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
			@RequestParam(value = "fechaFin", required = false) String fechaFinStr,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate fechaInicio = null;
		LocalDate fechaFin = null;

		try {
			if (fechaInicioStr != null) {
				fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
			}
			if (fechaFinStr != null) {
				fechaFin = LocalDate.parse(fechaFinStr, formatter);
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Formato de fecha inválido. Use yyyy-MM-dd");
		}

		List<Fua> resultados = fuaService.getFuasFiltrados(estado, fechaInicio, fechaFin, page, size);
		return ResponseEntity.ok(resultados);
	}


	/*@ModelAttribute("users")
	protected List<User> getUsers() {
		return userService.getAllUsers();
	}*/
	
	// Nuevo endpoint
	@RequestMapping(value = "/generateFromVisit/{visitUuid}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> generateFuaFromVisit(@PathVariable String visitUuid) {
		try {
			
			log.info("Generando FUA desde visita UUID: " + visitUuid);

			String url = "http://localhost:8080/openmrs/ws/rest/v1/visit/" + visitUuid + "?v=full";

			// Autenticación segura desde runtime.properties
			String username = "admin";//Context.getAdministrationService().getGlobalProperty("fua.rest.username");
			String password = "Admin123";//Context.getAdministrationService().getGlobalProperty("fua.rest.password");

			if (username == null || password == null) {
				log.error("Credenciales de REST no configuradas.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Credenciales REST no configuradas.");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(username, password);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				log.warn("No se pudo obtener la visita con UUID: " + visitUuid);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo obtener la visita.");
			}

			String payload = response.getBody();

			FuaEstado estadoPendiente = fuaEstadoService.getEstado(1);

			//HASTA ACA NO SE CAMBIA NADA

			Fua fua = fuaService.getFuaByVisitUuid(visitUuid);

			if (fua == null) {
				fua = new Fua();
				
				fua.setName("PRUEBA DE generateFuaFromVisit");
				fua.setVisitUuid(visitUuid);
				fua.setPayload(payload);
				fua.setFuaEstado(estadoPendiente);
				System.out.println("///////////////EL FUA ES NULL///////////////////////////////////////////////: " + fua);
				System.out.println("	EL FUA ES NUEVO:");
				System.out.println("	UUID: " + fua.getUuid());
				System.out.println("	ESTADO: " + fua.getFuaEstado());
				System.out.println("	ID: " + fua.getId());
			}
			else{
				fuaVersionService.saveFuaVersion(fua, "GenerateFromVisit");
				fua.setPayload(payload);
				System.out.println("///////////////EL FUA NO ES NULL///////////////////////////////////////////////: " + fua);
			}
			
			System.out.println("===== DETALLES DEL FUA =====");
			System.out.println("ID: " + fua.getId());
			System.out.println("UUID: " + fua.getUuid());
			System.out.println("Visit UUID: " + fua.getVisitUuid());
			System.out.println("Name: " + fua.getName());
			System.out.println("Payload: Siempre tiene algo xd");
			System.out.println("Estado: " + (fua.getFuaEstado() != null ? fua.getFuaEstado().getNombre() : "null")); // Asumiendo que FuaEstado tiene getNombre()
			System.out.println("Fecha de creación: " + fua.getFechaCreacion());
			System.out.println("Fecha de actualización: " + fua.getFechaActualizacion());
			System.out.println("Versión: " + fua.getVersion());
			System.out.println("Activo: " + fua.getActivo());
			System.out.println("==================================");


			fuaService.saveFua(fua);

			log.info("FUA generado exitosamente desde visita UUID: " + visitUuid);
			return ResponseEntity.ok(fua);

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			log.error("Error HTTP al obtener visita: " + ex.getStatusCode(), ex);
			return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("Error inesperado al generar FUA desde visita: " + visitUuid, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			        .body("Error al generar el FUA: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/estado/update/{fuaId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> actualizarEstadoFua(@PathVariable Integer fuaId, @RequestBody Map<String, Object> body) {
		try {
			if (!Context.isAuthenticated()) {
				UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "Admin123");
				Context.authenticate(credentials);
			}

			/*log.info("Cambiando estado del FUA ID: " + fuaId);

			Fua fua = fuaService.getFua(fuaId);
			if (fua == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FUA no encontrado con ID: " + fuaId);
			}*/

			if (!body.containsKey("estadoId")) {
				return ResponseEntity.badRequest().body("El cuerpo de la solicitud debe incluir 'estadoId'");
			}

			Integer nuevoEstadoId;
			try {
				nuevoEstadoId = (Integer) body.get("estadoId");
			} catch (ClassCastException e) {
				// Si viene como Double (por defecto en JSON), lo convertimos a Integer
				Double estadoDouble = (Double) body.get("estadoId");
				nuevoEstadoId = estadoDouble.intValue();
			}
			
			Fua fua = fuaService.getFuaById(fuaId);
			fuaVersionService.saveFuaVersion(fua, "Update estado de FUA");

			FuaEstado estadoPendiente = fuaEstadoService.getEstado(nuevoEstadoId);
			
			fua.setFuaEstado(estadoPendiente);
			fuaService.saveFua(fua);


			return ResponseEntity.ok(fua);
		} catch (Exception e) {
			log.error("Error al actualizar el estado del FUA", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al actualizar el estado del FUA.");
		}
	}


}
