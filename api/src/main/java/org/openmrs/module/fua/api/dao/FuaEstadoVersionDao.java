package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.FuaEstadoVersion;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("fua.FuaEstadoVersionDao")
public class FuaEstadoVersionDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public FuaEstadoVersion saveFuaEstadoVersion(FuaEstadoVersion version) {
		getSession().saveOrUpdate(version);
		return version;
	}
}
