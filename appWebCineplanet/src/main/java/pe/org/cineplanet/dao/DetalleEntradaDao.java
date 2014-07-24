package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;

/**
 * 
 * @author Hever Pumallihua
 */
public interface DetalleEntradaDao {

	public abstract DetalleEntrada find(DetalleEntradaPK id) throws Exception;

	public abstract DetalleEntrada save(DetalleEntrada obj) throws Exception;

	public abstract DetalleEntrada edit(DetalleEntrada obj) throws Exception;

	public abstract List<DetalleEntrada> getListaDetalleEntrada(Long idEntrada) throws Exception;
	
	public abstract List<DetalleEntrada> getListaByTipoEntrada(Long idTipoEntrada) throws Exception;
	
	public abstract DetalleEntrada getByIdCodigo(String idCodigo) throws Exception;


}
