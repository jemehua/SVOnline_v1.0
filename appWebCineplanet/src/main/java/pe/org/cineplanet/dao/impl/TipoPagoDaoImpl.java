package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoPagoDao;
import pe.org.cineplanet.model.jpa.TipoPago;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class TipoPagoDaoImpl implements TipoPagoDao {
	@PersistenceContext
	private EntityManager em;

	public TipoPago find(Integer id) throws Exception {
		return em.find(TipoPago.class, id);
	}

	@Transactional
	public TipoPago save(TipoPago obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public TipoPago edit(TipoPago obj) throws Exception {
		return em.merge(obj);
	}

	public List<TipoPago> getListaTipoPago() throws Exception {
		TypedQuery<TipoPago> tq = em.createNamedQuery("TipoPago.getAll", TipoPago.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();
	}

}
