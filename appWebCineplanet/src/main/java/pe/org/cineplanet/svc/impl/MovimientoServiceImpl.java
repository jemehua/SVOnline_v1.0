package pe.org.cineplanet.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.dao.MovimientoDao;
import pe.org.cineplanet.dao.VentasDao;
import pe.org.cineplanet.dto.MovimientoDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.svc.DetalleVentaService;
import pe.org.cineplanet.svc.MovimientoService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoDao movimientoDao;

	@Autowired
	private DetalleEntradaDao detalleEntradaDao;
	
	@Autowired
	private DetalleVentaDao detalleVentaDao;
	
	@Autowired
	private VentasDao ventasDao;

	public Movimiento find(MovimientoPK id) throws Exception {
		return movimientoDao.find(id);
	}

	public Movimiento find(String id) throws Exception {
		return movimientoDao.find(id);
	}

	@Transactional
	public Movimiento save(Movimiento obj) throws Exception {
		return movimientoDao.save(obj);
	}

	@Transactional
	public Movimiento edit(Movimiento obj) throws Exception {
		return movimientoDao.edit(obj);
	}

	@Transactional
	public void anularVenta(Movimiento obj) throws Exception {
		obj.setEstado(Constantes.INACTIVO);
		movimientoDao.edit(obj);

		DetalleEntrada de = detalleEntradaDao.find(new DetalleEntradaPK(obj
				.getId().getIdCodigo(), obj.getId().getIdEntrada()));

		de.setEstado(Constantes.ACTIVO);
		detalleEntradaDao.edit(de);
		
		DetalleVenta dv = detalleVentaDao.find(new DetalleVentaPK(obj.getId().getIdVenta(), obj.getId().getIdTipoEntrada()));
		dv.setCantidad(dv.getCantidad() - 1);
		detalleVentaDao.edit(dv);
		
		Venta v = ventasDao.find(obj.getId().getIdVenta());
		v.setTotal(v.getTotal() - 1);
		ventasDao.edit(v);
		
	}

	public List<Movimiento> getListaMovimiento() throws Exception {
		return movimientoDao.getListaMovimiento();
	}

	public List<VentaDTO> getListaVenta(Long idVenta) throws Exception {
		return movimientoDao.getListaVenta(idVenta);
	}

	public List<MovimientoDTO> getListaMovimiento(DetalleVentaPK id)
			throws Exception {
		return movimientoDao.getListaMovimiento(id);
	}

}
