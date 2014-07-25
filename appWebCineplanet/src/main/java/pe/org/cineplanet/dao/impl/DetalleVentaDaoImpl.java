package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class DetalleVentaDaoImpl implements DetalleVentaDao {
	@PersistenceContext
	private EntityManager em;

	public DetalleVenta find(DetalleVentaPK id) throws Exception {
		return em.find(DetalleVenta.class, id);
	}

	@Transactional
	public DetalleVenta save(DetalleVenta obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public DetalleVenta edit(DetalleVenta obj) throws Exception {
		return em.merge(obj);
	}

	public List<DetalleVenta> getListaByIdVenta(Long idVenta) throws Exception {
		TypedQuery<DetalleVenta> tq = em.createNamedQuery(
				"DetalleVenta.getAllByIdVenta", DetalleVenta.class);
		tq.setParameter("idVenta", idVenta);
		return tq.getResultList();
	}

}
