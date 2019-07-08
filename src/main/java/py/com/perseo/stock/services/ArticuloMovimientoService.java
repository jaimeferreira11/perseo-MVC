package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articulomovimiento;

public interface ArticuloMovimientoService extends GenericDao<Articulomovimiento, Integer>  {

	public String ajusteStock(Articulomovimiento articulomovimiento, Usuario usuario) throws Exception;
	
}
