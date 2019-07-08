package py.com.perseo.session.services;

import py.com.perseo.session.entities.Menu;

import java.io.Serializable;
import java.util.List;


public interface MenuService  {
	
	List<Menu> getAll();
	
	Menu update(Menu entity);
	
	void delete(Menu entity);
	
	Menu getById(Serializable key);

	Menu add(Menu entity);
	
	Menu getMenuById(Integer id);

	List<Menu> getByPerfil(Integer idperfil);

	List<Menu> getAllByPerfil(Integer idperfil);

	List<Menu> getMenuesWithoutPerfiles() throws Exception;
	
	public List<Menu> getMenuByEmpresa(Integer idEmpresa, Boolean all) throws Exception;
	
}
