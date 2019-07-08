package py.com.perseo.solicitudes.entities;

import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.comun.entities.*;
import py.com.perseo.creditos.entities.Credito;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "solicitud")
public class Solicitud implements Serializable {

	@Id
	@Column(name = "idsolicitud")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_idsolicitud_seq")
	@SequenceGenerator(name = "solicitud_idsolicitud_seq", sequenceName = "solicitud_idsolicitud_seq", allocationSize = 1)
	private Integer idsolicitud;

	@Column(name = "fecha", nullable=false)
	private Date fecha;

	@Column(name = "fechaprimervenc", nullable=false)
	private Date fechaprimervenc;

	@Column(name = "monto", columnDefinition = "numeric", nullable = false)
	private Double monto;
	
	@Column(name = "montodescuento", columnDefinition = "numeric")
	private Double montodescuento;
	
	@Column(name = "montoexoneracion", columnDefinition = "numeric")
	private Double montoexoneracion;
	
	@Column(name = "cantcuotas", columnDefinition = "int2")
	private Integer cantcuotas;

	@Column(name = "comentario", nullable=false)
	private String comentario;

	@Column(name = "garantia", length = 1, nullable=false)
	private String garantia;

	@Column(name = "tasainteres", columnDefinition = "numeric", nullable=false)
	private Double tasainteres;

	@Column(name = "pagainteres")
	private Boolean pagainteres;

	@Column(name = "formapago", length = 2)
	private String formapago;

	@Column(name = "sistemaamortizacion", length = 1, nullable=false)
	private String sistemamortizacion;

	@Column(name = "periodogracia", columnDefinition = "int2")
	private Integer periodogracia;

	@Column(name = "tasaiva", columnDefinition = "numeric",nullable=false)
	private Double tasaiva;

	@Column(name = "tiposolicitud", length = 1, nullable=false)
	private String tiposolicitud;

	@Column(name = "calificacionriesgo", length = 1)
	private String calificacionriesgo;

	@Column(name = "calificacionreferencia", length = 1)
	private String calificacionreferencia;

	@Column(name = "metodo", length = 1)
	private String metodo;

	@Column(name = "otrosgastos", columnDefinition = "numeric")
	private Double otrosgastos;
	
	@Column(name = "porcgasto", columnDefinition = "numeric")
	private Double porcGasto;
	
	@Column(name = "claseamortizacion")
	private String claseAmortizacion;

	@Column(name = "clasesolicitud")
	private String clasesolicitud;
	
	@Column(name = "exonerarintref", nullable=false)
	private Boolean exonerarintref;
	
	@ManyToOne(targetEntity = Tipoimpuesto.class)
	@JoinColumn(name = "idtipoimpuesto", nullable = false)
	Tipoimpuesto tipoimpuesto;
	
