package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Agencia.getAll", query = "SELECT a FROM Agencia a WHERE a.estado =:estado ORDER BY a.idAgencia ASC") })
public class Agencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idagencia", length = 16)
	@Size(max = 16)
	private String idAgencia;

	@Column(length = 200)
	@Size(max = 200)
	private String nombre;

	@Transient
	private String label;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	@Column(name="idagenciapadre", length = 16)
	@Size(max = 16)
	private String idAgenciaPadre;

	public Agencia() {
	}

	public String getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(String idAgencia) {
		this.idAgencia = idAgencia;
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

	public String getLabel() {
		if (idAgencia != null)
			return idAgencia + "-" + nombre;
		else
			return "";
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIdAgenciaPadre() {
		return idAgenciaPadre;
	}

	public void setIdAgenciaPadre(String idAgenciaPadre) {
		this.idAgenciaPadre = idAgenciaPadre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAgencia == null) ? 0 : idAgencia.hashCode());
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
		Agencia other = (Agencia) obj;
		if (idAgencia == null) {
			if (other.idAgencia != null)
				return false;
		} else if (!idAgencia.equals(other.idAgencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(idAgencia);
		builder.append(" - ");
		builder.append(nombre);
		return builder.toString();
	}

}
