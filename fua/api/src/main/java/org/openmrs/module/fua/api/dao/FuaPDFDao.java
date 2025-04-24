package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.FuaPDF;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FuaPDFDao")
public class FuaPDFDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<FuaPDF> getAllFuaPDFs() {
		return getSession().createCriteria(FuaPDF.class).list();
	}
	
	public FuaPDF getFuaPDF(Integer fuaPDFId) {
		return (FuaPDF) getSession().get(FuaPDF.class, fuaPDFId);
	}
	
	public FuaPDF saveFuaPDF(FuaPDF fuaPDF) {
		getSession().saveOrUpdate(fuaPDF);
		return fuaPDF;
	}
	
	public void purgeFuaPDF(FuaPDF fuaPDF) {
		getSession().delete(fuaPDF);
	}
}
