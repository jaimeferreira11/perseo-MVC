package py.com.perseo.comun.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.session.entities.Perfil;
import py.com.perseo.session.entities.Perfilusuario;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.sueldos.entities.Claseempleado;
import py.com.perseo.tesoreria.entities.Caja;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@JsonInclude(Include.NON_NULL)
public class Usuario {

	@Id
	@Column(name = "idusuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_idusuario_seq")
	@SequenceGenerator(name = "usuario_idusuario_seq", sequenceName = "usuario_idusuario_seq", allocationSize = 1)
	private Integer idusuario;

	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "nrodoc")
	private String nrodoc;

	@Column(name = "contrasena")
	private String clave;

	@Column(name = "activo")
	private Boolean estado;

	@Column(name = "oficial")
	private Boolean oficial;
	
	@Column(name = "nrooficial")
	private Integer nrooficial;
	
	@Column(name = "nrohijos")
	private Integer nrohijos;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "fechanacimiento")
	private Date fechanacimiento;
	
	@Column(name = "lugarnacimiento")
	private String lugarnacimiento;
	
	@Column(name = "nacionalidad")
	private String nacionalidad;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "celular")
	private String celular;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "profesion")
	private String profesion;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Transient
	List<Perfilusuario> perfilusuario;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;
	
	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito")
	Deposito deposito;
	
	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja")
	Caja caja;
	
	@ManyToOne(targetEntity = Claseempleado.class)
	@JoinColumn(name = "idclaseempleado")
	Claseempleado claseempleado;

	@ManyToMany(targetEntity = Perfil.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "perfil_usuario", joinColumns = @JoinColumn(name = "idusuario"), inverseJoinColumns = @JoinColumn(name = "idperfil"))
	private List<Perfil> perfiles;

	public Usuario(Integer idusuario, String nombreapellido) {
		this.idusuario = idusuario;
		this.nombreapellido = nombreapellido;
	}

	public Usuario() {
	}

	public List<Perfilusuario> getPerfilusuario() {
		return perfilusuario;
	}

	public void setPerfilusuario(List<Perfilusuario> perfilusuario) {
		this.perfilusuario = perfilusuario;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	@Column(name = "nombreapellido")
	private String nombreapellido;

	public String getNombreapellido() {
		return nombreapellido;
	}

	public void setNombreapellido(String nombreapellido) {
		this.nombreapellido = nombreapellido;
	}

	public Boolean getOficial() {
		return oficial;
	}

	public void setOficial(Boolean oficial) {
		this.oficial = oficial;
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
	
	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Claseempleado getClaseempleado() {
		return claseempleado;
	}

	public void setClaseempleado(Claseempleado claseempleado) {
		this.claseempleado = claseempleado;
	}

	public Integer getNrohijos() {
		return nrohijos;
	}

	public void setNrohijos(Integer nrohijos) {
		this.nrohijos = nrohijos;
	}

	public String getNrodoc() {
		return nrodoc;
	}

	public void setNrodoc(String nrodoc) {
		this.nrodoc = nrodoc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getNrooficial() {
		return nrooficial;
	}

	public void setNrooficial(Integer nrooficial) {
		this.nrooficial = nrooficial;
	}
	
	

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", usuario=" + usuario + ", clave=" + clave + ", estado=" + estado
				+ ", perfilusuario=" + perfilusuario + ", empresa=" + empresa.getIdempresa() + ", claseempleado=" + claseempleado.getIdclaseempleado()
				+ ", nombreapellido=" + nombreapellido + ", caja= "+caja.getIdcaja()+"]";
	}
	
	
}
