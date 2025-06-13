package org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "fua")
public class Fua extends BaseOpenmrsData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fua_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid;
	
	@Column(name = "visit_uuid", nullable = false, length = 38)
	private String visitUuid;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Lob
	@Column(name = "payload")
	private String payload;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fua_estado_id", nullable = false)
	private FuaEstado fuaEstado;
	
	@Column(name = "fecha_creacion", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	@Column(name = "fecha_actualizacion", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	
	// --- Getters y Setters ---
	
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
	
	public String getVisitUuid() {
		return visitUuid;
	}
	
	public void setVisitUuid(String visitUuid) {
		this.visitUuid = visitUuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public FuaEstado getFuaEstado() {
		return fuaEstado;
	}
	
	public void setFuaEstado(FuaEstado fuaEstado) {
		this.fuaEstado = fuaEstado;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) { ///CUIDADO CON ESTO PEDRO
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	
}
