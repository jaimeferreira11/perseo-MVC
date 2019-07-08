package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulodeposito;
import py.com.perseo.stock.entities.Articulomovimiento;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.ArticuloMovimientoService;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.venta.entities.Ventacab;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticuloMovimientoServiceImpl implements ArticuloMovimientoService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloMovimientoServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloDepositoService articuloDepositoService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Articulomovimiento add(Articulomovimiento entity) throws Exception {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar articulo deposito " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Articulomovimiento update(Articulomovimiento entity) throws Exception {
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
		Articulomovimiento entity = getById(key);
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
	public Articulomovimiento getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		Articulomovimiento articulo = new Articulomovimiento();
		try {
			
			String sql = " select * ";
			sql += " from articulomovimiento ";
			sql += " where idarticulomovimiento = ?  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, key);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				articulo.setIdarticulomovimiento(rs.getInt("idarticulomovimiento"));
				articulo.setFecha(rs.getDate("fecha"));
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setArticulo(a);
				Deposito dep = new Deposito();
				dep.setIddeposito(rs.getInt("iddeposito"));
				articulo.setDeposito(dep);
				Conceptomov con = new Conceptomov();
				articulo.setConceptomov(con);
				Sucursal s = new Sucursal();
				s.setIdsucursal(rs.getInt("idsucursal"));
				articulo.setSucursal(s);
				Usuario u = new Usuario();
				u.setIdusuario(rs.getInt("idusuario"));
				articulo.setUsuario(u);
				Ventacab cab = new Ventacab();
				cab.setIdventacab(rs.getInt("idvantacab"));
				articulo.setVentacab(cab);
				Compracab compra = new Compracab();
				compra.setIdcompracab(rs.getInt("idcompracab"));
				articulo.setCompracab(compra);
				articulo.setCantidad(rs.getDouble("cantidad"));
				articulo.setColumna(rs.getString("columna"));
				


			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articulo;
	}

	@Override
	public List<Articulomovimiento> getAll() throws Exception {
		Connection conn = dataSource.getConnection();
		List<Articulomovimiento> list = new ArrayList<>();
		try {
			
			String sql = " select * ";
			sql += " from articulomovimiento ";
			sql += " order by fecha ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Articulomovimiento articulo = new Articulomovimiento();
				articulo.setIdarticulomovimiento(rs.getInt("idarticulomovimiento"));
				articulo.setFecha(rs.getDate("fecha"));
				Articulo a = new Articulo();
				a.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setArticulo(a);
				Deposito dep = new Deposito();
				dep.setIddeposito(rs.getInt("iddeposito"));
				articulo.setDeposito(dep);
				Conceptomov con = new Conceptomov();
				articulo.setConceptomov(con);
				Sucursal s = new Sucursal();
				s.setIdsucursal(rs.getInt("idsucursal"));
				articulo.setSucursal(s);
				Usuario u = new Usuario();
				u.setIdusuario(rs.getInt("idusuario"));
				articulo.setUsuario(u);
				Ventacab cab = new Ventacab();
				cab.setIdventacab(rs.getInt("idvantacab"));
				articulo.setVentacab(cab);
				Compracab compra = new Compracab();
				compra.setIdcompracab(rs.getInt("idcompracab"));
				articulo.setCompracab(compra);
				articulo.setCantidad(rs.getDouble("cantidad"));
				articulo.setColumna(rs.getString("columna"));

				list.add(articulo);
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String ajusteStock(Articulomovimiento articulomovimiento, Usuario usuario) throws Exception {
		
		try {
		articulomovimiento.setFecha(new Date(System.currentTimeMillis()));
		articulomovimiento.setUsuario(usuario);
		add(articulomovimiento);
		
		Articulodeposito ad = articuloDepositoService.getByArticuloAndDeposito(articulomovimiento.getArticulo().getIdarticulo(),
																			   articulomovimiento.getDeposito().getIddeposito());
		Double res = new Double(0);
		if(articulomovimiento.getColumna().equals("I")){
			res =ad.getCantidad() + articulomovimiento.getCantidad();
		}else{
			res =ad.getCantidad() - articulomovimiento.getCantidad();
		}
		//actulizar el stock
		String sql = " update articulodeposito  set cantidad = ?  " ;
		sql+= " where idarticulo = ?  and iddeposito = ?";
		jdbcTemplate.update(sql,res, articulomovimiento.getArticulo().getIdarticulo(), articulomovimiento.getDeposito().getIddeposito());
		
		add(articulomovimiento);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "Ajuste realizado";
	}

}
