package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.AgenciaDao;
import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.svc.AgenciaService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class AgenciaServiceImpl implements AgenciaService {

	@Autowired
	private AgenciaDao agenciaDao;

	@Transactional
	public Agencia find(String id) throws Exception{
		return agenciaDao.find(id);
	}
	
	@Transactional
	public Agencia save(Agencia obj) throws Exception{
		return agenciaDao.save(obj);
	}

	@Transactional
	public Agencia save(Agencia obj, String tipo) throws Exception{
		Integer idAgencia = agenciaDao.getNextId(tipo);
		if(tipo.equals(Constantes.TIPO_EMPRESA))
			obj.setIdAgencia(tipo + StringUtils.leftPad(idAgencia.toString(), 3, "0"));
		else
			obj.setIdAgencia(tipo + StringUtils.leftPad(idAgencia.toString(), 5, "0"));
		return agenciaDao.save(obj);
	}

	@Transactional
	public Agencia edit(Agencia obj) throws Exception{
		return agenciaDao.edit(obj);

	}

	public List<Agencia> getListaAgencia() throws Exception{
		return agenciaDao.getListaAgencia();
	}
	
	public List<Agencia> getListaAgencia(String codigoEmpresa) throws Exception {
		return agenciaDao.getListaAgencia(codigoEmpresa);
	}
	
	public List<Agencia> getListaEmpresa() throws Exception {
		return agenciaDao.getListaEmpresa();
	}

	public List<SelectItem> getComboAgencia(String codigoEmpresa){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem("", "Seleccione Agencia");
		listaCombo.add(fila);
		
		List<Agencia> lista = new ArrayList<Agencia>();
		try {
			if(!codigoEmpresa.equals(Constantes.VACIO))
				lista = agenciaDao.getListaAgencia(codigoEmpresa);
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (Agencia tipo : lista) {
			fila = new SelectItem(tipo.getIdAgencia(),  tipo.getIdAgencia() + " - " + tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}
	
	public List<SelectItem> getComboEmpresa(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem("", "Seleccione Empresa");
		listaCombo.add(fila);
		
		List<Agencia> lista = new ArrayList<Agencia>();
		try {
			lista = agenciaDao.getListaEmpresa();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (Agencia tipo : lista) {
			fila = new SelectItem(tipo.getIdAgencia(), tipo.getIdAgencia() + " - " + tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}

}
