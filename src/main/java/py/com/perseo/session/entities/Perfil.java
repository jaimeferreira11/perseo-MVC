package py.com.perseo.session.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "perfil")
@NamedQueries( { @NamedQuery(name = "perfilfindAll", query = "select p from Perfil p"), 
				 @NamedQuery(name = "perfilfindByEstado", query = "select p from Perfil p where p.estado = :pestado"),
				 @NamedQuery(name = "findPerfilesByEmpleado", query = "select p from Perfil p, IN(p.usuarios) e where e.idusuario = :pusuario"),
				 @NamedQuery(name = "findByDescripcion", query = "select p from Perfil p where p.descripcion = :pdescripcion"),
				 @NamedQuery(name = "perfilfindByPrimaryKey", query = "select p from Perfil p where p.idperfil = :pidperfil")})
				 
public class Perfil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idperfil")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="perfil_idperfil_seq")
    @SequenceGenerator(name = "perfil_idperfil_seq", sequenceName = "perfil_idperfil_seq", allocationSize=1)
	private Integer idperfil;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;
	
	public Integer getIdperfil() {
		return idperfil;
	}

	public void setIdperfil(Integer idperfil) {
		this.idperfil = idperfil;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "perfiles", targetEntity = Usuario.class)
	private List<Usuario> usuarios;

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}
