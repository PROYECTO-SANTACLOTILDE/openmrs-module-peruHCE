package org.openmrs.module.fua.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FuaEstado;
import org.openmrs.module.fua.api.FuaEstadoService;
import org.openmrs.module.fua.api.dao.FuaEstadoDao;

import java.util.List;
import java.util.UUID;

public class FuaEstadoServiceImpl extends BaseOpenmrsService implements FuaEstadoService {
	
	private FuaEstadoDao dao;
	
	public void setDao(FuaEstadoDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<FuaEstado> getAllEstados() throws APIException {
		return dao.getAllEstados();
	}
	
	@Override
	public FuaEstado getEstado(Integer id) throws APIException {
		return dao.getEstado(id);
	}
	
	@Override
	public FuaEstado saveEstado(FuaEstado estado) throws APIException {
		if (StringUtils.isBlank(estado.getUuid())) {
			estado.setUuid(UUID.randomUUID().toString());
		}
		return dao.saveEstado(estado);
	}
	
	@Override
	public void purgeEstado(FuaEstado estado) throws APIException {
		dao.purgeEstado(estado);
	}
	
	@Override
	public FuaEstado getByUuid(String uuid) {
		return dao.getByUuid(uuid);
	}
}
