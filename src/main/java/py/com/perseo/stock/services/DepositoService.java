package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.stock.entities.Deposito;

import java.util.List;

public interface DepositoService extends GenericDao<Deposito, Integer>  {

	public List<Deposito> getDepositoByEmpresa(Integer idEmpresa) throws Exception;
	public List<Deposito> getDepositoBySucursal(Integer idSucursal) throws Exception;
}
