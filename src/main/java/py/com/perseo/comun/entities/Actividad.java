package py.com.perseo.comun.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "actividad")
@NamedQueries( { @NamedQuery(name = "actividadFindAll", query = "select p from Actividad p order by p.idactividad")})
public class Actividad implements Serializable {

	@Id
	@Column(name = "idactividad")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actividad_idactividad_seq")
	@SequenceGenerator(name = "actividad_idactividad_seq", sequenceName = "actividad_idactividad_seq", allocationSize = 1)
	private Integer idactividad;
	
	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdactividad() {
		return idactividad;
	}

	public void setIdactividad(Integer idactividad) {
		this.idactividad = idactividad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
