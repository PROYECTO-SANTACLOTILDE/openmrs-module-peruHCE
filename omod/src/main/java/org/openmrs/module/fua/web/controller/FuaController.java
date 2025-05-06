package org.openmrs.module.fua.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*import org.openmrs.User;
import org.openmrs.api.UserService;*/
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.api.FuaService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/module/fua/fua.form")
public class FuaController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private FuaService fuaService;
	
	/*@Autowired
	private UserService userService;*/
	
	private final String FORM_VIEW = "/module/fua/pages/addFua";
	
	@RequestMapping(method = RequestMethod.GET)
	public String onGet(ModelMap model, @RequestParam(value = "fuaId", required = false) Integer fuaId) {
		Fua fua = (fuaId != null) ? fuaService.getFua(fuaId) : new Fua();
		model.addAttribute("fua", fua);
		model.addAttribute("fuas", fuaService.getAllFuas());
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
		return "redirect:/module/fua/fua.form";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Fua> getAllFuas() {
		log.info("Llamada a /module/fua/fua.form/list");
		return fuaService.getAllFuas();
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

			Fua fua = new Fua();
			fua.setName("PRUEBA DE generateFuaFromVisit");
			fua.setVisitUuid(visitUuid);
			fua.setFormatoFuaUuid("47d8617b-f25b-4ead-a7d5-20dc4808c339");
			fua.setPayload(payload);

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
}
