package org.openmrs.module.fua.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FuaPDF;
import org.openmrs.module.fua.api.FuaPDFService;
import org.openmrs.module.fua.api.dao.FuaPDFDao;

import java.util.List;

public class FuaPDFServiceImpl extends BaseOpenmrsService implements FuaPDFService {
	
	private FuaPDFDao dao;
	
	public void setDao(FuaPDFDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<FuaPDF> getAllFuaPDFs() throws APIException {
		return dao.getAllFuaPDFs();
	}
	
	@Override
	public FuaPDF getFuaPDF(Integer fuaPDFId) throws APIException {
		return dao.getFuaPDF(fuaPDFId);
	}
	
	@Override
	public FuaPDF saveFuaPDF(FuaPDF fuaPDF) throws APIException {
		return dao.saveFuaPDF(fuaPDF);
	}
	
	@Override
	public void purgeFuaPDF(FuaPDF fuaPDF) throws APIException {
		dao.purgeFuaPDF(fuaPDF);
	}
}
