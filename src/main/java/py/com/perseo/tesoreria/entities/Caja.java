package py.com.perseo.tesoreria.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "caja")
//@NamedQueries( { @NamedQuery(name = "cajafindAll", query = "select p from Caja p order by p.idcaja"), 
//	@NamedQuery(name = "cajafindByEstado", query = "select p from Caja p where p.estado = :pestado order by p.idcaja"), 
//	@NamedQuery(name = "cajafindById", query = "select p from Caja p where p.idcaja = :pidcaja")})
	
public class Caja implements Serializable {

	@Id
	@Column(name = "idcaja")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caja_idcaja_seq")
	@SequenceGenerator(name = "caja_idcaja_seq", sequenceName = "caja_idcaja_seq", allocationSize = 1)
	private Integer idcaja;

	@ManyToOne(targetEntity = Sucursal.class)
	@JoinColumn(name = "idsucursal")
	Sucursal sucursal;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	Usuario usuario;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "nrocaja")
	private Integer nrocaja;

	public Integer getIdcaja() {
		return idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
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

	public Integer getNrocaja() {
		return nrocaja;
	}

	public void setNrocaja(Integer nrocaja) {
		this.nrocaja = nrocaja;
	}

}
