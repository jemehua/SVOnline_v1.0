package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.TipoDocumento;

/**
 * 
 * @author Hever Pumallihua
 */
public interface TipoDocumentoDao {

	public abstract TipoDocumento find(Long id) throws Exception;

	public abstract TipoDocumento save(TipoDocumento obj) throws Exception;

	public abstract TipoDocumento edit(TipoDocumento obj) throws Exception;

	public abstract List<TipoDocumento> getListaTipoDocumento() throws Exception;

}
