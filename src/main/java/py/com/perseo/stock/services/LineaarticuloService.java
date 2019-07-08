package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.stock.entities.Lineaarticulo;

import java.util.List;

public interface LineaarticuloService extends GenericDao<Lineaarticulo, Integer>  {

	public List<Lineaarticulo> getByFamilia(Integer idFamilia) throws Exception;

}
