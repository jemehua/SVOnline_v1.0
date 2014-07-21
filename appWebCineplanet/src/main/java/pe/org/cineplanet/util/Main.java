package pe.org.cineplanet.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ApplicationContext context = new ClassPathXmlApplicationContext("../WEB-INF/spring/root-context.xml");
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/root-context.xml");
		
		
        /*CampaniaService campaniaService = (CampaniaService)ctx.getBean("campaniaService");
        
        Campania campania = new Campania();
        campania.setIdCampania(100L);
        campania.setDenominacion("pruebaaa");
        
        campaniaService.save(campania);*/

	}

}
