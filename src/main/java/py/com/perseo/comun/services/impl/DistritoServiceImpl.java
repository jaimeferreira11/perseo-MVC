package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Distrito;
import py.com.perseo.comun.services.DistritoService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class DistritoServiceImpl implements DistritoService {

	private static final Logger logger = LoggerFactory.getLogger(DistritoServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Distrito getById(Serializable key) {
		TypedQuery<Distrito> query = em.createQuery("SELECT c FROM Distrito c where c.iddistrito = ?", Distrito.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Distrito> getAll() throws Exception {
		TypedQuery<Distrito> query = em.createQuery("SELECT c FROM Distrito c", Distrito.class);
		return query.getResultList();
	}

	@Override
	public Distrito add(Distrito entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar Distrito " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Distrito update(Distrito entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar Distrito " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Serializable key) {
		Distrito entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar distrito " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

}