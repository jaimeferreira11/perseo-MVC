package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.venta.entities.Facturadet;

import java.util.List;

public interface FacturaDetService extends GenericDao<Facturadet, Integer> {
	

    public List<Facturadet> getByIdFacturaCab(Integer idFacturaCab) throws Exception;
    
}
