package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Barrio;
import py.com.perseo.comun.services.BarrioService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional
public class BarrioServiceImpl implements BarrioService {

	private static final Logger logger = LoggerFactory.getLogger(BarrioServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Barrio getById(Integer key) {
		TypedQuery<Barrio> query = em.createQuery("SELECT c FROM Barrio c where c.idbarrio = ?", Barrio.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Barrio> getAll() throws Exception {
		TypedQuery<Barrio> query = em.createQuery("SELECT c FROM Barrio c", Barrio.class);
		return query.getResultList();
	}

	@Override
	public Barrio add(Barrio entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar Barrio " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Barrio update(Barrio entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar Barrio " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) {
		Barrio entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar barrio " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

}