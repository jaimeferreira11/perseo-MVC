package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.venta.entities.Ventadet;

import java.util.List;

public interface VentaDetService extends GenericDao<Ventadet, Integer> {
	

    public List<Ventadet> getByIdVentaCab(Integer idVentaCab) throws Exception;
    
}
