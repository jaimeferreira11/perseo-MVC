package py.com.perseo.creditos.entities;

import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.solicitudes.entities.Solicitud;
import py.com.perseo.venta.entities.Facturacab;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "credito")
//@NamedNativeQueries( { @NamedNativeQuery(name = "getCuotasPendientesByCliente", query = "select a.* from cuota a join credito b on b.idcredito = a.idcredito join solicitud c on c.idsolicitud = b.idsolicitud join cliente d on d.idcliente = c.idcliente where d.idcliente = ? and a.pagado = false order by a.idcredito, a.nrocuota  ", resultClass = Cuota.class), 
//	@NamedNativeQuery(name = "getCreditosByCliente", query = " select a.* from credito a join solicitud b on b.idsolicitud = a.idsolicitud where b.idcliente = ?  ", resultClass = Credito.class)})
public class Credito implements Serializable {

	@Id
	@Column(name = "idcredito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credito_idcredito_seq")
	@SequenceGenerator(name = "credito_idcredito_seq", sequenceName = "credito_idcredito_seq", allocationSize = 1)
	private Integer idcredito;

	@ManyToOne(targetEntity = Solicitud.class)
	@JoinColumn(name = "idsolicitud")
	Solicitud solicitud;

	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocab")
	Asientocab asientocab;
	
	@ManyToOne(targetEntity = Facturacab.class)
	@JoinColumn(name = "idfacturacab")
	Facturacab facturacab;

	@Column(name = "monto", columnDefinition = "numeric")
	private Double monto;

	@Column(name = "cantidadcuota", columnDefinition = "int2")
	private Integer cantidadcuota;

	@Column(name = "tasanominalanual", columnDefinition = "numeric")
	private Double tasanominalanual;

	@Column(name = "tasaefectiva", columnDefinition = "numeric")
	private Double tasaefectiva;

	@Column(name = "fechaoperacion")
	private Date fechaoperacion;
	
	@Column(name = "fechaincobrable")
	private Date fechaincobrable;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuarioincobrable")
	Usuario usuarioincobrable;
	
	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientoincobrable")
	Asientocab asientoincobrable;

	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "fecprimervenc")
	private Date fecprimervenc;

	@Column(name = "fecdesembolso")
	private Date fecdesembolso;

	@Column(name = "estado")
	private String estado;

	@Column(name = "situacion")
	private String situacion;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "iva", columnDefinition = "numeric")
	private Double iva;

	@Column(name = "interes", columnDefinition = "numeric")
	private Double interes;

	@Column(name = "montocuota", columnDefinition = "numeric")
	private Double montocuota;

	@Column(name = "sistemaamortizacion")
	private String sistemaamortizacion;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "credito")
	@OrderBy("nrocuota")
	private Set<Cuota> cuota = new HashSet<Cuota>(0);

	@Transient
	private Double saldocredito;
	
	@Transient
	private Double saldocapital;
	
	@Transient
	private Double saldointeres;
	
	@Transient
	private Double saldoiva;
	
	public Facturacab getFacturacab() {
		return facturacab;
	}

	public void setFacturacab(Facturacab facturacab) {
		this.facturacab = facturacab;
	}

	public Collection<Cuota> getCuota() {
		Collection c = new ArrayList<Cuota>(cuota);
		return c;
	}

	public void setCuota(Collection<Cuota> cuota) {
		Set<Cuota> cuota_ = new HashSet<Cuota>(cuota);
		this.cuota = cuota_;
	}

	public Integer getIdcredito() {
		return idcredito;
	}

	public void setIdcredito(Integer idcredito) {
		this.idcredito = idcredito;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Integer getCantidadcuota() {
		return cantidadcuota;
	}

	public void setCantidadcuota(Integer cantidadcuota) {
		this.cantidadcuota = cantidadcuota;
	}

	public Double getTasanominalanual() {
		return tasanominalanual;
	}

	public void setTasanominalanual(Double tasanominalanual) {
		this.tasanominalanual = tasanominalanual;
	}

	public Double getTasaefectiva() {
		return tasaefectiva;
	}

	public void setTasaefectiva(Double tasaefectiva) {
		this.tasaefectiva = tasaefectiva;
	}

	public Date getFechaoperacion() {
		return fechaoperacion;
	}

	public void setFechaoperacion(Date fechaoperacion) {
		this.fechaoperacion = fechaoperacion;
	}

	public Date getFecprimervenc() {
		return fecprimervenc;
	}

	public void setFecprimervenc(Date fecprimervenc) {
		this.fecprimervenc = fecprimervenc;
	}

	public Date getFecdesembolso() {
		return fecdesembolso;
	}

	public void setFecdesembolso(Date fecdesembolso) {
		this.fecdesembolso = fecdesembolso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getMontocuota() {
		return montocuota;
	}

	public void setMontocuota(Double montocuota) {
		this.montocuota = montocuota;
	}

	public String getSistemaamortizacion() {
		return sistemaamortizacion;
	}

	public void setSistemaamortizacion(String sistemaamortizacion) {
		this.sistemaamortizacion = sistemaamortizacion;
	}

	public Double getInteres() {
		return interes;
	}

	public void setInteres(Double interes) {
		this.interes = interes;
	}

	public Asientocab getAsientocab() {
		return asientocab;
	}

	public void setAsientocab(Asientocab asientocab) {
		this.asientocab = asientocab;
	}

	public Double getSaldocredito() {
		return saldocredito;
	}

	public void setSaldocredito(Double saldocredito) {
		this.saldocredito = saldocredito;
	}

	public Double getSaldocapital() {
		return saldocapital;
	}

	public void setSaldocapital(Double saldocapital) {
		this.saldocapital = saldocapital;
	}

	public Double getSaldointeres() {
		return saldointeres;
	}

	public void setSaldointeres(Double saldointeres) {
		this.saldointeres = saldointeres;
	}

	public Double getSaldoiva() {
		return saldoiva;
	}

	public void setSaldoiva(Double saldoiva) {
		this.saldoiva = saldoiva;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaincobrable() {
		return fechaincobrable;
	}

	public void setFechaincobrable(Date fechaincobrable) {
		this.fechaincobrable = fechaincobrable;
	}

	public Usuario getUsuarioincobrable() {
		return usuarioincobrable;
	}

	public void setUsuarioincobrable(Usuario usuarioincobrable) {
		this.usuarioincobrable = usuarioincobrable;
	}

	public Asientocab getAsientoincobrable() {
		return asientoincobrable;
	}

	public void setAsientoincobrable(Asientocab asientoincobrable) {
		this.asientoincobrable = asientoincobrable;
	}

}