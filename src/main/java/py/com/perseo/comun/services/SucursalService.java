package py.com.perseo.comun.services;

import py.com.perseo.comun.entities.Sucursal;

import java.util.List;

public interface SucursalService  {
	
	public Sucursal getSucursalById(Integer idSucursal) throws Exception;

	public List<Sucursal> getAllSucursales() throws Exception;
	
	public List<Sucursal> getSucursalesByEmpresa(Integer idEmpresa) throws Exception;
	
	public Sucursal addSucursal(Sucursal sucursal) throws Exception;
	
	public Sucursal update(Sucursal sucursal) throws Exception;
	
	public String delete(Integer idSucursal) throws Exception;
	
}
