package py.com.perseo.comun.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parametro")
@NamedQueries( { @NamedQuery(name = "parametroFindAllHabilitados", query = "select p from Parametro p where p.estado = true ") })
public class Parametro implements Serializable {

	@Id
	@Column(name = "idparametro")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parametro_idparametro_seq")
	@SequenceGenerator(name = "parametro_idparametro_seq", sequenceName = "parametro_idparametro_seq", allocationSize = 1)
	private Integer idparametro;

	@Column(name = "porcaporte", columnDefinition="numeric")
	private Double porcaporte;

	@Column(name = "seguro",columnDefinition="numeric")
	private Double seguro;

	@Column(name = "gastos",columnDefinition="numeric")
	private Double gastos;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "descripcion")
	private String descripcion;

	public Integer getIdparametro() {
		return idparametro;
	}

	public void setIdparametro(Integer idparametro) {
		this.idparametro = idparametro;
	}

	public Double getPorcaporte() {
		return porcaporte;
	}

	public void setPorcaporte(Double porcaporte) {
		this.porcaporte = porcaporte;
	}

	public Double getSeguro() {
		return seguro;
	}

	public void setSeguro(Double seguro) {
		this.seguro = seguro;
	}

	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}