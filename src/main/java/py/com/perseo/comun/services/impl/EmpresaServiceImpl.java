package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.services.EmpresaService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	private static final Logger logger = LoggerFactory.getLogger(EmpresaServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Empresa getById(Serializable key) {
		TypedQuery<Empresa> query = em.createQuery("SELECT c FROM Empresa c where c.idempresa = ?", Empresa.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Empresa> getAll() throws Exception {
		TypedQuery<Empresa> query = em.createQuery("SELECT c FROM Empresa c", Empresa.class);
		return query.getResultList();
	}

	@Override
	public Empresa add(Empresa entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar empresa " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Empresa update(Empresa entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar empresa " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Serializable key) {
		Empresa entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar empresa " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

}