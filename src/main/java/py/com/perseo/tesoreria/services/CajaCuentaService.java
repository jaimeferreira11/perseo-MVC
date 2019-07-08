package py.com.perseo.tesoreria.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.tesoreria.entities.Cajacuenta;

import java.util.List;

public interface CajaCuentaService extends GenericDao<Cajacuenta, Integer> {
	
	public List<Cajacuenta> getByCaja(Integer idcaja) throws Exception;
}
