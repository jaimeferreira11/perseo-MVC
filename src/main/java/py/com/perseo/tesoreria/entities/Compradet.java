package py.com.perseo.tesoreria.entities;

import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.stock.entities.Articulodeposito;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "compradet")
public class Compradet implements Serializable {

    @Id
    @Column(name = "idcompradet")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compradet_idcompradet_seq")
    @SequenceGenerator(name = "compradet_idcompradet_seq", sequenceName = "compradet_idcompradet_seq", allocationSize = 1)
    private Integer idcompradet;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "tipoprovision")
    private String tipoprovision;

    @Column(name = "cantidad", columnDefinition = "numeric")
    private Double cantidad;

    @Column(name = "precio", columnDefinition = "numeric")
    private Double precio;

    @Column(name = "ivaporcentaje", columnDefinition = "numeric")
    private Double ivaporcentaje;

    @Column(name = "gravada", columnDefinition = "numeric")
    private Double gravada;

    @Column(name = "exenta", columnDefinition = "numeric")
    private Double exenta;

    @Column(name = "ivaimporte", columnDefinition = "numeric")
    private Double ivaimporte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcompracab")
    private Compracab compracab;

    @Transient
    private Boolean inventario;

    @ManyToOne(targetEntity = Plancuenta.class)
    @JoinColumn(name = "idplancuentagasto")
    Plancuenta plancuentagasto;

    @ManyToOne(targetEntity = Plancuenta.class)
    @JoinColumn(name = "idplancuentaiva")
    Plancuenta plancuentaiva;

    @ManyToOne(targetEntity = Articulodeposito.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idarticulodeposito")
    Articulodeposito articulodeposito;


    public Integer getIdcompradet() {
        return idcompradet;
    }

    public void setIdcompradet(Integer idcompradet) {
        this.idcompradet = idcompradet;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getIvaporcentaje() {
        return ivaporcentaje;
    }

    public void setIvaporcentaje(Double ivaporcentaje) {
        this.ivaporcentaje = ivaporcentaje;
    }

    public Double getGravada() {
        return gravada;
    }

    public void setGravada(Double gravada) {
        this.gravada = gravada;
    }

    public Double getExenta() {
        return exenta;
    }

    public void setExenta(Double exenta) {
        this.exenta = exenta;
    }

    public Double getIvaimporte() {
        return ivaimporte;
    }

    public void setIvaimporte(Double ivaimporte) {
        this.ivaimporte = ivaimporte;
    }

    public Plancuenta getPlancuentagasto() {
        return plancuentagasto;
    }

    public void setPlancuentagasto(Plancuenta plancuentagasto) {
        this.plancuentagasto = plancuentagasto;
    }

    public Plancuenta getPlancuentaiva() {
        return plancuentaiva;
    }

    public void setPlancuentaiva(Plancuenta plancuentaiva) {
        this.plancuentaiva = plancuentaiva;
    }

    public Compracab getCompracab() {
        return compracab;
    }

    public void setCompracab(Compracab compracab) {
        this.compracab = compracab;
    }

    public Boolean getInventario() {
        return inventario;
    }

    public void setInventario(Boolean inventario) {
        this.inventario = inventario;
    }

    public String getTipoprovision() {
        return tipoprovision;
    }

    public void setTipoprovision(String tipoprovision) {
        this.tipoprovision = tipoprovision;
    }

    public Articulodeposito getArticulodeposito() {
        return articulodeposito;
    }

    public void setArticulodeposito(Articulodeposito articulodeposito) {
        this.articulodeposito = articulodeposito;
    }
}
