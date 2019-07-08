package py.com.perseo.stock.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lineaarticulo")
@JsonInclude(Include.NON_NULL)
public class Lineaarticulo {

	@Id
	@Column(name = "idlineaarticulo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lineaarticulo_idlineaarticulo_seq")
	@SequenceGenerator(name = "lineaarticulo_idlineaarticulo_seq", sequenceName = "lineaarticulo_idlineaarticulo_seq", allocationSize = 1)
	private Integer idlineaarticulo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@NotNull
	@ManyToOne(targetEntity = Familia.class)
	@JoinColumn(name = "idfamilia")
	Familia familia;

	public Integer getIdlineaarticulo() {
		return idlineaarticulo;
	}

	public void setIdlineaarticulo(Integer idlineaarticulo) {
		this.idlineaarticulo = idlineaarticulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	
}
