package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({ @NamedQuery(name = "TipoPago.getAll", query = "SELECT t FROM TipoPago t WHERE t.estado =:estado ORDER BY t.idTipoPago ASC") })
public class TipoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idTipoPago;

	@Column(length = 60)
	@Size(max = 60)
	private String nombre;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	public TipoPago() {
	}

	public Integer getIdTipoPago() {
		return idTipoPago;
	}

	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoPago == null) ? 0 : idTipoPago.hashCode());
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
		TipoPago other = (TipoPago) obj;
		if (idTipoPago == null) {
			if (other.idTipoPago != null)
				return false;
		} else if (!idTipoPago.equals(other.idTipoPago))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoPago [idTipoPago=");
		builder.append(idTipoPago);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", estado=");
		builder.append(estado);
		builder.append("]");
		return builder.toString();
	}

}
