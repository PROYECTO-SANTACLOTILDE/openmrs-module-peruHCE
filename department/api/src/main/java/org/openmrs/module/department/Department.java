package org.openmrs.module.department;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;
import java.io.Serializable;
import org.openmrs.BaseOpenmrsObject;

@Entity
@Table(name = "department_department")
public class Department extends BaseOpenmrsObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "department_id")
	private Integer departmentId;
	
	@Column(name = "name", length = 255, unique = true)
	private String name;
	
	@Column(name = "description", length = 255)
	private String description;
	
	// Getters y Setters...
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	@Override
	public Integer getId() {
		return getDepartmentId();
	}
	
	@Override
	public void setId(Integer id) {
		setDepartmentId(id);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
