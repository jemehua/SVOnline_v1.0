package pe.org.cineplanet.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.org.cineplanet.dao.DetalleVentaDao;
import pe.org.cineplanet.dto.ReporteDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.DetalleVenta;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class DetalleVentaDaoImpl implements DetalleVentaDao {
	@PersistenceContext
	private EntityManager em;

	public DetalleVenta find(DetalleVentaPK id) throws Exception {
		return em.find(DetalleVenta.class, id);
	}

	@Transactional
	public DetalleVenta save(DetalleVenta obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public DetalleVenta edit(DetalleVenta obj) throws Exception {
		return em.merge(obj);
	}

	public List<DetalleVenta> getListaByIdVenta(Long idVenta) throws Exception {
		TypedQuery<DetalleVenta> tq = em.createNamedQuery(
				"DetalleVenta.getAllByIdVenta", DetalleVenta.class);
		tq.setParameter("idVenta", idVenta);
		return tq.getResultList();
	}
	
	public List<ReporteDTO> getListaReporteDTO() throws Exception {

		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.tipoEntrada.idTipoEntrada, ");
		sb.append("m.tipoEntrada.precio, ");
		sb.append("m.cantidad, ");
		sb.append("m.venta.cliente.razonSocial, ");
		sb.append("m.venta.cliente.apellidos, ");
		sb.append("m.venta.cliente.agencia.idAgencia, ");
		sb.append("m.venta.fecRegistro ");
		sb.append("FROM DetalleVenta m ");
		sb.append("WHERE m.estado =:estado ");
		
		Query q = em.createQuery(sb.toString());
		q.setParameter("estado", Constantes.ACTIVO);
		
		List<Object[]> listaObjetos = new ArrayList<Object[]>();

		listaObjetos = q.getResultList();

		List<ReporteDTO> lista = new ArrayList<ReporteDTO>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Object[] row : listaObjetos) {
			ReporteDTO reporteDTO = new ReporteDTO();

			Long idTipoEntrada = (Long) row[0];
			BigDecimal precio = (BigDecimal) row[1];
			Integer cantidad = (Integer) row[2];
			
			
			if(idTipoEntrada == 1L){
				//reporteDTO.setCantEntrada17(cantEntrada17)
			}
			
			
			String fechaInicio = sdf.format((Date)row[4]);
			
			reporteDTO.setFechaPedido((String) row[0]);

			
			String fechaFin = sdf.format((Date)row[5]);
			

			lista.add(reporteDTO);
		}

		return lista;
		
		
	}

}
