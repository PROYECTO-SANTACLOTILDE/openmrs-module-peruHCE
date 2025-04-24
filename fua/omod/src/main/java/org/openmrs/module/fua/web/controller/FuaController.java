package org.openmrs.module.fua.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.api.FuaService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/module/fua/fua.form")
public class FuaController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	FuaService fuaService;
	
	private final String FORM_VIEW = "/module/fua/pages/addFua";
	
	@RequestMapping(method = RequestMethod.GET)
	public String onGet(ModelMap model, @RequestParam(value = "fuaId", required = false) Integer fuaId) {
		Fua fua = (fuaId != null) ? fuaService.getFua(fuaId) : new Fua();
		model.addAttribute("fua", fua);
		model.addAttribute("fuas", fuaService.getAllFuas());
		return FORM_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onPost(HttpSession session, @ModelAttribute("fua") Fua fua, BindingResult errors,
	        @RequestParam(required = false, value = "action") String action) {
		
		MessageSourceService mss = Context.getMessageSourceService();
		
		if (errors.hasErrors()) {
			return FORM_VIEW;
		}
		
		if (!Context.isAuthenticated()) {
			errors.reject("fua.auth.required");
		} else if (mss.getMessage("fua.purgeFua").equals(action)) {
			try {
				fuaService.purgeFua(fua);
				session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "fua.delete.success");
			}
			catch (Exception ex) {
				session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "fua.delete.failure");
				log.error("Failed to delete fua", ex);
			}
		} else {
			fuaService.saveFua(fua);
			session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "fua.saved");
		}
		return "redirect:/module/fua/fua.form";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Fua> getAllFuasJson() {
		log.info("Llamada al endpoint /list");
		return fuaService.getAllFuas();
	}
}
