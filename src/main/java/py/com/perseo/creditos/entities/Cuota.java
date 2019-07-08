package py.com.perseo.creditos.entities;

import py.com.perseo.tesoreria.entities.Chequetercero;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "cuota")
//@NamedNativeQuery(name="getCuotasByCredito", query = "select * from cuota where idcredito = ?",  resultClass=Cuota.class)
//@NamedQueries( { @NamedQuery(name = "getCuotaByCreditoAndNrocuota", query = "select p from Cuota p where p.credito.idcredito = :pidcredito AND p.nrocuota = :pnrocuota ") } )

public class Cuota implements Serializable {

	@Id
	@Column(name = "idcuota")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuota_idcuota_seq")
	@SequenceGenerator(name = "cuota_idcuota_seq", sequenceName = "cuota_idcuota_seq", allocationSize = 1)
	private Integer idcuota;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcredito")
	private Credito credito;
	
	@ManyToOne(targetEntity = Chequetercero.class)
	@JoinColumn(name = "idchequetercero")
	Chequetercero chequetercero;

	@Column(name = "nrocuota", columnDefinition = "int2")
	private Integer nrocuota;

	@Column(name = "monto", columnDefinition = "numeric")
	private Double monto;

	@Column(name = "capital", columnDefinition = "numeric")
	private Double capital;
	
	@Column(name = "iva", columnDefinition = "numeric")
	private Double iva;

	@Column(name = "interes", columnDefinition = "numeric")
	private Double interes;

	@Column(name = "interesmoratorio", columnDefinition = "numeric")
	private Double interesmoratorio;

	@Column(name = "fecvencimiento")
	private Date fecvencimiento;

	@Column(name = "promcalificacion", columnDefinition = "int2")
	private Integer promcalificacion;

	@Column(name = "periodogracia")
	private Boolean periodogracia;

	@Column(name = "pagado")
	private Boolean pagado;
	
	@Transient
	private Double saldocapital;
	
	@Transient
	private Double montocheque;
	
	@Transient
	private Double saldointeres;
	
	@Transient
	private Double comision;
	
	@Transient
	private Double iva10comision;
	
	@Transient
	private Double saldointeresmoratorio;
	
	@Transient
	private Double saldocuota;
	
	@Transient
	private Integer diasatraso;
	
	@Transient
	private Double saldoiva5;
	
	@Transient
	private Integer dias;
	
	@Transient
	private Double importecobro;
	
	@Transient
	private Double capitalcobro;

	@Transient
	private Double interescobro;
	
	@Transient
	private Double otrosingresos;
	
	@Transient
	private Double iva10otrosingresos;

	@Transient
	private Double iva5mora;
	
	@Transient
	private Double iva5interes;
	
	@Transient
	private Boolean mora;
	
	@Transient
	private Double moracobro;
	
	@Transient
	private Autorizacion autorizacion;
	
	@Transient
	private Boolean bautorizacion;
	
	@Transient
	private Boolean cancelacuota;
	
	public Boolean getCancelacuota() {
		return cancelacuota;
	}

	public void setCancelacuota(Boolean cancelacuota) {
		this.cancelacuota = cancelacuota;
	}

	public Boolean getBautorizacion() {
		return bautorizacion;
	}

	public void setBautorizacion(Boolean bautorizacion) {
		this.bautorizacion = bautorizacion;
	}

	public Double getOtrosingresos() {
		return otrosingresos;
	}

	public void setOtrosingresos(Double otrosingresos) {
		this.otrosingresos = otrosingresos;
	}

	public Double getCapitalcobro() {
		return capitalcobro;
	}

	public void setCapitalcobro(Double capitalcobro) {
		this.capitalcobro = capitalcobro;
	}

	public Double getInterescobro() {
		return interescobro;
	}

	public void setInterescobro(Double interescobro) {
		this.interescobro = interescobro;
	}

