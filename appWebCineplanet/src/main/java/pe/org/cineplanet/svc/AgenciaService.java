package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.Agencia;

/**
 * 
 * @author Hever Pumallihua
 */
public interface AgenciaService {

	public abstract Agencia find(Long id) throws Exception;

	public abstract Agencia save(Agencia obj) throws Exception;

	public abstract Agencia edit(Agencia obj) throws Exception;

	public abstract List<Agencia> getListaAgencia() throws Exception;

	public abstract List<SelectItem> getComboAgencia() throws Exception;

}
