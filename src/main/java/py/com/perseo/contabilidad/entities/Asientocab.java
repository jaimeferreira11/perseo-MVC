package py.com.perseo.contabilidad.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "asientocab")
//@NamedQueries( { @NamedQuery(name = "asientocabfindAll", query = "select p from Asientocab p order by p.idasientocab desc "),
//@NamedQuery(name = "asientocabfindById", query = "select p from Asientocab p where p.idasientocab = :pidasientocab")})

public class Asientocab implements Serializable {

	@Id
	@Column(name = "idasientocab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asientocab_idasientocab_seq")
	@SequenceGenerator(name = "asientocab_idasientocab_seq", sequenceName = "asientocab_idasientocab_seq", allocationSize = 1)
	private Integer idasientocab;

	@Column(name = "nroasiento", columnDefinition = "numeric")
	private Integer nroasiento;

	@Column(name = "fechaasiento")
	private Date fechaasiento;
	
	@Column(name = "tipo")
	private String tipo;

	@Column(name = "fechavigencia")
	private Timestamp fechavigencia;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "ejercicio", columnDefinition = "numeric")	
	private Integer ejercicio;
	
	@Column(name = "estado", nullable=false)
	private Boolean estado;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@ManyToOne(targetEntity = Tipotransaccion.class)
	@JoinColumn(name = "codtipotransaccion")
	Tipotransaccion tipotransaccion;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocaboriginal", nullable = true)
	Asientocab asientooriginal;

	@ManyToOne(targetEntity = Asientocab.class)
	@JoinColumn(name = "idasientocabreverso", nullable = true)
	Asientocab asientoreverso;

	@OneToMany(mappedBy = "asientocab", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, fetch=FetchType.EAGER)
	@OrderBy("idasientodet")
	private List<Asientodet> asientodet;

	public Integer getNroasiento() {
		return nroasiento;
	}

	public void setNroasiento(Integer nroasiento) {
		this.nroasiento = nroasiento;
	}

	public Date getFechaasiento() {
		return fechaasiento;
	}

	public void setFechaasiento(Date fechaasiento) {
		this.fechaasiento = fechaasiento;
	}

	public Timestamp getFechavigencia() {
		return fechavigencia;
	}

	public void setFechavigencia(Timestamp fechavigencia) {
		this.fechavigencia = fechavigencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public List<Asientodet> getAsientodet() {
		return asientodet;
	}

	public void setAsientodet(List<Asientodet> asientodet) {
		this.asientodet = asientodet;
	}

	public Integer getIdasientocab() {
		return idasientocab;
	}

	public void setIdasientocab(Integer idasientocab) {
		this.idasientocab = idasientocab;
	}

	public Asientocab getAsientooriginal() {
		return asientooriginal;
	}

	public void setAsientooriginal(Asientocab asientooriginal) {
		this.asientooriginal = asientooriginal;
	}

	public Asientocab getAsientoreverso() {
		return asientoreverso;
	}

	public void setAsientoreverso(Asientocab asientoreverso) {
		this.asientoreverso = asientoreverso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
