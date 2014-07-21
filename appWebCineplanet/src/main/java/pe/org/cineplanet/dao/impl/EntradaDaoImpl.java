package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.EntradaDao;
import pe.org.cineplanet.model.jpa.Entrada;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class EntradaDaoImpl implements EntradaDao {
	@PersistenceContext
	private EntityManager em;

	public Entrada find(Long id) throws Exception {
		return em.find(Entrada.class, id);
	}

	@Transactional
	public Entrada save(Entrada obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Entrada edit(Entrada obj) throws Exception {
		return em.merge(obj);
	}

	public List<Entrada> getListaEntrada() throws Exception {
		TypedQuery<Entrada> tq = em.createNamedQuery("Entrada.getAll",
				Entrada.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();
	}

	public Long getMaxId() throws Exception {
		return (Long) em.createQuery("select max(e.idEntrada) from Entrada e")
				.getSingleResult();
	}

}
