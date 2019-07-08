package py.com.perseo.session.entities;


import py.com.perseo.comun.entities.Empresa;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "clase")
//@NamedQueries( { @NamedQuery(name = "clasefindAll", query = "select p from Clase p "), 
//@NamedQuery(name = "clasefindByEstado", query = "select p from Clase p where p.estado = :pestado"),
//@NamedQuery(name = "clasefindByPrimaryKey", query = "select p from Clase p where p.idclase = :pidclase")})
			     
public class Clase implements Serializable {

	@Id
	@Column(name = "idclase")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="clase_idclase_seq")
    @SequenceGenerator(name = "clase_idclase_seq", sequenceName = "clase_idclase_seq", allocationSize=1)
	private Integer idclase;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "clase")
	private String clase;

	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "url")
	private String url;

	@Column(name = "activity")
	private String activity;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;
	
	
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIdclase() {
		return idclase;
	}

	public void setIdclase(Integer idclase) {
		this.idclase = idclase;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
