package py.com.perseo.tesoreria.entities;

import py.com.perseo.contabilidad.entities.Plancuenta;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cajacuenta")
//@NamedNativeQueries( { @NamedNativeQuery(name = "findByCajaAndNrocuenta", query = " select * from cajacuenta where idcaja = ? and numero = ? ", resultClass = Cajacuenta.class), 
//					   @NamedNativeQuery(name = "findAllByCaja", query = " select * from cajacuenta where idcaja = ? ", resultClass = Cajacuenta.class),
//					   @NamedNativeQuery(name = "cajacuentaFindAllById", query = " select * from cajacuenta where idcajacuenta = ? ", resultClass = Cajacuenta.class),
//					   @NamedNativeQuery(name = "findCajaCuentaByTipoAndCaja", query = " select * from cajacuenta where tipo = ? and idcaja = ? ", resultClass = Cajacuenta.class)})
					   
public class Cajacuenta implements Serializable {

	@Id
	@Column(name = "idcajacuenta")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cajacuenta_idcajacuenta_seq")
	@SequenceGenerator(name = "cajacuenta_idcajacuenta_seq", sequenceName = "cajacuenta_idcajacuenta_seq", allocationSize = 1)
	private Integer idcajacuenta;

	@Column(name = "numero")
	private Integer numero;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private Boolean estado;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "cuentabanco")
	private String cuentabanco;

	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja")
	Caja caja;

	@ManyToOne(targetEntity = Plancuenta.class)
	@JoinColumn(name = "idplancuenta")
	Plancuenta plancuenta;

	@ManyToOne(targetEntity = Moneda.class)
	@JoinColumn(name = "codmoneda")
	Moneda moneda;

	@ManyToOne(targetEntity = Banco.class)
	@JoinColumn(name = "idbanco")
	Banco banco;

	public Integer getIdcajacuenta() {
		return idcajacuenta;
	}

	public void setIdcajacuenta(Integer idcajacuenta) {
		this.idcajacuenta = idcajacuenta;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCuentabanco() {
		return cuentabanco;
	}

	public void setCuentabanco(String cuentabanco) {
		this.cuentabanco = cuentabanco;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Plancuenta getPlancuenta() {
		return plancuenta;
	}

	public void setPlancuenta(Plancuenta plancuenta) {
		this.plancuenta = plancuenta;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

}
