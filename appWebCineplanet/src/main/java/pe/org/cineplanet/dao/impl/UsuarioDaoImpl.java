package pe.org.cineplanet.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.UsuarioDao;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class UsuarioDaoImpl implements UsuarioDao {
	@PersistenceContext
	private EntityManager em;

	public Usuario find(Long id) {
		try {
			return em.find(Usuario.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public Usuario save(Usuario obj) {
		try {
			em.persist(obj);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public Usuario edit(Usuario obj) {
		try {
			return em.merge(obj);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> getListaUsuario() {

		TypedQuery<Usuario> tq = em.createNamedQuery(
				"Usuario.getAllListaUsuario", Usuario.class);

		try {
			return tq.getResultList();
		} catch (Exception as) {
			return null;
		}
	}

	public Usuario findByUsername(String username) {

		TypedQuery<Usuario> tq = em.createNamedQuery("Usuario.findByUsername",
				Usuario.class);

		tq.setParameter("username", username);
		tq.setParameter("estado", Constantes.ACTIVO);

		try {
			return tq.getSingleResult();
		} catch (Exception as) {
			return null;
		}
	}

	public Long getIdMax() {
		try {
			return (Long) em.createQuery(
					"select max(r.idUsuario) from Usuario r").getSingleResult() + 1L;
		} catch (Exception e) {
			e.printStackTrace();
			return 1L;
		}
	}

	public Usuario findByDniAndCampaniaAndRol(String dni, Long idRol,
			Long idCampania) {

		TypedQuery<Usuario> tq = em.createNamedQuery(
				"Usuario.findByDniAndCampaniaAndRol", Usuario.class);

		tq.setParameter("dni", dni);
		tq.setParameter("idRol", idRol);
		tq.setParameter("idCampania", idCampania);

		try {
			return tq.getSingleResult();
		} catch (Exception as) {
			return null;
		}
	}

	public List<Usuario> getListByUsername(String username) {

		TypedQuery<Usuario> tq = em.createNamedQuery("Usuario.findByUsername",
				Usuario.class);

		tq.setParameter("username", username);
		tq.setParameter("estado", Constantes.ACTIVO);

		try {
			return tq.getResultList();
		} catch (Exception as) {
			return null;
		}
	}
}
