package org.openmrs.module.department.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;

import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.department.Department;
import org.openmrs.module.department.api.DepartmentService;

import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/module/department/department.form")
public class DepartmentController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentService departmentService;
	
	//	Form view name
	private final String FORM_VIEW = "/module/department/pages/addDepartment";
	
	
	//	Initially called after the getUsers method to get the landing form name.
	//	@return String form view name
	
	@RequestMapping(method = RequestMethod.GET)
	public String onGet(ModelMap model, @RequestParam(value = "departmentId", required = false) Integer departmentId) {
		// Cargar el objeto de departamento (para el formulario)
		Department department = (departmentId != null) ? departmentService.getDepartment(departmentId) : new Department();
		model.addAttribute("department", department);
		
		// Cargar la lista de todos los departamentos
		model.addAttribute("departments", departmentService.getAllDepartments());
		
		return FORM_VIEW;
	}
	
	
	//	Handles form submissions.
	
	@RequestMapping(method = RequestMethod.POST)
	public String onPost(HttpSession httpSession, @ModelAttribute("department") Department department, BindingResult errors,
	        @RequestParam(required = false, value = "action") String action) {
		
		MessageSourceService mss = Context.getMessageSourceService();
		
		if (errors.hasErrors()) {
			return FORM_VIEW;
		}
		
		if (!Context.isAuthenticated()) {
			errors.reject("department.auth.required");
		} else if (mss.getMessage("department.purgeDepartment").equals(action)) {
			try {
				departmentService.purgeDepartment(department);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "department.delete.success");
			}
			catch (Exception ex) {
				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "department.delete.failure");
				log.error("Failed to delete department", ex);
			}
		} else {
			departmentService.saveDepartment(department);
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "department.saved");
		}
		return "redirect:/module/department/department.form";
	}
	
	
	//	Returns the form backing object. This can be a string, a boolean, or a normal java pojo.
	
	@ModelAttribute("users")
	protected List<User> getUsers() {
		List<User> users = userService.getAllUsers();
		return users;
	}
	
}
