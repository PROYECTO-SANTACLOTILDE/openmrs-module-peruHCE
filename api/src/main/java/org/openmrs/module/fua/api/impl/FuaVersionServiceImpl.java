package org.openmrs.module.fua.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FuaVersion;
import org.openmrs.module.fua.Fua;
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
	public FuaVersion saveFuaVersion(Fua fua, String descripcion) throws APIException {
		
		FuaVersion fuaVersion = new FuaVersion(fua);
		fuaVersion.setDescripcion(descripcion);
		
		fua.setVersion(fua.getVersion() + 1);
		
		System.out.println("		EL ID DEL FUA NO ES NULL, ASI QUE SE GENERA UNA COPIA DEL FUA");
		System.out.println("			UUID FUA: " + fua.getUuid());
		System.out.println("uuid del FUAVERSION: " + fuaVersion.getUuid());
		
		return dao.saveFuaVersion(fuaVersion);
	}
}
