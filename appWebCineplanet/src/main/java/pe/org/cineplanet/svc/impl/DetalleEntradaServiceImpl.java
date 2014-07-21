package pe.org.cineplanet.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;
import pe.org.cineplanet.svc.DetalleEntradaService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class DetalleEntradaServiceImpl implements DetalleEntradaService {

	@Autowired
	private DetalleEntradaDao detalleEntradaDao;

	@Transactional
	public DetalleEntrada find(DetalleEntradaPK id) throws Exception {
		return detalleEntradaDao.find(id);
	}

	@Transactional
	public DetalleEntrada save(DetalleEntrada obj) throws Exception {
		return detalleEntradaDao.save(obj);
	}

	@Transactional
	public DetalleEntrada edit(DetalleEntrada obj) throws Exception {
		return detalleEntradaDao.edit(obj);
	}

	public List<DetalleEntrada> getListaDetalleEntrada(Long idEntrada) throws Exception {
		return detalleEntradaDao.getListaDetalleEntrada(idEntrada);
	}
	
	public List<DetalleEntrada> getListaByTipoEntrada(Long idTipoEntrada) throws Exception {
		return detalleEntradaDao.getListaByTipoEntrada(idTipoEntrada);
	}

	public boolean getCantidadDispobibleByIdTipoEntrada(Long idTipoEntrada,
			Integer cantidad) {
		// TODO Auto-generated method stub
		List<DetalleEntrada> listDetalle = new ArrayList<DetalleEntrada>();
		 try {
			 listDetalle = getListaByTipoEntrada(idTipoEntrada);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 if(listDetalle.size() >= cantidad)
			 return true;
		 else
			 return false;
	}

}
