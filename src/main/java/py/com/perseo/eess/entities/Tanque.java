package py.com.perseo.eess.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Deposito;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tanque")
//@NamedQueries( {
//		@NamedQuery(name = "tanqueFindAll", query = "select p from Tanque p order by p.nrotanque"),
//		@NamedQuery(name = "tanqueFindAllByEstado", query = "select p from Tanque p where p.estado = :pestado order by p.nrotanque"),
//		@NamedQuery(name = "tanqueFindByID", query = "select p from Tanque p where p.nrotanque = :pnrotanque "),
//		@NamedQuery(name = "tanqueFindByIDAndEstado", query = "select p from Tanque p where p.nrotanque = :pidtanque and p.estado = :pestado order by p.nrotanque "),
//		@NamedQuery(name = "tanqueFindBySucursalDeposito", query = "select p from Tanque p where p.sucursal.idsucursal = :pidsucursal and p.deposito.iddeposito = :piddeposito order by p.nrotanque ")})
public class Tanque implements Serializable {

	@Id
	@Column(name = "nrotanque")
	private Integer nrotanque;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "fechalog")
	private Timestamp fechalog;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Deposito.class)
	@JoinColumn(name = "iddeposito")
	Deposito deposito;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;

	@Column(name = "precioventa", columnDefinition = "numeric")
	private Double precioventa;

	@Column(name = "existencia", columnDefinition = "numeric")
	private Double existencia;

	@Column(name = "tolerancia", columnDefinition = "numeric")
	private Double tolerancia;

	@Column(name = "totalcompras", columnDefinition = "numeric")
	private Double totalcompras;

	@Column(name = "totalventas", columnDefinition = "numeric")
	private Double totalventas;

	@Column(name = "medicion", columnDefinition = "numeric")
	private Double medicion;

	@Column(name = "diferencia", columnDefinition = "numeric")
	private Double diferencia;

	@Column(name = "estado")
	private Boolean estado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Double getExistencia() {
		return existencia;
	}

	public void setExistencia(Double existencia) {
		this.existencia = existencia;
	}

	public Double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(Double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public Double getTotalcompras() {
		return totalcompras;
	}

	public void setTotalcompras(Double totalcompras) {
		this.totalcompras = totalcompras;
	}

	public Double getTotalventas() {
		return totalventas;
	}

	public void setTotalventas(Double totalventas) {
		this.totalventas = totalventas;
	}

	public Double getMedicion() {
		return medicion;
	}

	public void setMedicion(Double medicion) {
		this.medicion = medicion;
	}

	public Double getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(Double diferencia) {
		this.diferencia = diferencia;
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

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Integer getNrotanque() {
		return nrotanque;
	}

	public void setNrotanque(Integer nrotanque) {
		this.nrotanque = nrotanque;
	}

	public Timestamp getFechalog() {
		return fechalog;
	}

	public void setFechalog(Timestamp fechalog) {
		this.fechalog = fechalog;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

}
