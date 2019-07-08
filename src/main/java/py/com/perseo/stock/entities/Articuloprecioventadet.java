package py.com.perseo.stock.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

@JsonInclude(Include.NON_NULL) 
@Entity
public class Articuloprecioventadet {
	
	@Id
	@Column(name = "idarticuloprecioventadet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articuloprecioventadet_idarticuloprecioventadet_seq")
	@SequenceGenerator(name = "articuloprecioventadet_idarticuloprecioventadet_seq", sequenceName = "articuloprecioventadet_idarticuloprecioventadet_seq", allocationSize = 1)
	private Integer idarticuloprecioventadet;
	
	@ManyToOne(targetEntity = Articuloprecioventacab.class)
	@JoinColumn(name = "idarticuloprecioventacab")
	Articuloprecioventacab articuloprecioventacab;
	
	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo")
	Articulo articulo;
	
	@Column(name = "precio")
	private Double precio;

	public Integer getIdarticuloprecioventadet() {
		return idarticuloprecioventadet;
	}

	public void setIdarticuloprecioventadet(Integer idarticuloprecioventadet) {
		this.idarticuloprecioventadet = idarticuloprecioventadet;
	}

	public Articuloprecioventacab getArticuloprecioventacab() {
		return articuloprecioventacab;
	}

	public void setArticuloprecioventacab(Articuloprecioventacab articuloprecioventacab) {
		this.articuloprecioventacab = articuloprecioventacab;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Articuloprecioventadet [idarticuloprecioventadet=" + idarticuloprecioventadet
				+ ", articuloprecioventacab=" + articuloprecioventacab + ", articulo=" + articulo+ ", precio=" + precio
				+ "]";
	}
	
	

}
