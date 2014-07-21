package pe.org.cineplanet.jsf.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleEntradaPK;
import pe.org.cineplanet.model.jpa.Entrada;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.svc.EntradaService;
import pe.org.cineplanet.svc.TipoEntradaService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("entradaBean")
@Scope("session")
public class EntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(EntradaBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private EntradaService entrdService;
	@Autowired
	private TipoEntradaService tipoEntradaService;

	private Usuario userSesion = null;
	// private Message message = new Message();

	private List<Entrada> listaEntrada = new ArrayList<Entrada>();
	private List<SelectItem> comboTipoEntrada = new ArrayList<SelectItem>();
	private Long tipoEntradaSelec;

	private byte[] bFile;
	private Entrada entrada;
	private String msgDoc;

	public EntradaBean() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("Init");
		entrada = new Entrada();
		tipoEntradaSelec = 0L;
		bFile = null;
		msgDoc = "";
		cargarComboTipoEntrada();
		cargarListaEntradas();
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
		entrada = new Entrada();
		tipoEntradaSelec = 0L;
		bFile = null;
		msgDoc = "";
		cargarComboTipoEntrada();
		cargarListaEntradas();
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {

			entrada.setTipoEntrada(tipoEntradaService.find(tipoEntradaSelec));

			if (entrada.getIdEntrada() != null) {

				entrada.setUsuModifica(userSesion.getUsuario());
				entrada.setFecModificacion(new Date());
				entrdService.edit(entrada);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
								"Registro Modificado"));
			} else {
				entrada.setIdEntrada(entrdService.getMaxId());
				entrada.setEstado(Constantes.ACTIVO);
				entrada.setUsuRegistra(userSesion.getUsuario());
				entrada.setFecRegistro(new Date());

				List<DetalleEntrada> listaDetalle = new ArrayList<DetalleEntrada>();
				DetalleEntrada detalleEntrada;
				DetalleEntradaPK id;

				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
						bFile);
				XSSFWorkbook wb = new XSSFWorkbook(byteArrayInputStream);
				XSSFSheet sheet = wb.getSheetAt(0);
				//Iterator iterator = sheet.rowIterator();
				// int rownum = 0;
				// boolean ultimoRegistro = false;
				// while (iterator.hasNext()) {

				for (int i = 0, z = sheet.getLastRowNum(); i <= z; i++) {
					detalleEntrada = new DetalleEntrada();

					XSSFRow row = sheet.getRow(i);
					if (row != null) {
						if (row.getCell(0) != null) {
							id = new DetalleEntradaPK(row.getCell(0).toString()
									.trim(), entrada.getIdEntrada());
							detalleEntrada.setId(id);
							detalleEntrada.setEstado(Constantes.ACTIVO);

							listaDetalle.add(detalleEntrada);
						}
					}

					// rownum++;
				}

				entrada.setCantidad(listaDetalle.size());

				entrdService.save(entrada, listaDetalle);

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
		cargarListaEntradas();
		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (bFile == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccione archivo (.xlsx)"));
			return false;
		}

		if (tipoEntradaSelec == 0L) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Seleccione Tipo de Entrada"));
			return false;
		}

		if (entrada.getFecInicio() == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese Fecha Incio"));
			return false;
		}

		if (entrada.getFecFin() == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese Fecha Fin"));
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

	public void cargarListaEntradas() {
		try {
			setListaEntrada(entrdService.getListaEntrada());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		msgDoc = "";
		String fileName = "";
		try {
			UploadedFile fu = event.getFile();
			fileName = fu.getFileName();
			bFile = fu.getContents();
			msgDoc = fileName + " cargado con exito.";
		} catch (Exception e) {
			e.printStackTrace();
			msgDoc = "Error al cargar archivo " + fileName;
		}
	}

	// GET - SET

	public Entrada getEntrada() {
		return entrada;
	}

	public List<Entrada> getListaEntrada() {
		return listaEntrada;
	}

	public void setListaEntrada(List<Entrada> listaEntrada) {
		this.listaEntrada = listaEntrada;
	}

	public List<SelectItem> getComboTipoEntrada() {
		return comboTipoEntrada;
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

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public String getMsgDoc() {
		return msgDoc;
	}

	public void setMsgDoc(String msgDoc) {
		this.msgDoc = msgDoc;
	}

}
