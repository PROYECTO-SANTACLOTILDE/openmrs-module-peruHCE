package org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fua")
public class Fua extends BaseOpenmrsObject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fua_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid;
	
	@Column(name = "visit_uuid", nullable = false, length = 38)
	private String visitUuid;
	
	@Column(name = "formatofua_uuid", nullable = false, length = 38)
	private String formatoFuaUuid;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Lob
	@Column(name = "payload")
	private String payload;
	
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
	
	public String getVisitUuid() {
		return visitUuid;
	}
	
	public void setVisitUuid(String visitUuid) {
		this.visitUuid = visitUuid;
	}
	
	public String getFormatoFuaUuid() {
		return formatoFuaUuid;
	}
	
	public void setFormatoFuaUuid(String formatoFuaUuid) {
		this.formatoFuaUuid = formatoFuaUuid;
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
}
