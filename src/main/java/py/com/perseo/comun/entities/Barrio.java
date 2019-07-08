package py.com.perseo.comun.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@Entity
@Table(name = "barrio")
//@NamedQueries( { @NamedQuery(name = "barriofindAll", query = "select p from Barrio p order by p.idbarrio "),
//@NamedQuery(name = "barriofindByEstado", query = "select p from Barrio p where p.estado = :pestado"),
//@NamedQuery(name = "barriofindById", query = "select p from Barrio p where p.idbarrio = :pidbarrio"),
//@NamedQuery(name = "barriofindByIdAndEstado", query = "select p from Barrio p where p.idbarrio = :pidbarrio AND p.estado = :pestado")})
@JsonInclude(Include.NON_NULL)
public class Barrio {

	@Id
	@Column(name = "idbarrio")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barrio_idbarrio_seq")
	@SequenceGenerator(name = "barrio_idbarrio_seq", sequenceName = "barrio_idbarrio_seq", allocationSize = 1)
	private Integer idbarrio;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;

	@ManyToOne(targetEntity = Distrito.class)
	@JoinColumn(name = "iddistrito")
	Distrito distrito;

	@ManyToOne(targetEntity = Departamento.class)
	@JoinColumn(name = "iddepartamento")
	Departamento departamento;
	
	public Barrio() {
		// TODO Auto-generated constructor stub
	}

	public Barrio(Integer idbarrio, String descripcion) {
		this.idbarrio = idbarrio;
		this.descripcion = descripcion;
	}

	public Integer getIdbarrio() {
		return idbarrio;
	}

	public void setIdbarrio(Integer idbarrio) {
		this.idbarrio = idbarrio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
