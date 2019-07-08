package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estadocompra")
@NamedQueries( { @NamedQuery(name = "estadocomprafindAll", query = "select p from Estadocompra p "),
@NamedQuery(name = "estadocomprafindById", query = "select p from Estadocompra p where p.idestadocompra = :pidestadocompra") })
public class Estadocompra implements Serializable {

	@Id
	@Column(name = "idestadocompra")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadocompra_idestadocompra_seq")
	@SequenceGenerator(name = "estadocompra_idestadocompra_seq", sequenceName = "estadocompra_idestadocompra_seq", allocationSize = 1)
	private Integer idestadocompra;

	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdestadocompra() {
		return idestadocompra;
	}

	public void setIdestadocompra(Integer idestadocompra) {
		this.idestadocompra = idestadocompra;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
