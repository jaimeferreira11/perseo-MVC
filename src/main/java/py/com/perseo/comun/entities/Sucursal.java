package py.com.perseo.comun.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sucursal")
//@NamedQueries( { @NamedQuery(name = "sucursalFindByEmpresa", query = "select p from Sucursal p where p.empresa.codempresa = :pempresa"),
//				 @NamedQuery(name = "sucursalfindAll", query = "select p from Sucursal p order by p.idsucursal")})
@JsonInclude(Include.NON_NULL)
public class Sucursal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idsucursal")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_idsucursal_seq")
	@SequenceGenerator(name = "sucursal_idsucursal_seq", sequenceName = "sucursal_idsucursal_seq", allocationSize = 1)
	private Integer idsucursal;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "telefono1")
	private String telefono1;

	@Column(name = "telefono2")
	private String telefono2;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "ciudad")
	private String ciudad;

	@Column(name = "pais")
	private String pais;
	
	@Column(name = "matriz")
	private Boolean matriz;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	private Empresa empresa;

	public Sucursal() {
		
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Boolean getMatriz() {
		return matriz;
	}

	public void setMatriz(Boolean matriz) {
		this.matriz = matriz;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
