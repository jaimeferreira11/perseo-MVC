package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "moneda")
//@NamedQueries( { @NamedQuery(name = "monedafindAll", query = "select p from Moneda p order by p.codmoneda"), 
//	@NamedQuery(name = "monedafindById", query = "select p from Moneda p where p.codmoneda = :pidmoneda") })
public class Moneda implements Serializable {

	@Id
	@Column(name = "codmoneda")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moneda_codmoneda_seq")
	@SequenceGenerator(name = "moneda_codmoneda_seq", sequenceName = "moneda_codmoneda_seq", allocationSize = 1)
	private Integer codmoneda;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "valordecimal", columnDefinition = "numeric")
	private Double valordecimal;

	public Integer getCodmoneda() {
		return codmoneda;
	}

	public void setCodmoneda(Integer codmoneda) {
		this.codmoneda = codmoneda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getValordecimal() {
		return valordecimal;
	}

	public void setValordecimal(Double valordecimal) {
		this.valordecimal = valordecimal;
	}

}
