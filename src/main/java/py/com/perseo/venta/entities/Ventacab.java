package py.com.perseo.venta.entities;

import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.tesoreria.entities.Metodocobro;
import py.com.perseo.tesoreria.entities.Moneda;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ventacab")
public class Ventacab implements Serializable {

	@Id
	@Column(name = "idventacab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ventacab_idventacab_seq")
	@SequenceGenerator(name = "ventacab_idventacab_seq", sequenceName = "ventacab_idventacab_seq", allocationSize = 1)
	private Integer idventacab;
	
	@Column(name = "fecha")
	private Date fecha;
	
	
	@Column(name = "fechafacturacion")
	private Date fechafacturacion;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito")
	Deposito deposito;
	
	
	@ManyToOne(targetEntity = Estadoventa.class)
	@JoinColumn(name = "idestadoventa")
	Estadoventa estadoventa;
	
	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "idcliente")
	Cliente cliente;
	
	@ManyToOne(targetEntity = Formapago.class)
	@JoinColumn(name = "idformapago")
	Formapago formapago;
	
	
	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;
	
	@ManyToOne(targetEntity = Metodocobro.class)
	@JoinColumn(name = "idmetodocobro")
	Metodocobro metodocobro;
	
	@Column(name = "importe", columnDefinition="numeric")
	private Double importe;
	
	
	@Column(name = "impuesto", columnDefinition="numeric")
	private Double impuesto;
	
	@Column(name = "cantidadtotal", columnDefinition="numeric")
	private Double cantidadtotal;
	
	@Column(name = "nroloteventa")
	private Integer nroloteventa;
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocab")
	Asientocab asientocab;
	
	
	@Column(name = "promediodescuento", columnDefinition="numeric")
	private Double promediodescuento;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "cantidadcaja")
	private Integer cantidadcaja;
	
	@Column(name = "retenido")
	private Boolean retenido;
	
	@Column(name = "motivoretencion")
	private String motivoretencion;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;
	
	@ManyToOne(targetEntity = Turno.class)
	@JoinColumn(name = "idturno")
	Turno turno;
	
	@Transient
	private List<Ventadet> detalleVenta;
	

	public List<Ventadet> getDetalleVenta() {
		return detalleVenta;
	}

	public void setDetalleVenta(List<Ventadet> detalleVenta) {
		this.detalleVenta = detalleVenta;
	}

	public Integer getIdventacab() {
		return idventacab;
	}

	public void setIdventacab(Integer idventacab) {
		this.idventacab = idventacab;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Date getFechafacturacion() {
		return fechafacturacion;
	}

	public void setFechafacturacion(Date fechafacturacion) {
		this.fechafacturacion = fechafacturacion;
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

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Estadoventa getEstadoventa() {
		return estadoventa;
	}

	public void setEstadoventa(Estadoventa estadoventa) {
		this.estadoventa = estadoventa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Formapago getFormapago() {
		return formapago;
	}

	public void setFormapago(Formapago formapago) {
		this.formapago = formapago;
	}


	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Double impuesto) {
		this.impuesto = impuesto;
	}

	public Double getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(Double cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	public Integer getNroloteventa() {
		return nroloteventa;
	}

	public void setNroloteventa(Integer nroloteventa) {
		this.nroloteventa = nroloteventa;
	}

	public Asientocab getAsientocab() {
		return asientocab;
	}

	public void setAsientocab(Asientocab asientocab) {
		this.asientocab = asientocab;
	}


	public Double getPromediodescuento() {
		return promediodescuento;
	}

	public void setPromediodescuento(Double promediodescuento) {
		this.promediodescuento = promediodescuento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getCantidadcaja() {
		return cantidadcaja;
	}

	public void setCantidadcaja(Integer cantidadcaja) {
		this.cantidadcaja = cantidadcaja;
	}

	public Boolean getRetenido() {
		return retenido;
	}

	public void setRetenido(Boolean retenido) {
		this.retenido = retenido;
	}

	public String getMotivoretencion() {
		return motivoretencion;
	}

	public void setMotivoretencion(String motivoretencion) {
		this.motivoretencion = motivoretencion;
	}

	public Metodocobro getMetodocobro() {
		return metodocobro;
	}

	public void setMetodocobro(Metodocobro metodocobro) {
		this.metodocobro = metodocobro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	
	
}
