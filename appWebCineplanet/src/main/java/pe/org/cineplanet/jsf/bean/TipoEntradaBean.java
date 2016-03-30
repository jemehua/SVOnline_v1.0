package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;
import java.math.BigDecimal;
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

import pe.org.cineplanet.model.jpa.TipoEntrada;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.TipoEntradaService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("tipoEntradaBean")
@Scope("session")
public class TipoEntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(TipoEntradaBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private TipoEntradaService tipoEntradaService;

	private Integer tipoValeSelec = 1;
	private Usuario userSesion = null;
	private TipoEntrada tipoEntrada;
	// private Message message = new Message();

	private List<SelectItem> comboTipoVale = new ArrayList<SelectItem>();
	private List<TipoEntrada> listaTipoEntrada = new ArrayList<TipoEntrada>();
	private List<TipoEntrada> filteredListaTipoEntrada = new ArrayList<TipoEntrada>();

	public TipoEntradaBean() {
		super();
	}

	@PostConstruct
	public void init() {
		tipoEntrada = new TipoEntrada();
		// tipoEntrada.setTipoVale(1);
		cargarComboTipoVale();
		cargarListaTipoEntrada(Constantes.CERO);
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
		tipoEntrada = new TipoEntrada();
		// tipoEntrada.setTipoVale(1);
		tipoValeSelec = 1;
		cargarListaTipoEntrada(Constantes.CERO);
	}

	public void handleItemSelect() {
		logger.info("empresaSelec: " + tipoValeSelec);
		cargarListaTipoEntrada(tipoValeSelec);
	}

	public void guardar() {
		validarSesion();

		try {
			
			if (!validarDatos())
				return;
			
			tipoEntrada.setNombre(tipoEntrada.getNombre().toUpperCase());
			tipoEntrada.setTipoVale(tipoValeSelec);
			if (tipoEntrada.getIdTipoEntrada() != null) {
				tipoEntradaService.edit(tipoEntrada);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				tipoEntrada.setEstado(Constantes.ACTIVO);
				tipoEntradaService.save(tipoEntrada);
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
		cargarListaTipoEntrada(tipoValeSelec);
		limpiar();
	}

	public boolean validarDatos() throws Exception {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (tipoValeSelec == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (tipoEntrada.getNombre().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (tipoEntrada.getNombre().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (tipoEntrada.getPrecio().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (tipoEntrada.getPrecio().intValue() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (tipoEntrada.getDescripcion().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (tipoEntrada.getRestricciones().equals("")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (tipoEntradaService.existPrecioByTipoVale(tipoValeSelec, tipoEntrada.getPrecio())) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ya existe precio registrado para el tipo de vale"));
			return false;
		}

		return true;
	}

	public void cargarComboTipoVale() {
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0, "Seleccione tipo vale");
		listaCombo.add(fila);
		fila = new SelectItem(1, "Entradas");
		listaCombo.add(fila);
		fila = new SelectItem(2, "Combo");
		listaCombo.add(fila);
		comboTipoVale = listaCombo;
	}

	public void cargarListaTipoEntrada(Integer tipoVale) {
		try {
			listaTipoEntrada = tipoEntradaService.getListaTipoVale();
			filteredListaTipoEntrada = listaTipoEntrada;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// GET - SET

	public List<SelectItem> getComboTipoVale() {
		return comboTipoVale;
	}

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public List<TipoEntrada> getListaTipoEntrada() {
		return listaTipoEntrada;
	}

	public void setListaTipoEntrada(List<TipoEntrada> listaTipoEntrada) {
		this.listaTipoEntrada = listaTipoEntrada;
	}

	public List<TipoEntrada> getFilteredListaTipoEntrada() {
		return filteredListaTipoEntrada;
	}

	public void setFilteredListaTipoEntrada(
			List<TipoEntrada> filteredListaTipoEntrada) {
		this.filteredListaTipoEntrada = filteredListaTipoEntrada;
	}

	public void setComboTipoVale(List<SelectItem> comboTipoVale) {
		this.comboTipoVale = comboTipoVale;
	}

	public Integer getTipoValeSelec() {
		return tipoValeSelec;
	}

	public void setTipoValeSelec(Integer tipoValeSelec) {
		this.tipoValeSelec = tipoValeSelec;
	}

}
