package py.com.perseo.venta.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "formapago")
public class Formapago implements Serializable {

	@Id
	@Column(name = "idformapago")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formapago_idformapago_seq")
	@SequenceGenerator(name = "formapago_idformapago_seq", sequenceName = "formapago_idformapago_seq", allocationSize = 1)
	private Integer idformapago;
	
	@Column(name = "descripcion")
	private String columna;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "estado")
	private Boolean estado;

	public Integer getIdformapago() {
		return idformapago;
	}

	public void setIdformapago(Integer idformapago) {
		this.idformapago = idformapago;
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
