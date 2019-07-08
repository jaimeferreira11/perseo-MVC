package py.com.perseo.solicitudes.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plazo")
@NamedQueries( { @NamedQuery(name = "plazofindAll", query = "select p from Plazo p order by p.dias "), @NamedQuery(name = "plazofindByEstado", query = "select p from Plazo p where p.estado = :pestado"), @NamedQuery(name = "plazofindById", query = "select p from Plazo p where p.idplazo = :pidplazo") })
public class Plazo implements Serializable {

	@Id
	@Column(name = "idplazo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plazo_idplazo_seq")
	@SequenceGenerator(name = "plazo_idplazo_seq", sequenceName = "plazo_idplazo_seq", allocationSize = 1)
	private Integer idplazo;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "dias")
	int dias;

	@Column(name = "estado")
	Boolean estado;

	public Integer getIdplazo() {
		return idplazo;
	}

	public void setIdplazo(Integer idplazo) {
		this.idplazo = idplazo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
