package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.stock.entities.Articuloprecioventacab;

import java.util.List;

public interface ArticuloPrecioVentacabService extends GenericDao<Articuloprecioventacab, Integer>  {

	public List<Articuloprecioventacab> getArticulosPrecioCabByEmpresa(Integer idEmpresa, Boolean all, Boolean estado) throws Exception;
	
	
}
