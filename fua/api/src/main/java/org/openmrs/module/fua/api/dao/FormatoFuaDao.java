package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.FormatoFua;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FormatoFuaDao")
public class FormatoFuaDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<FormatoFua> getAllFormatoFuas() {
		return getSession().createCriteria(FormatoFua.class).list();
	}
	
	public FormatoFua getFormatoFua(Integer formatoFuaId) {
		return (FormatoFua) getSession().get(FormatoFua.class, formatoFuaId);
	}
	
	public FormatoFua saveFormatoFua(FormatoFua formatoFua) {
		getSession().saveOrUpdate(formatoFua);
		return formatoFua;
	}
	
	public void purgeFormatoFua(FormatoFua formatoFua) {
		getSession().delete(formatoFua);
	}
}
