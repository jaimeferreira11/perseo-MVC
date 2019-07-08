package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.stock.entities.Familia;
import py.com.perseo.stock.entities.Lineaarticulo;
import py.com.perseo.stock.services.LineaarticuloService;

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
public class LineaarticuloServiceImpl implements LineaarticuloService {

	private static final Logger logger = LoggerFactory.getLogger(LineaarticuloServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Lineaarticulo getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from lineaarticulo ";
		sql += " where idlineaarticulo = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Lineaarticulo linea = new Lineaarticulo();
		while (rs.next()) {
			
			linea.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			linea.setDescripcion(rs.getString("descripcion"));
			Familia f = new Familia();
			f.setIdfamilia(rs.getInt("idlineaarticulo"));
			linea.setFamilia(f);
		
		}
		
		conn.close();
		
		return linea;
	}

	@Override
	public List<Lineaarticulo> getAll() throws Exception {

		Connection conn = dataSource.getConnection();
		List<Lineaarticulo> list = new ArrayList<Lineaarticulo>();
		
		String sql = "select * from lineaarticulo order by idlineaarticulo desc";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Lineaarticulo linea = new Lineaarticulo();
			linea.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			linea.setDescripcion(rs.getString("descripcion"));
			Familia f = new Familia();
			f.setIdfamilia(rs.getInt("idlineaarticulo"));
			linea.setFamilia(f);
			
			list.add(linea);
		}
		
		conn.close();
		
		return list;
		
	}

	@Override
	public Lineaarticulo add(Lineaarticulo entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar linea articulo " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Lineaarticulo update(Lineaarticulo entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar linea articulo " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Lineaarticulo entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar linea articulo " + e.getMessage());
		}
		  return  "linea articulo eliminado correctamente";
	}

	@Override
	public List<Lineaarticulo> getByFamilia(Integer idFamilia) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Lineaarticulo> list = new ArrayList<Lineaarticulo>();
		
		String sql = "select * from lineaarticulo where idfamilia = ? order  by idlineaarticulo desc";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idFamilia);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Lineaarticulo linea = new Lineaarticulo();
			linea.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			linea.setDescripcion(rs.getString("descripcion"));
			Familia f = new Familia();
			f.setIdfamilia(rs.getInt("idlineaarticulo"));
			linea.setFamilia(f);
			
			list.add(linea);
		}
		
		conn.close();
		
		return list;
	}

	

}