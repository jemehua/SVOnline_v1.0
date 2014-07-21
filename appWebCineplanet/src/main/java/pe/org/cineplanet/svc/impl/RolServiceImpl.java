package pe.org.cineplanet.svc.impl;


import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.RolDao;
import pe.org.cineplanet.model.jpa.Rol;
import pe.org.cineplanet.svc.RolService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolDao rolDao;

	@Transactional
	public Rol find(Long id) {
		return rolDao.find(id);
	}

	@Transactional
	public Rol save(Rol obj) {
		return rolDao.save(obj);
	}

	@Transactional
	public Rol edit(Rol obj) {
		return rolDao.save(obj);

	}

	public List<Rol> getListaRol() {
		return rolDao.getListaRol();
	}

	public List<Rol> getListaRolByNombre(String nombre){
		return rolDao.getListaRolByNombre(nombre);
	}

	public List<SelectItem> getComboRol(){
		return rolDao.getComboRol();
	}

}
