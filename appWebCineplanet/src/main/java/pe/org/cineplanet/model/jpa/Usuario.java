package pe.org.cineplanet.model.jpa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Usuario.getAllListaUsuario", query = "SELECT u FROM Usuario u ORDER BY u.apPaterno"),
		@NamedQuery(name = "Usuario.findByUsername", query = "SELECT u "
				+ "FROM Usuario u "
				+ "WHERE u.usuario =:username AND u.estado =:estado") })
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idUsuario;
	@Column
	private String nombre;
	@Column
	private String apPaterno;
	@Column
	private String apMaterno;
	@Column
	private String usuario;
	//@Column
	//@Lob
	//private byte[] password;
	@Column
	private String clave;
	@Column
	private String usuarioRegistra;
	@Column
	private String usuarioModifica;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	//@Column
	//private String dni;
	//@Column
	//private String correo;
	//@Column
	//private Long idCampania;
	//@Column
	//private String anioCampania;
	@Column
	private String estado;
	//@Transient
	//private List<String> listaGPublicos = new ArrayList<String>();
	@JoinColumn(name = "idRol")
	@ManyToOne
	private Rol rol;

	public Usuario() {
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuarioRegistra() {
		return usuarioRegistra;
	}

	public void setUsuarioRegistra(String usuarioRegistra) {
		this.usuarioRegistra = usuarioRegistra;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@PrePersist
	private void putFechaCreacion() {
		setFechaCreacion(new Date(Calendar.getInstance().getTime().getTime()));
	}

	@PreUpdate
	private void putFechaModificacion() {
		setFechaModificacion(new Date(Calendar.getInstance().getTime()
				.getTime()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nombre);
		builder.append(" ");
		builder.append(apPaterno);
		builder.append(" ");
		builder.append(apMaterno);
		return builder.toString();
	}

}
