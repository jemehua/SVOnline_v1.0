package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.ItemMenu;
import pe.org.cineplanet.model.jpa.Rol;

/**
 * 
 * @author Hever Pumallihua
 */
public interface ItemMenuDao {
	
	public abstract ItemMenu find(Long id);
	
	public abstract ItemMenu save(ItemMenu obj);
	
	public abstract void save(Rol rol, List<ItemMenu> listItemSelec, List<ItemMenu> listItemMenu);
	
	public abstract List<ItemMenu> getListaItemMenu();
	
	public abstract List<ItemMenu> getListaMenuItem(Long idMenu, Long idRol);

}
