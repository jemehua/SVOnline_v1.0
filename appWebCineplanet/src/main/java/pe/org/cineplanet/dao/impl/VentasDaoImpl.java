package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.VentasDao;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class VentasDaoImpl implements VentasDao {
	@PersistenceContext
	private EntityManager em;

	public Venta find(Long id) throws Exception {
		return em.find(Venta.class, id);
	}

	@Transactional
	public Venta save(Venta obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Venta edit(Venta obj) throws Exception {
		return em.merge(obj);
	}

	public List<Venta> getListaVentas() throws Exception {

		TypedQuery<Venta> tq = em.createNamedQuery("Venta.getAll", Venta.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();

	}
	
	public Long getMaxId() throws Exception {
		return (Long) em.createQuery("select max(v.idVenta) from Venta v")
				.getSingleResult();
	}

}