	public Double getSaldocapital() {
		return saldocapital;
	}

	public void setSaldocapital(Double saldocapital) {
		this.saldocapital = saldocapital;
	}

	public Integer getIdcuota() {
		return idcuota;
	}

	public void setIdcuota(Integer idcuota) {
		this.idcuota = idcuota;
	}

	public Chequetercero getChequetercero() {
		return chequetercero;
	}

	public void setChequetercero(Chequetercero chequetercero) {
		this.chequetercero = chequetercero;
	}

	public Integer getNrocuota() {
		return nrocuota;
	}

	public void setNrocuota(Integer nrocuota) {
		this.nrocuota = nrocuota;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getInteres() {
		return interes;
	}

	public void setInteres(Double interes) {
		this.interes = interes;
	}

	public Double getInteresmoratorio() {
		return interesmoratorio;
	}

	public void setInteresmoratorio(Double interesmoratorio) {
		this.interesmoratorio = interesmoratorio;
	}

	public Date getFecvencimiento() {
		return fecvencimiento;
	}

	public void setFecvencimiento(Date fecvencimiento) {
		this.fecvencimiento = fecvencimiento;
	}

	public Integer getPromcalificacion() {
		return promcalificacion;
	}

	public void setPromcalificacion(Integer promcalificacion) {
		this.promcalificacion = promcalificacion;
	}

	public Boolean getPeriodogracia() {
		return periodogracia;
	}

	public void setPeriodogracia(Boolean periodogracia) {
		this.periodogracia = periodogracia;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Integer getDiasatraso() {
		return diasatraso;
	}

	public void setDiasatraso(Integer diasatraso) {
		this.diasatraso = diasatraso;
	}

	public Double getImportecobro() {
		return importecobro;
	}

	public void setImportecobro(Double importecobro) {
		this.importecobro = importecobro;
	}

	public Double getSaldointeres() {
		return saldointeres;
	}

	public void setSaldointeres(Double saldointeres) {
		this.saldointeres = saldointeres;
	}

	public Double getSaldocuota() {
		return saldocuota;
	}

	public void setSaldocuota(Double saldocuota) {
		this.saldocuota = saldocuota;
	}

	public Double getSaldointeresmoratorio() {
		return saldointeresmoratorio;
	}

	public void setSaldointeresmoratorio(Double saldointeresmoratorio) {
		this.saldointeresmoratorio = saldointeresmoratorio;
	}

	public Boolean getMora() {
		return mora;
	}

	public void setMora(Boolean mora) {
		this.mora = mora;
	}

	public Autorizacion getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(Autorizacion autorizacion) {
		this.autorizacion = autorizacion;
	}

	public Double getMontocheque() {
		return montocheque;
	}

	public void setMontocheque(Double montocheque) {
		this.montocheque = montocheque;
	}

	public Double getMoracobro() {
		return moracobro;
	}

	public void setMoracobro(Double moracobro) {
		this.moracobro = moracobro;
	}

	public Double getSaldoiva5() {
		return saldoiva5;
	}

	public void setSaldoiva5(Double saldoiva5) {
		this.saldoiva5 = saldoiva5;
	}

	public Double getIva10otrosingresos() {
		return iva10otrosingresos;
	}

	public void setIva10otrosingresos(Double iva10otrosingresos) {
		this.iva10otrosingresos = iva10otrosingresos;
	}

	public Double getComision() {
		return comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Double getIva10comision() {
		return iva10comision;
	}

	public void setIva10comision(Double iva10comision) {
		this.iva10comision = iva10comision;
	}

	public Double getIva5mora() {
		return iva5mora;
	}

	public void setIva5mora(Double iva5mora) {
		this.iva5mora = iva5mora;
	}

	public Double getIva5interes() {
		return iva5interes;
	}

	public void setIva5interes(Double iva5interes) {
		this.iva5interes = iva5interes;
	}

}
