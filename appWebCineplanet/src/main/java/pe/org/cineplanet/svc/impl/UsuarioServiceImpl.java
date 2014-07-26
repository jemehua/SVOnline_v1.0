package pe.org.cineplanet.svc.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.UsuarioDao;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.UsuarioService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	public Usuario find(Long id) {
		return usuarioDao.find(id);
	}

	@Transactional
	public Usuario save(Usuario obj) {
		return usuarioDao.save(obj);
	}
	
	@Transactional
	public Usuario edit(Usuario obj) {
		return usuarioDao.edit(obj);		
	}

	public List<Usuario> getListaUsuario() {
		return usuarioDao.getListaUsuario();
	}
	
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}
	
	public Long getIdMax(){
		return usuarioDao.getIdMax();
	}
	
	public List<Usuario> getListByUsername(String username) {
		return usuarioDao.getListByUsername(username);
	}
	
}
