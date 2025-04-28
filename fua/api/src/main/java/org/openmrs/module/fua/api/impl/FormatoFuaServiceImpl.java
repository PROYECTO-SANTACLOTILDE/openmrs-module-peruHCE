package org.openmrs.module.fua.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FormatoFua;
import org.openmrs.module.fua.api.FormatoFuaService;
import org.openmrs.module.fua.api.dao.FormatoFuaDao;

import org.apache.commons.lang3.StringUtils; // Asegúrate de importar esto
import java.util.List;
import java.util.UUID;

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
		if (StringUtils.isBlank(formatoFua.getUuid())) {
			formatoFua.setUuid(UUID.randomUUID().toString());
		}
		return dao.saveFormatoFua(formatoFua);
	}
	
	@Override
	public void purgeFormatoFua(FormatoFua formatoFua) throws APIException {
		dao.purgeFormatoFua(formatoFua);
	}
}
