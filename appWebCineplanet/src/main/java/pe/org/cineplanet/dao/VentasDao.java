package pe.org.cineplanet.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import pe.org.cineplanet.dto.ReporteDTO;
import pe.org.cineplanet.model.jpa.Venta;

/**
 * 
 * @author Hever Pumallihua
 */
public interface VentasDao {

	public abstract Venta find(Long id) throws Exception;

	public abstract Venta save(Venta obj) throws Exception;

	public abstract Venta edit(Venta obj) throws Exception;

	public abstract List<Venta> getListaVentas() throws Exception;

	public abstract Long getMaxId() throws Exception;
	
	public abstract List<ReporteDTO> getListaReporte(Date fecInicio, Date fecFin, String usr) throws Exception;
	
	public abstract List<Object[]> getListaVenta(Date fecInicio, Date fecFin, String usr) throws Exception;
}
