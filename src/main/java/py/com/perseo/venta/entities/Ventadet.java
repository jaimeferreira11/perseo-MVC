package py.com.perseo.venta.entities;

import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ventadet")
public class Ventadet implements Serializable {

	@Id
	@Column(name = "idventadet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ventadet_idventadet_seq")
	@SequenceGenerator(name = "ventadet_idventadet_seq", sequenceName = "ventadet_idventadet_seq", allocationSize = 1)
	private Integer idventadet;
	
	@JoinColumn(name = "idventacab", referencedColumnName = "idventacab")
	@ManyToOne(optional = false)
	private Ventacab ventacab;
	
	@Column(name = "cantidad", columnDefinition = "numeric")
	private Double cantidad;
	
	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;
	
	@Column(name = "precioventa", columnDefinition = "numeric")
	private Double precioventa;
	
	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo")
	Articulo articulo;
	
	@ManyToOne(targetEntity = Tipoimpuesto.class)
	@JoinColumn(name = "idtipoimpuesto")
	Tipoimpuesto tipoimpuesto;

	@Column(name = "impuesto", columnDefinition = "numeric")
	private Double impuesto;
	
	@Column(name = "tasadescuento", columnDefinition = "numeric")
	private Double tasadescuento;
	
	@Column(name = "importedescuento", columnDefinition = "numeric")
	private Double importedescuento;
	
	@Column(name = "total", columnDefinition = "numeric")
	private Double total;

	public Integer getIdventadet() {
		return idventadet;
	}

	public void setIdventadet(Integer idventadet) {
		this.idventadet = idventadet;
	}

	public Ventacab getVentacab() {
		return ventacab;
	}

	public void setVentacab(Ventacab ventacab) {
		this.ventacab = ventacab;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
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


	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Tipoimpuesto getTipoimpuesto() {
		return tipoimpuesto;
	}

	public void setTipoimpuesto(Tipoimpuesto tipoimpuesto) {
		this.tipoimpuesto = tipoimpuesto;
	}

	public Double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Double impuesto) {
		this.impuesto = impuesto;
	}

	public Double getTasadescuento() {
		return tasadescuento;
	}

	public void setTasadescuento(Double tasadescuento) {
		this.tasadescuento = tasadescuento;
	}

	public Double getImportedescuento() {
		return importedescuento;
	}

	public void setImportedescuento(Double importedescuento) {
		this.importedescuento = importedescuento;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
