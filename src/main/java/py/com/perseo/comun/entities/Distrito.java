package py.com.perseo.comun.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@Entity
@Table(name = "distrito")
//@NamedQueries( { @NamedQuery(name = "distritofindAll", query = "select p from Distrito p order by p.iddistrito "),
//@NamedQuery(name = "distritofindByEstado", query = "select p from Distrito p where p.estado = :pestado"),
//@NamedQuery(name = "distritofindByIdAndEstado", query = "select p from Distrito p where p.iddistrito = :piddistrito AND p.estado = :pestado")})
@JsonInclude(Include.NON_NULL)
public class Distrito {

	@Id
	@Column(name = "iddistrito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distrito_iddistrito_seq")
	@SequenceGenerator(name = "distrito_iddistrito_seq", sequenceName = "distrito_iddistrito_seq", allocationSize = 1)
	private Integer iddistrito;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;

	@ManyToOne(targetEntity = Departamento.class)
	@JoinColumn(name = "iddepartamento")
	Departamento departamento;

	public Distrito() {
		
	}

	public Integer getIddistrito() {
		return iddistrito;
	}

	public void setIddistrito(Integer iddistrito) {
		this.iddistrito = iddistrito;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
