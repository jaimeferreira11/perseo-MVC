package py.com.perseo.tesoreria.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.venta.entities.Turno;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compracab")
@NamedQueries( { @NamedQuery(name = "compraFindById", query = "select p FROM Compracab p where p.idcompracab = :pidcompracab") ,	
@NamedQuery(name = "getCompracabByAsiento", query = "select p FROM Compracab p WHERE p.asientocab.idasientocab = :pidasientocab ") })
public class Compracab implements Serializable {

	@Id
	@Column(name = "idcompracab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compracab_idcompracab_seq")
	@SequenceGenerator(name = "compracab_idcompracab_seq", sequenceName = "compracab_idcompracab_seq", allocationSize = 1)
	private Integer idcompracab;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "fechalog")
	private Timestamp fechalog;

	@Column(name = "tipo")
	private String tipo;
	
	@Transient
	private Double saldo;

	@Column(name = "comprobante")
	private String comprobante;

	@Column(name = "importe", columnDefinition = "numeric")
	private Double importe;
	
	@Transient
	private Double importeeq;

	@Column(name = "pagado", columnDefinition = "numeric")
	private Double pagado;
	
	@Transient
	private Double importepago;

	@Column(name = "gravada10", columnDefinition = "numeric")
	private Double gravada10;

	@Column(name = "gravada5", columnDefinition = "numeric")
	private Double gravada5;

	@Column(name = "iva10", columnDefinition = "numeric")
	private Double iva10;

	@Column(name = "iva5", columnDefinition = "numeric")
	private Double iva5;

	@Column(name = "exenta", columnDefinition = "numeric")
	private Double exenta;

	@Column(name = "cotizacion", columnDefinition = "numeric")
	private Double cotizacion;

	@Column(name = "condicion")
	private String condicion;

	@Column(name = "timbrado")
	private String timbrado;

	@Column(name = "fecvenctimbrado")
	private Date fecvenctimbrado;
	
	@Transient
	private boolean generarOrdenPago;
	
	@Column(name = "cantidadcuota", columnDefinition = "int4")
	private Integer cantidadcuota;
	
	@ManyToOne(targetEntity = Turno.class)
	@JoinColumn(name = "idturno")
	Turno turno;
	
	@Transient
	private String concepto;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "compracab")
	private Set<Compradet> compradet = new HashSet<Compradet>(0);
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocab")
	Asientocab asientocab;

	@ManyToOne(targetEntity = Estadocompra.class)
	@JoinColumn(name = "idestadocompra")
	Estadocompra estadocompra;

	@ManyToOne(targetEntity = Proveedor.class)
	@JoinColumn(name = "idproveedor")
	Proveedor proveedor;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	@Transient
	private double montoOcasional;

	public double getMontoOcasional() {
		return montoOcasional;
	}

	public void setMontoOcasional(double montoOcasional) {
		this.montoOcasional = montoOcasional;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Integer getIdcompracab() {
		return idcompracab;
	}

	public void setIdcompracab(Integer idcompracab) {
		this.idcompracab = idcompracab;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechalog() {
		return fechalog;
	}

	public void setFechalog(Timestamp fechalog) {
		this.fechalog = fechalog;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getPagado() {
		return pagado;
	}

	public void setPagado(Double pagado) {
		this.pagado = pagado;
	}

	public Double getGravada10() {
		return gravada10;
	}

	public void setGravada10(Double gravada10) {
		this.gravada10 = gravada10;
	}

	public Double getGravada5() {
		return gravada5;
	}

	public void setGravada5(Double gravada5) {
		this.gravada5 = gravada5;
	}

	public Double getIva10() {
		return iva10;
	}

	public void setIva10(Double iva10) {
		this.iva10 = iva10;
	}

	public Double getIva5() {
		return iva5;
	}

	public void setIva5(Double iva5) {
		this.iva5 = iva5;
	}

	public Double getExenta() {
		return exenta;
	}

	public void setExenta(Double exenta) {
		this.exenta = exenta;
	}

	public Double getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Double cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}

	public Date getFecvenctimbrado() {
		return fecvenctimbrado;
	}

	public void setFecvenctimbrado(Date fecvenctimbrado) {
		this.fecvenctimbrado = fecvenctimbrado;
	}

	public Integer getCantidadcuota() {
		return cantidadcuota;
	}

	public void setCantidadcuota(Integer cantidadcuota) {
		this.cantidadcuota = cantidadcuota;
	}

	public Asientocab getAsientocab() {
		return asientocab;
	}

	public void setAsientocab(Asientocab asientocab) {
		this.asientocab = asientocab;
	}

	public Estadocompra getEstadocompra() {
		return estadocompra;
	}

	public void setEstadocompra(Estadocompra estadocompra) {
		this.estadocompra = estadocompra;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Compradet> getCompradet() {
		return compradet;
	}

	public void setCompradet(Set<Compradet> compradet) {
		this.compradet = compradet;
	}

	public void setImportepago(Double importepago) {
		this.importepago = importepago;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getImportepago() {
		return importepago;
	}

	public Double getImporteeq() {
		return importeeq;
	}

	public void setImporteeq(Double importeeq) {
		this.importeeq = importeeq;
	}

	public boolean isGenerarOrdenPago() {
		return generarOrdenPago;
	}

	public void setGenerarOrdenPago(boolean generarOrdenPago) {
		this.generarOrdenPago = generarOrdenPago;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	
}
