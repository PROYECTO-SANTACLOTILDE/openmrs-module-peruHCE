package org.openmrs.module.department.api.dao;

import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("department.DepartmentDao")
public class DepartmentDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartments() {
		return getSession().createCriteria(Department.class).list();
	}
	
	public Department getDepartment(Integer departmentId) {
		return (Department) getSession().get(Department.class, departmentId);
	}
	
	public Department saveDepartment(Department department) {
		getSession().saveOrUpdate(department);
		return department;
	}
	
	public void purgeDepartment(Department department) {
		getSession().delete(department);
	}
}
