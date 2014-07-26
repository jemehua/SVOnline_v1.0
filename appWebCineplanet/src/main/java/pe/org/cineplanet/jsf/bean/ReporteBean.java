package pe.org.cineplanet.jsf.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import pe.org.cineplanet.dto.ReporteDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.model.jpa.Usuario;
import pe.org.cineplanet.report.ReporteEntradas;
import pe.org.cineplanet.svc.AgenciaService;
import pe.org.cineplanet.svc.VentasService;
import pe.org.cineplanet.util.Constantes;
import pe.org.cineplanet.util.Excel;

/**
 * 
 * @author Hever Pumallihua
 */

@Component("reporteBean")
@Scope("session")
public class ReporteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ReporteBean.class);

	@Autowired
	private LoginController loginController;
	@Autowired
	private VentasService ventasService;

	private Usuario userSesion = null;
	private Date fecInicio;
	private Date fecFin;
	
	List<ReporteDTO> list = new ArrayList<ReporteDTO>();
	
	private StreamedContent file;
	
	public ReporteBean() {
		super();
	}

	@PostConstruct
	public void init() {
		fecInicio = new Date();
		fecFin = new Date();
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
		fecInicio = new Date();
		fecFin = new Date();
	}

	public void guardar() {
		validarSesion();

		if (!validarDatos())
			return;

		try {

			if(userSesion.getRol().getIdRol() == 1L)
				list = ventasService.getListaReporte(fecInicio, fecFin, "");
			else
				list = ventasService.getListaReporte(fecInicio, fecFin, userSesion.getUsuario());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		limpiar();
	}

	public boolean validarDatos() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (fecInicio == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}

		if (fecFin == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Ingrese campos obligatorios"));
			return false;
		}
		
		if (fecFin.before(fecInicio)) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"ADVERTENCIA", "Rango de fecha incorrecto"));
			return false;
		}

		return true;
	}
	
	public StreamedContent getFile() {

		System.out.println("StreamedContent.getFile()");

		if (list.size() > 0) {

			Excel excel =  new Excel();
			
			byte[] bytes = excel.crearExcel(list);
			
			SimpleDateFormat sdf =  new SimpleDateFormat("ddMMyyyy");
			
			String fileName = userSesion.getNombre()+sdf.format(new Date())+".xlsx";
			file = new DefaultStreamedContent(new ByteArrayInputStream(
					bytes), "application/vnd.ms-excel", fileName);
		}
		
		return file;

	}

	// GET - SET
	
	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

}
