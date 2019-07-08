package py.com.perseo.clientes.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.*;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.stock.entities.Articuloprecioventacab;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "cliente")
@JsonInclude(Include.NON_NULL)
public class Cliente {

	@Id
	@Column(name = "idcliente")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_idcliente_seq")
	@SequenceGenerator(name = "cliente_idcliente_seq", sequenceName = "cliente_idcliente_seq", allocationSize = 1)
	private Integer idcliente;

	@Column(name = "nombreapellido")
	private String nombreapellido;

	@Column(name = "nrodoc",nullable=false)
	private String nrodoc;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "fecnacimiento")
	private Date fecnacimiento;

	@Column(name = "lugarnacimiento")
	private String lugarnacimiento;

	@Column(name = "nacionalidad")
	private String nacionalidad;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "telefono1")
	private String telefono1;

	@Column(name = "telefono2")
	private String telefono2;

	@Column(name = "lineacredito", columnDefinition="numeric")
	private Double lineacredito;

	@NotNull
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "estadocivil")
	private String estadocivil;

	@Column(name = "fechaalta")
	private Timestamp fechaalta;

	@Column(name = "correo")
	private String correo;

	@Column(name = "web")
	private String web;
	
	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idoficial")
	Usuario oficial;
	
	@ManyToOne(targetEntity = Tipodocumento.class)
	@JoinColumn(name = "codtipodoc")
	Tipodocumento tipodocumento;

	@ManyToOne(targetEntity = Barrio.class)
	@JoinColumn(name = "idbarrio")
	Barrio barrio;

	@ManyToOne(targetEntity = Distrito.class)
	@JoinColumn(name = "iddistrito")
	Distrito distrito;

	@ManyToOne(targetEntity = Departamento.class)
	@JoinColumn(name = "iddepartamento")
	Departamento departamento;

	@NotNull
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@NotNull
	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "idclienteconyuge")
	Cliente clienteconyuge;
	
	@ManyToOne(targetEntity = Articuloprecioventacab.class)
	@JoinColumn(name = "idarticuloprecioventacab")
	Articuloprecioventacab articuloprecioventacab;

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}
	
	public Cliente getClienteconyuge() {
		return clienteconyuge;
	}

	public void setClienteconyuge(Cliente clienteconyuge) {
		this.clienteconyuge = clienteconyuge;
	}

	public Cliente() {

	}

	public Integer getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombreapellido() {
		return nombreapellido;
	}

	public void setNombreapellido(String nombreapellido) {
		this.nombreapellido = nombreapellido;
	}

	public String getNrodoc() {
		return nrodoc;
	}

	public void setNrodoc(String nrodoc) {
		this.nrodoc = nrodoc;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFecnacimiento() {
		return fecnacimiento;
	}

	public void setFecnacimiento(Date fecnacimiento) {
		this.fecnacimiento = fecnacimiento;
	}

	public String getLugarnacimiento() {
		return lugarnacimiento;
	}

	public void setLugarnacimiento(String lugarnacimiento) {
		this.lugarnacimiento = lugarnacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public Double getLineacredito() {
		return lineacredito;
	}

	public void setLineacredito(Double lineacredito) {
		this.lineacredito = lineacredito;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Timestamp getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(Timestamp fechaalta) {
		this.fechaalta = fechaalta;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Tipodocumento getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(Tipodocumento tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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

	public String getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(String estadocivil) {
		this.estadocivil = estadocivil;
	}

	public Usuario getOficial() {
		return oficial;
	}

	public void setOficial(Usuario oficial) {
		this.oficial = oficial;
	}
	
	

	public Articuloprecioventacab getArticuloprecioventacab() {
		return articuloprecioventacab;
	}

	public void setArticuloprecioventacab(Articuloprecioventacab articuloprecioventacab) {
		this.articuloprecioventacab = articuloprecioventacab;
	}

	@Override
	public String toString() {
		return "Cliente [idcliente=" + idcliente + ", nombreapellido=" + nombreapellido + ", nrodoc=" + nrodoc
				+ ", tipodocumento=" + tipodocumento + "]";
	}
	
	

}
