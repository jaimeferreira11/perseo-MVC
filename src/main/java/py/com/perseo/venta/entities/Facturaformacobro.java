package py.com.perseo.venta.entities;

import py.com.perseo.tesoreria.entities.Cajacuenta;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "facturaformacobro")
public class Facturaformacobro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idfacturaformacobro")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facturaformacobro_idfacturaformacobro_seq")
	@SequenceGenerator(name = "facturaformacobro_idfacturaformacobro_seq", sequenceName = "facturaformacobro_idfacturaformacobro_seq", allocationSize = 1)
	private Integer idfacturaformacobro;

	@Column(name = "importe", columnDefinition = "numeric")
	private Double importe;

	@Column(name = "estado")
	private Boolean estado;

	@ManyToOne(targetEntity = Cajacuenta.class)
	@JoinColumn(name = "idcajacuenta")
	Cajacuenta cajacuenta;

	@ManyToOne(targetEntity = Facturacab.class)
	@JoinColumn(name = "idfacturacab")
	Facturacab facturacab;

	public Integer getIdfacturaformacobro() {
		return idfacturaformacobro;
	}

	public void setIdfacturaformacobro(Integer idfacturaformacobro) {
		this.idfacturaformacobro = idfacturaformacobro;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Cajacuenta getCajacuenta() {
		return cajacuenta;
	}

	public void setCajacuenta(Cajacuenta cajacuenta) {
		this.cajacuenta = cajacuenta;
	}

	public Facturacab getFacturacab() {
		return facturacab;
	}

	public void setFacturacab(Facturacab facturacab) {
		this.facturacab = facturacab;
	}

}
