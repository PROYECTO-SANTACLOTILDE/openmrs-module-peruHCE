package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaEstado;
import org.openmrs.module.fua.FuaEstadoVersion;
import org.openmrs.module.fua.FuaConfig;
import org.springframework.transaction.annotation.Transactional;

public interface FuaEstadoVersionService extends OpenmrsService {
	
	@Authorized(FuaConfig.MANAGE_FUA_PRIVILEGE)
	@Transactional
	FuaEstadoVersion saveFuaEstadoVersion(FuaEstado fuaEstado, String descripcion) throws APIException;
}
