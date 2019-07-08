package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.stock.entities.Articulomovimiento;
import py.com.perseo.stock.entities.Articulotransferenciacab;
import py.com.perseo.stock.entities.Articulotransferenciadet;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.stock.services.ArticuloTransferenciaCabService;
import py.com.perseo.stock.services.ArticuloTransferenciaDetService;
import py.com.perseo.tesoreria.entities.Conceptomov;

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
public class ArticuloTransferenciaCabServiceImpl implements ArticuloTransferenciaCabService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloTransferenciaCabServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloTransferenciaDetService articuloTransferenciaDetService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Articulotransferenciacab add(Articulotransferenciacab entity) throws Exception {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar articulo transferencia " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Articulotransferenciacab update(Articulotransferenciacab entity) throws Exception {
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
		Articulotransferenciacab entity = getById(key);
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
	public Articulotransferenciacab getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		Articulotransferenciacab articulo = new Articulotransferenciacab();
		try {
			
			String sql = " select * ";
			sql += " from articulotransferenciacab ";
			sql += " where idarticulotransferenciacab = ?  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, key);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				articulo.setIdarticulotransferenciacab(rs.getInt("idarticulotransferenciacab"));
				articulo.setFecha(rs.getDate("fecha"));
				articulo.setEstado(rs.getBoolean("estado"));
				articulo.setCantidadtotal(rs.getInt("cantidadtotal"));
				Usuario u = new Usuario();
				u.setIdusuario(rs.getInt("idusuario"));
				articulo.setUsuario(u);
				Deposito dep = new Deposito();
				dep.setIddeposito(rs.getInt("iddepositoorigen"));
				articulo.setDepositoorigen(dep);
				Deposito dep1 = new Deposito();
				dep.setIddeposito(rs.getInt("iddepositodestino"));
				articulo.setDepositodestino(dep1);
				Empresa e = new Empresa();
				e.setIdempresa(rs.getInt("idempresa"));
				articulo.setEmpresa(e);
				
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articulo;
	}

	@Override
	public List<Articulotransferenciacab> getAll() throws Exception {
		Connection conn = dataSource.getConnection();
		List<Articulotransferenciacab> list = new ArrayList<>();
		try {
			
			String sql = " select * ";
			sql += " from articulotransferenciacab ";
			sql += " order by fecha ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Articulotransferenciacab articulo = new Articulotransferenciacab();
				articulo.setIdarticulotransferenciacab(rs.getInt("idarticulotransferenciacab"));
				articulo.setFecha(rs.getDate("fecha"));
				articulo.setEstado(rs.getBoolean("estado"));
				articulo.setCantidadtotal(rs.getInt("cantidadtotal"));
				Usuario u = new Usuario();
				u.setIdusuario(rs.getInt("idusuario"));
				articulo.setUsuario(u);
				Deposito dep = new Deposito();
				dep.setIddeposito(rs.getInt("iddepositoorigen"));
				articulo.setDepositoorigen(dep);
				Deposito dep1 = new Deposito();
				dep.setIddeposito(rs.getInt("iddepositodestino"));
				articulo.setDepositodestino(dep1);
				Empresa e = new Empresa();
				e.setIdempresa(rs.getInt("idempresa"));
				articulo.setEmpresa(e);

				list.add(articulo);
			}
			
			conn.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String transferirArticulos(Articulotransferenciacab articulotransferenciacab,
			List<Articulotransferenciadet> detalles, LoginData login) throws Exception {
		try {
			articulotransferenciacab.setFecha(new Date(System.currentTimeMillis()));
			articulotransferenciacab.setUsuario(login.getUsuario());
			articulotransferenciacab.setEmpresa(login.getEmpresa());
			articulotransferenciacab.setEstado(true);
			add(articulotransferenciacab);
			
			Long id_cab = usuarioService.getLastSecuenceFromName("articulotransferenciacab_idarticulotransferenciacab_seq"); 
			articulotransferenciacab.setIdarticulotransferenciacab(id_cab.intValue());
			
			for (Articulotransferenciadet element : detalles) {
				element.setArticulotransferenciacab(articulotransferenciacab);
				articuloTransferenciaDetService.add(element);
				
				// registrar la salida en articulo mov
				
				Articulomovimiento salida = new Articulomovimiento();
				salida.setArticulo(element.getArticulo());
				salida.setFecha(new Date(System.currentTimeMillis()));
				salida.setDeposito(articulotransferenciacab.getDepositoorigen());
				Conceptomov c = new Conceptomov();
				c.setIdconceptomov(13);	
				salida.setConceptomov(c);
				//salida.setSucursal(ad_origen.getSucursal());
				salida.setUsuario(login.getUsuario());
				salida.setCantidad(element.getCantidadrecibido().doubleValue());
				salida.setColumna("E");
				
				Articulomovimiento entrada = new Articulomovimiento();
				entrada.setArticulo(element.getArticulo());
				entrada.setFecha(new Date(System.currentTimeMillis()));
				entrada.setDeposito(articulotransferenciacab.getDepositodestino());
				Conceptomov c1 = new Conceptomov();
				c1.setIdconceptomov(14);	
				entrada.setConceptomov(c1);
			//	entrada.setSucursal(articulotransferenciacab.);
				entrada.setUsuario(login.getUsuario());
				entrada.setCantidad(element.getCantidadrecibido().doubleValue());
				entrada.setColumna("I");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	

}
