package py.com.perseo.tesoreria.beans;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Proveedor;

import java.io.Serializable;
import java.sql.Date;

public class CompracabBean implements Serializable {
	Integer idcompracab;
	String comprobante;
	Date fecha;
	Double importe;
	Double gravada10;
	Double gravada5;
	Double iva10;
	Double iva5;
	Double exenta;
	Integer tipo;
	Integer fkid;
	String persona;
	String nrodocumento;
	Moneda moneda;
	String tipocompra;
	
	String condicion;
	String observacion;
	Double pagado;
	Moneda monedadata;
	Proveedor proveedordata;
	Double importeultimopago;
	
	String estado;
	Integer timbrado;
	Date fechadesde;
	Date fechahasta;
	Usuario empleado;	
	Sucursal sucursal;
	
	Asientocab asiento;
	
	Double Apagar;

	public Double getApagar() {
		return Apagar;
	}

	public void setApagar(Double apagar) {
		Apagar = apagar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Integer timbrado) {
		this.timbrado = timbrado;
	}

	public Date getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}

	public Date getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Double getImporteultimopago() {
		return importeultimopago;
	}

	public void setImporteultimopago(Double importeultimopago) {
		this.importeultimopago = importeultimopago;
	}

	public Proveedor getProveedordata() {
		return proveedordata;
	}

	public void setProveedordata(Proveedor proveedordata) {
		this.proveedordata = proveedordata;
	}

	public Moneda getMonedadata() {
		return monedadata;
	}

	public void setMonedadata(Moneda monedadata) {
		this.monedadata = monedadata;
	}

	public Integer getIdcompracab() {
		return idcompracab;
	}

	public void setIdcompracab(Integer idcompracab) {
		this.idcompracab = idcompracab;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getGravada10() {
		return gravada10;
	}

	public void setGravada10(Double gravada10) {
		this.gravada10 = gravada10;
	}

	public Double getGravada5() {
		return gravada5;
	}

	public void setGravada5(Double gravada5) {
		this.gravada5 = gravada5;
	}

	public Double getIva10() {
		return iva10;
	}

	public void setIva10(Double iva10) {
		this.iva10 = iva10;
	}

	public Double getIva5() {
		return iva5;
	}

	public void setIva5(Double iva5) {
		this.iva5 = iva5;
	}

	public Double getExenta() {
		return exenta;
	}

	public void setExenta(Double exenta) {
		this.exenta = exenta;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getFkid() {
		return fkid;
	}

	public void setFkid(Integer fkid) {
		this.fkid = fkid;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Double getPagado() {
		return pagado;
	}

	public void setPagado(Double pagado) {
		this.pagado = pagado;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getTipocompra() {
		return tipocompra;
	}

	public void setTipocompra(String tipocompra) {
		this.tipocompra = tipocompra;
	}

	public Asientocab getAsiento() {
		return asiento;
	}

	public void setAsiento(Asientocab asiento) {
		this.asiento = asiento;
	}
}