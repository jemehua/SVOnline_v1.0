package pe.org.cineplanet.svc.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleEntradaDao;
import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.dao.MovimientoDao;
import pe.org.cineplanet.dao.TipoEntradaDao;
import pe.org.cineplanet.dao.VentasDao;
import pe.org.cineplanet.dto.ReporteDTO;
import pe.org.cineplanet.model.jpa.DetalleEntrada;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;
import pe.org.cineplanet.model.jpa.TipoEntrada;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.svc.VentasService;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Service
public class VentasServiceImpl implements VentasService {

	@Autowired
	private VentasDao ventasDao;
	@Autowired
	private DetalleVentaDao detalleVentaDao;
	@Autowired
	private MovimientoDao movimientoDao;
	@Autowired
	private DetalleEntradaDao detalleEntradaDao;
	@Autowired
	private TipoEntradaDao tipoEntradaDao;

	@Transactional
	public Venta find(Long id) throws Exception {
		return ventasDao.find(id);
	}

	@Transactional
	public Venta save(Venta obj) throws Exception {
		return ventasDao.save(obj);
	}

	@Transactional
	public Integer save(Venta obj, List<DetalleVenta> listaDetalleVenta)
			throws Exception {
		ventasDao.save(obj);

		Integer total = 0;
		for (DetalleVenta row : listaDetalleVenta) {
			DetalleVentaPK id = new DetalleVentaPK(obj.getIdVenta(), row.getTipoEntrada().getIdTipoEntrada());
			row.setId(id);

			detalleVentaDao.save(row);
			
			List<DetalleEntrada> listaDetalleEntradas = detalleEntradaDao.getListaByTipoEntrada(row
					.getTipoEntrada().getIdTipoEntrada());
			
			//System.out.println("listaDetalleEntradas por tipoEntrada"+listaDetalleEntradas.size());
			int count = 0;
			for(int i=0; i<row.getCantidad(); i++){
				
				DetalleEntrada detalleEntrada = listaDetalleEntradas.get(i);
				
				Movimiento mov;
				try {
					mov = movimientoDao.getByIdCodigo(detalleEntrada.getId().getIdCodigo());
				} catch (Exception e) {
					// TODO: handle exception
					mov = null;
				}
				
				if(mov == null) {
		
					MovimientoPK idMov = new MovimientoPK(id.getIdVenta(), id.getIdTipoEntrada(), detalleEntrada.getId().getIdCodigo(), detalleEntrada.getId().getIdEntrada());
					
					Movimiento movimiento = new Movimiento();
					movimiento.setId(idMov);
					movimiento.setEstado(Constantes.ACTIVO);
					
					movimientoDao.save(movimiento);
					
					detalleEntrada.setEstado(Constantes.VENDIDO);
					detalleEntradaDao.edit(detalleEntrada);
					
					count++;
				}else{
					System.out.println("El codigo "+detalleEntrada.getId().getIdCodigo()+" ya ha sido asignado");
				}
			}
			
			if(row.getCantidad() == count)
				total += row.getCantidad();
			else
				total = count;
		}
		obj.setTotal(total);
		ventasDao.edit(obj);
		
		return total;
	}

	@Transactional
	public Venta edit(Venta obj) throws Exception {
		return ventasDao.edit(obj);
	}

	public List<Venta> getListaVentas() throws Exception {
		return ventasDao.getListaVentas();
	}

	public Long getMaxId() {
		try {
			return ventasDao.getMaxId() + 1L;
		} catch (Exception e) {
			// TODO: handle exception
			return 1L;
		}
	}
	
	public List<ReporteDTO> getListaReporte(Date fecInicio, Date fecFin, String usr) throws Exception {
		return ventasDao.getListaReporte(fecInicio, fecFin, usr);
	}

	public Map<String, Object[]> getMapVentaReporte(Date fecInicio,
			Date fecFin, String usr) throws Exception {
		
		List<TipoEntrada> listTipoEntradas = tipoEntradaDao.getListaTipoVale();
		
		List<Object[]> listVenta = ventasDao.getListaVenta(fecInicio, fecFin, usr);
		
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		 
		int length = 12 + listTipoEntradas.size();
		
		Object[] cad = new Object[length];
    	cad[0] = "Nº";
    	cad[1] = "FECHA PEDIDO";
    	cad[2] = "DOCUMENTO";
    	cad[3] = "SERIE";
    	cad[4] = "NUMERO";
    	cad[5] = "PAGO";
    	cad[6] = "CODIGO EMPRESA";
    	cad[7] = "NOMBRE EMPRESA";
    	cad[8] = "CODIGO AGENCIA";
    	cad[9] = "NOMBRE AGENCIA";
    	cad[10] = "NOMBRES Y APELLIDOS";
		
    	int i = 11;
    	for(TipoEntrada tipoEntrada : listTipoEntradas){
    		if(tipoEntrada.getTipoVale() == Constantes.TIPO_VALE_ENTRADA)
    			cad[i] = "E-S/. " + tipoEntrada.getPrecio();
    		else
    			cad[i] = "C-S/. " + tipoEntrada.getPrecio();
    		i++;
    	}
    	cad[i] = "TOTAL VENTA";
    	
    	data.put("1", cad);
    	
    	int count = 1;
    	for (Object[] row : listVenta) {
    		
			cad = new Object[length];
			cad[0] = count;
	    	cad[1] = (Date) row[3];
	    	cad[2] = (String) row[6];
	    	cad[3] = (String) row[4];
	    	cad[4] = (String) row[5];
	    	cad[5] = (String) row[7];
	    	
	    	if(row[10] != null && row[11] != null){
	    		cad[6] = (String) row[10];
		    	cad[7] = (String) row[11];
		    	cad[8] = (String) row[8];
		    	cad[9] = (String) row[9];	    		
			}else{
				cad[6] = "";
		    	cad[7] = "";
		    	cad[8] = (String) row[8];
		    	cad[9] = (String) row[9];
			}
	    	cad[10] = (String) row[1] +" "+ row[2];
	    	
	    	
	    	int y = 11;
	    	Double totalVenta = 0.00;
	    	for(TipoEntrada tipoEntrada : listTipoEntradas){
	    		
	    		Long idVenta = ((BigInteger) row[0]).longValue();
				List<Object[]> listDetalleVenta = detalleVentaDao.getListDetalleVentaByIdVenta(idVenta);
				
				for(Object[] detVenta : listDetalleVenta){
					
					Long idTipoEntrada = (Long) detVenta[0];
					BigDecimal precio = (BigDecimal) detVenta[1];
					Integer cantidad = (Integer) detVenta[2];
					BigDecimal total = new BigDecimal(0.00);
					
					if(idTipoEntrada == tipoEntrada.getIdTipoEntrada()){
						cad[y] = cantidad;
						total = precio.multiply(new BigDecimal(cantidad));
						totalVenta += total.doubleValue();
					}
				}
	    		y++;
	    	}
	    	cad[y] = totalVenta;
	    	count++;
	    	data.put(""+count, cad);
		}
		return data;
	}

}
