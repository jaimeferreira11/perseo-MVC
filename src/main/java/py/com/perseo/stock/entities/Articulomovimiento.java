package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.venta.entities.Turno;
import py.com.perseo.venta.entities.Ventacab;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "articulomovimiento")
public class Articulomovimiento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idarticulomovimiento")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulomovimiento_idarticulomovimiento_seq")
	@SequenceGenerator(name = "articulomovimiento_idarticulomovimiento_seq", sequenceName = "articulomovimiento_idarticulomovimiento_seq", allocationSize = 1)
	private Integer idarticulomovimiento;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "columna")
	private String columna;
	
	@Transient
	private Double total;
	
	@Transient
	private Double precioventa;
	
	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo", nullable=false)
	Articulo articulo;
	
	@ManyToOne(targetEntity = Ventacab.class)
	@JoinColumn(name = "idventacab", nullable=true)
	Ventacab ventacab;
	
	@ManyToOne(targetEntity = Compracab.class)
	@JoinColumn(name = "idcompracab", nullable=true)
	Compracab compracab;
	
	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito")
	Deposito deposito;
	
	@ManyToOne(targetEntity = Conceptomov.class)
	@JoinColumn(name = "idconceptomov")
	Conceptomov conceptomov;
	
	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;
	
	@ManyToOne(targetEntity = Turno.class)
	@JoinColumn(name = "idturno")
	Turno turno;
	
	@Column(name = "cantidad",columnDefinition="numeric")
	private Double cantidad;
	
	private String obs;
	
	@Transient
	private Double cantidad_egreso;
	
	@Transient
	private Double cantidad_actual;
	
	public Double getCantidad_egreso() {
		return cantidad_egreso;
	}

	public void setCantidad_egreso(Double cantidad_egreso) {
		this.cantidad_egreso = cantidad_egreso;
	}

	public Integer getIdarticulomovimiento() {
		return idarticulomovimiento;
	}

	public void setIdarticulomovimiento(Integer idarticulomovimiento) {
		this.idarticulomovimiento = idarticulomovimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Conceptomov getConceptomov() {
		return conceptomov;
	}

	public void setConceptomov(Conceptomov conceptomov) {
		this.conceptomov = conceptomov;
	}

	public Ventacab getVentacab() {
		return ventacab;
	}

	public void setVentacab(Ventacab ventacab) {
		this.ventacab = ventacab;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Compracab getCompracab() {
		return compracab;
	}

	public void setCompracab(Compracab compracab) {
		this.compracab = compracab;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getPrecioventa() {
		return precioventa;
	}

	public void setPrecioventa(Double precioventa) {
		this.precioventa = precioventa;
	}

	public Double getCantidad_actual() {
		return cantidad_actual;
	}

	public void setCantidad_actual(Double cantidad_actual) {
		this.cantidad_actual = cantidad_actual;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
	
}
