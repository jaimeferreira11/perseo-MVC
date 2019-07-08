package py.com.perseo.contabilidad.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.entities.Moneda;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "asientodet")
public class Asientodet implements Serializable, Cloneable {

	@Id
	@Column(name = "idasientodet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asientodet_idasientodet_seq")
	@SequenceGenerator(name = "asientodet_idasientodet_seq", sequenceName = "asientodet_idasientodet_seq", allocationSize = 1)
	private Integer idasientodet;

	@JoinColumn(name = "idasientocab", referencedColumnName = "idasientocab")
	@ManyToOne(optional = false)
	private Asientocab asientocab;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja", nullable = true)
	Caja caja;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "caja")
	private Boolean iscaja;

	@Column(name = "conciliado", nullable=false)
	private Boolean conciliado;

	@Column(name = "concepto")
	private String concepto;

	@Column(name = "debito", columnDefinition = "numeric")
	private Double debito;

	@Column(name = "debitoequivalente", columnDefinition = "numeric")
	private Double debitoequivalente;

	@Column(name = "credito", columnDefinition = "numeric")
	private Double credito;

	@Column(name = "creditoequivalente", columnDefinition = "numeric")
	private Double creditoequivalente;

	public Integer getIdasientodet() {
		return idasientodet;
	}

	public void setIdasientodet(Integer idasientodet) {
		this.idasientodet = idasientodet;
	}

	public Asientocab getAsientocab() {
		return asientocab;
	}

	public void setAsientocab(Asientocab asientocab) {
		this.asientocab = asientocab;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Boolean getIscaja() {
		return iscaja;
	}

	public void setIscaja(Boolean iscaja) {
		this.iscaja = iscaja;
	}

	public Boolean getConciliado() {
		return conciliado;
	}

	public void setConciliado(Boolean conciliado) {
		this.conciliado = conciliado;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getDebito() {
		return debito;
	}

	public void setDebito(Double debito) {
		this.debito = debito;
	}

	public Double getDebitoequivalente() {
		return debitoequivalente;
	}

	public void setDebitoequivalente(Double debitoequivalente) {
		this.debitoequivalente = debitoequivalente;
	}

	public Double getCredito() {
		return credito;
	}

	public void setCredito(Double credito) {
		this.credito = credito;
	}

	public Double getCreditoequivalente() {
		return creditoequivalente;
	}

	public void setCreditoequivalente(Double creditoequivalente) {
		this.creditoequivalente = creditoequivalente;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede clonar ");
			ex.printStackTrace();
		}
		return obj;
	}
}
