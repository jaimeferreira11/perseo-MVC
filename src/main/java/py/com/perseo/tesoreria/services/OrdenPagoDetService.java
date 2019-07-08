package py.com.perseo.tesoreria.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.tesoreria.entities.Ordenpagodet;

import java.util.List;

public interface OrdenPagoDetService extends GenericDao<Ordenpagodet, Integer> {
	
	public List<Ordenpagodet> getDetByCab(Integer idordepagocab) throws Exception;
}
