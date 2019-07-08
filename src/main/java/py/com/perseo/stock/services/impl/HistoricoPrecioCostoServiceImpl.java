package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Historicopreciocosto;
import py.com.perseo.stock.services.HistoricoPrecioCostoService;

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
public class HistoricoPrecioCostoServiceImpl implements HistoricoPrecioCostoService{
	
	private static final Logger logger = LoggerFactory.getLogger(HistoricoPrecioCostoServiceImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;

	@Override
	public Historicopreciocosto add(Historicopreciocosto entity) throws Exception {
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
	public Historicopreciocosto update(Historicopreciocosto entity) throws Exception {
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
		Historicopreciocosto entity = getById(key);
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
	public Historicopreciocosto getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from historicopreciocosto ";
		sql += " where idhistoricopreciocosto = ?  ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Historicopreciocosto historico = new Historicopreciocosto();
		while (rs.next()) {
			historico.setIdhistoricopreciocosto(rs.getInt("idhistoricopreciocosto"));
			historico.setConcepto(rs.getString("concepto"));
			historico.setPreciocosto(rs.getDouble("preciocosto"));
			historico.setFecha(rs.getDate("fecha"));
			Articulo a = new Articulo();
			a.setIdarticulo(rs.getInt("idarticulo"));
			historico.setArticulo(a);
			Usuario u = new Usuario();
			u.setIdusuario(rs.getInt("idusuario"));
			historico.setUsuario(u);

		}
		
		conn.close();
		
		return historico;
	}

	@Override
	public List<Historicopreciocosto> getAll() throws Exception {
		
		Connection conn = dataSource.getConnection();
		List<Historicopreciocosto> list = new ArrayList<>();
		try {
			String sql = " select * ";
			sql += " from historicopreciocosto ";
			sql += "order by fecha  ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Historicopreciocosto historico = new Historicopreciocosto();
				historico.setIdhistoricopreciocosto(rs.getInt("idhistoricopreciocosto"));
				historico.setConcepto(rs.getString("concepto"));
				historico.setPreciocosto(rs.getDouble("preciocosto"));
				historico.setFecha(rs.getDate("fecha"));
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				historico.setArticulo(a);
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

	@Override
	public Double getPromedioCostoByArticulo(Articulo articulo) throws Exception {
		Connection conn = dataSource.getConnection();
		Double res = new Double(0);
		try {
			String sql = "select avg(preciocosto) as prom from historicopreciocosto where idarticulo = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, articulo.getIdarticulo());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {

				res = rs.getDouble("prom");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

}
