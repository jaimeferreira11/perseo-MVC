package py.com.perseo.tesoreria.entities;

import javax.persistence.*;

@Entity
@Table(name = "ordenpagodet")
public class Ordenpagodet {

	@Id
	@Column(name = "idordenpagodet")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordenpagodet_idordenpagodet_seq")
	@SequenceGenerator(name = "ordenpagodet_idordenpagodet_seq", sequenceName = "ordenpagodet_idordenpagodet_seq", allocationSize = 1)
	private Integer idordenpagodet;

	@Column(name = "importe", columnDefinition = "numeric")
	private Double importe;

	@Column(name = "concepto")
	private String concepto;
	
	@ManyToOne(targetEntity = Ordenpagocab.class)
	@JoinColumn(name = "idordenpagocab")
	private Ordenpagocab ordenpagocab;

	public Integer getIdordenpagodet() {
		return idordenpagodet;
	}

	public void setIdordenpagodet(Integer idordenpagodet) {
		this.idordenpagodet = idordenpagodet;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Ordenpagocab getOrdenpagocab() {
		return ordenpagocab;
	}

	public void setOrdenpagocab(Ordenpagocab ordenpagocab) {
		this.ordenpagocab = ordenpagocab;
	}

}