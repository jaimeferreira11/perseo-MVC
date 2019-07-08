package py.com.perseo.tesoreria.entities;

import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.solicitudes.entities.Solicitud;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "chequetercero")
//@NamedQueries( { @NamedQuery(name = "chequetercerofindAll", query = "select p from Chequetercero p "), 
//@NamedQuery(name = "chequetercerofindById", query = "select p from Chequetercero p where p.idchequetercero = :pidchequetercero") })
//@NamedNativeQuery(name="getChequeTerceroBySolicitud", query=" select * from chequetercero where idsolicitud = ? ",  resultClass=Chequetercero.class)
public class Chequetercero implements Serializable {

	@Id
	@Column(name = "idchequetercero")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chequetercero_idchequetercero_seq")
	@SequenceGenerator(name = "chequetercero_idchequetercero_seq", sequenceName = "chequetercero_idchequetercero_seq", allocationSize = 1)
	private Integer idchequetercero;

	@ManyToOne(targetEntity = Banco.class)
	@JoinColumn(name = "idbanco", nullable=false)
	Banco banco;
	
	@ManyToOne(targetEntity = Estadochequetercero.class)
	@JoinColumn(name = "idestadochequetercero")
	Estadochequetercero estadochequetercero;

	@ManyToOne(targetEntity = Solicitud.class)
	@JoinColumn(name = "idsolicitud")
	Solicitud solicitud;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Motivorechazo.class)
	@JoinColumn(name = "idmotivorechazo")
	Motivorechazo motivorechazo;
	
	@ManyToOne(targetEntity = Ubicaciondocumento.class)
	@JoinColumn(name = "idubicaciondocumento")
	Ubicaciondocumento ubicaciondocumento;
	
	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja")
	Caja caja;

	@Column(name = "librador")
	private String librador;
	
	@Transient
	private Double capital;
	
	@Transient
	private Integer idcredito;
	
	@Transient
	private String clienteDescripcion;
	
	@Transient
	private Double interes;
	
	@Transient
	private Double iva;
	
	@Transient
	private Integer dias;
	
	@Column(name = "nrocuenta")
	private String nrocuenta;

	@Column(name = "importe", columnDefinition = "numeric")
	private Double importe;

	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "fechadeposito")
	private Date fechadeposito;
	
	@Column(name = "tasa", columnDefinition="numeric")
	private Double tasa;

	@Column(name = "fechavencimiento")
	private Date fechavencimiento;

	@Column(name = "fechaconfirmacion")
	private Date fechaconfirmacion;

	@Column(name = "fecharechazo")
	private Date fecharechazo;
	
	@Column(name = "fecharetencion")
	private Date fecharetencion;

	@Column(name = "bancodescripcion")
	private String bancodescripcion;

	@Column(name = "nrocheque")
	private String nrocheque;
	
	
	@Column(name = "importedesembolso", columnDefinition="numeric")
	private Double importedesembolso;
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientotransferencia")
	Asientocab asientotransferencia;
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientorechazo")
	Asientocab asientorechazo;
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientodeposito")
	Asientocab asientodeposito;
	
	@ManyToOne(targetEntity = Cajacuenta.class)
	@JoinColumn(name = "idcajacuenta")
	Cajacuenta cajacuenta;

	public Cajacuenta getCajacuenta() {
		return cajacuenta;
	}

	public void setCajacuenta(Cajacuenta cajacuenta) {
		this.cajacuenta = cajacuenta;
	}

	public Integer getIdchequetercero() {
		return idchequetercero;
	}

	public void setIdchequetercero(Integer idchequetercero) {
		this.idchequetercero = idchequetercero;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Estadochequetercero getEstadochequetercero() {
		return estadochequetercero;
	}

	public void setEstadochequetercero(Estadochequetercero estadochequetercero) {
		this.estadochequetercero = estadochequetercero;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Motivorechazo getMotivorechazo() {
		return motivorechazo;
	}

	public void setMotivorechazo(Motivorechazo motivorechazo) {
		this.motivorechazo = motivorechazo;
	}

	public String getLibrador() {
		return librador;
	}

	public void setLibrador(String librador) {
		this.librador = librador;
	}

	public String getNrocuenta() {
		return nrocuenta;
	}

	public void setNrocuenta(String nrocuenta) {
		this.nrocuenta = nrocuenta;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechavencimiento() {
		return fechavencimiento;
	}

	public void setFechavencimiento(Date fechavencimiento) {
		this.fechavencimiento = fechavencimiento;
	}

	public Date getFechaconfirmacion() {
		return fechaconfirmacion;
	}

	public void setFechaconfirmacion(Date fechaconfirmacion) {
		this.fechaconfirmacion = fechaconfirmacion;
	}

	public Date getFecharechazo() {
		return fecharechazo;
	}

	public void setFecharechazo(Date fecharechazo) {
		this.fecharechazo = fecharechazo;
	}

	public String getBancodescripcion() {
		return bancodescripcion;
	}

	public void setBancodescripcion(String bancodescripcion) {
		this.bancodescripcion = bancodescripcion;
	}

	public String getNrocheque() {
		return nrocheque;
	}

	public void setNrocheque(String nrocheque) {
		this.nrocheque = nrocheque;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Asientocab getAsientotransferencia() {
		return asientotransferencia;
	}

	public void setAsientotransferencia(Asientocab asientotransferencia) {
		this.asientotransferencia = asientotransferencia;
	}

	public Double getImportedesembolso() {
		return importedesembolso;
	}

	public void setImportedesembolso(Double importedesembolso) {
		this.importedesembolso = importedesembolso;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}

	public Date getFechadeposito() {
		return fechadeposito;
	}

	public void setFechadeposito(Date fechadeposito) {
		this.fechadeposito = fechadeposito;
	}

	public Asientocab getAsientorechazo() {
		return asientorechazo;
	}

	public void setAsientorechazo(Asientocab asientorechazo) {
		this.asientorechazo = asientorechazo;
	}

	public Asientocab getAsientodeposito() {
		return asientodeposito;
	}

	public void setAsientodeposito(Asientocab asientodeposito) {
		this.asientodeposito = asientodeposito;
	}

	public Ubicaciondocumento getUbicaciondocumento() {
		return ubicaciondocumento;
	}

	public void setUbicaciondocumento(Ubicaciondocumento ubicaciondocumento) {
		this.ubicaciondocumento = ubicaciondocumento;
	}

	public Date getFecharetencion() {
		return fecharetencion;
	}

	public void setFecharetencion(Date fecharetencion) {
		this.fecharetencion = fecharetencion;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getInteres() {
		return interes;
	}

	public void setInteres(Double interes) {
		this.interes = interes;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Integer getIdcredito() {
		return idcredito;
	}

	public void setIdcredito(Integer idcredito) {
		this.idcredito = idcredito;
	}

	public String getClienteDescripcion() {
		return clienteDescripcion;
	}

	public void setClienteDescripcion(String clienteDescripcion) {
		this.clienteDescripcion = clienteDescripcion;
	}

}
