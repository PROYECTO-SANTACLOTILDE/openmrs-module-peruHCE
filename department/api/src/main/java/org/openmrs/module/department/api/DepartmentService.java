package org.openmrs.module.department.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.department.Department;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	List<Department> getAllDepartments() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	List<Department> getAllDepartmentsPrueba() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	Department getDepartment(Integer departmentId) throws APIException;
	
	@Authorized()
	@Transactional
	Department saveDepartment(Department department) throws APIException;
	
	@Authorized()
	@Transactional
	void purgeDepartment(Department department) throws APIException;
}
