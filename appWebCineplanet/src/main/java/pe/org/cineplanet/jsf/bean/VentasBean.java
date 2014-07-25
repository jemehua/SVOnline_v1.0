package pe.org.cineplanet.jsf.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.dto.MovimientoDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.TipoEntrada;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.report.ReporteEntradas;
import pe.org.cineplanet.svc.AgenciaService;
import pe.org.cineplanet.svc.ClienteService;
import pe.org.cineplanet.svc.DetalleEntradaService;
import pe.org.cineplanet.svc.DetalleVentaService;
import pe.org.cineplanet.svc.EntradaService;
import pe.org.cineplanet.svc.MovimientoService;
import pe.org.cineplanet.svc.TipoDocumentoService;
import pe.org.cineplanet.svc.TipoEntradaService;
import pe.org.cineplanet.svc.VentasService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("ventasBean")
@Scope("session")
public class VentasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(VentasBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private TipoEntradaService tipoEntradaService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private VentasService ventasService;
	@Autowired
	private DetalleVentaService detalleVentaService;
	@Autowired
	private EntradaService entradaService;
	@Autowired
	private DetalleEntradaService detalleEntradaService;
	@Autowired
	private AgenciaService agenciaService;
	@Autowired
	private MovimientoService movimientoService;

	private Usuario userSesion = null;
	// private Message message = new Message();
	private Venta venta;
	private List<Venta> listaVenta = new ArrayList<Venta>();
	private List<Venta> filteredListaVenta = new ArrayList<Venta>();

	private List<DetalleVenta> listaDetalleVenta = new ArrayList<DetalleVenta>();

	private String cantidad;
	private String descTipoEntrada;
	private Integer cantidadDisponible;
	//private String descAgencia;

	private List<SelectItem> comboTipoEntrada = new ArrayList<SelectItem>();
	private Long tipoEntradaSelec;
	// private List<SelectItem> comboCliente = new ArrayList<SelectItem>();
	// private Long clienteSelec;
	private List<SelectItem> comboTipoDocumento = new ArrayList<SelectItem>();
	private Long tipoDocumentoSelec;

	private Long idVentaSelec;
	private StreamedContent file;

	// autocomplete
	private Cliente cliente;

	// detalle
	//private Long idVentaSelec2;
	private List<DetalleVenta> listaDetVenta;
	private List<MovimientoDTO> listaMovimiento;

	public VentasBean() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("Init");
		venta = new Venta();
		// detalleVenta = new DetalleVenta();
		tipoEntradaSelec = 0L;
		// clienteSelec = 0L;
		tipoDocumentoSelec = 0L;
		cantidad = "";
		descTipoEntrada = "";
		cantidadDisponible = 0;
		//descAgencia = "";
		idVentaSelec = 0L;
		listaDetalleVenta = new ArrayList<DetalleVenta>();
		cargarComboTipoEntrada();
		// cargarComboCliente();
		cargarComboTipoDocumento();
		cargarListaVentas();
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
		venta = new Venta();
		// detalleVenta = new DetalleVenta();
		tipoEntradaSelec = 0L;
		// clienteSelec = 0L;
		tipoDocumentoSelec = 0L;
		cantidad = "";
		descTipoEntrada = "";
		cantidadDisponible = 0;
		//descAgencia = "";
		idVentaSelec = 0L;
		listaDetalleVenta = new ArrayList<DetalleVenta>();
		cargarComboTipoEntrada();
		// cargarComboCliente();
		cargarComboTipoDocumento();
		cargarListaVentas();

		cliente = new Cliente();
	}

	public void limpiarPedido() {
		cantidad = "";
		descTipoEntrada = "";
		cantidadDisponible = 0;
		tipoEntradaSelec = 0L;
	}

	public void agregar() {
		// validar si existe vales
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (!validarAgregar())
			return;

		try {

			int index = 0;
			boolean existe = false;
			for (DetalleVenta row : listaDetalleVenta) {
				if (row.getTipoEntrada().getIdTipoEntrada() == tipoEntradaSelec) {
					row.setCantidad(Integer.valueOf(cantidad));
					listaDetalleVenta.set(index, row);
					existe = true;
					break;
				}
				index++;
			}

			if (!existe) {
				TipoEntrada tipoEntrada = tipoEntradaService
						.find(tipoEntradaSelec);

				DetalleVenta detalleVenta = new DetalleVenta();
				detalleVenta.setTipoEntrada(tipoEntrada);
				detalleVenta.setCantidad(Integer.valueOf(cantidad));
				detalleVenta.setTotal(tipoEntrada.getPrecio().multiply(new BigDecimal(detalleVenta.getCantidad())));
				detalleVenta.setEstado(Constantes.ACTIVO);
				listaDetalleVenta.add(detalleVenta);
			}

			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"EXITO", "Se agrego el pedido con exito"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		limpiarPedido();
	}

	public boolean validarAgregar() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (tipoEntradaSelec == 0L) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccione Tipo Entrada"));
			return false;
		}

		if (cantidad.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese cantidad de pedidos"));
			return false;
		}

		if (noExisteVale(tipoEntradaSelec, Integer.valueOf(cantidad))) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA",
					"La cantidad disponible es menor a la cantidad de pedidos"));
			return false;
		}

		return true;
	}

	public boolean noExisteVale(Long idTipoEntrada, Integer cantidad) {

		if (cantidadDisponible < cantidad)
			return true;
		else
			return false;
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {

			// venta.setCliente(clienteService.find(clienteSelec));
			venta.setCliente(cliente);
			venta.setTipoDocumento(tipoDocumentoService
					.find(tipoDocumentoSelec));

			if (venta.getIdVenta() != null) {

				venta.setUsuModifica(userSesion.getUsuario());
				venta.setFecModificacion(new Date());
				ventasService.edit(venta);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				venta.setIdVenta(ventasService.getMaxId());
				venta.setEstado(Constantes.ACTIVO);
				venta.setUsuRegistra(userSesion.getUsuario());
				venta.setFecRegistro(new Date());

				Integer rpta = ventasService.save(venta, listaDetalleVenta);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Se ha registrado " + rpta
										+ " pediso, por favor verifique"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR",
							"Error al registrar"));
		}
		// cargarListaDetalleVenta();
		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (cliente == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccione Cliente"));
			return false;
		}

		if (venta.getOtorgado().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (venta.getSerie().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (venta.getNumero().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (listaDetalleVenta.size() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese Pedido"));
			return false;
		}

		return true;
	}

	public void cargarComboTipoEntrada() {
		try {
			setComboTipoEntrada(tipoEntradaService.getComboTipoVale());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public void cargarComboCliente() { try {
	 * setComboCliente(clienteService.getComboCliente()); } catch (Exception e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	public void cargarComboTipoDocumento() {
		try {
			setComboTipoDocumento(tipoDocumentoService.getComboTipoDocumento());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarListaVentas() {
		try {
			listaVenta = ventasService.getListaVentas();
			filteredListaVenta = listaVenta;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleItemSelect() {
		cargarDescTipoEntrada();
		cargarCantidadDisponibleByTipoEntrada();

	}

	public void cargarDescTipoEntrada() {
		descTipoEntrada = "";
		if (tipoEntradaSelec != 0L) {
			TipoEntrada tipoEntrada = null;
			try {
				tipoEntrada = tipoEntradaService.find(tipoEntradaSelec);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (tipoEntrada != null) {
				descTipoEntrada = tipoEntrada.getDescripcion();
			}
		}
	}

	public void cargarCantidadDisponibleByTipoEntrada() {
		cantidadDisponible = 0;
		List<DetalleEntrada> listDetalle = new ArrayList<DetalleEntrada>();
		if (tipoEntradaSelec != 0L) {
			try {
				listDetalle = detalleEntradaService
						.getListaByTipoEntrada(tipoEntradaSelec);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (listDetalle.size() > 0)
			cantidadDisponible = listDetalle.size();

	}

	 /*public void cargarDescAgenciaByCliente() {
		descAgencia = "";
		if (cliente != null) {

			Cliente cliente = null;
			try {
				cliente = clienteService.find(cliente.getIdCliente());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (cliente != null) {
				descAgencia = cliente.getAgencia().toString();
			}
		}
	}*/

	public StreamedContent getFile() {

		System.out.println("StreamedContent.getFile()");

		List<VentaDTO> listaVentas = new ArrayList<VentaDTO>();
		System.out.println("idVentaSelec" + idVentaSelec);

		if (idVentaSelec != 0L) {

			try {
				listaVentas = movimientoService.getListaVenta(idVentaSelec);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (VentaDTO row : listaVentas) {
			System.out.println("Codigo=" + row.getIdCodigo());
		}

		ReporteEntradas ventaReport = new ReporteEntradas();
		return ventaReport.entradas(listaVentas);

	}

	public void downloadAction(Long idVenta) {
		System.out.println("downloadAction " + idVentaSelec);
		idVentaSelec = idVenta;
	}

	public List<Cliente> complete(String query) throws Exception {

		System.out.println("query=" + query);
		List<Cliente> allThemes = clienteService.getListaCliente();
		List<Cliente> filteredThemes = new ArrayList<Cliente>();

		for (int i = 0; i < allThemes.size(); i++) {
			Cliente skin = allThemes.get(i);
			if (skin.getRazonSocial().toLowerCase()
					.startsWith(query.toLowerCase())) {
				filteredThemes.add(skin);
			}
		}

		return filteredThemes;
	}

	public void verDetalleVenta(Long idVenta) {

		try {
			listaDetVenta = detalleVentaService.getListaByIdVenta(idVenta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onRowToggle(ToggleEvent event) {
		try {
			DetalleVenta d = ((DetalleVenta) event.getData());

			if (d != null) {

				cargarListaMovimientos(d.getId());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarListaMovimientos(DetalleVentaPK id) {

		try {
			listaMovimiento = movimientoService.getListaMovimiento(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void anularVenta(String idCodigo){
		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			Movimiento m = movimientoService.find(idCodigo);
			movimientoService.anularVenta(m);
			
			verDetalleVenta(m.getId().getIdVenta());
			cargarListaVentas();
			
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"EXITO", "Pedido anulado con exito"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"EXITO", "Error al anular pedido"));
		}
		
		
	}

	// GET - SET

	public List<SelectItem> getComboTipoEntrada() {
		return comboTipoEntrada;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public List<Venta> getListaVenta() {
		return listaVenta;
	}

	public void setListaVenta(List<Venta> listaVenta) {
		this.listaVenta = listaVenta;
	}

	public void setComboTipoEntrada(List<SelectItem> comboTipoEntrada) {
		this.comboTipoEntrada = comboTipoEntrada;
	}

	public Long getTipoEntradaSelec() {
		return tipoEntradaSelec;
	}

	public void setTipoEntradaSelec(Long tipoEntradaSelec) {
		this.tipoEntradaSelec = tipoEntradaSelec;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public List<DetalleVenta> getListaDetalleVenta() {
		return listaDetalleVenta;
	}

	public void setListaDetalleVenta(List<DetalleVenta> listaDetalleVenta) {
		this.listaDetalleVenta = listaDetalleVenta;
	}

	public List<SelectItem> getComboTipoDocumento() {
		return comboTipoDocumento;
	}

	public void setComboTipoDocumento(List<SelectItem> comboTipoDocumento) {
		this.comboTipoDocumento = comboTipoDocumento;
	}

	public Long getTipoDocumentoSelec() {
		return tipoDocumentoSelec;
	}

	public void setTipoDocumentoSelec(Long tipoDocumentoSelec) {
		this.tipoDocumentoSelec = tipoDocumentoSelec;
	}

	public String getDescTipoEntrada() {
		return descTipoEntrada;
	}

	public void setDescTipoEntrada(String descTipoEntrada) {
		this.descTipoEntrada = descTipoEntrada;
	}

	public Integer getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(Integer cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public Long getIdVentaSelec() {
		return idVentaSelec;
	}

	public void setIdVentaSelec(Long idVentaSelec) {
		this.idVentaSelec = idVentaSelec;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Venta> getFilteredListaVenta() {
		return filteredListaVenta;
	}

	public void setFilteredListaVenta(List<Venta> filteredListaVenta) {
		this.filteredListaVenta = filteredListaVenta;
	}

	public List<DetalleVenta> getListaDetVenta() {
		return listaDetVenta;
	}

	public void setListaDetVenta(List<DetalleVenta> listaDetVenta) {
		this.listaDetVenta = listaDetVenta;
	}

	public List<MovimientoDTO> getListaMovimiento() {
		return listaMovimiento;
	}

	public void setListaMovimiento(List<MovimientoDTO> listaMovimiento) {
		this.listaMovimiento = listaMovimiento;
	}

}
