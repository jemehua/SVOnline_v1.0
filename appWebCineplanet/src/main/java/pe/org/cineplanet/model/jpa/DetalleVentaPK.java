package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Hever Pumallihua
 */
@Embeddable
public class DetalleVentaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "idVenta")
	private Long idVenta;

	@Basic(optional = false)
	@Column(name = "idTipoEntrada")
	private Long idTipoEntrada;

	public DetalleVentaPK() {
	}

	public DetalleVentaPK(Long idVenta, Long idTipoEntrada) {
		super();
		this.idVenta = idVenta;
		this.idTipoEntrada = idTipoEntrada;
	}

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public Long getIdTipoEntrada() {
		return idTipoEntrada;
	}

	public void setIdTipoEntrada(Long idTipoEntrada) {
		this.idTipoEntrada = idTipoEntrada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoEntrada == null) ? 0 : idTipoEntrada.hashCode());
		result = prime * result + ((idVenta == null) ? 0 : idVenta.hashCode());
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
		DetalleVentaPK other = (DetalleVentaPK) obj;
		if (idTipoEntrada == null) {
			if (other.idTipoEntrada != null)
				return false;
		} else if (!idTipoEntrada.equals(other.idTipoEntrada))
			return false;
		if (idVenta == null) {
			if (other.idVenta != null)
				return false;
		} else if (!idVenta.equals(other.idVenta))
			return false;
		return true;
	}

}
