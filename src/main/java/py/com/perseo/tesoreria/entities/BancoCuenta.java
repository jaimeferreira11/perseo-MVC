package py.com.perseo.tesoreria.entities;

import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "bancocuenta")
public class BancoCuenta implements Serializable {

    @Id
    @Column(name = "idbancocuenta")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bancocuenta_idbancocuenta_seq")
    @SequenceGenerator(name = "bancocuenta_idbancocuenta_seq", sequenceName = "bancocuenta_idbancocuenta_seq", allocationSize = 1)
    private Integer idbancocuenta;

    @Column(name = "fechacomprobante")
    private Date fechacomprobante;

    @Column(name = "fechaoperacion")
    private Date fechaoperacion;

    @Column(name = "fechaelim")
    private Date fechaelim;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "debe")
    private Double debe;

    @Column(name = "haber")
    private Double haber;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "idmovimiento")
    private Integer idmovimiento;

    @Column(name = "formapago")
    private String formapago;

    @Column(name = "nrocuenta")
    private String nrocuenta;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "cotizacion")
    private Double cotizacion;

    @Column(name = "comprobante")
    private Integer comprobante;

    @ManyToOne(targetEntity = Sucursal.class)
    @JoinColumn(name = "idsucursal")
    Sucursal sucursal;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuario")
    Usuario usuario;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuarioelim")
    Usuario usuarioElim;

    @ManyToOne(targetEntity = Moneda.class)
    @JoinColumn(name = "codmoneda")
    Moneda moneda;

    @ManyToOne(targetEntity = Banco.class)
    @JoinColumn(name = "idbanco")
    Banco banco;

    public Integer getIdbancocuenta() {
        return idbancocuenta;
    }

    public void setIdbancocuenta(Integer idbancocuenta) {
        this.idbancocuenta = idbancocuenta;
    }

    public Date getFechacomprobante() {
        return fechacomprobante;
    }

    public void setFechacomprobante(Date fechacomprobante) {
        this.fechacomprobante = fechacomprobante;
    }

    public Date getFechaoperacion() {
        return fechaoperacion;
    }

    public void setFechaoperacion(Date fechaoperacion) {
        this.fechaoperacion = fechaoperacion;
    }

    public Date getFechaelim() {
        return fechaelim;
    }

    public void setFechaelim(Date fechaelim) {
        this.fechaelim = fechaelim;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(Integer idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Integer getComprobante() {
        return comprobante;
    }

    public void setComprobante(Integer comprobante) {
        this.comprobante = comprobante;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioElim() {
        return usuarioElim;
    }

    public void setUsuarioElim(Usuario usuarioElim) {
        this.usuarioElim = usuarioElim;
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
