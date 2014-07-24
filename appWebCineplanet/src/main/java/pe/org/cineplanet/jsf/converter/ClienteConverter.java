package pe.org.cineplanet.jsf.converter;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.jsf.bean.VentasBean;
import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.svc.ClienteService;

@Component("clienteConverter")
@Scope("session")
//@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClienteService clienteService;
	
	public Object getAsObject(FacesContext fc, UIComponent arg1, String value) {
		
		System.out.println("value"+value);
		System.out.println("fc"+fc);
		if (value != null && value.trim().length() > 0) {
			/*ClienteService service = (ClienteService) fc.getExternalContext()
					.getApplicationMap().get("clienteService");*/
			
			/*VentasBean service2 = (VentasBean) fc.getExternalContext()
					.getApplicationMap().get("ventasBean");*/
			
			System.out.println("clienteService=" + clienteService);

			
			
			//if (clienteService != null) {
				try {

					return clienteService.find(Long.parseLong(value));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			/*} else
				return null;*/
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object != null) {
			
			System.out.println("getAsString");
			return String.valueOf(((Cliente) object).getIdCliente());
		} else {
			return null;
		}
	}

}
