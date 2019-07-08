package py.com.perseo.venta.entities;

import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulodeposito;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facturadet")
public class Facturadet implements Serializable {

  
	@Id
	@Column(name = "idfacturadet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facturadet_idfacturadet_seq")
	@SequenceGenerator(name = "facturadet_idfacturadet_seq", sequenceName = "facturadet_idfacturadet_seq", allocationSize = 1)
	private Integer idfacturadet;

	@Column(name = "concepto")
	private String concepto;

	@Column(name = "cantidad")
	private Double cantidad;

	@Column(name = "exenta", columnDefinition = "numeric")
	private Double exenta;

	@Column(name = "gravada5", columnDefinition = "numeric")
	private Double gravada5;

	@Column(name = "gravada10", columnDefinition = "numeric")
	private Double gravada10;

	@Column(name = "iva5", columnDefinition = "numeric")
	private Double iva5;

	@Column(name = "iva10", columnDefinition = "numeric")
	private Double iva10;
	
	@Column(name = "precioventa", columnDefinition = "numeric")
	private Double precioventa;
	
	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;
	
	@Column(name = "descuento", columnDefinition = "numeric")
	private Double descuento;
	
	@Column(name = "total", columnDefinition = "numeric")
	private Double total;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;
	
	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuentaiva")
	Plancuenta plancuentaiva;
	
	@ManyToOne(targetEntity = Facturacab.class)
	@JoinColumn(name = "idfacturacab")
	private Facturacab facturacab;
	
	@ManyToOne(targetEntity = Articulodeposito.class)
	@JoinColumn(name = "idarticulodeposito")
	private Articulodeposito articulodeposito;
	
	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo")
	private Articulo articulo;

	public Facturacab getFacturacab() {
		return facturacab;
	}

	public void setFacturacab(Facturacab facturacab) {
		this.facturacab = facturacab;
	}

	public Integer getIdfacturadet() {
		return idfacturadet;
	}

	public void setIdfacturadet(Integer idfacturadet) {
		this.idfacturadet = idfacturadet;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getExenta() {
		return exenta;
	}

	public void setExenta(Double exenta) {
		this.exenta = exenta;
	}

	public Double getGravada5() {
		return gravada5;
	}

	public void setGravada5(Double gravada5) {
		this.gravada5 = gravada5;
	}

	public Double getGravada10() {
		return gravada10;
	}

	public void setGravada10(Double gravada10) {
		this.gravada10 = gravada10;
	}

	public Double getIva5() {
		return iva5;
	}

	public void setIva5(Double iva5) {
		this.iva5 = iva5;
	}

	public Double getIva10() {
		return iva10;
	}

	public void setIva10(Double iva10) {
		this.iva10 = iva10;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Plancuenta getPlancuentaiva() {
		return plancuentaiva;
	}

	public void setPlancuentaiva(Plancuenta plancuentaiva) {
		this.plancuentaiva = plancuentaiva;
	}

	public Double getPrecioventa() {
		return precioventa;
	}

	public void setPrecioventa(Double precioventa) {
		this.precioventa = precioventa;
	}

	public Double getPreciocosto() {
		return preciocosto;
	}

	public void setPreciocosto(Double preciocosto) {
		this.preciocosto = preciocosto;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Articulodeposito getArticulodeposito() {
		return articulodeposito;
	}

	public void setArticulodeposito(Articulodeposito articulodeposito) {
		this.articulodeposito = articulodeposito;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	
	
	
}