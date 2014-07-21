package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.ClienteDao;
import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class ClienteDaoImpl implements ClienteDao {
	@PersistenceContext
	private EntityManager em;

	public Cliente find(String id) throws Exception {
		return em.find(Cliente.class, id);
	}

	@Transactional
	public Cliente save(Cliente obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Cliente edit(Cliente obj) throws Exception {
		return em.merge(obj);
	}

	public List<Cliente> getListaCliente() throws Exception {

		TypedQuery<Cliente> tq = em.createNamedQuery("Cliente.getAll", Cliente.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();

	}

}
