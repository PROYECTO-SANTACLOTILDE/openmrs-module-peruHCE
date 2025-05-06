package org.openmrs.module.fua.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
//import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.api.FuaService;
import org.openmrs.module.fua.api.dao.FuaDao;

import org.apache.commons.lang3.StringUtils; // Asegúrate de importar esto
import java.util.List;
import java.util.UUID;

public class FuaServiceImpl extends BaseOpenmrsService implements FuaService {
	
	private FuaDao dao;
	
	public void setDao(FuaDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Fua> getAllFuas() throws APIException {
		return dao.getAllFuas();
	}
	
	@Override
	public Fua getFua(Integer fuaId) throws APIException {
		return dao.getFua(fuaId);
	}
	
	@Override
	public Fua saveFua(Fua fua) throws APIException {
		if (StringUtils.isBlank(fua.getUuid())) {
			fua.setUuid(UUID.randomUUID().toString());
		}
		return dao.saveFua(fua);
	}
	
	@Override
	public void purgeFua(Fua fua) throws APIException {
		dao.purgeFua(fua);
	}
}
