package pe.org.cineplanet.dao;

import java.util.List;

import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;

/**
 * 
 * @author Hever Pumallihua
 */
public interface MovimientoDao {

	public abstract Movimiento find(MovimientoPK id) throws Exception;

	public abstract Movimiento save(Movimiento obj) throws Exception;

	public abstract Movimiento edit(Movimiento obj) throws Exception;

	public abstract List<Movimiento> getListaMovimiento() throws Exception;
	
	public List<VentaDTO> getListaVenta(Long idVenta) throws Exception;

}
