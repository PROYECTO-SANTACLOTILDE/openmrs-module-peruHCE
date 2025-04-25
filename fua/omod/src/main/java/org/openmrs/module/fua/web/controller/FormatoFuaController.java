package org.openmrs.module.fua.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.fua.FormatoFua;
import org.openmrs.module.fua.api.FormatoFuaService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/module/fua/formatofua.form")
public class FormatoFuaController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private FormatoFuaService formatoFuaService;
	
	@Autowired
	private UserService userService;
	
	private final String FORM_VIEW = "/module/fua/pages/addFormatoFua";
	
	@RequestMapping(method = RequestMethod.GET)
	public String onGet(ModelMap model, @RequestParam(value = "formatofuaId", required = false) Integer formatoFuaId) {
		FormatoFua formatoFua = (formatoFuaId != null) ? formatoFuaService.getFormatoFua(formatoFuaId) : new FormatoFua();
		model.addAttribute("formatoFua", formatoFua);
		model.addAttribute("formatos", formatoFuaService.getAllFormatoFuas());
		return FORM_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onPost(HttpSession httpSession, @ModelAttribute("formatoFua") FormatoFua formatoFua, BindingResult errors,
	        @RequestParam(required = false, value = "action") String action) {
		
		MessageSourceService mss = Context.getMessageSourceService();
		
		if (errors.hasErrors()) {
			return FORM_VIEW;
		}
		
		if (!Context.isAuthenticated()) {
			errors.reject("fua.auth.required");
		} else if ("purge".equals(action)) {
			try {
				formatoFuaService.purgeFormatoFua(formatoFua);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "formatofua.delete.success");
			}
			catch (Exception ex) {
				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "formatofua.delete.failure");
				log.error("Error al eliminar Formato FUA", ex);
			}
		} else {
			formatoFuaService.saveFormatoFua(formatoFua);
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "formatofua.saved");
		}
		return "redirect:/module/fua/formatofua.form";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<FormatoFua> getAllFormatoFuas() {
		log.info("Llamada a /module/fua/formatofua.form/list");
		return formatoFuaService.getAllFormatoFuas();
	}
	
	@ModelAttribute("users")
	protected List<User> getUsers() {
		return userService.getAllUsers();
	}
}
