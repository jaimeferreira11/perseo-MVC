package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.stock.entities.Articulo;

import java.util.List;

public interface ArticuloService extends GenericDao<Articulo, Integer>  {

	public List<Articulo> getArticulosByEmpresa(Integer idEmpresa) throws Exception;
	
	
	public List<Articulo> getArticulosByParams(String param, Boolean all, LoginData loginData) throws Exception;
	
	public List<Articulo> getArticulosByFamilia(Integer idfamilia) throws Exception;
	
	
	
}
