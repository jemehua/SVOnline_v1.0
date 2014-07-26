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

import pe.org.cineplanet.dao.VentasDao;
import pe.org.cineplanet.dto.ReporteDTO;
import pe.org.cineplanet.model.jpa.Venta;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class VentasDaoImpl implements VentasDao {
	@PersistenceContext
	private EntityManager em;

	public Venta find(Long id) throws Exception {
		return em.find(Venta.class, id);
	}

	@Transactional
	public Venta save(Venta obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Venta edit(Venta obj) throws Exception {
		return em.merge(obj);
	}

	public List<Venta> getListaVentas() throws Exception {

		TypedQuery<Venta> tq = em.createNamedQuery("Venta.getAll", Venta.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();

	}

	public Long getMaxId() throws Exception {
		return (Long) em.createQuery("select max(v.idVenta) from Venta v")
				.getSingleResult();
	}

	public List<ReporteDTO> getListaReporte(Date fecInicio, Date fecFin,
			String usr) throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT v.idVenta, ");
		sb.append("v.cliente.razonSocial, ");
		sb.append("v.cliente.apellidos, ");
		sb.append("v.cliente.agencia.idAgencia, ");
		sb.append("v.fecRegistro ");
		sb.append("FROM Venta v ");
		sb.append("WHERE v.estado =:estado ");
		sb.append("AND v.fecRegistro BETWEEN  :fecInicio AND :fecFin ");
		if(!usr.equalsIgnoreCase(""))
			sb.append("AND v.usuRegistra =:usr ");
		sb.append("ORDER BY v.cliente.razonSocial ASC ");

		StringBuilder sb00 = new StringBuilder();
		sb00.append("SELECT d.tipoEntrada.idTipoEntrada, ");
		sb00.append("d.tipoEntrada.precio, ");
		sb00.append("d.cantidad ");
		sb00.append("FROM DetalleVenta d ");
		sb00.append("WHERE d.venta.idVenta =:idVenta ");
		sb00.append("AND d.estado =:estado ");

		Query q = em.createQuery(sb.toString());
		q.setParameter("estado", Constantes.ACTIVO);
		q.setParameter("fecInicio", fecInicio);
		q.setParameter("fecFin", fecFin);
		if(!usr.equalsIgnoreCase(""))
			q.setParameter("usr", usr);

		List<Object[]> listaObjetos = new ArrayList<Object[]>();

		listaObjetos = q.getResultList();

		List<ReporteDTO> lista = new ArrayList<ReporteDTO>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		
		int count = 0;
		for (Object[] row : listaObjetos) {
			count++;
			ReporteDTO reporteDTO = new ReporteDTO();

			Long idVenta = (Long) row[0];
			reporteDTO.setContador(count);
			reporteDTO.setNombres((String) row[1] +" "+ row[2]);
			reporteDTO.setIdAgencia((String) row[3]);

			String fechaInicio = sdf.format((Date) row[4]);
			reporteDTO.setFechaPedido(fechaInicio);

			Query q00 = em.createQuery(sb00.toString());
			q00.setParameter("estado", Constantes.ACTIVO);
			q00.setParameter("idVenta", idVenta);

			List<Object[]> listaDetObjetos = new ArrayList<Object[]>();
			
			listaDetObjetos = q00.getResultList();

			Double totalVenta = 0.00;
			
			for (Object[] detVenta : listaDetObjetos) {

				Long idTipoEntrada = (Long) detVenta[0];
				BigDecimal precio = (BigDecimal) detVenta[1];
				Integer cantidad = (Integer) detVenta[2];
				BigDecimal total = new BigDecimal(0.00);
				
				if (idTipoEntrada == 1L) {
					reporteDTO.setCantEntrada17(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 2L) {
					reporteDTO.setCantEntrada10(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 3L) {
					reporteDTO.setCantEntrada9(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 4L) {
					reporteDTO.setCantEntrada7(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 5L) {
					reporteDTO.setCantCombo11(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 6L) {
					reporteDTO.setCantCombo10(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 7L) {
					reporteDTO.setCantCombo7(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}
				if (idTipoEntrada == 8L) {
					reporteDTO.setCantCombo9(cantidad);
					total = precio.multiply(new BigDecimal(cantidad));
					totalVenta += total.doubleValue();
				}

			}
			
			reporteDTO.setTotalVenta(totalVenta);

			lista.add(reporteDTO);
		}

		return lista;

	}

}
