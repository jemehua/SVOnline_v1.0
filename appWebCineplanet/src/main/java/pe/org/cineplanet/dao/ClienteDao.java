package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.Cliente;

/**
 * 
 * @author Hever Pumallihua
 */
public interface ClienteDao {

	public abstract Cliente find(String id) throws Exception;

	public abstract Cliente save(Cliente obj) throws Exception;

	public abstract Cliente edit(Cliente obj) throws Exception;

	public abstract List<Cliente> getListaCliente() throws Exception;

}
