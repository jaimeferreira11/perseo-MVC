package py.com.perseo.tesoreria.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "factura")
//@NamedQueries( { @NamedQuery(name = "facturaFindByNrofactura", query = " select p from Factura p where p.establecimiento = :pestablecimiento AND p.puntoexpedicion = :ppuntoexpedicion AND p.nrofactura = :pnrofactura and p.caja.idcaja = :pidcaja and p.timbrado = :timbrado "),
//@NamedQuery(name = "getSiguienteFacturaDisponible", query = " select p from Factura p where p.estado = 'D' and p.caja.idcaja = :pidcaja order by p.nrofactura ")})

public class Factura implements Serializable {

	@Id
	@Column(name = "idfactura")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_idfactura_seq")
	@SequenceGenerator(name = "factura_idfactura_seq", sequenceName = "factura_idfactura_seq", allocationSize = 1)
	private Integer idfactura;

	@Column(name = "establecimiento")
	private Integer establecimiento;
	
	@Column(name = "puntoexpedicion")
	private Integer puntoexpedicion;
	
	@Column(name = "nrofactura")
	private Integer nrofactura;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "timbrado")
	private Integer timbrado;

	@Column(name = "vigenciadesde")
	private Date vigenciadesde;
	
	@Column(name = "vigenciahasta")
	private Date vigenciahasta;
	
	@ManyToOne(targetEntity = Caja.class)
	@JoinColumn(name = "idcaja")
	Caja caja;
	
	public Integer getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Integer timbrado) {
		this.timbrado = timbrado;
	}

	public Date getVigenciadesde() {
		return vigenciadesde;
	}

	public void setVigenciadesde(Date vigenciadesde) {
		this.vigenciadesde = vigenciadesde;
	}

	public Date getVigenciahasta() {
		return vigenciahasta;
	}

	public void setVigenciahasta(Date vigenciahasta) {
		this.vigenciahasta = vigenciahasta;
	}

	public Integer getIdfactura() {
		return idfactura;
	}

	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Integer getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Integer establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Integer getNrofactura() {
		return nrofactura;
	}

	public void setNrofactura(Integer nrofactura) {
		this.nrofactura = nrofactura;
	}

	public Integer getPuntoexpedicion() {
		return puntoexpedicion;
	}

	public void setPuntoexpedicion(Integer puntoexpedicion) {
		this.puntoexpedicion = puntoexpedicion;
	}
}