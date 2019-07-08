package py.com.perseo.comun.base;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
	
	T add(T entity) throws Exception;

	T update(T entity) throws Exception;

	String delete(K key) throws Exception;

	T getById(K key) throws Exception;

	List<T> getAll() throws Exception;
}