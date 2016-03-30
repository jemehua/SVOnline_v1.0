package pe.org.cineplanet.svc;


import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

import pe.org.cineplanet.model.jpa.TipoEntrada;

/**
 * 
 * @author Hever Pumallihua
 */
public interface TipoEntradaService {

	public abstract TipoEntrada find(Long id) throws Exception;

	public abstract TipoEntrada save(TipoEntrada obj) throws Exception;

	public abstract TipoEntrada edit(TipoEntrada obj) throws Exception;

	public abstract List<TipoEntrada> getListaTipoVale() throws Exception;

	public abstract List<SelectItem> getComboTipoVale() throws Exception;
	
	public abstract boolean existPrecioByTipoVale(Integer tipoVale, BigDecimal precio) throws Exception;

}
