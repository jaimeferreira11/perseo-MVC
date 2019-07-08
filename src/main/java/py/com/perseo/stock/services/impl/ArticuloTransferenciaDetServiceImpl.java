package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulotransferenciacab;
import py.com.perseo.stock.entities.Articulotransferenciadet;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.ArticuloTransferenciaDetService;

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
public class ArticuloTransferenciaDetServiceImpl implements ArticuloTransferenciaDetService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloTransferenciaDetServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloDepositoService articuloDepositoService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Articulotransferenciadet add(Articulotransferenciadet entity) throws Exception {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar articulo transferencia " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Articulotransferenciadet update(Articulotransferenciadet entity) throws Exception {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar articulo deposito" + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Articulotransferenciadet entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar el entity" + e.getMessage());
		}
		  return "entity eliminado correctamente";
	}

	@Override
	public Articulotransferenciadet getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		Articulotransferenciadet articulo = new Articulotransferenciadet();
		try {
			
			String sql = " select * ";
			sql += " from articulotransferenciadet ";
			sql += " where idarticulotransferenciadet = ?  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, key);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				articulo.setIdarticulotransferenciadet(rs.getInt("idarticulotransferenciadet"));
				articulo.setCantidadcontado(rs.getInt("cantidadcontado"));
				articulo.setCantidadrecibido(rs.getInt("cantidadrecibido"));
				Articulotransferenciacab atc = new Articulotransferenciacab();
				atc.setIdarticulotransferenciacab(rs.getInt("idarticulotransferenciacab"));
				articulo.setArticulotransferenciacab(atc);
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setArticulo(a);
				
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articulo;
	}

	@Override
	public List<Articulotransferenciadet> getAll() throws Exception {
		Connection conn = dataSource.getConnection();
		List<Articulotransferenciadet> list = new ArrayList<>();
		try {
			
			String sql = " select * ";
			sql += " from articulotransferenciadet ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Articulotransferenciadet articulo = new Articulotransferenciadet();
				articulo.setIdarticulotransferenciadet(rs.getInt("idarticulotransferenciadet"));
				articulo.setCantidadcontado(rs.getInt("cantidadcontado"));
				articulo.setCantidadrecibido(rs.getInt("cantidadrecibido"));
				Articulotransferenciacab atc = new Articulotransferenciacab();
				atc.setIdarticulotransferenciacab(rs.getInt("idarticulotransferenciacab"));
				articulo.setArticulotransferenciacab(atc);
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setArticulo(a);

				list.add(articulo);
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Articulotransferenciadet> getByCab(Integer idArticuloTransferenciaCab) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Articulotransferenciadet> list = new ArrayList<>();
		try {
			
			String sql = " select * ";
			sql += " from articulotransferenciadet ";
			sql += " where idarticulotransferenciacab = ?  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idArticuloTransferenciaCab);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Articulotransferenciadet articulo = new Articulotransferenciadet();
				articulo.setIdarticulotransferenciadet(rs.getInt("idarticulotransferenciadet"));
				articulo.setCantidadcontado(rs.getInt("cantidadcontado"));
				articulo.setCantidadrecibido(rs.getInt("cantidadrecibido"));
				Articulotransferenciacab atc = new Articulotransferenciacab();
				atc.setIdarticulotransferenciacab(rs.getInt("idarticulotransferenciacab"));
				articulo.setArticulotransferenciacab(atc);
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setArticulo(a);

				list.add(articulo);
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

}
