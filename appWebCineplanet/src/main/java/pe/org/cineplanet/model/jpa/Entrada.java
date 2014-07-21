package pe.org.cineplanet.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Entrada.getAll", query = "SELECT e FROM Entrada e WHERE e.estado =:estado ORDER BY e.idEntrada ASC") })
public class Entrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idEntrada;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecInicio;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecFin;

	@Column
	private Integer cantidad;

	@Column(length = 60)
	@Size(max = 60)
	private String usuRegistra;

	@Column(length = 60)
	@Size(max = 60)
	private String usuModifica;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecRegistro;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecModificacion;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	@ManyToOne
	@JoinColumn(name = "idTipoEntrada")
	private TipoEntrada tipoEntrada;

	public Entrada() {
	}

	public Long getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getUsuRegistra() {
		return usuRegistra;
	}

	public void setUsuRegistra(String usuRegistra) {
		this.usuRegistra = usuRegistra;
	}

	public String getUsuModifica() {
		return usuModifica;
	}

	public void setUsuModifica(String usuModifica) {
		this.usuModifica = usuModifica;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Date getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
		Entrada other = (Entrada) obj;
		if (idEntrada == null) {
			if (other.idEntrada != null)
				return false;
		} else if (!idEntrada.equals(other.idEntrada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(fecInicio);
		builder.append("-");
		builder.append(fecFin);
		return builder.toString();
	}

}
