package org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "fua_estado_version")
public class FuaEstadoVersion extends BaseOpenmrsObject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fua_estado_version_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid;
	
	@Column(name = "fua_estado_id", nullable = false)
	private Integer fuaEstadoId;
	
	@Column(name = "fua_estado_uuid", nullable = false, length = 38)
	private String fuaEstadoUuid;
	
	@Column(name = "nombre", nullable = false, length = 255)
	private String nombre;
	
	@Column(name = "descripcion", length = 255)
	private String descripcion;
	
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	@Column(name = "fecha_actualizacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	
	@Column(name = "version", nullable = false)
	private Integer version;
	
	@Column(name = "activo", nullable = false)
	private Boolean activo;
	
	// Constructor vacío
	public FuaEstadoVersion() {
	}
	
	// Constructor que clona un FuaEstado
	public FuaEstadoVersion(FuaEstado estado) {
		this.uuid = UUID.randomUUID().toString();
		this.fuaEstadoId = estado.getId();
		this.fuaEstadoUuid = estado.getUuid();
		this.nombre = estado.getNombre();
		this.descripcion = null; // Asignar si se requiere
		this.fechaCreacion = estado.getFechaCreacion();
		this.fechaActualizacion = estado.getFechaActualizacion();
		this.version = estado.getVersion();
		this.activo = estado.getActivo();
	}
	
	// Getters y Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getUuid() {
		return uuid;
	}
	
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getFuaEstadoId() {
		return fuaEstadoId;
	}
	
	public void setFuaEstadoId(Integer fuaEstadoId) {
		this.fuaEstadoId = fuaEstadoId;
	}
	
	public String getFuaEstadoUuid() {
		return fuaEstadoUuid;
	}
	
	public void setFuaEstadoUuid(String fuaEstadoUuid) {
		this.fuaEstadoUuid = fuaEstadoUuid;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
