package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.Permiso;

/**
 * 
 * @author Hever Pumallihua
 */
public interface PermisoDao {
	
	public abstract Permiso find(Long id);
	
	public abstract Permiso save(Permiso obj);
	
	public abstract List<Permiso> getListaPermiso();
	
	public abstract List<Permiso> getListaPermiso(Long idRol);
	
	public abstract List<Permiso> getListaPermiso(Long idMenu, Long idRol);

}
