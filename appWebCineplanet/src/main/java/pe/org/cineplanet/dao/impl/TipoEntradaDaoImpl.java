package pe.org.cineplanet.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoEntradaDao;
import pe.org.cineplanet.model.jpa.TipoEntrada;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class TipoEntradaDaoImpl implements TipoEntradaDao {
	@PersistenceContext
	private EntityManager em;

	public TipoEntrada find(Long id) throws Exception {
		return em.find(TipoEntrada.class, id);
	}

	@Transactional
	public TipoEntrada save(TipoEntrada obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public TipoEntrada edit(TipoEntrada obj) throws Exception {
		return em.merge(obj);
	}

	public List<TipoEntrada> getListaTipoVale() throws Exception {
		TypedQuery<TipoEntrada> tq = em.createNamedQuery("TipoEntrada.getAll", TipoEntrada.class);
		tq.setParameter("estado", "A");
		return tq.getResultList();
	}

	public Long getMaxId() throws Exception {
		return (Long) em.createQuery("select max(e.idTipoEntrada) from TipoEntrada e")
				.getSingleResult();
	}

	public boolean existPrecioByTipoVale(Integer tipoVale, BigDecimal precio) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from tipoentrada where tipovale = :tipoVale and precio = :precio ");
		
		Query q = em.createNativeQuery(sb.toString());
		q.setParameter("tipoVale", tipoVale);
		q.setParameter("precio", precio);
		List<Object[]> list = q.getResultList();
		if(list.size() > 0)
			return true;
		else
			return false;
	}

}
