package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.FuaEstado;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FuaEstadoDao")
public class FuaEstadoDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<FuaEstado> getAllEstados() {
		return getSession().createCriteria(FuaEstado.class).list();
	}
	
	public FuaEstado getEstado(Integer id) {
		return (FuaEstado) getSession().get(FuaEstado.class, id);
	}
	
	public FuaEstado saveEstado(FuaEstado estado) {
		getSession().saveOrUpdate(estado);
		return estado;
	}
	
	public void purgeEstado(FuaEstado estado) {
		getSession().delete(estado);
	}
}
