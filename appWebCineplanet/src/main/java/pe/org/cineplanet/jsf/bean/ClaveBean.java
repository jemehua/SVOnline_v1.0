package pe.org.cineplanet.jsf.bean;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.UsuarioService;
import pe.org.cineplanet.util.Message;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("claveBean")
@Scope("session")
public class ClaveBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ClaveBean.class);
	
	@Autowired
	private LoginController loginController;
	@Autowired
	private UsuarioService usuarioService;

	private Usuario userSesion = null;
	private Message message = new Message();

	private String clave = "";
	private String nuevaClave = "";
	private String confirmarClave = "";

	public ClaveBean() {

	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNuevaClave() {
		return nuevaClave;
	}

	public void setNuevaClave(String nuevaClave) {
		this.nuevaClave = nuevaClave;
	}

	public String getConfirmarClave() {
		return confirmarClave;
	}

	public void setConfirmarClave(String confirmarClave) {
		this.confirmarClave = confirmarClave;
	}

	public void guardarClave() {

		validarSesion();

		try {
		
			if (!validarDatos())
				return;

			//userSesion.setPassword(Seguridad.encrypt(nuevaClave));
			userSesion.setClave(nuevaClave);
			usuarioService.edit(userSesion);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
							"Su contraseña ha sido cambiado con exito"));
		} catch (Exception e) {
			logger.warn("ERROR AL GUARDAR CAMBIO DE CLAVE");
		}
	}

	public boolean validarDatos() throws Exception{

		if (clave.length() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							"Ingrese su contraseña anterior"));
			return false;
		}

		if (nuevaClave.length() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							"Ingrese su nueva contraseña"));
			return false;
		}

		if (confirmarClave.length() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							"Confirme su nueva contraseña"));
			return false;
		}

		if (verificarClaveActual()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							"Verifique su contraseña actual"));
		}

		if (!nuevaClave.trim().equalsIgnoreCase(confirmarClave)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							message.getMessage("msgConfirmarClave")));
			return false;
		}

		return true;
	}
	
	public boolean verificarClaveActual() throws Exception {

		//String plainText = Seguridad.decrypt(userSesion.getPassword());
				
		if (userSesion.getClave().equalsIgnoreCase(clave))
			return false;
		else
			return true;

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

	public void limpiar() {
		clave = "";
		nuevaClave = "";
		confirmarClave = "";
	}

}
