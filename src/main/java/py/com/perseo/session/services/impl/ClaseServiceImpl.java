package py.com.perseo.session.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.session.entities.Clase;
import py.com.perseo.session.services.ClaseService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Transactional
public class ClaseServiceImpl implements ClaseService{
	
	private static final Logger logger = Logger.getLogger(ClaseServiceImpl.class.getName());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Clase add(Clase entity) {
		try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
        	logger.info("Error al agregar clase " + ex.getMessage());
        	throw ex;
     
        }
	}

	@Override
	public Clase update(Clase entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al modificar clase " + e.getClass().getCanonicalName());
        	throw e;
        }
	}


	@Override
	public Clase getById(Serializable key) {
		TypedQuery<Clase> query = em.createQuery("SELECT c FROM Clase c where c.idclase = ?", Clase.class);
		query.setParameter(1, key);
		return query.getSingleResult();
	}

	@Override
	public List<Clase> getAll() {
		TypedQuery<Clase> query = em.createQuery("SELECT c FROM Clase c", Clase.class);
		return query.getResultList();
	}

	@Override
	public String delete(Serializable key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clase> getClaseByEmpresa(Integer idEmpresa, Boolean all) throws Exception {
		List<Clase> menuList = new ArrayList();

		try {
			// administrador
			String sql = " select a.* ";
			sql += " from clase a ";
			sql += " where  a.idempresa = ?";
			if(!all){
				sql += " and a.estado = true ";
			}
			sql += " order by a.idclase ";

			List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql, idEmpresa);
			for (Map<String, Object> row : lista) {
				Clase clase = new Clase();
				clase.setIdclase((Integer) row.get("idclase"));
				clase.setDescripcion((String) row.get("descripcion"));
				clase.setClase((String) row.get("clase"));
				clase.setUrl((String) row.get("url"));
				clase.setActivity((String) row.get("activity"));
				clase.setEstado((Boolean) row.get("estado"));
				Empresa e = new Empresa();
				e.setIdempresa((Integer) row.get("idempresa"));
				clase.setEmpresa(e);
		
				menuList.add(clase);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return menuList;
	}

}
