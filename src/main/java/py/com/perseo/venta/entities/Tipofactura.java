package py.com.perseo.venta.entities;

import javax.persistence.*;

@Entity
@Table(name = "tipofactura")
public class Tipofactura{

	@Id
	@Column(name = "idtipofactura")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipofactura_idtipofactura_seq")
	@SequenceGenerator(name = "tipofactura_idtipofactura_seq", sequenceName = "tipofactura_idtipofactura_seq", allocationSize = 1)
	private Integer idtipofactura;

	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdtipofactura() {
		return idtipofactura;
	}

	public void setIdtipofactura(Integer idtipofactura) {
		this.idtipofactura = idtipofactura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

	

}
