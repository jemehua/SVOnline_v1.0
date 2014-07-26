package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.Usuario;

/**
 * 
 * @author Hever Pumallihua
 */
public interface UsuarioDao {
	
	public abstract Usuario find(Long id);
	
	public abstract Usuario save(Usuario obj);
	
	public abstract Usuario edit(Usuario obj);
	
	public abstract List<Usuario> getListaUsuario();
	
	public abstract Usuario findByUsername(String username);
	
	public abstract Long getIdMax();
	
	public abstract List<Usuario> getListByUsername(String username);

}
