package org.openmrs.module.fua.dto;

import org.openmrs.Visit;

import java.util.Date;

public class VisitDto {
	
	private String uuid;
	
	private Date startDatetime;
	
	private Date stopDatetime;
	
	private String patientUuid;
	
	public VisitDto(Visit visit) {
		this.uuid = visit.getUuid();
		this.startDatetime = visit.getStartDatetime();
		this.stopDatetime = visit.getStopDatetime();
		this.patientUuid = visit.getPatient() != null ? visit.getPatient().getUuid() : null;
	}
	
	// Getters y setters
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Date getStartDatetime() {
		return startDatetime;
	}
	
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}
	
	public Date getStopDatetime() {
		return stopDatetime;
	}
	
	public void setStopDatetime(Date stopDatetime) {
		this.stopDatetime = stopDatetime;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
}
