package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.AgenciaService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("agenciaBean")
@Scope("session")
public class AgenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(AgenciaBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private AgenciaService agenciaService;

	private String empresaSelec = "";
	private Usuario userSesion = null;
	private Agencia agencia;
	// private Message message = new Message();

	private List<SelectItem> comboEmpresa = new ArrayList<SelectItem>();
	private List<Agencia> listaAgencia = new ArrayList<Agencia>();
	private List<Agencia> filteredListaAgencia = new ArrayList<Agencia>();

	public AgenciaBean() {
		super();
	}

	@PostConstruct
	public void init() {
		agencia = new Agencia();
		cargarComboEmpresa();
		cargarListaAgencia(Constantes.VACIO);
		// message = new Message();
	}

	public void validarSesion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		userSesion = (Usuario) ctx.getExternalContext().getSessionMap()
				.get(loginController.getUsername());

		if (userSesion == null) {
			String ctxPath = ctx.getExternalContext().getRequestContextPath();
			try {
				ctx.getExternalContext().redirect(ctxPath + "/login.jsf");
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public void limpiar() {
		agencia = new Agencia();
		empresaSelec = "";
		cargarListaAgencia(Constantes.VACIO);

	}
	
	public void handleItemSelect() {
		logger.info("empresaSelec: " + empresaSelec);
		cargarListaAgencia(empresaSelec);
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {
			agencia.setIdAgenciaPadre(empresaSelec);
			agencia.setNombre(agencia.getNombre().toUpperCase());
			//Agencia a = agenciaService.find(agencia.getIdAgencia());

			//if (a != null) {
			if (agencia.getIdAgencia() != null
					&& agencia.getIdAgencia().length() > 0) {
				agenciaService.edit(agencia);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				agencia.setEstado(Constantes.ACTIVO);
				//agenciaService.save(agencia);
				agenciaService.save(agencia, Constantes.TIPO_AGENCIA);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Exitoso"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR",
							"Error al registrar"));
		}
		cargarListaAgencia(empresaSelec);
		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (empresaSelec.equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		/*if (agencia.getIdAgencia().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}*/

		if (agencia.getNombre().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		return true;
	}

	public void cargarComboEmpresa() {
		try {
			comboEmpresa = agenciaService.getComboEmpresa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarListaAgencia(String codigoEmpresa) {
		try {
			listaAgencia = agenciaService.getListaAgencia(codigoEmpresa);
			filteredListaAgencia = listaAgencia;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// GET - SET

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	/*
	 * public void setListaAgencia(List<Agencia> listaAgencia) {
	 * this.listaAgencia = listaAgencia; }
	 */

	public List<Agencia> getFilteredListaAgencia() {
		return filteredListaAgencia;
	}

	public void setFilteredListaAgencia(List<Agencia> filteredListaAgencia) {
		this.filteredListaAgencia = filteredListaAgencia;
	}

	public List<SelectItem> getComboEmpresa() {
		return comboEmpresa;
	}

	public void setComboEmpresa(List<SelectItem> comboEmpresa) {
		this.comboEmpresa = comboEmpresa;
	}

	public String getEmpresaSelec() {
		return empresaSelec;
	}

	public void setEmpresaSelec(String empresaSelec) {
		this.empresaSelec = empresaSelec;
	}

}
