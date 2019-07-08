package py.com.perseo.solicitudes.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "integrantecantidad")
@NamedQueries( { @NamedQuery(name = "integrantecantidadfindAll", query = "select p from Integrantecantidad p "), 
@NamedQuery(name = "integrantecantidadfindById", query = "select p from Integrantecantidad p where p.idintegrantecantidad = :pidintegrantecantidad") })

public class Integrantecantidad implements Serializable {

	@Id
	@Column(name = "idintegrantecantidad")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "integrantecantidad_idintegrantecantidad_seq")
	@SequenceGenerator(name = "integrantecantidad_idintegrantecantidad_seq", sequenceName = "integrantecantidad_idintegrantecantidad_seq", allocationSize = 1)
	private Integer idintegrantecantidad;

	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "estado")
	private Boolean estado;
	
	public Integer getIdintegrantecantidad() {
		return idintegrantecantidad;
	}

	public void setIdintegrantecantidad(Integer idintegrantecantidad) {
		this.idintegrantecantidad = idintegrantecantidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
