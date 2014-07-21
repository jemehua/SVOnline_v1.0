/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.cineplanet.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Hever Pumallihua
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Permiso.getAllListaPermiso", query = "SELECT p FROM Permiso p"),
		@NamedQuery(name = "Permiso.getListaPermisoRol", query = "SELECT p "
				+ "FROM Permiso p WHERE p.rol.idRol =:idRol "),
		@NamedQuery(name = "Permiso.getListaPermisoMenuAndRol", query = "SELECT p "
				+ "FROM Permiso p "
				+ "WHERE p.itemMenu.menu.idMenu =:idMenu AND p.rol.idRol =:idRol AND p.estado =:estado") })
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPermiso;
	@Column
	private boolean estado;
	@JoinColumn(name = "idItemMenu")
	@ManyToOne
	private ItemMenu itemMenu;
	@JoinColumn(name = "idRol")
	@ManyToOne
	private Rol rol;

	public Permiso() {
	}

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public ItemMenu getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(ItemMenu itemMenu) {
		this.itemMenu = itemMenu;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (estado ? 1231 : 1237);
		result = prime * result
				+ ((idPermiso == null) ? 0 : idPermiso.hashCode());
		result = prime * result
				+ ((itemMenu == null) ? 0 : itemMenu.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
		Permiso other = (Permiso) obj;
		if (estado != other.estado)
			return false;
		if (idPermiso == null) {
			if (other.idPermiso != null)
				return false;
		} else if (!idPermiso.equals(other.idPermiso))
			return false;
		if (itemMenu == null) {
			if (other.itemMenu != null)
				return false;
		} else if (!itemMenu.equals(other.itemMenu))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permiso [idPermiso=");
		builder.append(idPermiso);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", itemMenu=");
		builder.append(itemMenu);
		builder.append(", rol=");
		builder.append(rol);
		builder.append("]");
		return builder.toString();
	}

}
