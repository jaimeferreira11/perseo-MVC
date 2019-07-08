package py.com.perseo.stock.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "deposito")
@JsonInclude(Include.NON_NULL)
public class Deposito {
	
	@Id
	@Column(name = "iddeposito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposito_iddeposito_seq")
	@SequenceGenerator(name = "deposito_iddeposito_seq", sequenceName = "deposito_iddeposito_seq", allocationSize = 1)
	private Integer iddeposito;
	
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@NotNull
	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;
	
	@NotNull
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;
	
	@Column(name = "fechalog")
	private Timestamp fechalog;
	
	@NotNull
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuariolog")
	Usuario usuario;

	public Deposito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(Integer iddeposito) {
		this.iddeposito = iddeposito;
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

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Timestamp getFechalog() {
		return fechalog;
	}

	public void setFechalog(Timestamp fechalog) {
		this.fechalog = fechalog;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Deposito [iddeposito=" + iddeposito + ", descripcion=" + descripcion + ", estado=" + estado
				+ ", sucursal=" + sucursal + ", empresa=" + empresa + ", fechalog=" + fechalog + ", usuario=" + usuario
				+ "]";
	}
	
	
	

}
