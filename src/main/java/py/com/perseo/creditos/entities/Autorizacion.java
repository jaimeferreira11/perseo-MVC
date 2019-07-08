package py.com.perseo.creditos.entities;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "autorizacion")
public class Autorizacion implements Serializable {

	@Id
	@Column(name = "idautorizacion")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autorizacion_idautorizacion_seq")
	@SequenceGenerator(name = "autorizacion_idautorizacion_seq", sequenceName = "autorizacion_idautorizacion_seq", allocationSize = 1)
	private Integer idautorizacion;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "fechalog")
	private Timestamp fechalog;

	@Column(name = "nrocuota", nullable = false)
	private Integer nrocuota;

	@Column(name = "nrocredito", nullable = false)
	private Integer nrocredito;

	@Column(name = "tipoautorizacion", nullable = false)
	private String tipoautorizacion;

	@Column(name = "exonerarinteres", nullable = false)
	private Boolean exonerarinteres;
	
	/**
	 * PENDIENTE, APROBADO, PROCESADO, ANULADO
	 */
	@Column(name = "estado", nullable = false)
	private String estado;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	public Integer getIdautorizacion() {
		return idautorizacion;
	}

	public void setIdautorizacion(Integer idautorizacion) {
		this.idautorizacion = idautorizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechalog() {
		return fechalog;
	}

	public void setFechalog(Timestamp fechalog) {
		this.fechalog = fechalog;
	}

	public Integer getNrocuota() {
		return nrocuota;
	}

	public void setNrocuota(Integer nrocuota) {
		this.nrocuota = nrocuota;
	}

	public Integer getNrocredito() {
		return nrocredito;
	}

	public void setNrocredito(Integer nrocredito) {
		this.nrocredito = nrocredito;
	}

	public String getTipoautorizacion() {
		return tipoautorizacion;
	}

	public void setTipoautorizacion(String tipoautorizacion) {
		this.tipoautorizacion = tipoautorizacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Boolean getExonerarinteres() {
		return exonerarinteres;
	}

	public void setExonerarinteres(Boolean exonerarinteres) {
		this.exonerarinteres = exonerarinteres;
	}

}
