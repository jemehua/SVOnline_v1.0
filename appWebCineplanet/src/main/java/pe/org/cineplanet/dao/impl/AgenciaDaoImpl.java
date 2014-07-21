package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.AgenciaDao;
import pe.org.cineplanet.model.jpa.Agencia;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class AgenciaDaoImpl implements AgenciaDao {
	@PersistenceContext
	private EntityManager em;

	public Agencia find(Long id) throws Exception {
		return em.find(Agencia.class, id);
	}

	@Transactional
	public Agencia save(Agencia obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Agencia edit(Agencia obj) throws Exception {
		return em.merge(obj);
	}

	public List<Agencia> getListaAgencia() throws Exception {

		TypedQuery<Agencia> tq = em.createNamedQuery("Agencia.getAll", Agencia.class);
		tq.setParameter("estado", "A");
		return tq.getResultList();

	}

}
