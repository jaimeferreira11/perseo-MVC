package py.com.perseo.comun.entities;

import py.com.perseo.solicitudes.entities.Solicitud;
import py.com.perseo.tesoreria.entities.Chequetercero;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "solicitudcuota")
public class Solicitudcuota implements Serializable {

	@Id
	@Column(name = "idsolicitudcuota")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitudcuota_idsolicitudcuota_seq")
	@SequenceGenerator(name = "solicitudcuota_idsolicitudcuota_seq", sequenceName = "solicitudcuota_idsolicitudcuota_seq", allocationSize = 1)
	private Integer idsolicitudcuota;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idsolicitud")
	private Solicitud solicitud;
	
	@Column(name = "nrocuota", columnDefinition = "int2")
	private Integer nrocuota;

	@Column(name = "fecvencimiento")
	private Date fecvencimiento;
	
	@Column(name = "dias", columnDefinition = "int2")
	private Integer dias;
	
	@ManyToOne(targetEntity = Chequetercero.class)
	@JoinColumn(name = "idchequetercero")
	Chequetercero chequetercero;

	@Column(name = "capital", columnDefinition = "numeric")
	private Double capital;

	@Column(name = "interes", columnDefinition = "numeric")
	private Double interes;

	@Column(name = "iva", columnDefinition = "numeric")
	private Double iva;

	@Column(name = "monto", columnDefinition = "numeric")
	private Double monto;
	
	@Column(name = "saldocapital", columnDefinition = "numeric")
	private Double saldocapital;
	

	public Integer getIdsolicitudcuota() {
		return idsolicitudcuota;
	}

	public void setIdsolicitudcuota(Integer idsolicitudcuota) {
		this.idsolicitudcuota = idsolicitudcuota;
	}

	public Integer getNrocuota() {
		return nrocuota;
	}

	public void setNrocuota(Integer nrocuota) {
		this.nrocuota = nrocuota;
	}

	public Date getFecvencimiento() {
		return fecvencimiento;
	}

	public void setFecvencimiento(Date fecvencimiento) {
		this.fecvencimiento = fecvencimiento;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getInteres() {
		return interes;
	}

	public void setInteres(Double interes) {
		this.interes = interes;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Chequetercero getChequetercero() {
		return chequetercero;
	}

	public void setChequetercero(Chequetercero chequetercero) {
		this.chequetercero = chequetercero;
	}

	public Double getSaldocapital() {
		return saldocapital;
	}

	public void setSaldocapital(Double saldocapital) {
		this.saldocapital = saldocapital;
	}

}
