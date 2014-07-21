package pe.org.cineplanet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoDocumentoDao;
import pe.org.cineplanet.model.jpa.TipoDocumento;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class TipoDocumentoDaoImpl implements TipoDocumentoDao {
	@PersistenceContext
	private EntityManager em;

	public TipoDocumento find(Long id) throws Exception {
		return em.find(TipoDocumento.class, id);
	}

	@Transactional
	public TipoDocumento save(TipoDocumento obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public TipoDocumento edit(TipoDocumento obj) throws Exception {
		return em.merge(obj);
	}

	public List<TipoDocumento> getListaTipoDocumento() throws Exception {
		TypedQuery<TipoDocumento> tq = em.createNamedQuery("TipoDocumento.getAll", TipoDocumento.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();
	}

}
