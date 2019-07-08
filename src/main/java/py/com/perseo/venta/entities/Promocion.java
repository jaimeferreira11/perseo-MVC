package py.com.perseo.venta.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "promocion")
public class Promocion  implements Serializable{

	@Id
	@Column(name = "idpromocion")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promocion_idpromocion_seq")
	@SequenceGenerator(name = "promocion_idpromocion_seq", sequenceName = "promocion_idpromocion_seq", allocationSize = 1)
	private Integer idpromocion;
	
	@Column(name = "fechalog")
	private Timestamp fechalog;
	
	@Column(name = "fechadesde")
	private Date fechadesde;
	
	@Column(name = "fechahasta")
	private Date fechahasta;
	
	@Column(name = "porcdescuento", columnDefinition="numeric")
	private Double porcdescuento;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdpromocion() {
		return idpromocion;
	}

	public void setIdpromocion(Integer idpromocion) {
		this.idpromocion = idpromocion;
	}

	public Timestamp getFechalog() {
		return fechalog;
	}

	public void setFechalog(Timestamp fechalog) {
		this.fechalog = fechalog;
	}

	public Date getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}

	public Date getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
	}

	public Double getPorcdescuento() {
		return porcdescuento;
	}

	public void setPorcdescuento(Double porcdescuento) {
		this.porcdescuento = porcdescuento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
