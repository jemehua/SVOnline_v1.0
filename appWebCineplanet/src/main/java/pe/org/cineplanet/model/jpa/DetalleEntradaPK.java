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
public class DetalleEntradaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(length = 25)
	@Size(max = 25)
	private String idCodigo;
	
	@Basic(optional = false)
	@Column(name = "idEntrada")
	private Long idEntrada;

	public DetalleEntradaPK() {
	}

	public DetalleEntradaPK(String idCodigo, Long idEntrada) {
		super();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCodigo == null) ? 0 : idCodigo.hashCode());
		result = prime * result
				+ ((idEntrada == null) ? 0 : idEntrada.hashCode());
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
		DetalleEntradaPK other = (DetalleEntradaPK) obj;
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
		return true;
	}

}
