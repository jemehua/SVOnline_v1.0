package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@NamedQueries({ 
	@NamedQuery(name = "DetalleEntrada.getAllByIdEntrada", query = "SELECT d FROM DetalleEntrada d WHERE d.id.idEntrada =:idEntrada AND d.estado =:estado ORDER BY d.id.idCodigo ASC"),
	@NamedQuery(name = "DetalleEntrada.getByIdCodigo", query = "SELECT d FROM DetalleEntrada d WHERE d.id.idCodigo =:idCodigo "),
	@NamedQuery(name = "DetalleEntrada.getAllByIdTipoEntrada", query = "SELECT d FROM DetalleEntrada d WHERE d.entrada.tipoEntrada.idTipoEntrada =:idTipoEntrada AND d.estado =:estado ORDER BY d.id.idEntrada ASC")})
public class DetalleEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetalleEntradaPK id;

	@Column(length = 1)
	@Size(max = 1)
	private String estado;

	//@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "idEntrada", referencedColumnName = "idEntrada", insertable = false, updatable = false)
	private Entrada entrada;

	public DetalleEntrada() {
	}

	public DetalleEntradaPK getId() {
		return id;
	}

	public void setId(DetalleEntradaPK id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
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
		DetalleEntrada other = (DetalleEntrada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
