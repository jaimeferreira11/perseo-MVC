package py.com.perseo.stock.entities;

import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "historicopreciocosto")
public class Historicopreciocosto {

    @Id
    @Column(name = "idhistoricopreciocosto")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicopreciocosto_idhistoricopreciocosto_seq")
    @SequenceGenerator(name = "historicopreciocosto_idhistoricopreciocosto_seq", sequenceName = "historicopreciocosto_idhistoricopreciocosto_seq", allocationSize = 1)
    private Integer idhistoricopreciocosto;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "preciocosto")
    private Double preciocosto;


    @ManyToOne(targetEntity = Articulo.class)
    @JoinColumn(name = "idarticulo", nullable = false)
    private Articulo articulo;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    public Integer getIdhistoricopreciocosto() {
        return idhistoricopreciocosto;
    }

    public void setIdhistoricopreciocosto(Integer idhistoricopreciocosto) {
        this.idhistoricopreciocosto = idhistoricopreciocosto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getPreciocosto() {
        return preciocosto;
    }

    public void setPreciocosto(Double preciocosto) {
        this.preciocosto = preciocosto;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
