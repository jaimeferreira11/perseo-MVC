package py.com.perseo.stock.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.Empresa;

import javax.persistence.*;

@JsonInclude(Include.NON_NULL) 
@Entity
public class Articuloprecioventacab {
	
	@Id
	@Column(name = "idarticuloprecioventacab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articuloprecioventacab_idarticuloprecioventacab_seq")
	@SequenceGenerator(name = "articuloprecioventacab_idarticuloprecioventacab_seq", sequenceName = "articuloprecioventacab_idarticuloprecioventacab_seq", allocationSize = 1)
	private Integer idarticuloprecioventacab;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	public Integer getIdarticuloprecioventacab() {
		return idarticuloprecioventacab;
	}

	public void setIdarticuloprecioventacab(Integer idarticuloprecioventacab) {
		this.idarticuloprecioventacab = idarticuloprecioventacab;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	

}
