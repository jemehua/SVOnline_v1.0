package pe.org.cineplanet.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.dao.MovimientoDao;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;
import pe.org.cineplanet.svc.DetalleEntradaService;
import pe.org.cineplanet.svc.MovimientoService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoDao movimientoDao;

	@Transactional
	public Movimiento find(MovimientoPK id) throws Exception {
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

	public List<Movimiento> getListaMovimiento() throws Exception {
		return movimientoDao.getListaMovimiento();
	}
	
	public List<VentaDTO> getListaVenta(Long idVenta) throws Exception {
		return movimientoDao.getListaVenta(idVenta);
	}

}
