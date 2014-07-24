package pe.org.cineplanet.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.dao.MovimientoDao;
import pe.org.cineplanet.dao.VentasDao;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.svc.VentasService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class VentasServiceImpl implements VentasService {

	@Autowired
	private VentasDao ventasDao;
	@Autowired
	private DetalleVentaDao detalleVentaDao;
	@Autowired
	private MovimientoDao movimientoDao;
	@Autowired
	private DetalleEntradaDao detalleEntradaDao;

	@Transactional
	public Venta find(Long id) throws Exception {
		return ventasDao.find(id);
	}

	@Transactional
	public Venta save(Venta obj) throws Exception {
		return ventasDao.save(obj);
	}

	@Transactional
	public Integer save(Venta obj, List<DetalleVenta> listaDetalleVenta)
			throws Exception {
		ventasDao.save(obj);

		Integer total = 0;
		for (DetalleVenta row : listaDetalleVenta) {
			DetalleVentaPK id = new DetalleVentaPK(obj.getIdVenta(), row.getTipoEntrada().getIdTipoEntrada());
			row.setId(id);

			detalleVentaDao.save(row);
			
			List<DetalleEntrada> listaDetalleEntradas = detalleEntradaDao.getListaByTipoEntrada(row
					.getTipoEntrada().getIdTipoEntrada());
			
			//System.out.println("listaDetalleEntradas por tipoEntrada"+listaDetalleEntradas.size());
			int count = 0;
			for(int i=0; i<row.getCantidad(); i++){
				
				DetalleEntrada detalleEntrada = listaDetalleEntradas.get(i);
				
				Movimiento mov;
				try {
					mov = movimientoDao.getByIdCodigo(detalleEntrada.getId().getIdCodigo());
				} catch (Exception e) {
					// TODO: handle exception
					mov = null;
				}
				
				
				
				if(mov == null) {
					MovimientoPK idMov = new MovimientoPK(id.getIdVenta(), id.getIdTipoEntrada(), detalleEntrada.getId().getIdCodigo(), detalleEntrada.getId().getIdEntrada());
					
					Movimiento movimiento = new Movimiento();
					movimiento.setId(idMov);
					movimiento.setEstado(Constantes.ACTIVO);
					
					movimientoDao.save(movimiento);
					
					detalleEntrada.setEstado(Constantes.VENDIDO);
					detalleEntradaDao.edit(detalleEntrada);
					
					count++;
				}else{
					System.out.println("El codigo "+detalleEntrada.getId().getIdCodigo()+" ya ha sido asignado");
				}
			}
			
			if(row.getCantidad() == count)
				total += row.getCantidad();
			else
				total = count;
		}
		obj.setTotal(total);
		ventasDao.edit(obj);
		
		return total;
	}

	@Transactional
	public Venta edit(Venta obj) throws Exception {
		return ventasDao.edit(obj);
	}

	public List<Venta> getListaVentas() throws Exception {
		return ventasDao.getListaVentas();
	}

	public Long getMaxId() {
		try {
			return ventasDao.getMaxId() + 1L;
		} catch (Exception e) {
			// TODO: handle exception
			return 1L;
		}
	}

}
