package main.java.org.openmrs.module.fua;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "formatofua")
public class FormatoFua extends BaseOpenmrsObject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "formatofua_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid;
	
	@Column(name = "campos", nullable = false, length = 1024)
	private String campos;
	
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
	
	public String getCampos() {
		return campos;
	}
	
	public void setCampos(String campos) {
		this.campos = campos;
	}
}
