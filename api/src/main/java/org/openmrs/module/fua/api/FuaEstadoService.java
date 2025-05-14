package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaEstado;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaEstadoService extends OpenmrsService {
	
	@Transactional(readOnly = true)
	List<FuaEstado> getAllEstados() throws APIException;
	
	@Transactional(readOnly = true)
	FuaEstado getEstado(Integer id) throws APIException;
	
	@Transactional
	FuaEstado saveEstado(FuaEstado estado) throws APIException;
	
	@Transactional
	void purgeEstado(FuaEstado estado) throws APIException;
}
