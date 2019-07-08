package py.com.perseo.venta.entities;

import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.entities.Metodocobro;
import py.com.perseo.tesoreria.entities.Moneda;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "facturacab")
public class Facturacab {

	@Id
	@Column(name = "idfacturacab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facturacab_idfacturacab_seq")
	@SequenceGenerator(name = "facturacab_idfacturacab_seq", sequenceName = "facturacab_idfacturacab_seq", allocationSize = 1)
	private Integer idfacturacab;

	@Column(name = "fecha")
	private Date fecha;


	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "establecimiento")
	private Integer establecimiento;

	@Column(name = "puntoexpedicion")
	private Integer puntoexpedicion;

	@Column(name = "nrofactura")
	private Integer nrofactura;

	@ManyToOne(targetEntity = Tipofactura.class)
	@JoinColumn(name = "idtipofactura")
	private Tipofactura tipofactura;

	@Column(name = "importe", columnDefinition = "numeric")
	private Double importe;

	@Column(name = "saldo", columnDefinition = "numeric")
	private Double saldo;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "impreso")
	private Boolean impreso;

	@Transient
	private List<Facturadet> detalleFactura;
	
	@Transient
	private List<Facturaformacobro> listFormaPago;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocab")
	Asientocab asientocab;

	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "idcliente")
	Cliente cliente;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@ManyToOne(targetEntity = Metodocobro.class)
	@JoinColumn(name = "idmetodocobro")
	Metodocobro metodocobro;
	
	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja")
	Caja caja;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;
	
	@ManyToOne(targetEntity = Ventacab.class)
	@JoinColumn(name = "idventacab")
	Ventacab ventacab;
	
	@ManyToOne(targetEntity = Turno.class)
	@JoinColumn(name = "idturno")
	Turno turno;
	
	@Column(name = "timbrado")
	private Integer timbrado;
	
	@Column(name = "vigenciatimbrado")
	private Date vigenciatimbrado;
	
	@Column(name = "clasefactura")
	private String clasefactura;
	
	

	public List<Facturaformacobro> getListFormaPago() {
		return listFormaPago;
	}

	public void setListFormaPago(List<Facturaformacobro> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}

	public String getClasefactura() {
		return clasefactura;
	}

	public void setClasefactura(String clasefactura) {
		this.clasefactura = clasefactura;
	}

	public Metodocobro getMetodocobro() {
		return metodocobro;
	}

	public void setMetodocobro(Metodocobro metodocobro) {
		this.metodocobro = metodocobro;
	}

	public Integer getIdfacturacab() {
		return idfacturacab;
	}

	public void setIdfacturacab(Integer idfacturacab) {
		this.idfacturacab = idfacturacab;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Integer establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Integer getPuntoexpedicion() {
		return puntoexpedicion;
	}

	public void setPuntoexpedicion(Integer puntoexpedicion) {
		this.puntoexpedicion = puntoexpedicion;
	}

	public Integer getNrofactura() {
		return nrofactura;
	}

	public void setNrofactura(Integer nrofactura) {
		this.nrofactura = nrofactura;
	}

	public Tipofactura getTipofactura() {
		return tipofactura;
	}

	public void setTipofactura(Tipofactura tipofactura) {
		this.tipofactura = tipofactura;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Asientocab getAsientocab() {
		return asientocab;
	}

	public void setAsientocab(Asientocab asientocab) {
		this.asientocab = asientocab;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public List<Facturadet> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<Facturadet> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	public Boolean getImpreso() {
		return impreso;
	}

	public void setImpreso(Boolean impreso) {
		this.impreso = impreso;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Ventacab getVentacab() {
		return ventacab;
	}

	public void setVentacab(Ventacab ventacab) {
		this.ventacab = ventacab;
	}

	public Integer getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Integer timbrado) {
		this.timbrado = timbrado;
	}

	public Date getVigenciatimbrado() {
		return vigenciatimbrado;
	}

	public void setVigenciatimbrado(Date vigenciatimbrado) {
		this.vigenciatimbrado = vigenciatimbrado;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	

}
