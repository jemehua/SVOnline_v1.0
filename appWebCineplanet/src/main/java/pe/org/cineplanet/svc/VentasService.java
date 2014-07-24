package pe.org.cineplanet.svc;


import java.util.List;

import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.Venta;

/**
 * 
 * @author Hever Pumallihua
 */
public interface VentasService {

	public abstract Venta find(Long id) throws Exception;

	public abstract Venta save(Venta obj) throws Exception;
	
	public abstract Integer save(Venta obj, List<DetalleVenta> listaDetalleVenta) throws Exception;

	public abstract Venta edit(Venta obj) throws Exception;

	public abstract List<Venta> getListaVentas() throws Exception;
	
	public abstract Long getMaxId();

}
