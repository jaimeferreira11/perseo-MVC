package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.com.perseo.comun.base.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jaime Ferreira
 * @param <ET>
 * @param <PK>
 */
public class GenericImpl<ET, PK extends Serializable>
        implements GenericDao<ET, PK> {
    // Not showing implementations of getSession() and setSessionFactory()
	
	private static final Logger logger = LoggerFactory.getLogger(GenericImpl.class);

    @PersistenceContext
    public EntityManager em;

    @Override
    public ET add(ET entity) {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
        	logger.error(getEntityName() + ".add", ex);
            return null;
        }
    }

    @Override
    public ET update(ET entity) {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception ex) {
        	logger.error(getEntityName() + ".update", ex);
            return null;
        }
    }

    @Override
    public ET getById(PK key) {
        try {
            Query query = em.createNamedQuery(this.getClass().getName() + ".findById").setParameter("id", key);
            return ((ET) query.getSingleResult());
        } catch (Exception e) {
        	logger.error(getEntityName() + ".getById", e);
            return null;
        }
    }

    @Override
    public List<ET> getAll() {
        try {
        	logger.info("GetAll >" + getEntityName() + ".findAll");
            return (List<ET>) em.createNamedQuery(getEntityName() + ".findAll").getResultList();
        } catch (Exception ex) {
        	logger.error(getEntityName() + ".getAll", ex);
            return null;
        }
    }

    @Override
    public String delete(PK key) {
    	ET entity = getById(key);
        try {
          
          if (!em.contains(entity)) {
            entity = em.merge(entity);
          }

            em.remove(entity);
            
        } catch (Exception e) {
        	logger.error(this.getClass().getName() + ".delete", e);
        	return this.getClass().getName() + ".delete" + e.getMessage();
        }
        return entity.getClass().getName() + " eliminado correctamente";
    }

    private String getEntityName() {
        
//        return ET.getClass().getSimpleName().replace("Impl", "");
        return this.getGenericName().replace("Manager", "");
    }

    protected String getGenericName() {
        return ((Class<ET>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }
}