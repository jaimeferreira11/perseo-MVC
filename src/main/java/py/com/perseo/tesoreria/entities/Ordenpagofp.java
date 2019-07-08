package py.com.perseo.tesoreria.entities;

import javax.persistence.*;

@Entity
@Table(name = "ordenpagofp")
public class Ordenpagofp {

	@Id
	@Column(name = "idordenpagofp")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordenpagofp_idordenpagofp_seq")
	@SequenceGenerator(name = "ordenpagofp_idordenpagofp_seq", sequenceName = "ordenpagofp_idordenpagofp_seq", allocationSize = 1)
	private Integer idordenpagofp;
	
	@Column(name = "importe", columnDefinition="numeric")
	private Double importe;
	
	@Column(name = "cotizacion", columnDefinition="numeric")
	private Double cotizacion;
	
	@Column(name = "transaccion")
	private String transaccion;
	
	@ManyToOne(targetEntity = Ordenpagocab.class)
	@JoinColumn(name = "idordenpagocab")
	private Ordenpagocab ordenpagocab;
	
	@ManyToOne(targetEntity = Cajacuenta.class)
	@JoinColumn(name = "idcajacuenta")
	Cajacuenta cajacuenta;

	public Integer getIdordenpagofp() {
		return idordenpagofp;
	}

	public void setIdordenpagofp(Integer idordenpagofp) {
		this.idordenpagofp = idordenpagofp;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Double cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(String transaccion) {
		this.transaccion = transaccion;
	}

	public Ordenpagocab getOrdenpagocab() {
		return ordenpagocab;
	}

	public void setOrdenpagocab(Ordenpagocab ordenpagocab) {
		this.ordenpagocab = ordenpagocab;
	}

	public Cajacuenta getCajacuenta() {
		return cajacuenta;
	}

	public void setCajacuenta(Cajacuenta cajacuenta) {
		this.cajacuenta = cajacuenta;
	}
}