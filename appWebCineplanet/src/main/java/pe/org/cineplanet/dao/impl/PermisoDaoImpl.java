package pe.org.cineplanet.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.PermisoDao;
import pe.org.cineplanet.model.jpa.Permiso;

/**
 * 
 * @author Hever Pumallihua García
 */
@Repository
public class PermisoDaoImpl implements PermisoDao {
	@PersistenceContext
	private EntityManager em;

	public Permiso find(Long id) {
		return em.find(Permiso.class, id);
	}

	@Transactional
	public Permiso save(Permiso obj) {
		if (obj.getIdPermiso() == null) {
			em.persist(obj);
			return obj;
		} else {
			return em.merge(obj);
		}
	}

	public List<Permiso> getListaPermiso() {
		TypedQuery<Permiso> tq = em.createNamedQuery(
				"Permiso.getAllListaPermiso", Permiso.class);
		
		try {
			return tq.getResultList();
		} catch (Exception as) {
			as.printStackTrace();
			return null;
		}
	}
	
	public List<Permiso> getListaPermiso(Long idRol) {
		
		TypedQuery<Permiso> tq = em.createNamedQuery(
				"Permiso.getListaPermisoRol", Permiso.class);
		
		tq.setParameter("idRol", idRol);
		try {
			return tq.getResultList();
		} catch (Exception as) {
			as.printStackTrace();
			return null;
		}
	}
	
	public List<Permiso> getListaPermiso(Long idMenu, Long idRol) {
		
		TypedQuery<Permiso> tq = em.createNamedQuery(
				"Permiso.getListaPermisoMenuAndRol", Permiso.class);
		
		tq.setParameter("idMenu", idMenu);
		tq.setParameter("idRol", idRol);
		tq.setParameter("estado", true);
		try {
			return tq.getResultList();
		} catch (Exception as) {
			as.printStackTrace();
			return null;
		}
	}
	
}
