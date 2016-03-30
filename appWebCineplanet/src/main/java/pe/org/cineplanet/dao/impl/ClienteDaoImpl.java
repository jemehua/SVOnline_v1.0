package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	public Cliente find(Long id) throws Exception {
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

	@SuppressWarnings("unchecked")
	public List<Cliente> getListaCliente() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select c.* from cliente c ");
		sb.append("where c.estado  = :estado ");
		sb.append("ORDER BY c.razonsocial ASC ");
		
		Query q = em.createNativeQuery(sb.toString(), Cliente.class);
		q.setParameter("estado", Constantes.ACTIVO);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getListaClienteByAgencia(String codigoAgencia)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select c.* from cliente c  ");
		sb.append("inner join agencia a on (a.idagencia = c.idagencia)  ");
		sb.append("where c.estado  = :estado ");
		sb.append("and a.idagencia = :codigoAgencia ");
		sb.append("ORDER BY c.razonsocial ASC ");
		
		Query q = em.createNativeQuery(sb.toString(), Cliente.class);
		q.setParameter("estado", Constantes.ACTIVO);
		q.setParameter("codigoAgencia", codigoAgencia);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getListaClienteByEmpresa(String codigoEmpresa)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select c.* from cliente c  ");
		sb.append("inner join agencia a on (a.idagencia = c.idagencia)  ");
		sb.append("inner join agencia e on (e.idagencia = a.idagenciapadre)  ");
		sb.append("where c.estado  = :estado ");
		sb.append("and e.idagencia = :codigoEmpresa ");
		
		sb.append("union all  ");
		
		sb.append("select c.* from cliente c  ");
		sb.append("inner join agencia a on (a.idagencia = c.idagencia)  ");
		sb.append("where c.estado  = :estado ");
		sb.append("and a.idagencia = :codigoEmpresa ");
		
		Query q = em.createNativeQuery(sb.toString(), Cliente.class);
		q.setParameter("estado", Constantes.ACTIVO);
		q.setParameter("codigoEmpresa", codigoEmpresa);
		return q.getResultList();
	}
	
	public Long getMaxId() throws Exception {
		return (Long) em.createQuery("select max(c.idCliente) from Cliente c")
				.getSingleResult();
	}

}
