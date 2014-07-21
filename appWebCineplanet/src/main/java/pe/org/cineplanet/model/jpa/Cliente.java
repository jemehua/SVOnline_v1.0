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
@NamedQueries({ @NamedQuery(name = "Cliente.getAll", query = "SELECT c FROM Cliente c WHERE c.estado =:estado ORDER BY c.razonSocial ASC") })
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Id
	// private Long idCliente;
	@Id
	@Column(length = 15)
	@Size(max = 15)
	private String nroDocumento;

	@Column(length = 200)
	@Size(max = 200)
	private String razonSocial;

	@Column(length = 60)
	@Size(max = 60)
	private String abreviatura;

	@Column(length = 200)
	@Size(max = 200)
	private String direccion;

	@Column(length = 15)
	@Size(max = 15)
	private String telefono;

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

	@JoinColumn(name = "idAgencia")
	@ManyToOne
	private Agencia agencia;

	public Cliente() {
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nroDocumento == null) ? 0 : nroDocumento.hashCode());
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
		Cliente other = (Cliente) obj;
		if (nroDocumento == null) {
			if (other.nroDocumento != null)
				return false;
		} else if (!nroDocumento.equals(other.nroDocumento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(razonSocial);
		return builder.toString();
	}

}
