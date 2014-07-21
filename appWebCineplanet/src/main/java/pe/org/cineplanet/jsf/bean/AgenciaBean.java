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

	private Usuario userSesion = null;
	private Agencia agencia;
	// private Message message = new Message();

	private List<Agencia> listaAgencia = new ArrayList<Agencia>();

	public AgenciaBean() {
		super();
	}

	@PostConstruct
	public void init() {
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
		cargarListaAgencia();
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {

			agencia.setEstado(Constantes.ACTIVO);

			Agencia a = agenciaService.find(agencia.getIdAgencia());

			if (a != null) {
				agenciaService.edit(agencia);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				agenciaService.save(agencia);
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

		if (agencia.getIdAgencia().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (agencia.getNombre().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		return true;
	}

	public void cargarListaAgencia() {
		try {
			setListaAgencia(agenciaService.getListaAgencia());
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

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

}
