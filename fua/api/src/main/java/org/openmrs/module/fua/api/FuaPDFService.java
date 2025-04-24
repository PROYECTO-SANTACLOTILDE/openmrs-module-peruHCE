package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaPDF;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaPDFService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	List<FuaPDF> getAllFuaPDFs() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	FuaPDF getFuaPDF(Integer fuaPDFId) throws APIException;
	
	@Authorized()
	@Transactional
	FuaPDF saveFuaPDF(FuaPDF fuaPDF) throws APIException;
	
	@Authorized()
	@Transactional
	void purgeFuaPDF(FuaPDF fuaPDF) throws APIException;
}
