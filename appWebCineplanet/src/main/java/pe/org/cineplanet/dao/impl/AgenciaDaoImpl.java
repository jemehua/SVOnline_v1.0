package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.AgenciaDao;
import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class AgenciaDaoImpl implements AgenciaDao {
	@PersistenceContext
	private EntityManager em;

	public Agencia find(String id) throws Exception {
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
	
	@SuppressWarnings("unchecked")
	public List<Agencia> getListaAgencia(String codigoEmpresa) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.* FROM agencia a WHERE a.estado =:estado AND a.idagenciapadre IS NOT NULL ");
		if(!codigoEmpresa.equals(Constantes.VACIO))
			sb.append("AND a.idagenciapadre = :codigoEmpresa ");
		sb.append("ORDER BY a.idagencia ASC ");
		Query q = em.createNativeQuery(sb.toString(), Agencia.class);
		if(!codigoEmpresa.equals(Constantes.VACIO))
			q.setParameter("codigoEmpresa", codigoEmpresa);
		q.setParameter("estado", "A");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Agencia> getListaEmpresa() throws Exception {
		String sql = "SELECT a.* FROM agencia a WHERE a.estado =:estado AND a.idagenciapadre IS NULL ORDER BY a.idagencia ASC";
		Query q = em.createNativeQuery(sql, Agencia.class);
		q.setParameter("estado", "A");
		return q.getResultList();
	}

	public Integer getNextId(String tipo) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select max(SUBSTRING(idagencia, 2, char_length(idagencia))) from agencia where idagenciapadre ");
		if(tipo.equals(Constantes.TIPO_EMPRESA))
			sb.append("is null ");
		if(tipo.equals(Constantes.TIPO_AGENCIA))
			sb.append("is not null ");
		Query q = em.createNativeQuery(sb.toString());
		String result = (String) q.getSingleResult();
		
		if(result == null || result.equals(Constantes.VACIO))
			return 1;
		else
			return Integer.parseInt(result) + 1;
	}

}
