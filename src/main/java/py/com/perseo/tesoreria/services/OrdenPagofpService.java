package py.com.perseo.tesoreria.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.tesoreria.entities.Ordenpagofp;

import java.util.List;

public interface OrdenPagofpService extends GenericDao<Ordenpagofp, Integer> {
	
	public List<Ordenpagofp> getByOdenPagoCab(Integer idordepagocab) throws Exception;
}
