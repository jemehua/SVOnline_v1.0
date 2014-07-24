package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.Entrada;

/**
 * 
 * @author Hever Pumallihua
 */
public interface EntradaService {

	public abstract Entrada find(Long id) throws Exception;

	public abstract Entrada save(Entrada obj) throws Exception;
	
	public abstract Integer save(Entrada obj, List<DetalleEntrada> listaDetalle) throws Exception;

	public abstract Entrada edit(Entrada obj) throws Exception;

	public abstract List<Entrada> getListaEntrada() throws Exception;

	public abstract List<SelectItem> getComboTipoVale() throws Exception;
	
	public abstract Long getMaxId();

}
