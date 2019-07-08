package py.com.perseo.contabilidad.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plancuenta")
@JsonInclude(Include.NON_NULL)
public class Plancuenta implements Serializable {
	
	@Id
	@Column(name = "idplancuenta")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plancuenta_idplancuenta_seq")
	@SequenceGenerator(name = "plancuenta_idplancuenta_seq", sequenceName = "plancuenta_idplancuenta_seq", allocationSize = 1)
	private Integer idplancuenta;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "caja")
	private Boolean caja;

	@Column(name = "rubro")
	private String rubro;

	@Column(name = "asentable")
	private Boolean asentable;

	@Column(name = "nivel", columnDefinition="int2", nullable=false)
	private Integer nivel;

	@Column(name = "activo", nullable=false)
	private Boolean activo;

	@Column(name = "tipo", nullable=false)
	private String tipo;

	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa", nullable=false)
	Empresa empresa;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal",nullable=false)
	Sucursal sucursal;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuentasuperior")
	Plancuenta plancuentasuperior;

	public Integer getIdplancuenta() {
		return idplancuenta;
	}

	public void setIdplancuenta(Integer idplancuenta) {
		this.idplancuenta = idplancuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public Boolean getAsentable() {
		return asentable;
	}

	public void setAsentable(Boolean asentable) {
		this.asentable = asentable;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public Plancuenta getPlancuentasuperior() {
		return plancuentasuperior;
	}

	public void setPlancuentasuperior(Plancuenta plancuentasuperior) {
		this.plancuentasuperior = plancuentasuperior;
	}

	public Boolean getCaja() {
		return caja;
	}

	public void setCaja(Boolean caja) {
		this.caja = caja;
	}

}
