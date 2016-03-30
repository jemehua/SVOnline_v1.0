package pe.org.cineplanet.jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.RolService;
import pe.org.cineplanet.svc.UsuarioService;
import pe.org.cineplanet.util.Constantes;
import pe.org.cineplanet.util.Message;

/**
 * 
 * @author Hever Pumallihua
 */
@Component("usuarioBean")
@Scope("session")
public class UsuarioBean {

	private static final Logger logger = LoggerFactory
			.getLogger(UsuarioBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;

	private Usuario nuevo = new Usuario();
	private Usuario modifica = new Usuario();
	private Usuario usuarioSelec = null;

	private List<Usuario> list = new ArrayList<Usuario>();
	//private List<SelectItem> comboCampania = new ArrayList<SelectItem>();
	//private Long campaniaSelec = 0L;
	private List<SelectItem> comboRol = new ArrayList<SelectItem>();
	private List<SelectItem> comboEstado = new ArrayList<SelectItem>();
	private Long rolSelec = 0L;
	private Long rolSelecM = 0L;
	private String confirmarClave = "";

	private Usuario userSesion = null;

	private List<Usuario> filteredUser;

	private Message message = new Message();

	public UsuarioBean() {

	}

	public Long getRolSelecM() {
		return rolSelecM;
	}

	public void setRolSelecM(Long rolSelecM) {
		this.rolSelecM = rolSelecM;
	}

	public String getConfirmarClave() {
		return confirmarClave;
	}

	public void setConfirmarClave(String confirmarClave) {
		this.confirmarClave = confirmarClave;
	}

	public List<Usuario> getFilteredUser() {
		return filteredUser;
	}

	public void setFilteredUser(List<Usuario> filteredUser) {
		this.filteredUser = filteredUser;
	}

	public Long getRolSelec() {
		return rolSelec;
	}

	public void setRolSelec(Long rolSelec) {
		this.rolSelec = rolSelec;
	}

	public Usuario getModifica() {
		return modifica;
	}

	public void setModifica(Usuario modifica) {
		this.modifica = modifica;
	}

	public Usuario getNuevo() {
		return nuevo;
	}

	public void setNuevo(Usuario nuevo) {
		this.nuevo = nuevo;
	}

	public Usuario getUsuarioSelec() {
		return usuarioSelec;
	}

	public void setUsuarioSelec(Usuario usuarioSelec) {
		this.usuarioSelec = usuarioSelec;
	}

	public List<Usuario> getList() {
		return list = usuarioService.getListaUsuario();
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

	public List<SelectItem> getComboRol() {
		comboRol = rolService.getComboRol();
		return comboRol;
	}

	public void setComboRol(List<SelectItem> comboRol) {
		this.comboRol = comboRol;
	}

	public List<SelectItem> getComboEstado() {
		comboEstado = new ArrayList<SelectItem>();
		SelectItem item = null;
		item = new SelectItem(Constantes.ACTIVO, "Activo");
		comboEstado.add(item);
		item = new SelectItem(Constantes.INACTIVO, "Inactivo");
		comboEstado.add(item);

		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public void guardar() {

		validarSesion();

		if (!validarDatos())
			return;

		try {

			nuevo.setIdUsuario(usuarioService.getIdMax());
			nuevo.setRol(rolService.find(rolSelec));
			nuevo.setUsuarioRegistra(userSesion.getUsuario());
			//nuevo.setPassword(Seguridad.encrypt(nuevo.getClave()));
			nuevo.setEstado(Constantes.ACTIVO);

			usuarioService.save(nuevo);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
							message.getMessage("msgRegistrar")));
			limpiar();
		} catch (Exception e) {
			logger.info("ERRROR AL REGISTRAR USUARIO");
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR",
							message.getMessage("msgRegistrarError")));
		}
	}

	public void modificar() {

		validarSesion();

		try {

			if (!validarModificar())
				return;

			modifica.setUsuarioModifica(userSesion.getUsuario());
			// modifica.setFechaModificacion(new Date());
			modifica.setRol(rolService.find(rolSelecM));
			usuarioService.edit(modifica);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
							message.getMessage("msgModificar")));

			limpiar();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR",
							message.getMessage("msgModificarError")));
		}
	}

	public boolean validarModificar() {

		String txtObligatorio = message.getMessage("msgTxtObligatorio");

		if (modifica.getNombre().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}

		if (modifica.getApPaterno().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}

		if (rolSelecM.equals(0L)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							message.getMessage("msgSelecRol")));
			return false;
		}

		return true;

	}

	public void limpiar() {
		nuevo = new Usuario();
		modifica = new Usuario();
		rolSelec = 0L;
		// cargarUsuarios();
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
				// e.printStackTrace();
			}
		}
	}

	public boolean validarDatos() {

		String txtObligatorio = message.getMessage("msgTxtObligatorio");

		if (nuevo.getNombre().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}
		if (nuevo.getApPaterno().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}

		if (nuevo.getUsuario().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}

		if (nuevo.getClave().trim().equalsIgnoreCase("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							txtObligatorio));
			return false;
		}

		if (rolSelec.equals(0L)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							message.getMessage("msgSelecRol")));
			return false;
		}

		if (validarUsuario(nuevo.getUsuario())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							message.getMessage("msgUsernameExiste")));
			return false;
		}

		if (!nuevo.getClave().trim().equalsIgnoreCase(confirmarClave)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
							message.getMessage("msgConfirmarClave")));
			return false;
		}

		return true;
	}


	public boolean validarUsuario(String username) {

		Usuario user = usuarioService.findByUsername(username);

		if (user != null)
			return true;
		else
			return false;
	}

}
