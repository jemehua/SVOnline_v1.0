package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;

/**
 * 
 * @author Hever Pumallihua
 */
public interface DetalleVentaDao {

	public abstract DetalleVenta find(DetalleVentaPK id) throws Exception;

	public abstract DetalleVenta save(DetalleVenta obj) throws Exception;

	public abstract DetalleVenta edit(DetalleVenta obj) throws Exception;

	public abstract List<DetalleVenta> getListaByIdVenta(Long idVenta) throws Exception;


}
