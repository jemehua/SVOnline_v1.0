package pe.org.cineplanet.svc;


import java.util.Date;
import java.util.List;
import java.util.Map;

import pe.org.cineplanet.dto.ReporteDTO;
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
	
	public abstract List<ReporteDTO> getListaReporte(Date fecInicio, Date fecFin, String usr) throws Exception;
	
	public abstract Map<String, Object[]> getMapVentaReporte(Date fecInicio, Date fecFin, String usr) throws Exception;

}
