package py.com.perseo.session.services;

import py.com.perseo.session.entities.Perfil;
import py.com.perseo.session.entities.Perfilusuario;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;


public interface PerfilService {

	public Perfil getById(Serializable idpefil);
	
	public List<Perfil> getAll();
	
	public Perfil add(Perfil entity);
	
	public Perfil update(Perfil entity);
	
	public List<Perfil> getPerfiles() throws Exception;
	
	public List<Perfil> getPerfilesByEmpresa(Integer idEmpresa, Boolean all) throws Exception;

	public Perfil getPerfilById(String id) throws Exception;

	public List<Perfil> getAPerfilesByEstado(Boolean estado) throws Exception;
	
	public void AddPerfilUsuario(Perfilusuario perfilusuario) throws Exception;
	
	public Boolean ifAsigned(Perfilusuario perfilusuario) throws Exception;
	
	public void deletePerilByUser(Integer idusuario) throws Exception;
	
	public void validarPerfilUsuario(HttpServletRequest request, String url) throws Exception;

}
