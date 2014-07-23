package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.Cliente;

/**
 * 
 * @author Hever Pumallihua
 */
public interface ClienteService {

	public abstract Cliente find(Long id) throws Exception;

	public abstract Cliente save(Cliente obj) throws Exception;

	public abstract Cliente edit(Cliente obj) throws Exception;

	public abstract List<Cliente> getListaCliente() throws Exception;

	public abstract List<SelectItem> getComboCliente() throws Exception;
	
	public abstract Long getMaxId();

}
