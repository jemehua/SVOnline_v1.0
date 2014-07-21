package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.TipoDocumentoDao;
import pe.org.cineplanet.model.jpa.TipoDocumento;
import pe.org.cineplanet.svc.TipoDocumentoService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	private TipoDocumentoDao tipoDocumentoDao;

	@Transactional
	public TipoDocumento find(Long id) throws Exception{
		return tipoDocumentoDao.find(id);
	}

	@Transactional
	public TipoDocumento save(TipoDocumento obj) throws Exception{
		return tipoDocumentoDao.save(obj);
	}

	@Transactional
	public TipoDocumento edit(TipoDocumento obj) throws Exception{
		return tipoDocumentoDao.edit(obj);

	}

	public List<TipoDocumento> getListaTipoDocumento() throws Exception{
		return tipoDocumentoDao.getListaTipoDocumento();
	}

	public List<SelectItem> getComboTipoDocumento(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione Tipo de Documento");
		listaCombo.add(fila);
		
		List<TipoDocumento> lista = new ArrayList<TipoDocumento>();
		try {
			lista = tipoDocumentoDao.getListaTipoDocumento();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (TipoDocumento tipo : lista) {
			fila = new SelectItem(tipo.getIdTipoDocumento(), tipo.getNombre());
			listaCombo.add(fila);
		}
		return listaCombo;
	}

}
