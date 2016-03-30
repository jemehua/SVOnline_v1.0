package pe.org.cineplanet.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class DetalleEntradaDaoImpl implements DetalleEntradaDao {
	@PersistenceContext
	private EntityManager em;

	public DetalleEntrada find(DetalleEntradaPK id) throws Exception {
		return em.find(DetalleEntrada.class, id);
	}

	@Transactional
	public DetalleEntrada save(DetalleEntrada obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public DetalleEntrada edit(DetalleEntrada obj) throws Exception {
		return em.merge(obj);
	}

	public List<DetalleEntrada> getListaDetalleEntrada(Long idEntrada)
			throws Exception {
		TypedQuery<DetalleEntrada> tq = em.createNamedQuery(
				"DetalleEntrada.getAllByIdEntrada", DetalleEntrada.class);
		tq.setParameter("idEntrada", idEntrada);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();
	}
	
	public List<DetalleEntrada> getListaByTipoEntrada(Long idTipoEntrada)
			throws Exception {
		TypedQuery<DetalleEntrada> tq = em.createNamedQuery(
				"DetalleEntrada.getAllByIdTipoEntrada", DetalleEntrada.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		tq.setParameter("idTipoEntrada", idTipoEntrada);
		return tq.getResultList();
	}
	
	public DetalleEntrada getByIdCodigo(String idCodigo)
			throws Exception {
		TypedQuery<DetalleEntrada> tq = em.createNamedQuery(
				"DetalleEntrada.getByIdCodigo", DetalleEntrada.class);
		tq.setParameter("idCodigo", idCodigo);
		return tq.getSingleResult();
	}

	public Integer getCountCodigoEntradasByEstado(Long idEntrada, String estado) throws Exception {
		String sql = "select count(*) from detalleentrada where identrada = :idEntrada and estado = :estado ";
		Query q = em.createNativeQuery(sql);
		q.setParameter("idEntrada", idEntrada);
		q.setParameter("estado", estado);
		return ((BigInteger) q.getSingleResult()).intValue();
	}

}
