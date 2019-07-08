package py.com.perseo.comun.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fondocredito")
@NamedQueries( { @NamedQuery(name = "fondoCreditofindAll", query = "select p from Fondocredito p "), @NamedQuery(name = "fondocreditofindByEstado", query = "select p from Fondocredito p where p.estado = :pestado"), @NamedQuery(name = "fondocreditofindById", query = "select p from Fondocredito p where p.idfondocredito = :pidfondocredito"), @NamedQuery(name = "fondocreditofindByIdAndEstado", query = "select p from Fondocredito p where p.idfondocredito = :pidfondocredito AND p.estado = :pestado") })
public class Fondocredito implements Serializable {

	@Id
	@Column(name = "idfondocredito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fondocredito_idfondocredito_seq")
	@SequenceGenerator(name = "fondocredito_idfondocredito_seq", sequenceName = "fondocredito_idfondocredito_seq", allocationSize = 1)
	private Integer idfondocredito;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdfondocredito() {
		return idfondocredito;
	}

	public void setIdfondocredito(Integer idfondocredito) {
		this.idfondocredito = idfondocredito;
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

}
