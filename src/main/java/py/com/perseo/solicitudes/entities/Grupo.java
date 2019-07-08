package py.com.perseo.solicitudes.entities;


import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
//@Table(name = "grupo")
//@NamedQueries( { @NamedQuery(name = "grupofindAll", query = "select p from Grupo p "), @NamedQuery(name = "grupofindByEstado", query = "select p from Grupo p where p.estado = :pestado"), @NamedQuery(name = "grupofindById", query = "select p from Grupo p where p.idgrupo = :pidgrupo") })
public class Grupo implements Serializable {

	@Id
	@Column(name = "idgrupo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_idgrupo_seq")
	@SequenceGenerator(name = "grupo_idgrupo_seq", sequenceName = "grupo_idgrupo_seq", allocationSize = 1)
	private Integer idgrupo;

	@Column(name = "nombregrupo")
	private String nombregrupo;

	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "historico")
	private Boolean historico;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Integrantecantidad.class)
	@JoinColumn(name = "idintegrantecantidad")
	Integrantecantidad integrantecantidad;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "grupo")
	@OrderBy("lider DESC")
	private Set<Grupointegrante> grupointegrante = new HashSet<Grupointegrante>(0);

	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombregrupo() {
		return nombregrupo;
	}

	public void setNombregrupo(String nombregrupo) {
		this.nombregrupo = nombregrupo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getHistorico() {
		return historico;
	}

	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integrantecantidad getIntegrantecantidad() {
		return integrantecantidad;
	}

	public void setIntegrantecantidad(Integrantecantidad integrantecantidad) {
		this.integrantecantidad = integrantecantidad;
	}

	public Set<Grupointegrante> getGrupointegrante() {
		return grupointegrante;
	}

	public void setGrupointegrante(Set<Grupointegrante> grupointegrante) {
		this.grupointegrante = grupointegrante;
	}

}
