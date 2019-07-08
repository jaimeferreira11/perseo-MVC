package py.com.perseo.tesoreria.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.tesoreria.entities.Proveedor;

import java.util.List;

public interface ProveedorService extends GenericDao<Proveedor, Integer>  {

	public List<Proveedor> getProveedoresByEmpresa(Integer idEmpresa) throws Exception;
	
	public List<Proveedor> getProveedorByParams(String parametro, LoginData login) throws Exception;
}
