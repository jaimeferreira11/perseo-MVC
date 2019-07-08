package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Familia;

public interface FamiliaService extends GenericDao<Familia, Integer>  {

	public Familia updateFamilia(Familia entity, Usuario usuario) throws Exception;

}
