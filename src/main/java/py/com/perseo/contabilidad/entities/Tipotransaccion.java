package py.com.perseo.contabilidad.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipotransaccion")
//@NamedQueries( { @NamedQuery(name = "ttfindAll", query = "select p from Tipotransaccion p order by p.codtipotransaccion "), 
//@NamedQuery(name = "ttfindByEstado", query = "select p from Tipotransaccion p where p.estado = :pestado order by p.codtipotransaccion "), 
//@NamedQuery(name = "ttfindById", query = "select p from Tipotransaccion p where p.codtipotransaccion = :pcodtipotransaccion") })
public class Tipotransaccion implements Serializable {

	@Id
	@Column(name = "codtipotransaccion")
	private Integer codtipotransaccion;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "tipo")
	private String tipo;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	public Integer getCodtipotransaccion() {
		return codtipotransaccion;
	}

	public void setCodtipotransaccion(Integer codtipotransaccion) {
		this.codtipotransaccion = codtipotransaccion;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

}
