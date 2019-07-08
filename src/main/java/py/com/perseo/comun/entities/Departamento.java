package py.com.perseo.comun.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "departamento")
//@NamedQueries( { @NamedQuery(name = "departamentofindAll", query = "select p from Departamento p order by p.iddepartamento "), 
//@NamedQuery(name = "departamentofindByEstado", query = "select p from Departamento p where p.estado = :pestado "),
//@NamedQuery(name = "departamentofindById", query = "select p from Departamento p where p.iddepartamento = :piddepartamento"),
//@NamedQuery(name = "departamentofindByIdAndEstado", query = "select p from Departamento p where p.iddepartamento = :piddepartamento AND p.estado = :pestado")})
@JsonInclude(Include.NON_NULL)
public class Departamento implements Serializable {

	@Id
	@Column(name = "iddepartamento")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_iddepartamento_seq")
	@SequenceGenerator(name = "departamento_iddepartamento_seq", sequenceName = "departamento_iddepartamento_seq", allocationSize = 1)
	private Integer iddepartamento;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "capital")
	private String capital;
	
	@Column(name = "estado")
	private Boolean estado;

	public Departamento() {
		
	}
	
	public Departamento(Integer iddepartamento, String descripcion) {
		this.iddepartamento = iddepartamento;
		this.descripcion = descripcion;
	}

	public Integer getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(Integer iddepartamento) {
		this.iddepartamento = iddepartamento;
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

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
}
