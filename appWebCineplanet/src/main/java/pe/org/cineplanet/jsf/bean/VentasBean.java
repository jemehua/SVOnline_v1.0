package pe.org.cineplanet.jsf.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.dto.MovimientoDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.Agencia;
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
import pe.org.cineplanet.svc.TipoPagoService;
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
	private TipoPagoService tipoPagoService;
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

	private List<SelectItem> comboEmpresa = new ArrayList<SelectItem>();
	private String empresaSelec = "";
	private List<SelectItem> comboAgencia = new ArrayList<SelectItem>();
	private String agenciaSelec;
	private List<SelectItem> comboTipoEntrada = new ArrayList<SelectItem>();
	private Long tipoEntradaSelec;
	private List<SelectItem> comboTipoDocumento = new ArrayList<SelectItem>();
	private Long tipoDocumentoSelec;
	private List<SelectItem> comboTipoPago = new ArrayList<SelectItem>();
	private Integer tipoPagoSelec;

	private Long idVentaSelec;
	private String nombreSelec;
	private StreamedContent file;

	// autocomplete
	private Cliente cliente;

	// detalle
	private List<DetalleVenta> listaDetVenta;
	private List<MovimientoDTO> listaMovimiento;

	private BigDecimal totalVenta = new BigDecimal(0.00);

	// agregar empresa
	// private Agencia empresa;
	private Agencia agencia;
	private Integer esEmpresa;

	// disabled add cliente
	//private boolean disabledAddEmpresa;

	// agregar cliente
	private Cliente c;

	public VentasBean() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("Init");
		venta = new Venta();
		venta.setSerie("001");
		venta.setNumero("11");
		// empresa = new Agencia();
		agencia = new Agencia();
		tipoEntradaSelec = 0L;
		tipoDocumentoSelec = 1L;
		tipoPagoSelec = 1;
		empresaSelec = "";
		agenciaSelec = "";
		cantidad = "";
		descTipoEntrada = "";
		cantidadDisponible = 0;
		idVentaSelec = 0L;
		listaDetalleVenta = new ArrayList<DetalleVenta>();
		cargarComboEmpresa();
		cargarComboAgencia();
		cargarComboTipoEntrada();
		cargarComboTipoDocumento();
		cargarComboTipoPago();
		cargarListaVentas();
		// message = new Message();

		esEmpresa = 1;
		c = new Cliente();
		//disabledAddEmpresa = true;
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
		tipoEntradaSelec = 0L;
		tipoDocumentoSelec = 1L;
		tipoPagoSelec = 1;
		empresaSelec = "";
		agenciaSelec = "";
		cantidad = "";
		descTipoEntrada = "";
		cantidadDisponible = 0;
		idVentaSelec = 0L;
		listaDetalleVenta = new ArrayList<DetalleVenta>();
		cargarComboEmpresa();
		cargarComboAgencia();
		cargarComboTipoEntrada();
		cargarComboTipoDocumento();
		cargarComboTipoPago();
		cargarListaVentas();
		totalVenta = new BigDecimal(0.00);
		cliente = new Cliente();
		// empresa = new Agencia();
		agencia = new Agencia();
		esEmpresa = 1;
		c = new Cliente();

		//disabledAddEmpresa = true;
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
			BigDecimal total = new BigDecimal(0.00);
			for (DetalleVenta row : listaDetalleVenta) {
				if (row.getTipoEntrada().getIdTipoEntrada() == tipoEntradaSelec) {
					row.setCantidad(Integer.valueOf(cantidad));
					row.setTotal(row.getTipoEntrada().getPrecio()
							.multiply(new BigDecimal(row.getCantidad())));
					total = total.add(row.getTotal());
					listaDetalleVenta.set(index, row);
					existe = true;
					// break;
				} else
					total = total.add(row.getTotal());
				index++;
			}

			if (!existe) {
				TipoEntrada tipoEntrada = tipoEntradaService
						.find(tipoEntradaSelec);

				DetalleVenta detalleVenta = new DetalleVenta();
				detalleVenta.setTipoEntrada(tipoEntrada);
				detalleVenta.setCantidad(Integer.valueOf(cantidad));
				detalleVenta.setTotal(tipoEntrada.getPrecio().multiply(
						new BigDecimal(detalleVenta.getCantidad())));
				detalleVenta.setEstado(Constantes.ACTIVO);
				listaDetalleVenta.add(detalleVenta);
				total = total.add(detalleVenta.getTotal());
			}

			totalVenta = total;
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"EXITO", "Se agrego el pedido con exito"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		limpiarPedido();
	}

	public void agregarEmpresa() {
		try {
			agencia = new Agencia();
			if (esEmpresa == 0)
				agencia.setIdAgenciaPadre(empresaSelec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarCliente() {
		try {
			c = new Cliente();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			venta.setTipoPago(tipoPagoService.find(tipoPagoSelec));

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
										+ " pedidos, por favor verifique."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR",
							"Error al registrar"));
		}

		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (cliente == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccione Cliente"));
			return false;
		}

		if (tipoPagoSelec == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (venta.getOtorgado().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (tipoDocumentoSelec == 0) {
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

	public void handleItemSelectEmpresa() {
		logger.info("empresaSelec: " + empresaSelec);
		/*if (!empresaSelec.equals(Constantes.VACIO))
			disabledAddEmpresa = false;*/

		cargarComboAgencia();
	}

	public void cargarComboEmpresa() {
		try {
			comboEmpresa = agenciaService.getComboEmpresa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarComboAgencia() {
		try {
			setComboAgencia(agenciaService.getComboAgencia(empresaSelec));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarComboTipoEntrada() {
		try {
			setComboTipoEntrada(tipoEntradaService.getComboTipoVale());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarComboTipoDocumento() {
		try {
			setComboTipoDocumento(tipoDocumentoService.getComboTipoDocumento());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarComboTipoPago() {
		try {
			setComboTipoPago(tipoPagoService.getComboTipoPago());
		} catch (Exception e) {
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

		ReporteEntradas ventaReport = new ReporteEntradas();
		byte[] bytes = ventaReport.entradas(listaVentas);

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

		String fileName = nombreSelec + "_" + sdf.format(new Date()) + ".pdf";
		file = new DefaultStreamedContent(new ByteArrayInputStream(bytes),
				"application/pdf", fileName);

		return file;
	}

	public void downloadAction(Long idVenta, String nombre) {
		System.out.println("downloadAction " + idVentaSelec);
		nombreSelec = nombre;
		idVentaSelec = idVenta;
	}

	public List<Cliente> complete(String query) throws Exception {

		System.out.println("query=" + query);
		List<Cliente> allThemes = clienteService.getListaCliente(empresaSelec, agenciaSelec);
		List<Cliente> filteredThemes = new ArrayList<Cliente>();

		String nombreCompleto = "";
		for (int i = 0; i < allThemes.size(); i++) {
			Cliente skin = allThemes.get(i);
			nombreCompleto = skin.getRazonSocial() + " " + skin.getApellidos();
			if (nombreCompleto.trim().toLowerCase().startsWith(query.toLowerCase())) {
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

	public void anularVenta(String idCodigo) {
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
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Error al anular pedido"));
		}

	}

	public void guardarEmpresa() {
		validarSesion();

		if (!validarDatosEmpresa())
			return;

		try {
			agencia.setNombre(agencia.getNombre().toUpperCase());
			// Agencia a = agenciaService.find(agencia.getIdAgencia());

			// if (a != null) {
			if (agencia.getIdAgencia() != null
					&& agencia.getIdAgencia().length() > 0) {
				agenciaService.edit(agencia);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				agencia.setEstado(Constantes.ACTIVO);
				if (esEmpresa == 1)
					agenciaService.save(agencia, Constantes.TIPO_EMPRESA);
				else
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
		if (esEmpresa == 1)
			cargarComboEmpresa();
		else
			cargarComboAgencia();
	}

	public boolean validarDatosEmpresa() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (esEmpresa == 0) {
			if (agencia.getIdAgenciaPadre().length() == 0) {
				ctx.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
						"Ingrese campos obligatorios"));
				return false;
			}
		}

		if (agencia.getNombre().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		return true;
	}

	public void guardarCliente() {
		validarSesion();

		if (!validarDatosCliente())
			return;

		try {

			// Cliente e = clienteService.find(cliente.getNroDocumento());
			// cliente.setAgencia(agenciaService.find(agenciaSelec));

			if (agenciaSelec.equals(Constantes.VACIO)) {

				if (!empresaSelec.equals(Constantes.VACIO))
					c.setAgencia(agenciaService.find(empresaSelec));

			} else {
				c.setAgencia(agenciaService.find(agenciaSelec));
			}

			// cliente.setAgencia(agencia);

			if (c.getIdCliente() != null) {
				c.setUsuModifica(userSesion.getUsuario());
				c.setFecModificacion(new Date());
				clienteService.edit(c);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				c.setIdCliente(clienteService.getMaxId());
				c.setEstado(Constantes.ACTIVO);
				c.setUsuRegistra(userSesion.getUsuario());
				c.setFecRegistro(new Date());
				clienteService.save(c);
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
	}

	public boolean validarDatosCliente() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (agenciaSelec.equals(Constantes.VACIO)) {
			if (empresaSelec.equals(Constantes.VACIO)) {
				ctx.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
						"Seleccione una Empresa/Agencia"));
				return false;
			}
		}

		if (c.getRazonSocial().trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		/*
		 * if (agencia == null) { ctx.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA",
		 * "Seleccion Agencia")); return false; }
		 */

		return true;
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

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public String getNombreSelec() {
		return nombreSelec;
	}

	public void setNombreSelec(String nombreSelec) {
		this.nombreSelec = nombreSelec;
	}

	public List<SelectItem> getComboTipoPago() {
		return comboTipoPago;
	}

	public void setComboTipoPago(List<SelectItem> comboTipoPago) {
		this.comboTipoPago = comboTipoPago;
	}

	public Integer getTipoPagoSelec() {
		return tipoPagoSelec;
	}

	public void setTipoPagoSelec(Integer tipoPagoSelec) {
		this.tipoPagoSelec = tipoPagoSelec;
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Integer getEsEmpresa() {
		return esEmpresa;
	}

	public void setEsEmpresa(Integer esEmpresa) {
		this.esEmpresa = esEmpresa;
	}

	public Cliente getC() {
		return c;
	}

	public void setC(Cliente c) {
		this.c = c;
	}

	/*public boolean isDisabledAddEmpresa() {
		return disabledAddEmpresa;
	}

	public void setDisabledAddEmpresa(boolean disabledAddEmpresa) {
		this.disabledAddEmpresa = disabledAddEmpresa;
	}*/

}
