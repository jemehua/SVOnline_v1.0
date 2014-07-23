package pe.org.cineplanet.jsf.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pe.org.cineplanet.model.jpa.Cliente;
import pe.org.cineplanet.svc.ClienteService;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent arg1, String value) {
		if (value != null && value.trim().length() > 0) {
			ClienteService service = (ClienteService) fc.getExternalContext()
					.getApplicationMap().get("clienteService");
			System.out.println("service=" + service);

			if (service != null) {
				try {

					List<Cliente> list = service.getListaCliente();

					if (list.size() > 0) {
						return service.getListaCliente().get(
								Integer.parseInt(value));
					} else {
						return null;
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} else
				return null;
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object != null) {
			return String.valueOf(((Cliente) object).getIdCliente());
		} else {
			return null;
		}
	}

}
