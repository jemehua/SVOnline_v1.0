package pe.org.cineplanet.svc;


import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.TipoPago;

/**
 * 
 * @author Hever Pumallihua
 */
public interface TipoPagoService {

	public abstract TipoPago find(Integer id) throws Exception;

	public abstract TipoPago save(TipoPago obj) throws Exception;

	public abstract TipoPago edit(TipoPago obj) throws Exception;

	public abstract List<TipoPago> getListaTipoPago() throws Exception;

	public abstract List<SelectItem> getComboTipoPago() throws Exception;

}
