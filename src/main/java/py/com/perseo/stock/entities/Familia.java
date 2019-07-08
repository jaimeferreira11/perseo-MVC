package py.com.perseo.stock.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "familia")
public class Familia implements Serializable {

	@Id
	@Column(name = "idfamilia")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "familia_idfamilia_seq")
	@SequenceGenerator(name = "familia_idfamilia_seq", sequenceName = "familia_idfamilia_seq", allocationSize = 1)
	private Integer idfamilia;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "procentajeganancia")
	private Double procentajeganancia;
	
	@Transient
	private String listaPrecio;

	public Integer getIdfamilia() {
		return idfamilia;
	}

	public void setIdfamilia(Integer idfamilia) {
		this.idfamilia = idfamilia;
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

	public Double getProcentajeganancia() {
		return procentajeganancia;
	}

	public void setProcentajeganancia(Double procentajeganancia) {
		this.procentajeganancia = procentajeganancia;
	}

	public String getListaPrecio() {
		return listaPrecio;
	}

	public void setListaPrecio(String listaPrecio) {
		this.listaPrecio = listaPrecio;
	}

	@Override
	public String toString() {
		return "Familia [idfamilia=" + idfamilia + ", descripcion=" + descripcion + ", estado=" + estado
				+ ", procentajeganancia=" + procentajeganancia + ", listaPrecio=" + listaPrecio + "]";
	}
	
	

}
