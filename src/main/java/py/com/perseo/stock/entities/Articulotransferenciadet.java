package py.com.perseo.stock.entities;

import javax.persistence.*;

@Entity
@Table(name = "articulotransferenciadet")
public class Articulotransferenciadet {

	@Id
	@Column(name = "idarticulotransferenciadet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulotransferenciadet_idarticulotransferenciadet_seq")
	@SequenceGenerator(name = "articulotransferenciadet_idarticulotransferenciadet_seq", sequenceName = "articulotransferenciadet_idarticulotransferenciadet_seq", allocationSize = 1)
	private Integer idarticulotransferenciadet;
	
	@Column(name = "cantidadcontado")
	private Integer cantidadcontado;
	
	@Column(name = "cantidadrecibido")
	private Integer cantidadrecibido;
	
	@ManyToOne(targetEntity = Articulotransferenciacab.class)
	@JoinColumn(name = "idarticulotransferenciacab", nullable=false)
	Articulotransferenciacab articulotransferenciacab;

	@ManyToOne(targetEntity = Articulo.class)
	@JoinColumn(name = "idarticulo")
	Articulo articulo;

	

	public Integer getIdarticulotransferenciadet() {
		return idarticulotransferenciadet;
	}

	public void setIdarticulotransferenciadet(Integer idarticulotransferenciadet) {
		this.idarticulotransferenciadet = idarticulotransferenciadet;
	}

	public Integer getCantidadcontado() {
		return cantidadcontado;
	}

	public void setCantidadcontado(Integer cantidadcontado) {
		this.cantidadcontado = cantidadcontado;
	}

	public Integer getCantidadrecibido() {
		return cantidadrecibido;
	}

	public void setCantidadrecibido(Integer cantidadrecibido) {
		this.cantidadrecibido = cantidadrecibido;
	}

	public Articulotransferenciacab getArticulotransferenciacab() {
		return articulotransferenciacab;
	}

	public void setArticulotransferenciacab(Articulotransferenciacab articulotransferenciacab) {
		this.articulotransferenciacab = articulotransferenciacab;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	
}
