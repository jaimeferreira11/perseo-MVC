package py.com.perseo.solicitudes.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estadosolicitud")
//@NamedQueries( { @NamedQuery(name = "estadosolicitudfindAll", query = "select p from Estadosolicitud p "), 
//@NamedQuery(name = "estadosolicitudfindById", query = "select p from Estadosolicitud p where p.idestadosolicitud = :pidestadosolicitud") })
public class Estadosolicitud implements Serializable {

	@Id
	@Column(name = "idestadosolicitud")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadosolicitud_idestadosolicitud_seq")
	@SequenceGenerator(name = "estadosolicitud_idestadosolicitud_seq", sequenceName = "estadosolicitud_idestadosolicitud_seq", allocationSize = 1)
	private Integer idestadosolicitud;
	
	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdestadosolicitud() {
		return idestadosolicitud;
	}

	public void setIdestadosolicitud(Integer idestadosolicitud) {
		this.idestadosolicitud = idestadosolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
