package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conceptomov")

@NamedQueries( {@NamedQuery(name = "conceptoMovAll", query = "select p from Conceptomov p order by p.idconceptomov")})

public class Conceptomov implements Serializable {

	@Id
	@Column(name = "idconceptomov")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conceptomov_idconceptomov_seq")
	@SequenceGenerator(name = "conceptomov_idconceptomov_seq", sequenceName = "conceptomov_idconceptomov_seq", allocationSize = 1)
	private Integer idconceptomov;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "tipo")
	private String tipo;

	public Integer getIdconceptomov() {
		return idconceptomov;
	}

	public void setIdconceptomov(Integer idconceptomov) {
		this.idconceptomov = idconceptomov;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
