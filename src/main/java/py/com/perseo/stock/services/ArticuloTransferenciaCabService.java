package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.stock.entities.Articulotransferenciacab;
import py.com.perseo.stock.entities.Articulotransferenciadet;

import java.util.List;

public interface ArticuloTransferenciaCabService extends GenericDao<Articulotransferenciacab, Integer>  {

	public String transferirArticulos(Articulotransferenciacab articulotransferenciacab, List<Articulotransferenciadet> detalles, LoginData login) throws Exception;

}
