package py.com.perseo.stock.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "marca")
//@NamedQueries( { @NamedQuery(name = "marcafindAll", query = "select p from Marca p "), 
//@NamedQuery(name = "marcafindByPrimaryKey", query = "select p from Marca p where p.idmarca = :pidmarca")})
public class Marca implements Serializable {

	@Id
	@Column(name = "idmarca")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marca_idmarca_seq")
	@SequenceGenerator(name = "marca_idmarca_seq", sequenceName = "marca_idmarca_seq", allocationSize = 1)
	private Integer idmarca;
	
	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdmarca() {
		return idmarca;
	}

	public void setIdmarca(Integer idmarca) {
		this.idmarca = idmarca;
	}

}
