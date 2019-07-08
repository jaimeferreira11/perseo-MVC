package py.com.perseo.solicitudes.entities;

import py.com.perseo.clientes.entities.Cliente;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "grupointegrante")
public class Grupointegrante implements Serializable {

	@Id
	@Column(name = "idgrupointegrante")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupointegrante_idgrupointegrante_seq")
	@SequenceGenerator(name = "grupointegrante_idgrupointegrante_seq", sequenceName = "grupointegrante_idgrupointegrante_seq", allocationSize = 1)
	private Integer idgrupointegrante;

	@Column(name = "aportecuota", columnDefinition = "numeric")
	private Double aportecuota;

	@Column(name = "lider")
	private Boolean lider;

	@Column(name = "activo")
	private Boolean activo;

	@Column(name = "fechaingreso")
	private Date fechaingreso;
	
	@Column(name = "importecuota", columnDefinition="numeric")
	private Double importecuota;

	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "idcliente")
	Cliente cliente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idgrupo")
	private Grupo grupo;

	public Integer getIdgrupointegrante() {
		return idgrupointegrante;
	}

	public void setIdgrupointegrante(Integer idgrupointegrante) {
		this.idgrupointegrante = idgrupointegrante;
	}

	public Double getAportecuota() {
		return aportecuota;
	}

	public void setAportecuota(Double aportecuota) {
		this.aportecuota = aportecuota;
	}

	public Boolean getLider() {
		return lider;
	}

	public void setLider(Boolean lider) {
		this.lider = lider;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaingreso() {
		return fechaingreso;
	}

	public void setFechaingreso(Date fechaingreso) {
		this.fechaingreso = fechaingreso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Double getImportecuota() {
		return importecuota;
	}

	public void setImportecuota(Double importecuota) {
		this.importecuota = importecuota;
	}

}
