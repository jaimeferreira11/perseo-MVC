package py.com.perseo.pojos;

import java.util.Date;

public class OrdenInfo {
    private Double importeOrden;
    private Double importeCompra;
    private  String comprobante;
    private String tipo;
    private Date fecha;

    public Double getImporteOrden() {
        return importeOrden;
    }

    public void setImporteOrden(Double importeOrden) {
        this.importeOrden = importeOrden;
    }

    public Double getImporteCompra() {
        return importeCompra;
    }

    public void setImporteCompra(Double importeCompra) {
        this.importeCompra = importeCompra;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
