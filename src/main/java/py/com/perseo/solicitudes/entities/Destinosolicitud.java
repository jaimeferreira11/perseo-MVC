package py.com.perseo.solicitudes.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "destinosolicitud")
@NamedQueries( { @NamedQuery(name = "destinosolicitudfindAll", query = "select p from Destinosolicitud p "), 
				 @NamedQuery(name = "destinosolicitudfindById", query = "select p from Destinosolicitud p where p.iddestinosolicitud = :piddestinosolicitud ") })
public class Destinosolicitud implements Serializable {

	@Id
	@Column(name = "iddestinosolicitud")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destinosolicitud_iddestinosolicitud_seq")
	@SequenceGenerator(name = "destinosolicitud_iddestinosolicitud_seq", sequenceName = "destinosolicitud_iddestinosolicitud_seq", allocationSize = 1)
	private Integer iddestinosolicitud;

	@Column(name = "descripcion")
	private String descripcion;
	
	public Integer getIddestinosolicitud() {
		return iddestinosolicitud;
	}

	public void setIddestinosolicitud(Integer iddestinosolicitud) {
		this.iddestinosolicitud = iddestinosolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
