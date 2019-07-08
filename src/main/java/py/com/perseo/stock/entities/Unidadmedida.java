package py.com.perseo.stock.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "unidadmedida")
//@NamedQueries( { @NamedQuery(name = "unidadmedidafindAll", query = "select p from Unidadmedida p "), 
//@NamedQuery(name = "unidadmedidafindByEstado", query = "select p from Unidadmedida p where p.estado = :pestado order by p.descripcion"),
//@NamedQuery(name = "unidadmedidafindByPrimaryKey", query = "select p from Unidadmedida p where p.idunidadmedida = :pidunidadmedida")})
public class Unidadmedida implements Serializable {

	@Id
	@Column(name = "idunidadmedida")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidadmedida_idunidadmedida_seq")
	@SequenceGenerator(name = "unidadmedida_idunidadmedida_seq", sequenceName = "unidadmedida_idunidadmedida_seq", allocationSize = 1)
	private Integer idunidadmedida;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdunidadmedida() {
		return idunidadmedida;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void setIdunidadmedida(Integer idunidadmedida) {
		this.idunidadmedida = idunidadmedida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
