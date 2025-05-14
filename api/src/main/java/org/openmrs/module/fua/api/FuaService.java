package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.Fua;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	List<Fua> getAllFuas() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	Fua getFua(Integer fuaId) throws APIException;
	
	@Authorized()
	@Transactional
	Fua saveFua(Fua fua) throws APIException;
	
	@Authorized()
	@Transactional
	void purgeFua(Fua fua) throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	Fua getFuaByUuid(String uuid) throws APIException;
	
}
