package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.stock.entities.Articulotransferenciadet;

import java.util.List;

public interface ArticuloTransferenciaDetService extends GenericDao<Articulotransferenciadet, Integer>  {

	public List<Articulotransferenciadet> getByCab(Integer idArticuloTransferenciaCab) throws Exception;

}
