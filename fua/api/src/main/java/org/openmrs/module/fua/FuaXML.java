package org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fuaxml")
public class FuaXML extends BaseOpenmrsObject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fuaxml_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid;
	
	@Column(name = "fua_uuid", nullable = false, length = 38)
	private String fuaUuid;
	
	@Lob
	@Column(name = "archivo")
	private String archivo;
	
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
	
	public String getFuaUuid() {
		return fuaUuid;
	}
	
	public void setFuaUuid(String fuaUuid) {
		this.fuaUuid = fuaUuid;
	}
	
	public String getArchivo() {
		return archivo;
	}
	
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
}
