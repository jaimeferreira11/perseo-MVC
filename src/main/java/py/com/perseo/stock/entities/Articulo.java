package py.com.perseo.stock.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

import javax.persistence.*;

@Entity
@Table(name = "articulo")
@JsonInclude(Include.NON_NULL)
public class Articulo {
	
	@Id
	@Column(name = "idarticulo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articulo_idarticulo_seq")
	@SequenceGenerator(name = "articulo_idarticulo_seq", sequenceName = "articulo_idarticulo_seq", allocationSize = 1)
	private Integer idarticulo;

	@Column(name = "idmarca")
	private Integer idmarca;

	@Column(name = "idfamilia")
	private Integer idfamilia;
	
	@Column(name = "idlineaarticulo")
	private Integer idlineaarticulo;

	@Column(name = "idunidadmedida")
	private Integer idunidadmedida;

	@Column(name = "codmoneda")
	private Integer codmoneda;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "codigobarra")
	private String codigobarra;

	@ManyToOne(targetEntity = Tipoimpuesto.class)
	@JoinColumn(name = "idtipoimpuesto")
	Tipoimpuesto tipoimpuesto;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	
	@Column(name = "preciocosto", columnDefinition = "numeric")
	private Double preciocosto;
	
	@ManyToOne(targetEntity = Empresa.class)
	@JoinColumn(name = "idempresa")
	Empresa empresa;

	public Articulo() {
		super();
	}

	public Integer getIdarticulo() {
		return idarticulo;
	}

	public void setIdarticulo(Integer idarticulo) {
		this.idarticulo = idarticulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tipoimpuesto getTipoimpuesto() {
		return tipoimpuesto;
	}

	public void setTipoimpuesto(Tipoimpuesto tipoimpuesto) {
		this.tipoimpuesto = tipoimpuesto;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getCodigobarra() {
		return codigobarra;
	}

	public void setCodigobarra(String codigobarra) {
		this.codigobarra = codigobarra;
	}


	public Integer getIdmarca() {
		return idmarca;
	}

	public void setIdmarca(Integer idmarca) {
		this.idmarca = idmarca;
	}

	public Integer getIdfamilia() {
		return idfamilia;
	}

	public void setIdfamilia(Integer idfamilia) {
		this.idfamilia = idfamilia;
	}

	public Integer getIdunidadmedida() {
		return idunidadmedida;
	}

	public void setIdunidadmedida(Integer idunidadmedida) {
		this.idunidadmedida = idunidadmedida;
	}

	public Integer getCodmoneda() {
		return codmoneda;
	}

	public void setCodmoneda(Integer codmoneda) {
		this.codmoneda = codmoneda;
	}

	public Integer getIdlineaarticulo() {
		return idlineaarticulo;
	}

	public void setIdlineaarticulo(Integer idlineaarticulo) {
		this.idlineaarticulo = idlineaarticulo;
	}

	public Double getPreciocosto() {
		return preciocosto;
	}

	public void setPreciocosto(Double preciocosto) {
		this.preciocosto = preciocosto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Articulo [idarticulo=" + idarticulo + ", idmarca=" + idmarca + ", idfamilia=" + idfamilia
				+ ", idlineaarticulo=" + idlineaarticulo + ", idunidadmedida=" + idunidadmedida + ", codmoneda="
				+ codmoneda + ", descripcion=" + descripcion + ", codigobarra=" + codigobarra + ", tipoimpuesto="
				+ tipoimpuesto + ", estado=" + estado + ", usuario=" + usuario + ", preciocosto=" + preciocosto + "]";
	}
	
	
	
	
}
