package org.openmrs.module.fua.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fua.FuaXML;
import org.openmrs.module.fua.api.FuaXMLService;
import org.openmrs.module.fua.api.dao.FuaXMLDao;

import java.util.List;

public class FuaXMLServiceImpl extends BaseOpenmrsService implements FuaXMLService {
	
	private FuaXMLDao dao;
	
	public void setDao(FuaXMLDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<FuaXML> getAllFuaXMLs() throws APIException {
		return dao.getAllFuaXMLs();
	}
	
	@Override
	public FuaXML getFuaXML(Integer fuaXMLId) throws APIException {
		return dao.getFuaXML(fuaXMLId);
	}
	
	@Override
	public FuaXML saveFuaXML(FuaXML fuaXML) throws APIException {
		return dao.saveFuaXML(fuaXML);
	}
	
	@Override
	public void purgeFuaXML(FuaXML fuaXML) throws APIException {
		dao.purgeFuaXML(fuaXML);
	}
}
