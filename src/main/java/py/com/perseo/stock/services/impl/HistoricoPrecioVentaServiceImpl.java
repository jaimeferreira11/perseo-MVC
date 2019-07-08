package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articuloprecioventadet;
import py.com.perseo.stock.entities.Historicoprecioventa;
import py.com.perseo.stock.services.HistoricoPrecioVentaService;

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
public class HistoricoPrecioVentaServiceImpl implements HistoricoPrecioVentaService{
	
	private static final Logger logger = LoggerFactory.getLogger(HistoricoPrecioVentaServiceImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;

	@Override
	public Historicoprecioventa add(Historicoprecioventa entity) throws Exception {
		try {
			em.persist(entity);
			em.flush();
			em.refresh(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar articulo deposito " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Historicoprecioventa update(Historicoprecioventa entity) throws Exception {
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
		Historicoprecioventa entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar articulo deposito" + e.getMessage());
		}
		  return "Articulodeposito eliminado correctamente";
	}

	@Override
	public Historicoprecioventa getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from historicoprecioventa ";
		sql += " where idhistoricoprecioventa = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Historicoprecioventa historico = new Historicoprecioventa();
		while (rs.next()) {
			historico.setIdhistoricoprecioventa(rs.getInt("idhistoricoprecioventa"));
			historico.setConcepto(rs.getString("concepto"));
			historico.setPreciocosto(rs.getDouble("preciocosto"));
			historico.setPrecioventa(rs.getDouble("precioventa"));
			historico.setPorcentaje(rs.getDouble("porcentaje"));
			historico.setFecha(rs.getDate("fecha"));
			Articuloprecioventadet det = new Articuloprecioventadet();
			det.setIdarticuloprecioventadet(rs.getInt("idarticuloprecioventadet"));
			historico.setArticuloprecioventadet(det);
			Usuario u = new Usuario();
			u.setIdusuario(rs.getInt("idusuario"));
			historico.setUsuario(u);

		}
		
		conn.close();
		
		return historico;
	}

	@Override
	public List<Historicoprecioventa> getAll() throws Exception {
		Connection conn = dataSource.getConnection();
		List<Historicoprecioventa> list = new ArrayList<>();
		try {
			String sql = " select * ";
			sql += " from historicoprecioventa ";
			sql += "order by fecha  ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Historicoprecioventa historico = new Historicoprecioventa();
				historico.setIdhistoricoprecioventa(rs.getInt("idhistoricoprecioventa"));
				historico.setConcepto(rs.getString("concepto"));
				historico.setPreciocosto(rs.getDouble("preciocosto"));
				historico.setPrecioventa(rs.getDouble("precioventa"));
				historico.setPorcentaje(rs.getDouble("porcentaje"));
				historico.setFecha(rs.getDate("fecha"));
				Articuloprecioventadet det = new Articuloprecioventadet();
				det.setIdarticuloprecioventadet(rs.getInt("idarticuloprecioventadet"));
				historico.setArticuloprecioventadet(det);
				Usuario u = new Usuario();
				u.setIdusuario(rs.getInt("idusuario"));
				historico.setUsuario(u);
				
				list.add(historico);
				
			}
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}

}
