package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.venta.entities.Facturaformacobro;

import java.util.List;

public interface FacturaFormaCobroService extends GenericDao<Facturaformacobro, Integer> {
	
    public List<Facturaformacobro> getByFacturacab(Integer idfacturacab) throws Exception;
}
