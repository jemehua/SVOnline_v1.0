package pe.org.cineplanet.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pe.org.cineplanet.dto.ReporteDTO;

public class Excel {

	private static final String[] TITLES = { "Nº", "FECHA PEDIDO", "DOCUMENTO",
			"SERIE", "NUMERO", "PAGO", "CODIGO EMPRESA", "NOMBRE EMPRESA", "CODIGO AGENCIA", "NOMBRE AGENCIA",
			"NOMBRES Y APELLIDOS", "E-S/.17", "E-S/.10", "E-S/.9", "E-S/.7",
			"C-S/.11", "C-S/.10", "C-S/.9", "C-S/.7", "TOTAL VENTA" };

	public Excel() {
		super();
	}

	public byte[] crearExcel(List<ReporteDTO> listReport) {

		XSSFWorkbook workbook = new XSSFWorkbook();// xlsx
		XSSFSheet sheet = workbook.createSheet("Pedidos");

		// Create a new font and alter it.
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(false);
		font.setStrikeout(false);
		// font.setColor(IndexedColors.WHITE.getIndex());
		
		Row headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(20);
		Cell headerCell;
		for (int i = 0; i < TITLES.length; i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(TITLES[i]);
			//headerCell.setCellStyle(cellStyle);
		}
		
		// Aqua background
		CellStyle cellDateStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		// cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		// cellStyle.setFillPattern(CellStyle.SQUARES);
		// cellStyle.setAlignment(CellStyle.ALIGN_JUSTIFY);
		// cellStyle.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		//cellStyle.setFont(font);
		
		CellStyle styleCurrencyFormat  = workbook.createCellStyle();
		//styleCurrencyFormat.setDataFormat((short)8);
		//styleCurrencyFormat.setDataFormat((short)7);
		styleCurrencyFormat.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00;\\-#,##0.00"));
		
		for (int r = 0; r < listReport.size(); r++) {
			Row row = sheet.createRow(r + 1);

			ReporteDTO obj = listReport.get(r);

			Field[] fields = ReporteDTO.class.getDeclaredFields();

			for (int c = 0; c < fields.length; c++) {
				Cell cell = row.createCell(c);
				try {

					String tipo = fields[c].getType().toString().toLowerCase();

					Method getter = ReporteDTO.class.getMethod("get"
							+ String.valueOf(fields[c].getName().charAt(0))
									.toUpperCase()
							+ fields[c].getName().substring(1));

					Object value = getter.invoke(obj, new Object[0]);
					
					if(value != null){
						if (tipo.contains("date")) {
							Date fecha = (Date) value;
							cell.setCellValue(fecha);
							cell.setCellStyle(cellDateStyle);
						} else if (tipo.contains("string"))
							cell.setCellValue((String) value);
						else if (tipo.contains("long")) {
							cell.setCellValue((Long) value);
						} else if (tipo.contains("double")){
							cell.setCellValue((Double) value);
							cell.setCellStyle(styleCurrencyFormat);
						} else if (tipo.contains("int"))
							cell.setCellValue((Integer) value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * try { File yourFile = new File("C:\\prueba.xlsx"); if
		 * (!yourFile.exists()) { yourFile.createNewFile(); } FileOutputStream
		 * oFile = new FileOutputStream(yourFile, false); workbook.write(oFile);
		 * oFile.close(); } catch (Exception e) { e.printStackTrace(); }
		 */

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bos.toByteArray();
	}

}