	@ManyToOne(targetEntity = Credito.class)
	@JoinColumn(name = "idcreditoref")
	Credito creditoref;

	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "idcliente", nullable = false)
	Cliente cliente;

	@ManyToOne(targetEntity = Estadosolicitud.class)
	@JoinColumn(name = "idestadosolicitud")
	Estadosolicitud estadosolicitud;

	@ManyToOne(targetEntity = Grupo.class)
	@JoinColumn(name = "idgrupo", nullable = true)
	Grupo grupo;
	
	@Transient
	private List<Grupointegrante> grupointegrante;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Destinosolicitud.class)
	@JoinColumn(name = "iddestinosolicitud")
	Destinosolicitud destinosolicitud;

	@ManyToOne(targetEntity = Plazo.class)
	@JoinColumn(name = "idplazo")
	Plazo plazo;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idoficial")
	Usuario oficial;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Parametro.class)
	@JoinColumn(name = "idparametro")
	Parametro parametro;

	@ManyToOne(targetEntity = Fondocredito.class)
	@JoinColumn(name = "idfondocredito")
	Fondocredito fondocredito;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuariohechopor")
	Usuario usuariohechopor;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuarioaprobadopor")
	Usuario usuarioaprobadopor;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuarioprocesadopor")
	Usuario usuarioprocesadopor;
	
	@ManyToOne(targetEntity = Actividad.class)
	@JoinColumn(name = "idactividad")
	Actividad actividad;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitud")
	@OrderBy("nrocuota")
	private Set<Solicitudcuota> solicitudcuota = new HashSet<Solicitudcuota>(0);
	
	public Set<Solicitudcuota> getSolicitudcuota() {
		return solicitudcuota;
	}

	public void setSolicitudcuota(Set<Solicitudcuota> solicitudcuota) {
		this.solicitudcuota = solicitudcuota;
	}

	public Usuario getUsuariohechopor() {
		return usuariohechopor;
	}

	public void setUsuariohechopor(Usuario usuariohechopor) {
		this.usuariohechopor = usuariohechopor;
	}

	public Usuario getUsuarioaprobadopor() {
		return usuarioaprobadopor;
	}

	public void setUsuarioaprobadopor(Usuario usuarioaprobadopor) {
		this.usuarioaprobadopor = usuarioaprobadopor;
	}

	public Usuario getUsuarioprocesadopor() {
		return usuarioprocesadopor;
	}

	public void setUsuarioprocesadopor(Usuario usuarioprocesadopor) {
		this.usuarioprocesadopor = usuarioprocesadopor;
	}

	public Integer getIdsolicitud() {
		return idsolicitud;
	}

	public void setIdsolicitud(Integer idsolicitud) {
		this.idsolicitud = idsolicitud;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Integer getCantcuotas() {
		return cantcuotas;
	}

	public void setCantcuotas(Integer cantcuotas) {
		this.cantcuotas = cantcuotas;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public Double getTasainteres() {
		return tasainteres;
	}

	public void setTasainteres(Double tasainteres) throws Exception {
		this.tasainteres = tasainteres;
	}

	public Boolean getPagainteres() {
		return pagainteres;
	}

	public void setPagainteres(Boolean pagainteres) {
		this.pagainteres = pagainteres;
	}

	public String getFormapago() {
		return formapago;
	}

	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}

	public String getSistemamortizacion() {
		return sistemamortizacion;
	}

	public void setSistemamortizacion(String sistemamortizacion) {
		this.sistemamortizacion = sistemamortizacion;
	}

	public Integer getPeriodogracia() {
		return periodogracia;
	}

	public void setPeriodogracia(Integer periodogracia) {
		this.periodogracia = periodogracia;
	}

	public Double getTasaiva() {
		return tasaiva;
	}

	public void setTasaiva(Double tasaiva) {
		this.tasaiva = tasaiva;
	}

	public String getTiposolicitud() {
		return tiposolicitud;
	}

	public void setTiposolicitud(String tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}

	public String getCalificacionriesgo() {
		return calificacionriesgo;
	}

	public void setCalificacionriesgo(String calificacionriesgo) {
		this.calificacionriesgo = calificacionriesgo;
	}

	public String getCalificacionreferencia() {
		return calificacionreferencia;
	}

	public void setCalificacionreferencia(String calificacionreferencia) {
		this.calificacionreferencia = calificacionreferencia;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Estadosolicitud getEstadosolicitud() {
		return estadosolicitud;
	}

	public void setEstadosolicitud(Estadosolicitud estadosolicitud) {
		this.estadosolicitud = estadosolicitud;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Destinosolicitud getDestinosolicitud() {
		return destinosolicitud;
	}

	public void setDestinosolicitud(Destinosolicitud destinosolicitud) {
		this.destinosolicitud = destinosolicitud;
	}

	public Plazo getPlazo() {
		return plazo;
	}

	public void setPlazo(Plazo plazo) {
		this.plazo = plazo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Usuario getOficial() {
		return oficial;
	}

	public void setOficial(Usuario oficial) {
		this.oficial = oficial;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Double getOtrosgastos() {
		return otrosgastos;
	}

	public void setOtrosgastos(Double otrosgastos) {
		this.otrosgastos = otrosgastos;
	}

	public Date getFechaprimervenc() {
		return fechaprimervenc;
	}

	public void setFechaprimervenc(Date fechaprimervenc) {
		this.fechaprimervenc = fechaprimervenc;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public String getClasesolicitud() {
		return clasesolicitud;
	}

	public void setClasesolicitud(String clasesolicitud) {
		this.clasesolicitud = clasesolicitud;
	}

	public Credito getCreditoref() {
		return creditoref;
	}

	public void setCreditoref(Credito creditoref) {
		this.creditoref = creditoref;
	}

	public Fondocredito getFondocredito() {
		return fondocredito;
	}

	public void setFondocredito(Fondocredito fondocredito) {
		this.fondocredito = fondocredito;
	}

	public Boolean getExonerarintref() {
		return exonerarintref;
	}

	public void setExonerarintref(Boolean exonerarintref) {
		this.exonerarintref = exonerarintref;
	}

	public Double getMontodescuento() {
		return montodescuento;
	}

	public void setMontodescuento(Double montodescuento) {
		this.montodescuento = montodescuento;
	}

	public List<Grupointegrante> getGrupointegrante() {
		return grupointegrante;
	}

	public void setGrupointegrante(List<Grupointegrante> grupointegrante) {
		this.grupointegrante = grupointegrante;
	}

	public Double getMontoexoneracion() {
		return montoexoneracion;
	}

	public void setMontoexoneracion(Double montoexoneracion) {
		this.montoexoneracion = montoexoneracion;
	}

	public Tipoimpuesto getTipoimpuesto() {
		return tipoimpuesto;
	}

	public void setTipoimpuesto(Tipoimpuesto tipoimpuesto) {
		this.tipoimpuesto = tipoimpuesto;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Double getPorcGasto() {
		return porcGasto;
	}

	public void setPorcGasto(Double porcGasto) {
		this.porcGasto = porcGasto;
	}

	public String getClaseAmortizacion() {
		return claseAmortizacion;
	}

	public void setClaseAmortizacion(String claseAmortizacion) {
		this.claseAmortizacion = claseAmortizacion;
	}

}
