package org.openmrs.module.fua.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UsernamePasswordCredentials;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.FuaEstado;
import org.openmrs.module.fua.api.FuaEstadoService;
import org.openmrs.module.fua.api.FuaService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;


import org.openmrs.module.fua.dto.VisitDto;


@Controller
@RequestMapping(value = "/module/fua")
public class FuaController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private FuaService fuaService;

	@Autowired
	private FuaEstadoService fuaEstadoService;
	
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

	@RequestMapping(value = "/visituuid/{visitUuid}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getFuaByVisitUuid(@PathVariable("visitUuid") String visitUuid) {
		Fua fua = fuaService.getFuaByVisitUuid(visitUuid);

		if (fua == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FUA no encontrado para visitUuid: " + visitUuid);
		}

		return ResponseEntity.ok(fua);
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

	@RequestMapping(value = "/visitInfo/{visitUuid}", method = RequestMethod.GET, produces = "application/json")
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
	}


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

            // Asegurar que esté autenticado (por si se llama fuera del contexto habitual)
            if (!Context.isAuthenticated()) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "Admin123");
				Context.authenticate(credentials);
            }

            Visit visit = Context.getVisitService().getVisitByUuid(visitUuid);
            if (visit == null) {
                log.warn("No se encontró la visita con UUID: " + visitUuid);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Visita no encontrada.");
            }

            // Convertir objeto Visit a JSON usando Jackson
            
			VisitDto visitDto = new VisitDto(visit);
			String payload = new ObjectMapper().writeValueAsString(visitDto);
			
			/*
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
			
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.registerModule(new Hibernate5Module().disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION));

			String payload = mapper.writeValueAsString(visit);
			*/

            FuaEstado estadoPendiente = fuaEstadoService.getEstado(1); // Estado inicial: Pendiente

            Fua fua = new Fua();
            fua.setName("FUA generado desde visita UUID: " + visitUuid);
            fua.setVisitUuid(visitUuid);
            fua.setPayload(payload);
            fua.setFuaEstado(estadoPendiente);

            fuaService.saveFua(fua);

            log.info("FUA generado exitosamente desde visita UUID: " + visitUuid);
            return ResponseEntity.ok(fua);

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
			FuaEstado estadoPendiente = fuaEstadoService.getEstado(nuevoEstadoId);

			Fua fua = fuaService.updateEstadoFua(fuaId, estadoPendiente);

			return ResponseEntity.ok(fua);
		} catch (Exception e) {
			log.error("Error al actualizar el estado del FUA", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al actualizar el estado del FUA.");
		}
	}


}
