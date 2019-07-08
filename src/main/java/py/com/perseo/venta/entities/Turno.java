package py.com.perseo.venta.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Caja;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "turno")
//@NamedQuery(name = "findTurnoById", query = "select p from Turno p where p.idturno = :pidturno")
public class Turno implements Serializable {

	@Id
	@Column(name = "idturno")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_idturno_seq")
	@SequenceGenerator(name = "turno_idturno_seq", sequenceName = "turno_idturno_seq", allocationSize = 1)
	private Integer idturno;

	@Column(name = "fecha",nullable=false)
	private Date fecha;

	@Column(name = "fechaapertura",nullable=false)
	private Timestamp fechaapertura;

	@Column(name = "fechaultapertura")
	private Date fechaultapertura;

	@Column(name = "fechacierre")
	private Timestamp fechacierre;
	
	/**
	 * PLAYA
	 * SHOP
	 */
	@Column(name = "tipoturno")
	private String tipoturno;

	@Column(name = "fechaultcierre")
	private Date fechaultcierre;

	@Column(name = "totalefectivo", columnDefinition = "numeric")
	private Double totalefectivo;

	@Column(name = "totalcheque", columnDefinition = "numeric")
	private Double totalcheque;

	@Column(name = "totaltarjeta", columnDefinition = "numeric")
	private Double totaltarjeta;

	@Column(name = "cantventa", columnDefinition = "numeric")
	private Double cantventa;

	@Column(name = "cantventanula", columnDefinition = "numeric")
	private Double cantventanula;

	@Column(name = "cantcompra", columnDefinition = "numeric")
	private Double cantcompra;

	@Column(name = "cantcompranula", columnDefinition = "numeric")
	private Double cantcompranula;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal",nullable=false)
	Sucursal sucursal;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario",nullable=false)
	Usuario usuario;

	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja",nullable=false)
	Caja caja;

	@Column(name = "estado",nullable=false)
	private Boolean estado;

	public Integer getIdturno() {
		return idturno;
	}

	public void setIdturno(Integer idturno) {
		this.idturno = idturno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechaapertura() {
		return fechaapertura;
	}

	public void setFechaapertura(Timestamp fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Date getFechaultapertura() {
		return fechaultapertura;
	}

	public void setFechaultapertura(Date fechaultapertura) {
		this.fechaultapertura = fechaultapertura;
	}

	public Timestamp getFechacierre() {
		return fechacierre;
	}

	public void setFechacierre(Timestamp fechacierre) {
		this.fechacierre = fechacierre;
	}

	public String getTipoturno() {
		return tipoturno;
	}

	public void setTipoturno(String tipoturno) {
		this.tipoturno = tipoturno;
	}

	public Date getFechaultcierre() {
		return fechaultcierre;
	}

	public void setFechaultcierre(Date fechaultcierre) {
		this.fechaultcierre = fechaultcierre;
	}

	public Double getTotalefectivo() {
		return totalefectivo;
	}

	public void setTotalefectivo(Double totalefectivo) {
		this.totalefectivo = totalefectivo;
	}

	public Double getTotalcheque() {
		return totalcheque;
	}

	public void setTotalcheque(Double totalcheque) {
		this.totalcheque = totalcheque;
	}

	public Double getTotaltarjeta() {
		return totaltarjeta;
	}

	public void setTotaltarjeta(Double totaltarjeta) {
		this.totaltarjeta = totaltarjeta;
	}

	public Double getCantventa() {
		return cantventa;
	}

	public void setCantventa(Double cantventa) {
		this.cantventa = cantventa;
	}

	public Double getCantventanula() {
		return cantventanula;
	}

	public void setCantventanula(Double cantventanula) {
		this.cantventanula = cantventanula;
	}

	public Double getCantcompra() {
		return cantcompra;
	}

	public void setCantcompra(Double cantcompra) {
		this.cantcompra = cantcompra;
	}

	public Double getCantcompranula() {
		return cantcompranula;
	}

	public void setCantcompranula(Double cantcompranula) {
		this.cantcompranula = cantcompranula;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Turno [idturno=" + idturno + ", fecha=" + fecha + ", fechaapertura=" + fechaapertura
				+ ", fechaultapertura=" + fechaultapertura + "]";
	}
	
	

	

}
