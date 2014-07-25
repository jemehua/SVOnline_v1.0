package pe.org.cineplanet.jsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pe.org.cineplanet.model.jpa.Agencia;
import pe.org.cineplanet.svc.AgenciaService;

@Component("agenciaConverter")
@Scope("session")
// @FacesConverter("clienteConverter")
public class AgenciaConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private AgenciaService agenciaService;

	public Object getAsObject(FacesContext fc, UIComponent arg1, String value) {
		System.out.println("value=" + value);
		if(value != null && value.trim().length() > 0) {
			try {
				return agenciaService.find(value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		System.out.println("object="+object);
		if (object != null ) {
			System.out.println("getAsString");
			return ((Agencia) object).getIdAgencia();
		} else {
			return null;
		}
		
	}

}
