package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipoimpuesto")
//@NamedQueries( { @NamedQuery(name = "tipoimpuestofindAll", query = "select p from Tipoimpuesto p "), 
//	@NamedQuery(name = "tipoimpuestofindByEstado", query = "select p from Tipoimpuesto p where p.estado = :pestado "), 
//	@NamedQuery(name = "tipoimpuestofindById", query = "select p from Tipoimpuesto p where p.idtipoimpuesto = :idtipoimpuesto ")})
	
public class Tipoimpuesto implements Serializable {

	@Id
	@Column(name = "idtipoimpuesto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipoimpuesto_idtipoimpuesto_seq")
	@SequenceGenerator(name = "tipoimpuesto_idtipoimpuesto_seq", sequenceName = "tipoimpuesto_idtipoimpuesto_seq", allocationSize = 1)
	private Integer idtipoimpuesto;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "tasa", columnDefinition="numeric")
	private Double tasa;
	
	@Column(name = "dividendo", columnDefinition="numeric")
	private Double dividendo;

	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdtipoimpuesto() {
		return idtipoimpuesto;
	}

	public void setIdtipoimpuesto(Integer idtipoimpuesto) {
		this.idtipoimpuesto = idtipoimpuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}

	public Double getDividendo() {
		return dividendo;
	}

	public void setDividendo(Double dividendo) {
		this.dividendo = dividendo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
