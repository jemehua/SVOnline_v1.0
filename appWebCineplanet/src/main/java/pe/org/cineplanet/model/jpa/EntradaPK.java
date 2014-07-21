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
public class EntradaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "idEntrada")
	private Long idEntrada;
	@Basic(optional = false)
	@Column(name = "idTipoEntrada")
	private Long idTipoEntrada;

	public EntradaPK() {
	}

	public EntradaPK(Long idEntrada, Long idTipoEntrada) {
		super();
		this.idEntrada = idEntrada;
		this.idTipoEntrada = idTipoEntrada;
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
				+ ((idEntrada == null) ? 0 : idEntrada.hashCode());
		result = prime * result
				+ ((idTipoEntrada == null) ? 0 : idTipoEntrada.hashCode());
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
		EntradaPK other = (EntradaPK) obj;
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
		return true;
	}

}
