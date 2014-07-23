package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.ClienteDao;
import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.svc.ClienteService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteDao clienteDao;

	@Transactional
	public Cliente find(Long id) throws Exception{
		return clienteDao.find(id);
	}

	@Transactional
	public Cliente save(Cliente obj) throws Exception{
		return clienteDao.save(obj);
	}

	@Transactional
	public Cliente edit(Cliente obj) throws Exception{
		return clienteDao.edit(obj);

	}

	public List<Cliente> getListaCliente() throws Exception{
		return clienteDao.getListaCliente();
	}

	public List<SelectItem> getComboCliente(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione Cliente");
		listaCombo.add(fila);
		
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			lista = clienteDao.getListaCliente();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (Cliente tipo : lista) {
			fila = new SelectItem(tipo.getIdCliente(), tipo.getRazonSocial());
			listaCombo.add(fila);
		}
		return listaCombo;
	}
	
	public Long getMaxId() {
		try {
			return clienteDao.getMaxId() + 1L;
		} catch (Exception e) {
			// TODO: handle exception
			return 1L;
		}
	}

}
