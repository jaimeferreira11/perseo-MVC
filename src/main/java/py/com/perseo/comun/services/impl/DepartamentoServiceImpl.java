package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Departamento;
import py.com.perseo.comun.services.DepartamentoService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

	private static final Logger logger = LoggerFactory.getLogger(DepartamentoServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Departamento getById(Integer key) {
		TypedQuery<Departamento> query = em.createQuery("SELECT c FROM Departamento c where c.iddepartamento = ?", Departamento.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Departamento> getAll() throws Exception {
		TypedQuery<Departamento> query = em.createQuery("SELECT c FROM Departamento c", Departamento.class);
		return query.getResultList();
	}

	@Override
	public Departamento add(Departamento entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar Departamento " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Departamento update(Departamento entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar Departamento " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) {
		Departamento entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar departamento " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

}