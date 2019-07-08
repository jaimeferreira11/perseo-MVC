package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "historicoprecioventa")
public class Historicoprecioventa {
	
	@Id
	@Column(name = "idhistoricoprecioventa")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicoprecioventa_idhistoricoprecioventa_seq")
	@SequenceGenerator(name = "historicoprecioventa_idhistoricoprecioventa_seq", sequenceName = "historicoprecioventa_idhistoricoprecioventa_seq", allocationSize = 1)
	private Integer idhistoricoprecioventa;
	
	@Column(name = "fecha")
	private Date fecha;
	

	@Column(name = "concepto")
	private String concepto;
	
	@Column(name = "preciocosto")
	private Double preciocosto;

	@Column(name = "precioventa")
	private Double precioventa;
	
	@Column(name = "porcentaje")
	private Double porcentaje;
	
	@ManyToOne(targetEntity = Articuloprecioventadet.class)
	@JoinColumn(name = "idarticuloprecioventadet",nullable=false)
	private Articuloprecioventadet articuloprecioventadet;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario",nullable=false)
	private Usuario usuario;

	


	public Integer getIdhistoricoprecioventa() {
		return idhistoricoprecioventa;
	}

	public void setIdhistoricoprecioventa(Integer idhistoricoprecioventa) {
		this.idhistoricoprecioventa = idhistoricoprecioventa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getPreciocosto() {
		return preciocosto;
	}

	public void setPreciocosto(Double preciocosto) {
		this.preciocosto = preciocosto;
	}

	public Double getPrecioventa() {
		return precioventa;
	}

	public void setPrecioventa(Double precioventa) {
		this.precioventa = precioventa;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Articuloprecioventadet getArticuloprecioventadet() {
		return articuloprecioventadet;
	}

	public void setArticuloprecioventadet(Articuloprecioventadet articuloprecioventadet) {
		this.articuloprecioventadet = articuloprecioventadet;
	}


	
	

}
