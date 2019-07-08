package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.tesoreria.services.ConceptomovService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConceptomovServiceImpl implements ConceptomovService{
	
	private static final Logger logger = LoggerFactory.getLogger(ConceptomovServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

	@Override
	public Conceptomov add(Conceptomov entity) throws Exception {
		try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar proveedor " + ex.getMessage());
            throw ex;
        }
	}

	@Override
	public Conceptomov update(Conceptomov entity) throws Exception {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar proveedor " + e.getMessage());
            throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Conceptomov getById(Integer key) throws Exception {
		TypedQuery<Conceptomov> query = em.createQuery("SELECT c FROM Conceptomov c where c.idconceptomov = ?", Conceptomov.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Conceptomov> getAll() throws Exception {
		TypedQuery<Conceptomov> query = em.createQuery("SELECT c FROM Conceptomov c", Conceptomov.class);
		return query.getResultList();
	}

}
