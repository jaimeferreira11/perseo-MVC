package py.com.perseo.session.services;

import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.session.entities.Perfil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginService {

	public LoginData getLoginCache(HttpServletRequest request) throws Exception;

	public LoginData getLoginUsuario(String username, String string, String id, String ua) throws Exception;
	
	public void actualizarCache() throws Exception;
	
	public List<Empresa> getAllEmpresas() throws Exception;
	
	public List<Perfil> getAllPerfiles() throws Exception;
	
	public Long getSecuenceFromName(String name) throws Exception;
	
	public Long getLastSecuenceFromName(String name) throws Exception;
	
	public String getHashPassword(String pass) throws Exception;
	
	public Boolean checkHashPassword(String pass, String pass2) throws Exception;

	public List<String> getUsersConnected(HttpServletRequest request) throws Exception;
	
}
