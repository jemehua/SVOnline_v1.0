package pe.org.cineplanet.svc.impl;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.dao.EntradaDao;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.Entrada;
import pe.org.cineplanet.svc.EntradaService;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class EntradaServiceImpl implements EntradaService {

	@Autowired
	private EntradaDao entradaDao;
	@Autowired
	private DetalleEntradaDao detalleEntradaDao;

	@Transactional
	public Entrada find(Long id) throws Exception{
		return entradaDao.find(id);
	}

	@Transactional
	public Entrada save(Entrada obj) throws Exception{
		return entradaDao.save(obj);
	}
	
	@Transactional
	public Integer save(Entrada obj, List<DetalleEntrada> listaDetalle) throws Exception{
		
		entradaDao.save(obj);
		
		int count = 0;
		for(DetalleEntrada row : listaDetalle){
			
			//validacion codigo unico
			DetalleEntrada detEntrada = detalleEntradaDao.getByIdCodigo(row.getId().getIdCodigo());
			
			if(detEntrada == null){
				detalleEntradaDao.save(row);
				count++;
			}
			/*else{
				System.out.println("El codigo:"+row.getId().getIdCodigo()+" ya existe.");
			}*/
		}
		
		obj.setCantidad(count);
		entradaDao.edit(obj);
		
		return count;
		
	}

	@Transactional
	public Entrada edit(Entrada obj) throws Exception{
		return entradaDao.edit(obj);

	}

	public List<Entrada> getListaEntrada() throws Exception{
		return entradaDao.getListaEntrada();
	}

	public List<SelectItem> getComboTipoVale(){
		//return agenciaDao.getComboAgencia();
		List<SelectItem> listaCombo = new ArrayList<SelectItem>();
		SelectItem fila = new SelectItem(0L, "Seleccione Entrada");
		listaCombo.add(fila);
		
		List<Entrada> lista = new ArrayList<Entrada>();
		try {
			lista = entradaDao.getListaEntrada();
		} catch (Exception as) {
			as.printStackTrace();
		}

		for (Entrada tipo : lista) {
			fila = new SelectItem(tipo.getIdEntrada(), tipo.toString());
			listaCombo.add(fila);
		}
		return listaCombo;
	}
	
	public Long getMaxId() {
		try {
			return entradaDao.getMaxId()+1L;
		} catch (Exception e) {
			// TODO: handle exception
			return 1L;
		}
	}

}
