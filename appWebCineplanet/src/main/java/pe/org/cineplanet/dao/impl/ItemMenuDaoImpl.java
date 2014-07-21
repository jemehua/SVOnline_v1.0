package pe.org.cineplanet.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.ItemMenuDao;
import pe.org.cineplanet.model.jpa.ItemMenu;
import pe.org.cineplanet.model.jpa.Permiso;
import pe.org.cineplanet.model.jpa.Rol;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
// @Service
@Repository
public class ItemMenuDaoImpl implements ItemMenuDao {
	@PersistenceContext
	private EntityManager em;

	public ItemMenu find(Long id) {
		return em.find(ItemMenu.class, id);
	}

	@Transactional
	public ItemMenu save(ItemMenu obj) {
		if (obj.getIdItemMenu() == null) {
			em.persist(obj);
			return obj;
		} else {
			return em.merge(obj);
		}
	}

	@Transactional
	public void save(Rol obj, List<ItemMenu> listItemSelec,
			List<ItemMenu> listItemMenu) {

		em.persist(obj);

		Permiso permiso = null;
		for (ItemMenu row : listItemMenu) {
			permiso = new Permiso();
			permiso.setRol(obj);
			permiso.setItemMenu(row);

			for (ItemMenu itemSelec : listItemSelec) {
				if (row.getIdItemMenu().equals(itemSelec.getIdItemMenu())) {
					permiso.setEstado(true);
					break;
				} else
					permiso.setEstado(false);
			}

			em.persist(permiso);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemMenu> getListaItemMenu() {

		Query q = null;

		try {
			q = em.createNamedQuery("ItemMenu.getListaItemMenu");
			q.setParameter("estado", Constantes.ACTIVO);
			return q.getResultList();
		} catch (Exception as) {
			as.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemMenu> getListaMenuItem(Long idMenu, Long idRol) {
		Query q = (Query) em
				.createQuery("SELECT im FROM ItemMenu im where im.rol.idRol =:idRol AND im.menu.idMenu =:idMenu AND im.estado =:estado");

		try {
			q.setParameter("idRol", idRol);
			q.setParameter("idMenu", idMenu);
			q.setParameter("estado", Constantes.ACTIVO);

			return q.getResultList();
		} catch (Exception as) {
			as.printStackTrace();
			return null;
		}
	}

}
