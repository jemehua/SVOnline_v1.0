package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoPagoDao;
import pe.org.cineplanet.model.jpa.TipoPago;
import pe.org.cineplanet.svc.TipoPagoService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class TipoPagoServiceImpl implements TipoPagoService {

	@Autowired
	private TipoPagoDao tipoPagoDao;

	@Transactional
	public TipoPago find(Integer id) throws Exception{
		return tipoPagoDao.find(id);
	}

	@Transactional
	public TipoPago save(TipoPago obj) throws Exception{
		return tipoPagoDao.save(obj);
	}

	@Transactional
	public TipoPago edit(TipoPago obj) throws Exception{
		return tipoPagoDao.edit(obj);

	}

	public List<TipoPago> getListaTipoPago() throws Exception{
		return tipoPagoDao.getListaTipoPago();
	}

	public List<SelectItem> getComboTipoPago(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione Tipo de Pago");
		listaCombo.add(fila);
		
		List<TipoPago> lista = new ArrayList<TipoPago>();
		try {
			lista = tipoPagoDao.getListaTipoPago();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (TipoPago tipo : lista) {
			fila = new SelectItem(tipo.getIdTipoPago(), tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}

}
