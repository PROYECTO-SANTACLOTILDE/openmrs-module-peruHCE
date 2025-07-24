package org.openmrs.module.fua.api.dao;

import org.hibernate.Query;
import org.openmrs.module.fua.FuaVersion;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FuaVersionDao")
public class FuaVersionDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public FuaVersion saveFuaVersion(FuaVersion fuaVersion) {
		getSession().saveOrUpdate(fuaVersion);
		return fuaVersion;
	}
}
