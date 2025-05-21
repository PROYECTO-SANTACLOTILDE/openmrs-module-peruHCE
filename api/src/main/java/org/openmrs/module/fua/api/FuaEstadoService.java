package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaEstado;
import org.openmrs.module.fua.FuaConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaEstadoService extends OpenmrsService {
	
	@Authorized(FuaConfig.READ_FUA_PRIVILEGE)
	@Transactional(readOnly = true)
	List<FuaEstado> getAllEstados() throws APIException;
	
	@Authorized(FuaConfig.READ_FUA_PRIVILEGE)
	@Transactional(readOnly = true)
	FuaEstado getEstado(Integer id) throws APIException;
	
	@Authorized(FuaConfig.READ_FUA_PRIVILEGE)
	@Transactional(readOnly = true)
	FuaEstado getByUuid(String uuid) throws APIException;
	
	@Authorized(FuaConfig.MANAGE_FUA_PRIVILEGE)
	@Transactional
	FuaEstado saveEstado(FuaEstado estado) throws APIException;
	
	@Authorized(FuaConfig.DELETE_FUA_PRIVILEGE)
	@Transactional
	void purgeEstado(FuaEstado estado) throws APIException;
}
