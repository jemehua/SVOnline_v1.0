package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoEntradaDao;
import pe.org.cineplanet.model.jpa.TipoEntrada;
import pe.org.cineplanet.svc.TipoEntradaService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class TipoEntradaServiceImpl implements TipoEntradaService {

	@Autowired
	private TipoEntradaDao tipoValeDao;

	@Transactional
	public TipoEntrada find(Long id) throws Exception{
		return tipoValeDao.find(id);
	}

	@Transactional
	public TipoEntrada save(TipoEntrada obj) throws Exception{
		return tipoValeDao.save(obj);
	}

	@Transactional
	public TipoEntrada edit(TipoEntrada obj) throws Exception{
		return tipoValeDao.edit(obj);

	}

	public List<TipoEntrada> getListaTipoVale() throws Exception{
		return tipoValeDao.getListaTipoVale();
	}

	public List<SelectItem> getComboTipoVale(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione tipo de Vale");
		listaCombo.add(fila);
		
		List<TipoEntrada> lista = new ArrayList<TipoEntrada>();
		try {
			lista = tipoValeDao.getListaTipoVale();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (TipoEntrada tipo : lista) {
			fila = new SelectItem(tipo.getIdTipoEntrada(), tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}

}
