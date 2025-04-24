package org.openmrs.module.fua.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FormatoFua;
import org.openmrs.module.fua.api.FormatoFuaService;
import org.openmrs.module.fua.api.dao.FormatoFuaDao;

import java.util.List;

public class FormatoFuaServiceImpl extends BaseOpenmrsService implements FormatoFuaService {
	
	private FormatoFuaDao dao;
	
	public void setDao(FormatoFuaDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<FormatoFua> getAllFormatoFuas() throws APIException {
		return dao.getAllFormatoFuas();
	}
	
	@Override
	public FormatoFua getFormatoFua(Integer formatoFuaId) throws APIException {
		return dao.getFormatoFua(formatoFuaId);
	}
	
	@Override
	public FormatoFua saveFormatoFua(FormatoFua formatoFua) throws APIException {
		return dao.saveFormatoFua(formatoFua);
	}
	
	@Override
	public void purgeFormatoFua(FormatoFua formatoFua) throws APIException {
		dao.purgeFormatoFua(formatoFua);
	}
}
