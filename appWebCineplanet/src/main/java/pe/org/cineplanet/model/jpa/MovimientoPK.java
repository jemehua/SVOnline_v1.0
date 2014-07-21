package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Embeddable
public class MovimientoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "idVenta")
	private Long idVenta;

	@Basic(optional = false)
	@Column(name = "idTipoEntrada")
	private Long idTipoEntrada;

	@Basic(optional = false)
	@Column(name = "idCodigo", length = 25)
	@Size(max = 25)
	private String idCodigo;

	@Basic(optional = false)
	@Column(name = "idEntrada")
	private Long idEntrada;

	public MovimientoPK() {
	}

	public MovimientoPK(Long idVenta, Long idTipoEntrada, String idCodigo,
			Long idEntrada) {
		super();
		this.idVenta = idVenta;
		this.idTipoEntrada = idTipoEntrada;
		this.idCodigo = idCodigo;
		this.idEntrada = idEntrada;
	}

	public String getIdCodigo() {
		return idCodigo;
	}

	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}

	public Long getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
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
				+ ((idCodigo == null) ? 0 : idCodigo.hashCode());
		result = prime * result
				+ ((idEntrada == null) ? 0 : idEntrada.hashCode());
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
		MovimientoPK other = (MovimientoPK) obj;
		if (idCodigo == null) {
			if (other.idCodigo != null)
				return false;
		} else if (!idCodigo.equals(other.idCodigo))
			return false;
		if (idEntrada == null) {
			if (other.idEntrada != null)
				return false;
		} else if (!idEntrada.equals(other.idEntrada))
			return false;
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
