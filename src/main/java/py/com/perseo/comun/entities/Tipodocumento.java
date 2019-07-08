package py.com.perseo.comun.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tipodocumento")
//@NamedQueries( { @NamedQuery(name = "tipodocumentofindAll", query = "select p from Tipodocumento p ") })
@JsonInclude(Include.NON_NULL)
public class Tipodocumento implements Serializable {

	@Id
	@Column(name = "codtipodoc")
	private String codtipodoc;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	public Tipodocumento() {
		
	}

	public String getCodtipodoc() {
		return codtipodoc;
	}

	public void setCodtipodoc(String codtipodoc) {
		this.codtipodoc = codtipodoc;
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

	@Override
	public String toString() {
		return "Tipodocumento [codtipodoc=" + codtipodoc + ", descripcion=" + descripcion + ", estado=" + estado + "]";
	}
	
	
}
