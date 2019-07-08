package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.eess.entities.Tanque;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "articulodeposito")
public class Articulodeposito implements Serializable {

	@Id
	@Column(name = "idarticulodeposito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulodeposito_idarticulodeposito_seq")
	@SequenceGenerator(name = "articulodeposito_idarticulodeposito_seq", sequenceName = "articulodeposito_idarticulodeposito_seq", allocationSize = 1)
	private Integer idarticulodeposito;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario",nullable=false)
	Usuario usuario;
	
	@Transient
	private Boolean modificado;
	
	@Transient
	private Double montocobrado;
	
	@Transient
	private Double margen;
	
	@Transient
	private Double cantidadVenta;

	@Column(name = "cantidad", columnDefinition = "numeric")
	private Double cantidad;

	@Column(name = "cantidadminima", columnDefinition = "numeric")
	private Double cantidadminima;

	@Column(name = "cantidadbloqueo", columnDefinition = "numeric")
	private Double cantidadbloqueo;

	@Column(name = "inventariofisico", columnDefinition = "numeric")
	private Double inventariofisico;

	@Column(name = "fechaultinventario")
	private Date fechaultinventario;

	@Column(name = "fechavencimiento")
	private Date fechavencimiento;

	@Column(name = "fechaultcompra")
	private Date fechaultcompra;

	@Column(name = "fechaultventa")
	private Date fechaultventa;

	@Column(name = "nrolote")
	private Integer nrolote;

	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;

	@Column(name = "preciocostoeq", columnDefinition = "numeric")
	private Double preciocostoeq;

	@Column(name = "precioventa", columnDefinition = "numeric")
	private Double precioventa;

	@Column(name = "precioventaeq", columnDefinition = "numeric")
	private Double precioventaeq;
	
	@Column(name = "estado", nullable=false)
	private Boolean estado;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal",nullable=false)
	Sucursal sucursal;

	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo",nullable=false)
	Articulo articulo;
	
	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito",nullable=false)
	Deposito deposito;

	@ManyToOne(targetEntity = Tanque.class)
	@JoinColumn(name = "nrotanque")
	Tanque tanque;

	public Integer getIdarticulodeposito() {
		return idarticulodeposito;
	}

	public void setIdarticulodeposito(Integer idarticulodeposito) {
		this.idarticulodeposito = idarticulodeposito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getCantidadminima() {
		return cantidadminima;
	}

	public void setCantidadminima(Double cantidadminima) {
		this.cantidadminima = cantidadminima;
	}

	public Double getCantidadbloqueo() {
		return cantidadbloqueo;
	}

	public void setCantidadbloqueo(Double cantidadbloqueo) {
		this.cantidadbloqueo = cantidadbloqueo;
	}

	public Double getInventariofisico() {
		return inventariofisico;
	}

	public void setInventariofisico(Double inventariofisico) {
		this.inventariofisico = inventariofisico;
	}

	public Date getFechaultinventario() {
		return fechaultinventario;
	}

	public void setFechaultinventario(Date fechaultinventario) {
		this.fechaultinventario = fechaultinventario;
	}

	public Date getFechavencimiento() {
		return fechavencimiento;
	}

	public void setFechavencimiento(Date fechavencimiento) {
		this.fechavencimiento = fechavencimiento;
	}

	public Date getFechaultcompra() {
		return fechaultcompra;
	}

	public void setFechaultcompra(Date fechaultcompra) {
		this.fechaultcompra = fechaultcompra;
	}

	public Date getFechaultventa() {
		return fechaultventa;
	}

	public void setFechaultventa(Date fechaultventa) {
		this.fechaultventa = fechaultventa;
	}

	public Integer getNrolote() {
		return nrolote;
	}

	public void setNrolote(Integer nrolote) {
		this.nrolote = nrolote;
	}

	public Double getPreciocosto() {
		return preciocosto;
	}

	public void setPreciocosto(Double preciocosto) {
		this.preciocosto = preciocosto;
	}

	public Double getPreciocostoeq() {
		return preciocostoeq;
	}

	public void setPreciocostoeq(Double preciocostoeq) {
		this.preciocostoeq = preciocostoeq;
	}

	public Double getPrecioventa() {
		return precioventa;
	}

	public void setPrecioventa(Double precioventa) {
		this.precioventa = precioventa;
	}

	public Double getPrecioventaeq() {
		return precioventaeq;
	}

	public void setPrecioventaeq(Double precioventaeq) {
		this.precioventaeq = precioventaeq;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Tanque getTanque() {
		return tanque;
	}

	public void setTanque(Tanque tanque) {
		this.tanque = tanque;
	}

	public Boolean getModificado() {
		return modificado;
	}

	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}

	public Double getMontocobrado() {
		return montocobrado;
	}

	public void setMontocobrado(Double montocobrado) {
		this.montocobrado = montocobrado;
	}

	public Double getCantidadVenta() {
		return cantidadVenta;
	}

	public void setCantidadVenta(Double cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Double getMargen() {
		return margen;
	}

	public void setMargen(Double margen) {
		this.margen = margen;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	


}
