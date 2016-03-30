package pe.org.cineplanet.dao.impl;

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

import pe.org.cineplanet.dao.MovimientoDao;
import pe.org.cineplanet.dto.MovimientoDTO;
import pe.org.cineplanet.dto.VentaDTO;
import pe.org.cineplanet.model.jpa.DetalleVentaPK;
import pe.org.cineplanet.model.jpa.Movimiento;
import pe.org.cineplanet.model.jpa.MovimientoPK;
import pe.org.cineplanet.util.Constantes;

/**
 * 
 * @author Hever Pumallihua
 */
@Repository
public class MovimientoDaoImpl implements MovimientoDao {
	@PersistenceContext
	private EntityManager em;

	public Movimiento find(MovimientoPK id) throws Exception {
		return em.find(Movimiento.class, id);
	}
	
	public Movimiento find(String idCodigo) throws Exception {
		
		TypedQuery<Movimiento> tq = em.createNamedQuery(
				"Movimiento.getByIdCodigo", Movimiento.class);
		tq.setParameter("idCodigo", idCodigo);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getSingleResult();
		
	}

	@Transactional
	public Movimiento save(Movimiento obj) throws Exception {
		em.persist(obj);
		return obj;
	}

	@Transactional
	public Movimiento edit(Movimiento obj) throws Exception {
		return em.merge(obj);
	}

	public List<Movimiento> getListaMovimiento()
			throws Exception {
		TypedQuery<Movimiento> tq = em.createNamedQuery(
				"Movimiento.getAllByIdVenta", Movimiento.class);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getResultList();
	}
	
	public List<VentaDTO> getListaVenta(Long idVenta )
			throws Exception {
	
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.id.idCodigo, ");
		//sb.append("m.detalleEntrada.entrada.tipoEntrada.nombre, ");
		//sb.append("m.detalleEntrada.entrada.tipoEntrada.descripcion, ");
		sb.append("m.detalleVenta.tipoEntrada.nombre, ");
		sb.append("m.detalleVenta.tipoEntrada.descripcion, ");
		sb.append("m.detalleVenta.tipoEntrada.tipoVale, ");
		sb.append("m.detalleEntrada.entrada.fecInicio, ");
		sb.append("m.detalleEntrada.entrada.fecFin, ");
		sb.append("m.detalleVenta.venta.otorgado, ");
		sb.append("m.detalleVenta.venta.fecRegistro, ");
		sb.append("m.estado, ");
		sb.append("m.detalleVenta.tipoEntrada.restricciones  ");
		sb.append("FROM Movimiento m ");
		sb.append("WHERE m.id.idVenta =:idVenta AND m.estado =:estado ");
		
		Query q = em.createQuery(sb.toString());
		q.setParameter("idVenta", idVenta);
		q.setParameter("estado", Constantes.ACTIVO);
		
		List<Object[]> listaObjetos = new ArrayList<Object[]>();

		listaObjetos = q.getResultList();

		List<VentaDTO> lista = new ArrayList<VentaDTO>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Object[] row : listaObjetos) {
			VentaDTO ventaDTO = new VentaDTO();

			ventaDTO.setIdCodigo((String) row[0]);
			ventaDTO.setNombreVale((String) row[1]);
			ventaDTO.setDescVale((String) row[2]);
			ventaDTO.setTipoVale((Integer) row[3]);
			String fechaInicio = sdf.format((Date)row[4]);
			String fechaFin = sdf.format((Date)row[5]);
			ventaDTO.setFecValidez(fechaInicio+"-"+fechaFin);
			ventaDTO.setOtorgado((String) row[6]);
			ventaDTO.setFecVenta(sdf.format((Date) row[7]));
			ventaDTO.setDiasValidos("Lu-Ma-Mi-Ju-Vi-Sa-Do");
			ventaDTO.setRestricciones((String) row[9]);

			lista.add(ventaDTO);
		}

		return lista;
	}
	
	public Movimiento getByIdCodigo(String idCodigo)
			throws Exception {
		TypedQuery<Movimiento> tq = em.createNamedQuery(
				"Movimiento.getByIdCodigo", Movimiento.class);
		tq.setParameter("idCodigo", idCodigo);
		tq.setParameter("estado", Constantes.ACTIVO);
		return tq.getSingleResult();
	}
	
	
	public List<MovimientoDTO> getListaMovimiento(DetalleVentaPK id )
			throws Exception {
	
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.id.idCodigo, ");
		sb.append("m.detalleEntrada.estado ");
		sb.append("FROM Movimiento m ");
		sb.append("WHERE m.id.idVenta =:idVenta AND m.id.idTipoEntrada =:idTipoEntrada AND m.estado =:estado ");
		
		Query q = em.createQuery(sb.toString());
		q.setParameter("idVenta", id.getIdVenta());
		q.setParameter("idTipoEntrada", id.getIdTipoEntrada());
		q.setParameter("estado", Constantes.ACTIVO);
		
		List<Object[]> listaObjetos = new ArrayList<Object[]>();

		listaObjetos = q.getResultList();

		List<MovimientoDTO> lista = new ArrayList<MovimientoDTO>();

		for (Object[] row : listaObjetos) {
			MovimientoDTO ventaDTO = new MovimientoDTO();

			ventaDTO.setIdCodigo((String) row[0]);
			ventaDTO.setEstado((String) row[1]);

			lista.add(ventaDTO);
		}

		return lista;
	}

}
