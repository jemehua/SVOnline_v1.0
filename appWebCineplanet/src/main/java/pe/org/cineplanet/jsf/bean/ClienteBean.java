package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.AgenciaService;
import pe.org.cineplanet.svc.ClienteService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("clienteBean")
@Scope("session")
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ClienteBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private AgenciaService agenciaService;

	private Usuario userSesion = null;
	private Cliente cliente;
	// private Message message = new Message();

	private List<Cliente> listaCliente = new ArrayList<Cliente>();
	private List<Cliente> filteredListaCliente = new ArrayList<Cliente>();
	private List<SelectItem> comboAgencia = new ArrayList<SelectItem>();
	private String agenciaSelec;

	public ClienteBean() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("Init");
		cliente = new Cliente();
		agenciaSelec = "";
		cargarComboAgencia();
		cargarListaClientes();
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
		cliente = new Cliente();
		agenciaSelec = "";
		cargarComboAgencia();
		cargarListaClientes();
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {

			// Cliente e = clienteService.find(cliente.getNroDocumento());

			cliente.setAgencia(agenciaService.find(agenciaSelec));

			if (cliente.getIdCliente() != null) {
				cliente.setUsuModifica(userSesion.getUsuario());
				cliente.setFecModificacion(new Date());
				clienteService.edit(cliente);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				cliente.setIdCliente(clienteService.getMaxId());
				cliente.setEstado(Constantes.ACTIVO);
				cliente.setUsuRegistra(userSesion.getUsuario());
				cliente.setFecRegistro(new Date());
				clienteService.save(cliente);
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
		cargarListaClientes();
		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		/*
		 * if (cliente.getNroDocumento().trim().length() == 0) {
		 * ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
		 * "ADVERTENCIA", "Ingrese campos obligatorios")); return false; }
		 * 
		 * if (!(cliente.getNroDocumento().length() == 8 ||
		 * cliente.getNroDocumento().length() == 11)) { ctx.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
		 * "Ingrese numero de documento valido")); return false; }
		 */

		if (cliente.getRazonSocial().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		/*
		 * if (cliente.getAbreviatura().trim().length() == 0) {
		 * ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
		 * "ADVERTENCIA", "Ingrese campos obligatorios")); return false; }
		 * 
		 * if (cliente.getDireccion().trim().length() == 0) {
		 * ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
		 * "ADVERTENCIA", "Ingrese campos obligatorios")); return false; }
		 * 
		 * if (cliente.getTelefono().trim().length() == 0) {
		 * ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
		 * "ADVERTENCIA", "Ingrese campos obligatorios")); return false; }
		 */

		if (agenciaSelec.equalsIgnoreCase("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccion Agencia"));
			return false;
		}

		return true;
	}

	public void cargarComboAgencia() {
		try {
			setComboAgencia(agenciaService.getComboAgencia());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarListaClientes() {
		try {
			listaCliente = clienteService.getListaCliente();
			filteredListaCliente = listaCliente;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// GET - SET

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public List<SelectItem> getComboAgencia() {
		return comboAgencia;
	}

	public void setComboAgencia(List<SelectItem> comboAgencia) {
		this.comboAgencia = comboAgencia;
	}

	public String getAgenciaSelec() {
		return agenciaSelec;
	}

	public void setAgenciaSelec(String agenciaSelec) {
		this.agenciaSelec = agenciaSelec;
	}

	public List<Cliente> getFilteredListaCliente() {
		return filteredListaCliente;
	}

	public void setFilteredListaCliente(List<Cliente> filteredListaCliente) {
		this.filteredListaCliente = filteredListaCliente;
	}

}
