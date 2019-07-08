package py.com.perseo.comun.base;

import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.session.entities.Menu;
import py.com.perseo.session.entities.Perfil;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.venta.entities.Turno;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class LoginData implements Serializable {

	private Date fecha;
	private Timestamp fechahora;
	private Perfil perfil;
	private Usuario usuario;
	private Empresa empresa;
	private Sucursal sucursal;
	private Moneda moneda;
	private Caja caja;
	private List<Perfil> perfiles;
	private Turno turno;
	
	private List<Menu> menues;
	
	private Perfil perfilactual;
	

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public List<Menu> getMenues() {
		return menues;
	}

	public void setMenues(List<Menu> menues) {
		this.menues = menues;
	}

	public Perfil getPerfilactual() {
		return perfilactual;
	}

	public void setPerfilactual(Perfil perfilactual) {
		this.perfilactual = perfilactual;
	}

	public Double getRedondeo(Double valor) {
		BigDecimal bd = new BigDecimal(valor);
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
	
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param date
	 */
	public void setFecha(Date date) {
		fecha = date;
	}

	/**
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param data
	 */
	public void setUsuario(Usuario data) {
		usuario = data;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Timestamp getFechahora() {
		return fechahora;
	}

	public void setFechahora(Timestamp fechahora) {
		this.fechahora = fechahora;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
}