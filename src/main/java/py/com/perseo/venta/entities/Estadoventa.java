package py.com.perseo.venta.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estadoventa")
public class Estadoventa implements Serializable {

	@Id
	@Column(name = "idestadoventa")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estadoventa_idestadoventa_seq")
	@SequenceGenerator(name = "estadoventa_idestadoventa_seq", sequenceName = "estadoventa_idestadoventa_seq", allocationSize = 1)
	private Integer idestadoventa;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdestadoventa() {
		return idestadoventa;
	}

	public void setIdestadoventa(Integer idestadoventa) {
		this.idestadoventa = idestadoventa;
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