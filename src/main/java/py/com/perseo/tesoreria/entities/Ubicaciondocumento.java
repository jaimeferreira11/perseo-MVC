package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ubicaciondocumento")
//@NamedQueries( { @NamedQuery(name = "ubicacionFindAll", query = "select p from Ubicaciondocumento p order by p.idubicaciondocumento "),
//@NamedQuery(name = "ubicacionFindByEstado", query = "select p from Ubicaciondocumento p where p.estado = :pestado"),
//@NamedQuery(name = "ubicacionFindById", query = "select p from Ubicaciondocumento p where p.idubicaciondocumento = :pidubicaciondocumento"),
//@NamedQuery(name = "ubicacionFindByIdAndEstado", query = "select p from Ubicaciondocumento p where p.idubicaciondocumento = :pidubicaciondocumento AND p.estado = :pestado")})
public class Ubicaciondocumento implements Serializable {

	@Id
	@Column(name = "idubicaciondocumento")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicaciondocumento_idubicaciondocumento_seq")
	@SequenceGenerator(name = "ubicaciondocumento_idubicaciondocumento_seq", sequenceName = "ubicaciondocumento_idubicaciondocumento_seq", allocationSize = 1)
	private Integer idubicaciondocumento;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private boolean estado;

	public Integer getIdubicaciondocumento() {
		return idubicaciondocumento;
	}

	public void setIdubicaciondocumento(Integer idubicaciondocumento) {
		this.idubicaciondocumento = idubicaciondocumento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
