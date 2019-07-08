package py.com.perseo.session.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.session.entities.Clase;

import java.io.Serializable;
import java.util.List;

public interface ClaseService extends GenericDao<Clase, Serializable>{
	
	public List<Clase> getClaseByEmpresa(Integer idEmpresa, Boolean all) throws Exception;

}
