package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.venta.entities.Turno;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "historicoarticulo")
public class Historicoarticulo implements Serializable {

	@Id
	@Column(name = "idhistoricoarticulo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicoarticulo_idhistoricoarticulo_seq")
	@SequenceGenerator(name = "historicoarticulo_idhistoricoarticulo_seq", sequenceName = "historicoarticulo_idhistoricoarticulo_seq", allocationSize = 1)
	private Integer idhistoricoarticulo;

	@Column(name = "anterior", columnDefinition = "numeric")
	private Double anterior;

	@Column(name = "entrada", columnDefinition = "numeric")
	private Double entrada;

	@Column(name = "venta", columnDefinition = "numeric")
	private Double venta;

	@Column(name = "actual", columnDefinition = "numeric")
	private Double actual;

	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;

	@Column(name = "precioventa", columnDefinition = "numeric")
	private Double precioventa;
	
	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito")
	Deposito deposito;

	@ManyToOne(targetEntity = Articulodeposito.class)
	@JoinColumn(name = "idarticulodeposito")
	Articulodeposito articulodeposito;

	@ManyToOne(targetEntity = Turno.class)
	@JoinColumn(name = "idturno")
	Turno turno;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	public Integer getIdhistoricoarticulo() {
		return idhistoricoarticulo;
	}

	public void setIdhistoricoarticulo(Integer idhistoricoarticulo) {
		this.idhistoricoarticulo = idhistoricoarticulo;
	}

	public Double getAnterior() {
		return anterior;
	}

	public void setAnterior(Double anterior) {
		this.anterior = anterior;
	}

	public Double getEntrada() {
		return entrada;
	}

	public void setEntrada(Double entrada) {
		this.entrada = entrada;
	}

	public Double getVenta() {
		return venta;
	}

	public void setVenta(Double venta) {
		this.venta = venta;
	}

	public Double getActual() {
		return actual;
	}

	public void setActual(Double actual) {
		this.actual = actual;
	}

	public Double getPreciocosto() {
		return preciocosto;
	}

	public void setPreciocosto(Double preciocosto) {
		this.preciocosto = preciocosto;
	}

	public Double getPrecioventa() {
		return precioventa;
	}

	public void setPrecioventa(Double precioventa) {
		this.precioventa = precioventa;
	}

	public Articulodeposito getArticulodeposito() {
		return articulodeposito;
	}

	public void setArticulodeposito(Articulodeposito articulodeposito) {
		this.articulodeposito = articulodeposito;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

}
