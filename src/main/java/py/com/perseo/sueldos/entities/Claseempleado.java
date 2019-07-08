package py.com.perseo.sueldos.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "claseempleado")
public class Claseempleado implements Serializable {

	@Id
	@Column(name = "idclaseempleado")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claseempleado_idclaseempleado_seq")
	@SequenceGenerator(name = "claseempleado_idclaseempleado_seq", sequenceName = "claseempleado_idclaseempleado_seq", allocationSize = 1)
	private Integer idclaseempleado;

	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdclaseempleado() {
		return idclaseempleado;
	}

	public void setIdclaseempleado(Integer idclaseempleado) {
		this.idclaseempleado = idclaseempleado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
