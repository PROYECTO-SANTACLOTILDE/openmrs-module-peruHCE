package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FormatoFua;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FormatoFuaService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	List<FormatoFua> getAllFormatoFuas() throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	FormatoFua getFormatoFua(Integer formatoFuaId) throws APIException;
	
	@Authorized()
	@Transactional
	FormatoFua saveFormatoFua(FormatoFua formatoFua) throws APIException;
	
	@Authorized()
	@Transactional
	void purgeFormatoFua(FormatoFua formatoFua) throws APIException;
}
