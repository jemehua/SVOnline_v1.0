package pe.org.cineplanet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ApplicationContext context = new ClassPathXmlApplicationContext("../WEB-INF/spring/root-context.xml");
		//ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/root-context.xml");
		
		
        /*CampaniaService campaniaService = (CampaniaService)ctx.getBean("campaniaService");
        
        Campania campania = new Campania();
        campania.setIdCampania(100L);
        campania.setDenominacion("pruebaaa");
        
        campaniaService.save(campania);*/
		
		
		/*String colores = "rojo. amarillo. verde. azul. morado. marrón.";
		String newCad = colores.replace(".", ",");
		System.out.println(newCad);
		String[] arrayColores = newCad.split(",");
		 
		// En este momento tenemos un array en el que cada elemento es un color.
		for (int i = 0; i < arrayColores.length; i++) {
			System.out.println(arrayColores[i].trim());
		}*/
		
		/*String cad = "hever. sdsd.";
		
		String phraseRC = "";
		if(cad != null){
			
			String newCad = cad.replace(".", ",");
			System.out.println(newCad);
			String[] arrayColores = newCad.split(",");
			 
			// En este momento tenemos un array en el que cada elemento es un color.
			for (int i = 0; i < arrayColores.length; i++) {
				if(i==0)
					phraseRC += "";
				else
					phraseRC += "'\n'";
				phraseRC += arrayColores[i].trim();
			}
		}
		System.out.println(phraseRC);*/
		
	//}
	
	
	static XSSFWorkbook workbook = new XSSFWorkbook();

    public void getList(List<String> listcriteria){
        Map<Integer, List<String>> hashmap = new HashMap<Integer , List<String>>();
        String[] data1 = {"ACSS Description1", "ACSS Description2", "ACSS Description3", "SACSS Description4"};
        List s1 = Arrays.asList(data1);
        hashmap.put(1,s1); 
        String[] data2 = {"11", "1", "4", "12"};
        List s2 = Arrays.asList(data2);
        hashmap.put(2,s2); 

        System.out.println("hashmap : "+hashmap);
        Set<Integer> keyset = hashmap.keySet();
        int rownum = 1;
        int cellnum = 0;
        XSSFSheet sheet = workbook.getSheetAt(0);
        for(Integer key : keyset){
            List<String> nameList = hashmap.get(key);
            for(String s : nameList){
                XSSFRow row = sheet.getRow(rownum++);
                Cell cell = row.getCell(cellnum);
                if(null!=cell){
                    cell.setCellValue(s);
                }
            }
            cellnum++;
            rownum=1;
        }
    }

//    public static void main(String[] args) throws IOException {
//        //Creation of List from an Array to test getList Method
//        String[] ss = {"a","b","c","d","e"};
//        List<String> listcriteria = new ArrayList<String>();
//        listcriteria.addAll(Arrays.asList(ss));
//        /***********************************************************/
//
//        Main t = new Main();
//        // Because I put 5 key values pairs in hashmap (see getList method), I create  Writesheet.xlsx 
//        // file that contains 5 rows each row contains 5 cell
//        FileOutputStream out = new FileOutputStream( new File("C:\\Writesheet.xlsx"));
//        XSSFSheet sheet = workbook.createSheet();
//        for(int i = 0;i<5;i++){
//            XSSFRow row = sheet.createRow(i);
//            for(int j=0;j<5;j++)
//            row.createCell(j);
//        }
//        workbook.write(out);
//        out.close();//end creation of Excel file
//
//        // I open Writesheet.xlsx file and write the data on it
//        InputStream inp = new FileInputStream( new File("C:\\Writesheet.xlsx"));
//        workbook = new XSSFWorkbook(inp);
//        // listcriteria contains the data that will be written it on  Writesheet.xlsx
//        t.getList(listcriteria);
//        out = new FileOutputStream( new File("C:\\Writesheet.xlsx"));
//        workbook.write(out);
//        out.close();
//        inp.close();
//        System.out.println("Writesheet.xlsx written successfully" );
//        
//      /*  try { File yourFile = new File("C:\\prueba.xlsx"); if
//		 * (!yourFile.exists()) { yourFile.createNewFile(); } FileOutputStream
//		 * oFile = new FileOutputStream(yourFile, false); workbook.write(oFile);
//		 * oFile.close(); } catch (Exception e) { e.printStackTrace(); }*/
//
//    }
    
    public static void main(String[] args) {
    	
    	Object[] o = new Object[2];
    	o[0] = "hola ";
    	o[1] = "tu ";
//    	o[2] = "puesdes";
    	
    	
    	System.out.println(o.length );
    	
    	
//        //Create a new Workbook
//        XSSFWorkbook workbook = new XSSFWorkbook();
//
//        //Create a blank sheet
//        XSSFSheet sheet = workbook.createSheet("Student data");
//
//        //Create the data for the excel sheet
//        Map<String, Object[]> data = new TreeMap<String, Object[]>();
//        data.put("1", new Object[] {"ID", "FIRSTNAME", "LASTNAME"});
//        data.put("2", new Object[] {1, "Randy", "Maven"});
//        data.put("3", new Object[] {2, "Raymond", "Smith"});
//        data.put("4", new Object[] {3, "Dinesh", "Arora"});
//        data.put("5", new Object[] {4, "Barbra", "Klien"});
//
//        //Iterate over data and write it to the sheet
//        Set<String> keyset = data.keySet();
//        int rownum = 0;
//        for (String key : keyset)
//        {
//            Row row = sheet.createRow(rownum++);
//            Object [] objArr = data.get(key);
//            int cellnum = 0;
//            for (Object obj : objArr)
//            {
//                Cell cell = row.createCell(cellnum++);
//                if(obj instanceof String)
//                    cell.setCellValue((String)obj);
//                else if(obj instanceof Integer)
//                    cell.setCellValue((Integer)obj);
//            }
//        }
//        //Save the excel sheet
//        try{
//            FileOutputStream out = new FileOutputStream(new File("c:\\javahabitExcelDemo.xlsx"));
//            workbook.write(out);
//            out.close();
//            System.out.println("javahabitExcelDemo.xlsx Successfully created");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
