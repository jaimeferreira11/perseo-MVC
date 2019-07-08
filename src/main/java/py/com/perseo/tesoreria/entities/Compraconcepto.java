package py.com.perseo.tesoreria.entities;


import py.com.perseo.contabilidad.entities.Plancuenta;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "compraconcepto")
//@NamedQueries( { @NamedQuery(name = "compraconceptofindAll", query = "select p from Compraconcepto p order by p.idcompraconcepto ") ,
//@NamedQuery(name = "compraconceptofindById", query = "select p from Compraconcepto p where p.idcompraconcepto = :pidcompraconcepto")})
public class Compraconcepto implements Serializable {

	@Id
	@Column(name = "idcompraconcepto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compraconcepto_idcompraconcepto_seq")
	@SequenceGenerator(name = "compraconcepto_idcompraconcepto_seq", sequenceName = "compraconcepto_idcompraconcepto_seq", allocationSize = 1)
	private Integer idcompraconcepto;

	@Column(name = "descripcion")
	private String descripcion;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	public Integer getIdcompraconcepto() {
		return idcompraconcepto;
	}

	public void setIdcompraconcepto(Integer idcompraconcepto) {
		this.idcompraconcepto = idcompraconcepto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

}
