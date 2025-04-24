package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaXML;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaXMLService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	List<FuaXML> getAllFuaXMLs() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	FuaXML getFuaXML(Integer fuaXMLId) throws APIException;
	
	@Authorized()
	@Transactional
	FuaXML saveFuaXML(FuaXML fuaXML) throws APIException;
	
	@Authorized()
	@Transactional
	void purgeFuaXML(FuaXML fuaXML) throws APIException;
}
