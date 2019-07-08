package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.stock.entities.Unidadmedida;
import py.com.perseo.stock.services.UnidadmedidaService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UnidadmedidaServiceImpl implements UnidadmedidaService {

	private static final Logger logger = LoggerFactory.getLogger(UnidadmedidaServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Unidadmedida getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from Unidadmedida ";
		sql += " where idunidadmedida = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Unidadmedida unidadmedida = new Unidadmedida();
		while (rs.next()) {
			
			unidadmedida.setIdunidadmedida(rs.getInt("idunidadmedida"));
			unidadmedida.setDescripcion(rs.getString("descripcion"));
			unidadmedida.setEstado(rs.getBoolean("estado"));
		}
		
		conn.close();
		
		return unidadmedida;
	}

	@Override
	public List<Unidadmedida> getAll() throws Exception {

		Connection conn = dataSource.getConnection();
		List<Unidadmedida> familias = new ArrayList<Unidadmedida>();
		
		String sql = " select * from unidadmedida order by idunidadmedida desc ";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Unidadmedida unidadmedida = new Unidadmedida();
			unidadmedida.setIdunidadmedida(rs.getInt("idunidadmedida"));
			unidadmedida.setDescripcion(rs.getString("descripcion"));
			unidadmedida.setEstado(rs.getBoolean("estado"));
			
			familias.add(unidadmedida);
		}
		
		conn.close();
		
		return familias;
		
	}

	@Override
	public Unidadmedida add(Unidadmedida entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar Unidadmedida " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Unidadmedida update(Unidadmedida entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar Unidadmedida " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Unidadmedida entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar Unidadmedida " + e.getMessage());
		}
		  return " Unidadmedida eliminado correctamente";
	}

	

}