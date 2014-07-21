package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.Rol;

/**
 * 
 * @author Hever Pumallihua
 */
public interface RolService {

	public abstract Rol find(Long id);

	public abstract Rol save(Rol obj);

	public abstract Rol edit(Rol obj);

	public abstract List<Rol> getListaRol();

	public abstract List<Rol> getListaRolByNombre(String nombre);

	public abstract List<SelectItem> getComboRol();

}
