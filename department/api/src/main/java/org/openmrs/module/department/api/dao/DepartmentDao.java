package org.openmrs.module.department.api.dao;

import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
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
	
	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartmentsPrueba() {
		// Ejecutar la consulta y obtener los resultados como una lista de objetos
		List<Object[]> results = getSession().createQuery(
		    "SELECT d.uuid, d.departmentId, d.name, d.description FROM Department d").list();
		
		// Convertir los resultados en objetos Department manualmente
		List<Department> departments = new ArrayList<Department>();
		for (Object[] row : results) {
			Department department = new Department();
			department.setUuid((String) row[0]);
			department.setDepartmentId((Integer) row[1]);
			department.setName((String) row[2]);
			department.setDescription((String) row[3]);
			departments.add(department);
		}
		return departments;
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
