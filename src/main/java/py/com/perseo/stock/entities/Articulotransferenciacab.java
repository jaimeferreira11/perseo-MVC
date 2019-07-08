package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "articulotransferenciacab")
public class Articulotransferenciacab {

	@Id
	@Column(name = "idarticulotransferenciacab")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulotransferenciacab_idarticulotransferenciacab_seq")
	@SequenceGenerator(name = "articulotransferenciacab_idarticulotransferenciacab_seq", sequenceName = "articulotransferenciacab_idarticulotransferenciacab_seq", allocationSize = 1)
	private Integer idarticulotransferenciacab;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "cantidadtotal")
	private Integer cantidadtotal;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa", nullable=false)
	Empresa empresa;

	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddepositoorigen")
	Deposito depositoorigen;
	
	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddepositodestino")
	Deposito depositodestino;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	public Integer getIdarticulotransferenciacab() {
		return idarticulotransferenciacab;
	}

	public void setIdarticulotransferenciacab(Integer idarticulotransferenciacab) {
		this.idarticulotransferenciacab = idarticulotransferenciacab;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(Integer cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Deposito getDepositoorigen() {
		return depositoorigen;
	}

	public void setDepositoorigen(Deposito depositoorigen) {
		this.depositoorigen = depositoorigen;
	}

	public Deposito getDepositodestino() {
		return depositodestino;
	}

	public void setDepositodestino(Deposito depositodestino) {
		this.depositodestino = depositodestino;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	
}
