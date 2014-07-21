package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.Agencia;

/**
 * 
 * @author Hever Pumallihua
 */
public interface AgenciaDao {

	public abstract Agencia find(Long id) throws Exception;

	public abstract Agencia save(Agencia obj) throws Exception;

	public abstract Agencia edit(Agencia obj) throws Exception;

	public abstract List<Agencia> getListaAgencia() throws Exception;

}
