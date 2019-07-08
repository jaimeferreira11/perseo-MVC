package py.com.perseo.venta.beans;

import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Proveedor;
import py.com.perseo.venta.entities.Tipofactura;

import java.io.Serializable;
import java.sql.Date;

public class FacturacabBean implements Serializable {

	Integer idfacturacab;
	Character tipocomprobante;
	String persona;
	Date fecha;
	Double importe_factura;
	Integer establecimiento;
	Integer puntoexpedicion;
	Long nrofactura;
	String nrofactura_string;
	String concepto;
	Double importe_notacredito;
	Tipofactura tipofactura;
	Integer tipo;
	Moneda moneda;
	String Receptora_nombre_completo;
	Integer Id_Receptora;
	Integer cantidad;
	Integer Id_factura_det;
	Integer Id_concepto;
	String Concepto_descripcion;
	Double importe_pago;
	Double importe_cobro;
	Double saldo;
	Date Fecha_OP;
	Integer Id_OP;
	Integer Montoequivalente;
	Integer PrecioUnitario;
	Integer fkid;
	Boolean estado;
	Double importe;
	Cliente beneficiario;
	Proveedor proveedor;
	Ordenpagocab ordenPago;
	String nrodocumento;
	Double iva10;
	Double iva5;
	Double gravada10;
	Double gravada5;
	Double exenta;
	Integer nronotacredito;
	Date fechanc;
	Boolean estadonc;
	String clase;
	Usuario empleado;
	Sucursal sucursal;
	Integer timbrado;

	public Integer getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Integer timbrado) {
		this.timbrado = timbrado;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Boolean getEstadonc() {
		return estadonc;
	}

	public void setEstadonc(Boolean estadonc) {
		this.estadonc = estadonc;
	}

	public Date getFechanc() {
		return fechanc;
	}

	public void setFechanc(Date fechanc) {
		this.fechanc = fechanc;
	}

	public Integer getNronotacredito() {
		return nronotacredito;
	}

	public void setNronotacredito(Integer nronotacredito) {
		this.nronotacredito = nronotacredito;
	}

	public Double getIva10() {
		return iva10;
	}

	public void setIva10(Double iva10) {
		this.iva10 = iva10;
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

	public Double getExenta() {
		return exenta;
	}

	public void setExenta(Double exenta) {
		this.exenta = exenta;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public Cliente getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Cliente beneficiario) {
		this.beneficiario = beneficiario;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getPrecioUnitario() {
		return PrecioUnitario;
	}

	public void setPrecioUnitario(Integer precioUnitario) {
		PrecioUnitario = precioUnitario;
	}

	public Integer getMontoequivalente() {
		return Montoequivalente;
	}

	public void setMontoequivalente(Integer montoequivalente) {
		Montoequivalente = montoequivalente;
	}

	public Integer getId_OP() {
		return Id_OP;
	}

	public void setId_OP(Integer id_OP) {
		Id_OP = id_OP;
	}

	public Date getFecha_OP() {
		return Fecha_OP;
	}

	public void setFecha_OP(Date fecha_OP) {
		Fecha_OP = fecha_OP;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getImporte_pago() {
		return importe_pago;
	}

	public void setImporte_pago(Double importe_pago) {
		this.importe_pago = importe_pago;
	}

	public Integer getId_factura_det() {
		return Id_factura_det;
	}

	public void setId_factura_det(Integer id_factura_det) {
		Id_factura_det = id_factura_det;
	}

	public Integer getId_concepto() {
		return Id_concepto;
	}

	public void setId_concepto(Integer id_concepto) {
		Id_concepto = id_concepto;
	}

	public Integer getId_Receptora() {
		return Id_Receptora;
	}

	public void setId_Receptora(Integer id_Receptora) {
		Id_Receptora = id_Receptora;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getReceptora_nombre_completo() {
		return Receptora_nombre_completo;
	}

	public void setReceptora_nombre_completo(String receptora_nombre_completo) {
		Receptora_nombre_completo = receptora_nombre_completo;
	}

	public Integer getIdfacturacab() {
		return idfacturacab;
	}

	public void setIdfacturacab(Integer idfacturacab) {
		this.idfacturacab = idfacturacab;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Integer establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Integer getPuntoexpedicion() {
		return puntoexpedicion;
	}

	public void setPuntoexpedicion(Integer puntoexpedicion) {
		this.puntoexpedicion = puntoexpedicion;
	}

	public Long getNrofactura() {
		return nrofactura;
	}

	public void setNrofactura(Long nrofactura) {
		this.nrofactura = nrofactura;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Double getImporte_factura() {
		return importe_factura;
	}

	public void setImporte_factura(Double importe_factura) {
		this.importe_factura = importe_factura;
	}

	public Double getImporte_notacredito() {
		return importe_notacredito;
	}

	public void setImporte_notacredito(Double importe_notacredito) {
		this.importe_notacredito = importe_notacredito;
	}

	

	public Tipofactura getTipofactura() {
		return tipofactura;
	}

	public void setTipofactura(Tipofactura tipofactura) {
		this.tipofactura = tipofactura;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getConcepto_descripcion() {
		return Concepto_descripcion;
	}

	public void setConcepto_descripcion(String concepto_descripcion) {
		Concepto_descripcion = concepto_descripcion;
	}

	public Integer getFkid() {
		return fkid;
	}

	public void setFkid(Integer fkid) {
		this.fkid = fkid;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Ordenpagocab getOrdenPago() {
		return ordenPago;
	}

	public void setOrdenPago(Ordenpagocab ordenPago) {
		this.ordenPago = ordenPago;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public Double getImporte_cobro() {
		return importe_cobro;
	}

	public void setImporte_cobro(Double importe_cobro) {
		this.importe_cobro = importe_cobro;
	}

	public Character getTipocomprobante() {
		return tipocomprobante;
	}

	public void setTipocomprobante(Character tipocomprobante) {
		this.tipocomprobante = tipocomprobante;
	}

	public Double getIva5() {
		return iva5;
	}

	public void setIva5(Double iva5) {
		this.iva5 = iva5;
	}

	public String getNrofactura_string() {
		return nrofactura_string;
	}

	public void setNrofactura_string(String nrofactura_string) {
		this.nrofactura_string = nrofactura_string;
	}
}
