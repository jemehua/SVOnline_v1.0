package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

@Component("empresaBean")
@Scope("session")
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EmpresaBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private AgenciaService agenciaService;

	private Usuario userSesion = null;
	private Agencia agencia;
	// private Message message = new Message();

	private List<Agencia> listaAgencia = new ArrayList<Agencia>();
	private List<Agencia> filteredListaAgencia = new ArrayList<Agencia>();

	public EmpresaBean() {
		super();
	}

	@PostConstruct
	public void init() {
		logger.info("EmpresaBean init");
		agencia = new Agencia();
		cargarListaAgencia();
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
		//filteredListaAgencia = new ArrayList<Agencia>();
		cargarListaAgencia();
		
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {
			agencia.setNombre(agencia.getNombre().toUpperCase());
			//Agencia a = agenciaService.find(agencia.getIdAgencia());

			//if (a != null) {
			if (agencia.getIdAgencia() != null && agencia.getIdAgencia().length() > 0) {
				agenciaService.edit(agencia);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				agencia.setEstado(Constantes.ACTIVO);
				agenciaService.save(agencia, Constantes.TIPO_EMPRESA);
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
		cargarListaAgencia();
		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

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

	public void cargarListaAgencia() {
		logger.info("cargarListaAgencia");
		try {
			 listaAgencia = agenciaService.getListaEmpresa();
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

	/*public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}*/

	public List<Agencia> getFilteredListaAgencia() {
		return filteredListaAgencia;
	}

	public void setFilteredListaAgencia(List<Agencia> filteredListaAgencia) {
		this.filteredListaAgencia = filteredListaAgencia;
	}

}
