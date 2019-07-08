package py.com.perseo.sueldos.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.sueldos.entities.Claseempleado;
import py.com.perseo.sueldos.services.ClaseEmpleadoService;

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
public class ClaseEmpleadoServiceImpl implements ClaseEmpleadoService {

	private static final Logger logger = LoggerFactory.getLogger(ClaseEmpleadoServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Claseempleado getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.* ";
		sql += " from claseempleado a ";
		sql += " where a.idclaseempleado = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Claseempleado claseEmpleado = new Claseempleado();
		while (rs.next()) {
			claseEmpleado.setIdclaseempleado(rs.getInt("idclaseempleado"));
			claseEmpleado.setDescripcion(rs.getString("descripcion"));
		}
		
		conn.close();
		
		return claseEmpleado;
	}

	@Override
	public List<Claseempleado> getAll() throws Exception {

		Connection conn = dataSource.getConnection();
		List<Claseempleado> claseEmpleados = new ArrayList<Claseempleado>();
		
		String sql = " select a.* ";
		sql += " from claseempleado a ";
		sql += " order by a.idclaseempleado desc ";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Claseempleado claseEmpleado = new Claseempleado();
			claseEmpleado.setIdclaseempleado(rs.getInt("idclaseempleado"));
			claseEmpleado.setDescripcion(rs.getString("descripcion"));
			
			claseEmpleados.add(claseEmpleado);
		}
		
		conn.close();
		
		return claseEmpleados;
		
	}

	@Override
	public Claseempleado add(Claseempleado entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar k " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Claseempleado update(Claseempleado entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar claseEmpleado " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Claseempleado entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar claseEmpleado " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

	

	

}