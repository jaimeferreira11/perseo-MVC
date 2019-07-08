package py.com.perseo.comun.services;

import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Usuario;

import java.util.List;

public interface UsuarioService {

	public List<Usuario> getUsuarios() throws Exception;

	public List<Usuario> getUsuariosByEmpresa(Integer idempresa) throws Exception;

	public Usuario getUsuarioById(Integer id) throws Exception;

	public Usuario getUsuarioByUsername(String username) throws Exception;
	
	public LoginData getLoginUsuario(String username) throws Exception;
	
	public Usuario updateUsuario(Usuario usuario) throws Exception;
	
	public Usuario addUsuario(Usuario usuario) throws Exception;
	
	public void changePassword(Integer idusuario, String password) throws Exception;
	
	public Boolean checkPassword(Integer idusuario, String password) throws Exception;

	public Usuario add(Usuario entity) throws Exception;
	
	public Usuario update(Usuario entity) throws Exception;
	
	public String delete(Integer idUsuario) throws Exception;
	
	public Long getSecuenceFromName(String name) throws Exception;
	
	public Long getLastSecuenceFromName(String name) throws Exception;
	
	
}