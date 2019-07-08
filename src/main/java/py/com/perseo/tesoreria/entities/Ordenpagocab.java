package py.com.perseo.tesoreria.entities;

import py.com.perseo.comun.entities.Usuario;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ordenpagocab")
public class Ordenpagocab {

    public static final String PENDIENTE = "PE";
    public static final String PAGADO = "PA";

    @Id
    @Column(name = "idordenpagocab")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordenpagocab_idordenpagocab_seq")
    @SequenceGenerator(name = "ordenpagocab_idordenpagocab_seq", sequenceName = "ordenpagocab_idordenpagocab_seq", allocationSize = 1)
    private Integer idordenpagocab;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "concepto")
    private String concepto;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "idusuario")
    Usuario usuario;
    
    @ManyToOne(targetEntity = Moneda.class)
    @JoinColumn(name = "codmoneda")
    Moneda moneda;

    @Column(name = "importe", columnDefinition = "numeric")
    private Double importe;

    @Column(name = "importeretenido", columnDefinition = "numeric")
    private Double importeretenido;

    @Column(name = "retencion")
    private Boolean retencion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "beneficiario")
    private String beneficiario;
    
    @Transient
    private List<Ordenpagodet> detalleOrden;
    
    @Transient
    private List<Ordenpagofp> listFormaPago;

    public Integer getIdordenpagocab() {
        return idordenpagocab;
    }

    public void setIdordenpagocab(Integer idordenpagocab) {
        this.idordenpagocab = idordenpagocab;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getImporteretenido() {
        return importeretenido;
    }

    public void setImporteretenido(Double importeretencion) {
        this.importeretenido = importeretencion;
    }

    public Boolean getRetencion() {
        return retencion;
    }

    public void setRetencion(Boolean retencion) {
        this.retencion = retencion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public List<Ordenpagodet> getDetalleOrden() {
		return detalleOrden;
	}

	public void setDetalleOrden(List<Ordenpagodet> detalleOrden) {
		this.detalleOrden = detalleOrden;
	}

	public List<Ordenpagofp> getListFormaPago() {
		return listFormaPago;
	}

	public void setListFormaPago(List<Ordenpagofp> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}
	
	
    
    
}