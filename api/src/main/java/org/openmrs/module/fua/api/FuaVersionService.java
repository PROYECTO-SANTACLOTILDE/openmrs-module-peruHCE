package org.openmrs.module.fua.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fua.FuaVersion;
import org.openmrs.module.fua.FuaConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FuaVersionService extends OpenmrsService {
	
	@Authorized(FuaConfig.MANAGE_FUA_PRIVILEGE)
	@Transactional
	FuaVersion saveFuaVersion(FuaVersion fuaVersion, String descripcion) throws APIException;
}
