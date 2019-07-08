package py.com.perseo.session.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Clase que representa columnas de la tabla MENU
 * 
 * @author mcespedes
 * 
 */
@Entity
@Table(name = "menu")
//@NamedQueries( { @NamedQuery(name = "menufindAll", query = "select p from Menu p "), @NamedQuery(name = "menufindByEstado", query = "select p from Menu p where p.estado = :pestado"), @NamedQuery(name = "findByPerfil", query = "select p from Menu p where p.perfil.idperfil = :idperfil order by p.orden"), @NamedQuery(name = "findByMenuSuperior", query = "select p from Menu p where p.menuanterior.idmenu = :idmenu "), @NamedQuery(name = "findByPerfilTipoM", query = "select p from Menu p where p.perfil.idperfil = :pidperfil and p.tipo = 'M' and p.estado = true order by p.orden"), @NamedQuery(name = "menufindByPrimaryKey", query = "select p from Menu p where p.idmenu = :pidmenu ") })
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idmenu")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_idmenu_seq")
	@SequenceGenerator(name = "menu_idmenu_seq", sequenceName = "menu_idmenu_seq", allocationSize = 1)
	private Integer idmenu;

	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "estado")
	private Boolean estado;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "mnemonic")
	private String mnemonic;
	@Column(name = "orden")
	private Integer orden;
	private Integer menuanterior;

	@Transient
	private Integer idperfil;
	
	@Transient
	private String perfil;
	
	@Transient
	private Integer idclase;


	public Integer getIdclase() {
		return idclase;
	}

	public void setIdclase(Integer idclase) {
		this.idclase = idclase;
	}

	@ManyToOne(targetEntity = Clase.class)
	@JoinColumn(name = "idclase", nullable=true)
	Clase clase;

	/*@ManyToOne(targetEntity = Menu.class)
	@JoinColumn(name = "menuanterior", nullable=true)
	private Menu menuanterior;
*/
	@OneToMany(mappedBy = "menuanterior")
	private List<Menu> menuanteriores;

	@ManyToOne(targetEntity = Perfil.class)
	@JoinColumn(name = "idperfil")
	private Perfil perfil_data;
	
	@Transient
	private List<Menu> submenulist;

	
	
	public Integer getIdperfil() {
		return idperfil;
	}

	public void setIdperfil(Integer idperfil) {
		this.idperfil = idperfil;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Perfil getPerfil_data() {
		return perfil_data;
	}

	public void setPerfil_data(Perfil perfil_data) {
		this.perfil_data = perfil_data;
	}

	public List<Menu> getSubmenulist() {
		return submenulist;
	}

	public void setSubmenulist(List<Menu> submenulist) {
		this.submenulist = submenulist;
	}


	public List<Menu> getMenuanteriores() {
		return menuanteriores;
	}

	public void setMenuanteriores(List<Menu> menuanteriores) {
		this.menuanteriores = menuanteriores;
	}

	

	public Integer getMenuanterior() {
		return menuanterior;
	}

	public void setMenuanterior(Integer menuanterior) {
		this.menuanterior = menuanterior;
	}

	public Menu() {

	}

	public Integer getIdmenu() {
		return idmenu;
	}

	public void setIdmenu(Integer idmenu) {
		this.idmenu = idmenu;
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

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public Menu(Integer idmenu, String descripcion, Boolean estado) {
		this.idmenu = idmenu;
		this.descripcion = descripcion;
		this.estado = estado;
	}

}
