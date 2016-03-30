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
@NamedQueries({ @NamedQuery(name = "Venta.getAll", query = "SELECT v FROM Venta v WHERE v.estado =:estado ORDER BY v.idVenta DESC") })
public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idVenta;

	@Column(length = 200)
	@Size(max = 200)
	private String otorgado;

	@Column(length = 3)
	@Size(max = 3)
	private String serie;

	@Column(length = 20)
	@Size(max = 20)
	private String numero;

	@Column
	private Integer total;

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

	@JoinColumn(name = "idCliente")
	@ManyToOne
	private Cliente cliente;

	@JoinColumn(name = "idTipoDocumento")
	@ManyToOne
	private TipoDocumento tipoDocumento;

	@JoinColumn(name = "idTipoPago")
	@ManyToOne
	private TipoPago tipoPago;

	public Venta() {
	}

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public String getOtorgado() {
		return otorgado;
	}

	public void setOtorgado(String otorgado) {
		this.otorgado = otorgado;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Venta other = (Venta) obj;
		if (idVenta == null) {
			if (other.idVenta != null)
				return false;
		} else if (!idVenta.equals(other.idVenta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(serie);
		builder.append("-");
		builder.append(numero);
		return builder.toString();
	}

}
