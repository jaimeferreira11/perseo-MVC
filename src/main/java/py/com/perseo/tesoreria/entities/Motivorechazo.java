package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "motivorechazo")
@NamedQueries( { @NamedQuery(name = "motivorechazofindAll", query = "select p from Motivorechazo p where p.estado = true "), 
	@NamedQuery(name = "motivorechazofindByEstado", query = "select p from Motivorechazo p where p.estado = :pestado"), 
	@NamedQuery(name = "motivorechazofindById", query = "select p from Motivorechazo p where p.idmotivorechazo = :pidmotivorechazo and p.estado = true") })
public class Motivorechazo implements Serializable {

	@Id
	@Column(name = "idmotivorechazo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motivorechazo_idmotivorechazo_seq")
	@SequenceGenerator(name = "motivorechazo_idmotivorechazo_seq", sequenceName = "motivorechazo_idmotivorechazo_seq", allocationSize = 1)
	private Integer idmotivorechazo;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdmotivorechazo() {
		return idmotivorechazo;
	}

	public void setIdmotivorechazo(Integer idmotivorechazo) {
		this.idmotivorechazo = idmotivorechazo;
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