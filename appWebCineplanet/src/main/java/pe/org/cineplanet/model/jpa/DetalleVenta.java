package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({ @NamedQuery(name = "DetalleVenta.getAll", query = "SELECT d FROM DetalleVenta d WHERE d.estado =:estado ORDER BY d.id.idVenta ASC") })
public class DetalleVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetalleVentaPK id;

	@Column
	private Integer cantidad;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	// @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "idVenta", referencedColumnName = "idVenta", insertable = false, updatable = false)
	private Venta venta;

	// @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "idTipoEntrada", referencedColumnName = "idTipoEntrada", insertable = false, updatable = false)
	private TipoEntrada tipoEntrada;

	public DetalleVenta() {
	}

	public DetalleVentaPK getId() {
		return id;
	}

	public void setId(DetalleVentaPK id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
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
		DetalleVenta other = (DetalleVenta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
