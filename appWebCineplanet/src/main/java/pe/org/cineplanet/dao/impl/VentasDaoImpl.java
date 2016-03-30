package pe.org.cineplanet.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		sb.append("SELECT v.idventa, c.razonsocial, c.apellidos, v.fecRegistro, v.serie, ");
		sb.append("v.numero, t.nombre as tipodocumento, t2.nombre as tipopago, ");
		sb.append("a.idAgencia, a.nombre as nombreagencia, ");
		sb.append("a.idagenciapadre, e.nombre as nombreempresa ");
		sb.append("FROM venta v ");
		sb.append("INNER JOIN cliente c on (c.idcliente = v.idcliente) ");
		sb.append("INNER JOIN agencia a on (a.idagencia = c.idagencia) ");
		sb.append("LEFT JOIN agencia e on (e.idagencia = a.idagenciapadre) ");
		sb.append("INNER JOIN tipodocumento t ON (t.idtipodocumento = v.idtipodocumento) ");
		sb.append("LEFT JOIN tipopago t2 ON (t2.idtipopago = v.idtipopago) ");
		sb.append("WHERE v.estado =:estado ");
		sb.append("AND v.fecregistro BETWEEN  :fecInicio AND :fecFin ");
		if(!usr.equalsIgnoreCase(""))
			sb.append("AND v.usuregistra =:usr ");
		sb.append("ORDER BY c.razonsocial ASC ");

		StringBuilder sb00 = new StringBuilder();
		sb00.append("SELECT d.tipoEntrada.idTipoEntrada, ");
		sb00.append("d.tipoEntrada.precio, ");
		sb00.append("d.cantidad ");
		sb00.append("FROM DetalleVenta d ");
		sb00.append("WHERE d.venta.idVenta =:idVenta ");
		sb00.append("AND d.estado =:estado ");

		Query q = em.createNativeQuery(sb.toString());
		q.setParameter("estado", Constantes.ACTIVO);
		q.setParameter("fecInicio", fecInicio);
		q.setParameter("fecFin", fecFin);
		if(!usr.equalsIgnoreCase(""))
			q.setParameter("usr", usr);

		List<Object[]> listaObjetos = new ArrayList<Object[]>();
		listaObjetos = q.getResultList();

		int count = 0;
		List<ReporteDTO> lista = new ArrayList<ReporteDTO>();
		for (Object[] row : listaObjetos) {
			count++;
			ReporteDTO reporteDTO = new ReporteDTO();

			Long idVenta = ((BigInteger) row[0]).longValue();
			reporteDTO.setContador(count);
			reporteDTO.setNombres((String) row[1] +" "+ row[2]);
			reporteDTO.setFechaPedido((Date) row[3]);
			reporteDTO.setSerie((String) row[4]);
			reporteDTO.setNumero((String) row[5]);
			reporteDTO.setTipoDocumento((String) row[6]);
			reporteDTO.setTipoPago((String) row[7]);
			
			if(row[10] != null && row[11] != null){
				reporteDTO.setIdAgencia((String) row[8]);
				reporteDTO.setNomAgencia((String) row[9]);
				reporteDTO.setIdEmpresa((String) row[10]);
				reporteDTO.setNomEmpresa((String) row[11]);
			}else{
				reporteDTO.setIdAgencia("");
				reporteDTO.setNomAgencia("");
				reporteDTO.setIdEmpresa((String) row[8]);
				reporteDTO.setNomEmpresa((String) row[9]);
			}
			
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
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getListaVenta(Date fecInicio, Date fecFin, String usr)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT v.idventa, c.razonsocial, c.apellidos, v.fecRegistro, v.serie, ");
		sb.append("v.numero, t.nombre as tipodocumento, t2.nombre as tipopago, ");
		sb.append("a.idAgencia, a.nombre as nombreagencia, ");
		sb.append("a.idagenciapadre, e.nombre as nombreempresa ");
		sb.append("FROM venta v ");
		sb.append("INNER JOIN cliente c on (c.idcliente = v.idcliente) ");
		sb.append("INNER JOIN agencia a on (a.idagencia = c.idagencia) ");
		sb.append("LEFT JOIN agencia e on (e.idagencia = a.idagenciapadre) ");
		sb.append("INNER JOIN tipodocumento t ON (t.idtipodocumento = v.idtipodocumento) ");
		sb.append("LEFT JOIN tipopago t2 ON (t2.idtipopago = v.idtipopago) ");
		sb.append("WHERE v.estado =:estado ");
		sb.append("AND v.fecregistro BETWEEN  :fecInicio AND :fecFin ");
		if(!usr.equalsIgnoreCase(""))
			sb.append("AND v.usuregistra =:usr ");
		sb.append("ORDER BY c.razonsocial ASC ");

		Query q = em.createNativeQuery(sb.toString());
		q.setParameter("estado", Constantes.ACTIVO);
		q.setParameter("fecInicio", fecInicio);
		q.setParameter("fecFin", fecFin);
		if(!usr.equalsIgnoreCase(""))
			q.setParameter("usr", usr);

		return q.getResultList();
	}

}
