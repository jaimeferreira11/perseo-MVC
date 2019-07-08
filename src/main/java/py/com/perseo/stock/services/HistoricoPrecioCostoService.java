package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Historicopreciocosto;

public interface HistoricoPrecioCostoService extends GenericDao<Historicopreciocosto, Integer>  {

	
	public Double getPromedioCostoByArticulo (Articulo articulo) throws Exception;
}
