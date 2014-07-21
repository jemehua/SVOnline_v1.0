package pe.org.cineplanet.svc;


import java.util.List;

import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;

/**
 * 
 * @author Hever Pumallihua
 */
public interface DetalleEntradaService {

	public abstract DetalleEntrada find(DetalleEntradaPK id) throws Exception;

	public abstract DetalleEntrada save(DetalleEntrada obj) throws Exception;

	public abstract DetalleEntrada edit(DetalleEntrada obj) throws Exception;

	public abstract List<DetalleEntrada> getListaDetalleEntrada(Long idEntrada) throws Exception;

	public abstract List<DetalleEntrada> getListaByTipoEntrada(Long idTipoEntrada) throws Exception;
	
	public abstract boolean getCantidadDispobibleByIdTipoEntrada(Long idTipoEntrada, Integer cantidad);

}
