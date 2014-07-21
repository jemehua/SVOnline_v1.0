package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.TipoDocumento;

/**
 * 
 * @author Hever Pumallihua
 */
public interface TipoDocumentoService {

	public abstract TipoDocumento find(Long id) throws Exception;

	public abstract TipoDocumento save(TipoDocumento obj) throws Exception;

	public abstract TipoDocumento edit(TipoDocumento obj) throws Exception;

	public abstract List<TipoDocumento> getListaTipoDocumento() throws Exception;

	public abstract List<SelectItem> getComboTipoDocumento() throws Exception;

}
