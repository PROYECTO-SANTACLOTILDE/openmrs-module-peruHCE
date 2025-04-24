package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.FuaXML;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FuaXMLDao")
public class FuaXMLDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<FuaXML> getAllFuaXMLs() {
		return getSession().createCriteria(FuaXML.class).list();
	}
	
	public FuaXML getFuaXML(Integer fuaXMLId) {
		return (FuaXML) getSession().get(FuaXML.class, fuaXMLId);
	}
	
	public FuaXML saveFuaXML(FuaXML fuaXML) {
		getSession().saveOrUpdate(fuaXML);
		return fuaXML;
	}
	
	public void purgeFuaXML(FuaXML fuaXML) {
		getSession().delete(fuaXML);
	}
}
