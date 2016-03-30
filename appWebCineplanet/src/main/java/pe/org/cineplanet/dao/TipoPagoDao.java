package pe.org.cineplanet.dao;


import java.util.List;

import pe.org.cineplanet.model.jpa.TipoPago;

/**
 * 
 * @author Hever Pumallihua
 */
public interface TipoPagoDao {

	public abstract TipoPago find(Integer id) throws Exception;

	public abstract TipoPago save(TipoPago obj) throws Exception;

	public abstract TipoPago edit(TipoPago obj) throws Exception;

	public abstract List<TipoPago> getListaTipoPago() throws Exception;

}
