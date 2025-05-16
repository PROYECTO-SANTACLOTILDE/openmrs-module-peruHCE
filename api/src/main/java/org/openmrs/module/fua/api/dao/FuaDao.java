package org.openmrs.module.fua.api.dao;

import org.openmrs.module.fua.Fua;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fua.FuaDao")
public class FuaDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<Fua> getAllFuas() {
		return getSession().createCriteria(Fua.class).list();
	}
	
	public Fua getFua(Integer fuaId) {
		return (Fua) getSession().get(Fua.class, fuaId);
	}
	
	public Fua saveFua(Fua fua) {
		getSession().saveOrUpdate(fua);
		return fua;
	}
	
	public void purgeFua(Fua fua) {
		getSession().delete(fua);
	}
	
	public void updateEstado(Integer fuaId, Integer nuevoEstadoId) {
		Fua fua = getFua(fuaId);
		if (fua != null) {
			fua.setFuaEstadoId(nuevoEstadoId);
			saveFua(fua);
		}
	}
	
}
