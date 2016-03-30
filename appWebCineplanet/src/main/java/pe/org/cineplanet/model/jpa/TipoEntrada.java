package pe.org.cineplanet.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

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
@NamedQueries({ @NamedQuery(name = "TipoEntrada.getAll", query = "SELECT t FROM TipoEntrada t WHERE t.estado =:estado ORDER BY t.tipoVale ASC, t.precio DESC ") })
public class TipoEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idTipoEntrada;

	@Column(length = 60)
	@Size(max = 60)
	private String nombre;

	@Column(length = 200)
	@Size(max = 200)
	private String descripcion;

	@Column(length = 500)
	@Size(max = 500)
	private String restricciones;

	@Column
	private Integer tipoVale;

	@Column
	private BigDecimal precio;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	public TipoEntrada() {
	}

	public Long getIdTipoEntrada() {
		return idTipoEntrada;
	}

	public void setIdTipoEntrada(Long idTipoEntrada) {
		this.idTipoEntrada = idTipoEntrada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getTipoVale() {
		return tipoVale;
	}

	public void setTipoVale(Integer tipoVale) {
		this.tipoVale = tipoVale;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getRestricciones() {
		return restricciones;
	}

	public void setRestricciones(String restricciones) {
		this.restricciones = restricciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		TipoEntrada other = (TipoEntrada) obj;
		if (idTipoEntrada == null) {
			if (other.idTipoEntrada != null)
				return false;
		} else if (!idTipoEntrada.equals(other.idTipoEntrada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nombre);
		return builder.toString();
	}

}
