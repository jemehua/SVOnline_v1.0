package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Movimiento.getAllByIdVenta", query = "SELECT m FROM Movimiento m WHERE m.id.idVenta =:idVenta AND m.estado =:estado ORDER BY m.id.idCodigo ASC"),
		@NamedQuery(name = "Movimiento.getByIdCodigo", query = "SELECT m FROM Movimiento m WHERE m.id.idCodigo =:idCodigo ") })
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MovimientoPK id;

	// @Column
	// private Integer cantidad;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
			@JoinColumn(name = "idVenta", referencedColumnName = "idVenta", insertable = false, updatable = false),
			@JoinColumn(name = "idTipoEntrada", referencedColumnName = "idTipoEntrada", insertable = false, updatable = false) })
	private DetalleVenta detalleVenta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
			@JoinColumn(name = "idCodigo", referencedColumnName = "idCodigo", insertable = false, updatable = false),
			@JoinColumn(name = "idEntrada", referencedColumnName = "idEntrada", insertable = false, updatable = false) })
	private DetalleEntrada detalleEntrada;

	public Movimiento() {
	}

	public MovimientoPK getId() {
		return id;
	}

	public void setId(MovimientoPK id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public DetalleVenta getDetalleVenta() {
		return detalleVenta;
	}

	public void setDetalleVenta(DetalleVenta detalleVenta) {
		this.detalleVenta = detalleVenta;
	}

	public DetalleEntrada getDetalleEntrada() {
		return detalleEntrada;
	}

	public void setDetalleEntrada(DetalleEntrada detalleEntrada) {
		this.detalleEntrada = detalleEntrada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimiento other = (Movimiento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
