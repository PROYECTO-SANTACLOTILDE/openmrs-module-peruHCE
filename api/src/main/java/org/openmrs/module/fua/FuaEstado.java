package org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fua_estado")
public class FuaEstado extends BaseOpenmrsData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fua_estado_id")
	private Integer id;
	
	@Column(name = "uuid", nullable = false, unique = true, length = 38)
	private String uuid;
	
	@Column(name = "nombre", nullable = false, length = 255)
	private String nombre;
	
	// Getters y Setters
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
