package pe.org.cineplanet.jsf.bean;


import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.Usuario;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("sesionController")
@Scope("session")
public class SesionController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(SesionController.class);

	private FacesContext ctx = null;
	private String ctxPath = "";

	public SesionController() {

	}

	public boolean crearSesion(Usuario userSesion) {

		ctx = FacesContext.getCurrentInstance();
		ctxPath = ctx.getExternalContext().getRequestContextPath();

		try {

			ctx.getExternalContext().getSessionMap()
					.put("userSesion", userSesion);
			ctx.getExternalContext().redirect(ctxPath + "/home.jsf");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void cerrarSesion() {
		ctx = FacesContext.getCurrentInstance();
		HttpSession session = null;

		try {
			session = (HttpSession) ctx.getExternalContext().getSession(false);

			if (session != null) {
				session.invalidate();
			}

			ctxPath = ctx.getExternalContext().getRequestContextPath();
			ctx.getExternalContext().redirect(ctxPath + "/login.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setearContextoExterno(String nombreClases, Object monitorClases) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();
			if (monitorClases != null && nombreClases.length() > 0)
				servletContext.setAttribute(nombreClases, monitorClases);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean recuperarContextoExterno(String nombreClase) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();
			if (servletContext.getAttribute(nombreClase) != null) {
				return true;
			} else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
