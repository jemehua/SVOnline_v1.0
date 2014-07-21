package pe.org.cineplanet.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.svc.DetalleVentaService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

	@Autowired
	private DetalleVentaDao detalleVentaDao;

	@Transactional
	public DetalleVenta find(DetalleVentaPK id) throws Exception {
		return detalleVentaDao.find(id);
	}

	@Transactional
	public DetalleVenta save(DetalleVenta obj) throws Exception {
		return detalleVentaDao.save(obj);
	}

	@Transactional
	public DetalleVenta edit(DetalleVenta obj) throws Exception {
		return detalleVentaDao.edit(obj);
	}

	public List<DetalleVenta> getListaDetalleVenta() throws Exception {
		return detalleVentaDao.getListaDetalleVenta();
	}

}
