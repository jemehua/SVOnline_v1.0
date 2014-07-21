package pe.org.cineplanet.jsf.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.dao.PermisoDao;
import pe.org.cineplanet.model.jpa.Permiso;
import pe.org.cineplanet.model.jpa.Usuario;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("contenidoController")
@Scope("session")
public class ContenidoController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ContenidoController.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private PermisoDao permisoDao;
	
	private String includedPage = "";
	private Usuario userSesion = null;
	private List<Permiso> listaPermisoAdm = new ArrayList<Permiso>();
	private List<Permiso> listaPermisoOpe = new ArrayList<Permiso>();
	private List<Permiso> listaPermisoMon = new ArrayList<Permiso>();
	private List<Permiso> listaPermisoNoti = new ArrayList<Permiso>();
	private List<Permiso> listaPermisoRep = new ArrayList<Permiso>();

	public ContenidoController() {
		includedPage = "contents/bienvenido.xhtml";
	}
	
	@PostConstruct
	public void init(){
		validarSesion();
		getPermisoUsuario();
	}

	public void getPermisoUsuario(){
		listaPermisoAdm = permisoDao.getListaPermiso(1L, userSesion.getRol().getIdRol());
		listaPermisoOpe = permisoDao.getListaPermiso(2L, userSesion.getRol().getIdRol());
		listaPermisoMon = permisoDao.getListaPermiso(3L, userSesion.getRol().getIdRol());
		listaPermisoNoti = permisoDao.getListaPermiso(4L, userSesion.getRol().getIdRol());
		listaPermisoRep = permisoDao.getListaPermiso(5L, userSesion.getRol().getIdRol());
	}
	
	public String getIncludedPage() {
		validarSesion();
		return includedPage;
	}

	public void setIncludedPage(String includedPage) {
		//validarSesion();
		eliminarSesion(includedPage);
		this.includedPage = includedPage;
	}

	public void validarSesion() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		userSesion = (Usuario) context.getExternalContext().getSessionMap()
				.get(loginController.getUsername());

		if (userSesion == null) {
			String ctxPath = context.getExternalContext()
					.getRequestContextPath();
			try {
				context.getExternalContext().redirect(ctxPath + "/login.jsf");
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	public void eliminarSesion(String nombre) {
		try {
			String nombreControl = "";
			if (nombre.equalsIgnoreCase("contents/aprobacionMetas.xhtml")) {
				nombreControl = "aprobacionMetasController";
			} else if (nombre
					.equalsIgnoreCase("contents/planDesarrolloCompetencias.xhtml")) {
				nombreControl = "planCompetenciasController";
			} else if (nombre
					.equalsIgnoreCase("contents/planAccionMetas.xhtml")) {
				nombreControl = "planMetasController";
			} else if (nombre
					.equalsIgnoreCase("contents/verificarPlanesGP.xhtml")) {
				nombreControl = "verificarPlanesGPController";
			} else if (nombre
					.equalsIgnoreCase("contents/cualitativaCompetencias.xhtml")) {
				nombreControl = "cualitativaCompetenciasController";
			} else if (nombre
					.equalsIgnoreCase("contents/cualitativaMetas.xhtml")) {
				nombreControl = "cualitativaMetasController";
			} else if (nombre
					.equalsIgnoreCase("contents/cuantitativaCompetencias.xhtml")) {
				nombreControl = "cuantitativaCompetenciasController";
			} else if (nombre
					.equalsIgnoreCase("contents/cuantitativaMetas.xhtml")) {
				nombreControl = "cuantitativaMetasController";
			} else if (nombre
					.equalsIgnoreCase("contents/descargarAdjuntarPlanesGP.xhtml")) {
				nombreControl = "adjuntarPlanesGPController";
			} else if (nombre
					.equalsIgnoreCase("contents/evaluacionFinalGP.xhtml")) {
				nombreControl = "evaluacionFinalGPController";
			} else if (nombre
					.equalsIgnoreCase("contents/reporteMonitoreo.xhtml")) {
				nombreControl = "reporteMonitoreoController";
			}
			
			if(nombreControl.length() > 0){
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().remove(nombreControl);
			}
		} catch (Exception e) {
			System.out.println("Error al eliminar sesión");
		}
	}

	public Usuario getUserSesion() {
		return userSesion;
	}

	public void setUserSesion(Usuario userSesion) {
		this.userSesion = userSesion;
	}
	
	public List<Permiso> getListaPermisoAdm() {
		return listaPermisoAdm;
	}

	public void setListaPermisoAdm(List<Permiso> listaPermisoAdm) {
		this.listaPermisoAdm = listaPermisoAdm;
	}

	public List<Permiso> getListaPermisoOpe() {
		return listaPermisoOpe;
	}

	public void setListaPermisoOpe(List<Permiso> listaPermisoOpe) {
		this.listaPermisoOpe = listaPermisoOpe;
	}

	public List<Permiso> getListaPermisoMon() {
		return listaPermisoMon;
	}

	public void setListaPermisoMon(List<Permiso> listaPermisoMon) {
		this.listaPermisoMon = listaPermisoMon;
	}

	public List<Permiso> getListaPermisoNoti() {
		return listaPermisoNoti;
	}

	public void setListaPermisoNoti(List<Permiso> listaPermisoNoti) {
		this.listaPermisoNoti = listaPermisoNoti;
	}

	public List<Permiso> getListaPermisoRep() {
		return listaPermisoRep;
	}

	public void setListaPermisoRep(List<Permiso> listaPermisoRep) {
		this.listaPermisoRep = listaPermisoRep;
	}
	
}
