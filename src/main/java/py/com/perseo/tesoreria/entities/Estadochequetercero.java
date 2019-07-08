package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estadochequetercero")
//@NamedQueries( { @NamedQuery(name = "estadochequetercerofindAll", query = "select p from Estadochequetercero p "), 
//	@NamedQuery(name = "estadochequetercerofindByEstado", query = "select p from Estadochequetercero p where p.estado = :pestado"), 
//	@NamedQuery(name = "estadochequetercerofindById", query = "select p from Estadochequetercero p where p.idestadochequetercero = :pidestadochequetercero") })
public class Estadochequetercero implements Serializable {

	@Id
	@Column(name = "idestadochequetercero")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadochequetercero_idestadochequetercero_seq")
	@SequenceGenerator(name = "estadochequetercero_idestadochequetercero_seq", sequenceName = "estadochequetercero_idestadochequetercero_seq", allocationSize = 1)
	private Integer idestadochequetercero;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdestadochequetercero() {
		return idestadochequetercero;
	}

	public void setIdestadochequetercero(Integer idestadochequetercero) {
		this.idestadochequetercero = idestadochequetercero;
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

}
