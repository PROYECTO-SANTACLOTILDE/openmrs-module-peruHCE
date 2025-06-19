package org.openmrs.module.fua.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FuaVersion;
import org.openmrs.module.fua.api.FuaVersionService;
import org.openmrs.module.fua.api.dao.FuaVersionDao;

import java.util.List;
import java.util.UUID;

public class FuaVersionServiceImpl extends BaseOpenmrsService implements FuaVersionService {
	
	private FuaVersionDao dao;
	
	public void setDao(FuaVersionDao dao) {
		this.dao = dao;
	}
	
	@Override
	public FuaVersion saveFuaVersion(FuaVersion fuaVersion, String descripcion) throws APIException {
		fuaVersion.setDescripcion(descripcion);
		System.out.println("uuid del FUAVERSION: " + fuaVersion.getUuid());
		return dao.saveFuaVersion(fuaVersion);
	}
}
