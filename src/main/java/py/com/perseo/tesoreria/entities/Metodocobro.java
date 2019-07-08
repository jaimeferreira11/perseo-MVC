package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "metodocobro")
public class Metodocobro implements Serializable {

	@Id
	@Column(name = "idmetodocobro")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metodocobro_idmetodocobro_seq")
	@SequenceGenerator(name = "metodocobro_idmetodocobro_seq", sequenceName = "metodocobro_idmetodocobro_seq", allocationSize = 1)
	private Integer idmetodocobro;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "minimocomision", columnDefinition="numeric")
	private Double minimocomision;
	
	@Column(name = "maximocomision", columnDefinition="numeric")
	private Double maximocomision;
	
	public Integer getIdmetodocobro() {
		return idmetodocobro;
	}

	public void setIdmetodocobro(Integer idmetodocobro) {
		this.idmetodocobro = idmetodocobro;
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

	public Double getMinimocomision() {
		return minimocomision;
	}

	public void setMinimocomision(Double minimocomision) {
		this.minimocomision = minimocomision;
	}

	public Double getMaximocomision() {
		return maximocomision;
	}

	public void setMaximocomision(Double maximocomision) {
		this.maximocomision = maximocomision;
	}

}
