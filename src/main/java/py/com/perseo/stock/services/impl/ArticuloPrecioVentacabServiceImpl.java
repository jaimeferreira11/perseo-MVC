package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.stock.entities.Articuloprecioventacab;
import py.com.perseo.stock.services.ArticuloPrecioVentacabService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticuloPrecioVentacabServiceImpl implements ArticuloPrecioVentacabService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloPrecioVentacabServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Articuloprecioventacab add(Articuloprecioventacab entity) throws Exception {
		try {
			em.persist(entity);
			em.flush();
			em.refresh(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar articulopreciocab " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Articuloprecioventacab update(Articuloprecioventacab entity) throws Exception {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar articulocab " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Articuloprecioventacab getById(Integer key) throws Exception {
		
		Articuloprecioventacab ret = new Articuloprecioventacab();
		
		String sql = " select a.* ";
		sql += " from articuloprecioventacab a ";
		sql += " where idarticuloprecioventacab = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, key);
		
		for (Map<String, Object> rs : list) {
			ret.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setDescripcion((String) rs.get("descripcion"));
			ret.setEstado((Boolean) rs.get("estado"));
			Empresa e = new Empresa();
			e.setIdempresa((Integer) rs.get("idempresa"));
			ret.setEmpresa(e);
		}
		
		
		return ret;
	}

	@Override
	public List<Articuloprecioventacab> getAll() throws Exception {
		List<Articuloprecioventacab> res = new ArrayList<>();
		
		String sql = " select a.* ";
		sql += " from articuloprecioventacab a ";
		sql += " order by idarticuloprecioventacab ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		for (Map<String, Object> rs : list) {
			Articuloprecioventacab ret = new Articuloprecioventacab();
			ret.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setDescripcion((String) rs.get("descripcion"));
			ret.setEstado((Boolean) rs.get("estado"));
			Empresa e = new Empresa();
			e.setIdempresa((Integer) rs.get("idempresa"));
			ret.setEmpresa(e);
			
			res.add(ret);
		}
		
		return res;
	}

	@Override
	public List<Articuloprecioventacab> getArticulosPrecioCabByEmpresa(Integer idEmpresa, Boolean all, Boolean estado)
			throws Exception {
		List<Articuloprecioventacab> res = new ArrayList<>();
		
		String sql = " select a.* ";
		sql += " from articuloprecioventacab a ";
		sql += " where idempresa = ?  ";
		if(all != true){			
			sql += " and estado =  " + estado;
		}
		sql += " order by idarticuloprecioventacab ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, idEmpresa);
		
		for (Map<String, Object> rs : list) {
			Articuloprecioventacab ret = new Articuloprecioventacab();
			ret.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setDescripcion((String) rs.get("descripcion"));
			ret.setEstado((Boolean) rs.get("estado"));
			Empresa e = new Empresa();
			e.setIdempresa((Integer) rs.get("idempresa"));
			ret.setEmpresa(e);
			
			res.add(ret);
		}
		return res;
	}

}
