package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "banco")
@NamedQueries( { @NamedQuery(name = "bancofindAll", query = "select p from Banco p "), 
@NamedQuery(name = "bancofindById", query = "select p from Banco p where p.idbanco = :pidbanco"),
@NamedQuery(name = "bancofindByIdAndEstado", query = "select p from Banco p where p.idbanco = :pidbanco AND p.estado = :pestado"),
@NamedQuery(name = "bancofindByEstado", query = "select p from Banco p where p.estado = :pestado")})

public class Banco implements Serializable {

	@Id
	@Column(name = "idbanco")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banco_idbanco_seq")
	@SequenceGenerator(name = "banco_idbanco_seq", sequenceName = "banco_idbanco_seq", allocationSize = 1)
	private Integer idbanco;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "telefonos")
	private String telefonos;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "sitioweb")
	private String sitioweb;

	@Column(name = "email")
	private String email;
	
	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdbanco() {
		return idbanco;
	}

	public void setIdbanco(Integer idbanco) {
		this.idbanco = idbanco;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSitioweb() {
		return sitioweb;
	}

	public void setSitioweb(String sitioweb) {
		this.sitioweb = sitioweb;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
