package pe.org.cineplanet.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import pe.org.cineplanet.dto.VentaDTO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ReporteEntradas {
	public StreamedContent entradas(List<VentaDTO> listVentaDTO) {
		StreamedContent pdfFile = null;

		try {

			if (!listVentaDTO.isEmpty()) {
				Document document = new Document();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter writer = PdfWriter.getInstance(document, baos);

				document.setPageSize(PageSize.A4);
				document.setMargins(36, 36, 36, 36);
				document.setMarginMirroring(true);
				document.open();

				PdfContentByte canvas = writer.getDirectContent();

				String tipoVale = "VALE CORPORATIVO";
				String tipoValeC = "VALE DE CONSUMO";
				SimpleDateFormat formatoFecha = new SimpleDateFormat(
						"dd/MM/yyyy", new Locale("ES"));
				String fechaEmision = formatoFecha.format(new Date());
				String diasValido = "Lu-Ma-Mi-Ju-Vi-Sa-Do";
				String path = "font/Bar.ttf";
				String ruta = FacesContext.getCurrentInstance()
						.getExternalContext().getRealPath(path);

				System.out.println("ruta"+ruta);
				
				
				Font fontTitulo = new Font(FontFamily.HELVETICA, 9, Font.BOLD,
						BaseColor.BLUE);
				BaseFont bf1 = BaseFont.createFont(ruta, BaseFont.IDENTITY_H,
						BaseFont.EMBEDDED);
				Font FONT1 = new Font(bf1, 12);
				Font fontCuerpoNegrita = FontFactory.getFont("Garamond", 8,
						Font.BOLD);
				Font fontCuerpo = FontFactory.getFont("Garamond", 7);
				Font fontRestNegrita = FontFactory.getFont("Garamond", 6,
						Font.BOLD);
				Font fontRest = FontFactory.getFont("Garamond", 5);
				String restriccionTitulo = "Restricciones";

				String phraseRC = "No valido en la primera semana de estreno. \n No renovables / No ampliables. \n"
						+ "No validos en salas 3D  (de acuerdo al tarifario). \n No se aceptan cambios ni devoluciones. \n"
						+ "No acumula puntos Premium. \n No valido salas prime.";

				String phraseRCC = "No renovables / No ampliables. \n No acumula puntos Premium.";

				String nota = "Nota:";
				String phraseN = "Prohibida su reventa y/o reproducci�n. \n Una vez canjeado este vale ser� inactivado.";

				String rutaImagen = "/images/log.jpg";
				String ruta2 = FacesContext.getCurrentInstance()
						.getExternalContext().getRealPath(rutaImagen);
				
				System.out.println("ruta2+"+ruta2);
				Image img = Image.getInstance(ruta2);

				PdfPTable tablaGeneral = new PdfPTable(2);
				tablaGeneral.setTotalWidth(590.0F);
				tablaGeneral.setLockedWidth(true);

				PdfPCell cellvacio = new PdfPCell(new Phrase(""));
				cellvacio.setColspan(4);
				cellvacio.setBorder(Rectangle.NO_BORDER);
				
				int cantidad = listVentaDTO.size();
				
				for (VentaDTO ventaDTO : listVentaDTO) {

					PdfPTable tablaContenido = new PdfPTable(4);
					float[] anchoTabla2Celda = { 8f, 4f, 8f, 4f };
					tablaContenido.setWidths(anchoTabla2Celda);
					tablaContenido.setTotalWidth(230.0F);
					tablaContenido.setLockedWidth(true);

					tablaContenido.addCell(cellvacio);

					if (ventaDTO.getTipoVale() == 1) {
						PdfPCell titulo = new PdfPCell(new Phrase(tipoVale,
								fontTitulo));
						titulo.setColspan(4);
						titulo.setBorder(Rectangle.NO_BORDER);
						titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablaContenido.addCell(titulo);
					} else {
						PdfPCell titulo = new PdfPCell(new Phrase(tipoValeC,
								fontTitulo));
						titulo.setColspan(4);
						titulo.setBorder(Rectangle.NO_BORDER);
						titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablaContenido.addCell(titulo);
					}

					tablaContenido.addCell(cellvacio);

					PdfPCell textoUno = new PdfPCell(new Phrase("Otorgado a:",
							fontCuerpoNegrita));
					textoUno.setHorizontalAlignment(Element.ALIGN_LEFT);
					textoUno.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(textoUno);

					PdfPCell respUno = new PdfPCell(new Phrase(
							ventaDTO.getOtorgado(), fontCuerpo));
					respUno.setHorizontalAlignment(Element.ALIGN_LEFT);
					respUno.setBorder(Rectangle.NO_BORDER);
					respUno.setColspan(2);
					tablaContenido.addCell(respUno);

					// imagen
					// Image img = Image.getInstance(IMG);
					img.scaleToFit(555f * 20f / 55f, 35);
					img.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);
					PdfPCell cellImage = new PdfPCell(img, false);
					cellImage.setRowspan(5);
					cellImage.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellImage.setBorder(Rectangle.NO_BORDER);
					cellImage.setPadding(5);
					tablaContenido.addCell(cellImage);

					PdfPCell textoDos = new PdfPCell(new Phrase("Valido para:",
							fontCuerpoNegrita));
					textoDos.setHorizontalAlignment(Element.ALIGN_LEFT);
					textoDos.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(textoDos);

					PdfPCell respDos = new PdfPCell(new Phrase(
							ventaDTO.getDescVale(), fontCuerpo));
					respDos.setHorizontalAlignment(Element.ALIGN_LEFT);
					respDos.setColspan(2);
					respDos.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(respDos);

					PdfPCell textoTres = new PdfPCell(new Phrase(
							"Fecha Emisión:", fontCuerpoNegrita));
					textoTres.setHorizontalAlignment(Element.ALIGN_LEFT);
					textoTres.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(textoTres);

					PdfPCell respTres = new PdfPCell(new Phrase(fechaEmision,
							fontCuerpo));
					respTres.setHorizontalAlignment(Element.ALIGN_LEFT);
					respTres.setBorder(Rectangle.NO_BORDER);
					respTres.setColspan(2);
					tablaContenido.addCell(respTres);

					PdfPCell textoCuatro = new PdfPCell(new Phrase("Validez:",
							fontCuerpoNegrita));
					textoCuatro.setHorizontalAlignment(Element.ALIGN_LEFT);
					textoCuatro.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(textoCuatro);

					PdfPCell respCuatro = new PdfPCell(new Phrase(
							ventaDTO.getFecValidez(), fontCuerpo));
					respCuatro.setHorizontalAlignment(Element.ALIGN_LEFT);
					respCuatro.setColspan(2);
					respCuatro.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(respCuatro);

					PdfPCell textoCinco = new PdfPCell(new Phrase(
							"Días Validos:", fontCuerpoNegrita));
					textoCinco.setHorizontalAlignment(Element.ALIGN_LEFT);
					textoCinco.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(textoCinco);

					PdfPCell respCinco = new PdfPCell(new Phrase(diasValido,
							fontCuerpo));
					respCinco.setHorizontalAlignment(Element.ALIGN_LEFT);
					respCinco.setBorder(Rectangle.NO_BORDER);
					respCinco.setColspan(2);
					tablaContenido.addCell(respCinco);

					tablaContenido.addCell(cellvacio);

					String codigoBarra = "*" + ventaDTO.getIdCodigo() + "*";
					PdfPCell cellNumeroCodigobarra = new PdfPCell(new Phrase(
							codigoBarra, FONT1));
					cellNumeroCodigobarra
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellNumeroCodigobarra.setColspan(4);
					cellNumeroCodigobarra.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(cellNumeroCodigobarra);

					PdfPCell cellCodigobarra = new PdfPCell(new Phrase(
							ventaDTO.getIdCodigo(), fontCuerpoNegrita));
					cellCodigobarra
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCodigobarra.setColspan(4);
					cellCodigobarra.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(cellCodigobarra);

					tablaContenido.addCell(cellvacio);

					PdfPCell cellRestriccion = new PdfPCell(new Phrase(
							restriccionTitulo, fontRestNegrita));
					cellRestriccion.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellRestriccion.setColspan(2);
					cellRestriccion.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(cellRestriccion);

					PdfPCell cellNota = new PdfPCell(new Phrase(nota,
							fontRestNegrita));
					cellNota.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellNota.setColspan(2);
					cellNota.setBorder(Rectangle.NO_BORDER);
					tablaContenido.addCell(cellNota);

					if (ventaDTO.getTipoVale() == 1) {
						PdfPCell cellRestriccionContenido = new PdfPCell(
								new Phrase(phraseRC, fontRest));
						cellRestriccionContenido.setColspan(2);
						cellRestriccionContenido.setBorder(Rectangle.NO_BORDER);
						cellRestriccionContenido
								.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						tablaContenido.addCell(cellRestriccionContenido);
					} else {
						PdfPCell cellRestriccionContenido = new PdfPCell(
								new Phrase(phraseRCC, fontRest));
						cellRestriccionContenido.setColspan(2);
						cellRestriccionContenido.setBorder(Rectangle.NO_BORDER);
						cellRestriccionContenido
								.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						tablaContenido.addCell(cellRestriccionContenido);

					}

					PdfPCell cellNotaContenido = new PdfPCell(new Phrase(
							phraseN, fontRest));
					cellNotaContenido.setColspan(2);
					cellNotaContenido.setBorder(Rectangle.NO_BORDER);
					cellNotaContenido
							.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tablaContenido.addCell(cellNotaContenido);

					tablaContenido.addCell(cellvacio);

					PdfPCell cellGen = new PdfPCell(tablaContenido);
					cellGen.setCellEvent(new DottedCell());
					cellGen.setBorder(PdfPCell.NO_BORDER);
					cellGen.setPadding(10f);
					tablaGeneral.addCell(cellGen);

				}

				if(cantidad%2==0){  
		             System.out.println(cantidad+" es par");  
		        }else{  
					PdfPTable tablaContenido = new PdfPTable(4);
					float[] anchoTabla2Celda = { 8f, 4f, 8f, 4f };
					tablaContenido.setWidths(anchoTabla2Celda);
					tablaContenido.setTotalWidth(230.0F);
					tablaContenido.setLockedWidth(true);
					
					PdfPCell cellGen = new PdfPCell(tablaContenido);
					cellGen.setCellEvent(new DottedCell());
					cellGen.setBorder(PdfPCell.NO_BORDER);
					cellGen.setPadding(10f);
					tablaGeneral.addCell(cellGen); 
		        } 
				
				document.add(tablaGeneral);

				document.close();

				byte[] bytes = baos.toByteArray();
				baos.close();
				String fileName = "Entrada.pdf";
				pdfFile = new DefaultStreamedContent(new ByteArrayInputStream(
						bytes), "application/pdf", fileName);
			}
		} catch (Exception e) {
			System.err.println("pdf error" + e.toString());
		}

		return pdfFile;
	}
}
