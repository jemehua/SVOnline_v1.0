package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.AgenciaDao;
import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.svc.AgenciaService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class AgenciaServiceImpl implements AgenciaService {

	@Autowired
	private AgenciaDao agenciaDao;

	@Transactional
	public Agencia find(Long id) throws Exception{
		return agenciaDao.find(id);
	}

	@Transactional
	public Agencia save(Agencia obj) throws Exception{
		return agenciaDao.save(obj);
	}

	@Transactional
	public Agencia edit(Agencia obj) throws Exception{
		return agenciaDao.edit(obj);

	}

	public List<Agencia> getListaAgencia() throws Exception{
		return agenciaDao.getListaAgencia();
	}

	public List<SelectItem> getComboAgencia(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione Agencia");
		listaCombo.add(fila);
		
		List<Agencia> lista = new ArrayList<Agencia>();
		try {
			lista = agenciaDao.getListaAgencia();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (Agencia tipo : lista) {
			fila = new SelectItem(tipo.getIdAgencia(), tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}

}
