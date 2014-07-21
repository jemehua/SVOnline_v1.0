package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.Entrada;

/**
 * 
 * @author Hever Pumallihua
 */
public interface EntradaDao {

	public abstract Entrada find(Long id) throws Exception;

	public abstract Entrada save(Entrada obj) throws Exception;

	public abstract Entrada edit(Entrada obj) throws Exception;

	public abstract List<Entrada> getListaEntrada() throws Exception;
	
	public abstract Long getMaxId() throws Exception;

}
