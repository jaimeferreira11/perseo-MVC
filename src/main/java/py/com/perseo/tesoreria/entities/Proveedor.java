package py.com.perseo.tesoreria.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Plancuenta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "proveedor")
@JsonInclude(Include.NON_NULL)
public class Proveedor {
	
	@Id
	@Column(name = "idproveedor")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_idcliente_seq")
	@SequenceGenerator(name = "cliente_idcliente_seq", sequenceName = "cliente_idcliente_seq", allocationSize = 1)
	private Integer idproveedor;

	@Column(name = "codtipodoc")
	private String codtipodoc;

	@Column(name = "nrodoc",nullable=false)
	private String nrodoc;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "telefono1")
	private String telefono1;
	
	@Column(name = "telefono2")
	private String telefono2;
	
	@Column(name = "fax")
	private String fax;

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "sitioweb")
	private String sitioweb;

	@NotNull
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "fechaalta")
	private Timestamp fechaalta;
	
	@NotNull
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@NotNull
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;
	
	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;
	
	@Column(name = "timbrado")
	private String timbrado;
	
	@Column(name = "venctimbrado")
	private Date venctimbrado;

	public Proveedor() {
		super();
	}

	public Integer getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getCodtipodoc() {
		return codtipodoc;
	}

	public void setCodtipodoc(String codtipodoc) {
		this.codtipodoc = codtipodoc;
	}

	public String getNrodoc() {
		return nrodoc;
	}

	public void setNrodoc(String nrodoc) {
		this.nrodoc = nrodoc;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSitioweb() {
		return sitioweb;
	}

	public void setSitioweb(String sitioweb) {
		this.sitioweb = sitioweb;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public String getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}

	public Date getVenctimbrado() {
		return venctimbrado;
	}

	public void setVenctimbrado(Date venctimbrado) {
		this.venctimbrado = venctimbrado;
	}
	
	

}
