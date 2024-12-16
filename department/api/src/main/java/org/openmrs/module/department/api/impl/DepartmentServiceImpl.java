package org.openmrs.module.department.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.department.Department;
import org.openmrs.module.department.api.DepartmentService;
import org.openmrs.module.department.api.dao.DepartmentDao;

import java.util.List;

public class DepartmentServiceImpl extends BaseOpenmrsService implements DepartmentService {
	
	private DepartmentDao dao;
	
	public void setDao(DepartmentDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Department> getAllDepartments() throws APIException {
		return dao.getAllDepartments();
	}
	
	@Override
	public Department getDepartment(Integer departmentId) throws APIException {
		return dao.getDepartment(departmentId);
	}
	
	@Override
	public Department saveDepartment(Department department) throws APIException {
		return dao.saveDepartment(department);
	}
	
	@Override
	public void purgeDepartment(Department department) throws APIException {
		dao.purgeDepartment(department);
	}
}
