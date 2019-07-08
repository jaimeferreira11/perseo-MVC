package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.ArticuloPrecioVentadetService;
import py.com.perseo.stock.services.ArticuloService;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

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
public class ArticuloServiceImpl implements ArticuloService {

	private static final Logger logger = LoggerFactory.getLogger(ArticuloServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloDepositoService articuloDepositoService;
	
	@Autowired
	ArticuloPrecioVentadetService articuloPrecioVentadetService;
	
	
	@Override
	public Articulo getById(Integer key) throws Exception  {
		
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.idarticulo, a.descripcion as articulo, a.codigobarra, a.estado, a.idmarca, a.idfamilia, a.idunidadmedida, a.idlineaarticulo, a.preciocosto, ";
		sql += " a.idtipoimpuesto, c.descripcion tipoimpuesto ";
		sql += " from articulo a ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = a.idtipoimpuesto ";
		sql += " where a.idarticulo = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key );
		
		ResultSet rs = ps.executeQuery();
		
		Articulo articulo = new Articulo();
		while (rs.next()) {
		
			articulo.setIdarticulo(rs.getInt("idarticulo"));
			articulo.setDescripcion(rs.getString("articulo"));
			articulo.setCodigobarra(rs.getString("codigobarra"));
			articulo.setEstado(rs.getBoolean("estado"));
			Tipoimpuesto tipoimpuesto =  new Tipoimpuesto();
			tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
			tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
			articulo.setTipoimpuesto(tipoimpuesto);
			articulo.setIdfamilia(rs.getInt("idfamilia"));
			articulo.setIdunidadmedida(rs.getInt("idunidadmedida"));
			articulo.setIdmarca(rs.getInt("idmarca"));
			articulo.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			articulo.setPreciocosto(rs.getDouble("preciocosto"));
		}
		
		conn.close();
		
		return articulo;
	}

	@Override
	public List<Articulo> getAll() throws Exception {
		
		Connection conn = dataSource.getConnection();
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		String sql = " select a.idarticulo, a.descripcion as articulo, a.codigobarra, a.estado, a.idmarca, a.idfamilia, a.idunidadmedida, a.idlineaarticulo,a.preciocosto, ";
		sql += " a.idtipoimpuesto, c.descripcion tipoimpuesto ";
		sql += " from articulo a ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = a.idtipoimpuesto ";
		sql += " order by a.idarticulo desc ";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Articulo articulo = new Articulo();
			articulo.setIdarticulo(rs.getInt("idarticulo"));
			articulo.setDescripcion(rs.getString("articulo"));
			articulo.setCodigobarra(rs.getString("codigobarra"));
			articulo.setEstado(rs.getBoolean("estado"));
			Tipoimpuesto tipoimpuesto =  new Tipoimpuesto();
			tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
			tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
			articulo.setTipoimpuesto(tipoimpuesto);
			articulo.setIdfamilia(rs.getInt("idfamilia"));
			articulo.setIdunidadmedida(rs.getInt("idunidadmedida"));
			articulo.setIdmarca(rs.getInt("idmarca"));
			articulo.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			articulo.setPreciocosto(rs.getDouble("preciocosto"));
			articulos.add(articulo);
		}
		
		if (articulos.isEmpty()) {
			articulos = null;
		}
		
		conn.close();
		
		return articulos;
	}

	@Override
	public Articulo add(Articulo entity) throws Exception{
		try {
			entity.setCodmoneda(1);
			em.persist(entity);
			em.flush();
			em.refresh(entity);

			articuloDepositoService.addArticuloInAllDeposito(entity);	
			articuloPrecioVentadetService.setPrecioArticulo(entity, 1, new Usuario());
			
			
			Articulo art = new Articulo();
			art.setIdarticulo(entity.getIdarticulo());
			art.setDescripcion(entity.getDescripcion());
			
			return art;
		} catch (Exception ex) {
			logger.info("Error al agregar articulo " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Articulo update(Articulo entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar articulo " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Articulo entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar articulo " + e.getMessage());
		}
		  return "Articulo eliminado correctamente";
	}

	@Override
	public List<Articulo> getArticulosByEmpresa(Integer idEmpresa) throws Exception {
		
		Connection conn = dataSource.getConnection();
		List<Articulo> articulos = new ArrayList<Articulo>();
		try{
			String sql = " select a.idarticulo, a.descripcion as articulo, a.codigobarra, a.estado, a.idmarca, a.idfamilia, a.idunidadmedida, a.idlineaarticulo,a.preciocosto, ";
			sql += " a.idtipoimpuesto, c.descripcion tipoimpuesto ";
			sql += " from articulo a ";
			sql += " join tipoimpuesto c on c.idtipoimpuesto = a.idtipoimpuesto ";
			sql += " where a.idempresa = ? ";
			sql += " order by a.idarticulo desc ";
			PreparedStatement ps = conn.prepareStatement(sql, idEmpresa);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Articulo articulo = new Articulo();
				articulo.setIdarticulo(rs.getInt("idarticulo"));
				articulo.setDescripcion(rs.getString("articulo"));
				articulo.setCodigobarra(rs.getString("codigobarra"));
				articulo.setEstado(rs.getBoolean("estado"));
				Tipoimpuesto tipoimpuesto =  new Tipoimpuesto();
				tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
				tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
				articulo.setTipoimpuesto(tipoimpuesto);
				articulo.setIdfamilia(rs.getInt("idfamilia"));
				articulo.setIdunidadmedida(rs.getInt("idunidadmedida"));
				articulo.setIdmarca(rs.getInt("idmarca"));
				articulo.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
				articulo.setPreciocosto(rs.getDouble("preciocosto"));
				articulos.add(articulo);
			}
			
			if (articulos.isEmpty()) {
				articulos = null;
			}
			
			conn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return articulos;
	}

	@Override
	public List<Articulo> getArticulosByParams(String parametro, Boolean all, LoginData login) throws Exception {
		String val = parametro;
		String firstLetter = "";

		try {
			firstLetter = val.substring(0, 1);
		} catch (StringIndexOutOfBoundsException e) {
			throw new Exception("Debe cargar un valor");
		}
		double d = 0D;
		boolean num = true;
		try {
			d = Double.parseDouble(firstLetter);
		} catch (NumberFormatException nfe) {
			num = false;
		}
	
		Connection conn = dataSource.getConnection();
		List<Articulo> articulos = new ArrayList<Articulo>();
		
		String sql = " select a.idarticulo, a.descripcion as articulo, a.codigobarra, a.estado, a.idmarca, a.idfamilia, a.idunidadmedida, a.preciocosto, ";
		sql += " a.idtipoimpuesto, a.idlineaarticulo,  c.descripcion tipoimpuesto ";
		sql += " from articulo a ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = a.idtipoimpuesto ";


		PreparedStatement ps;
		
		if (num) {

			sql += " where a.codigobarra = ? ";
			if(!all){				
				sql += " and a.estado = true ";
			}
			sql += " order by a.idarticulo desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, parametro);
			
		} else {
			sql += " where upper(a.descripcion) like upper('%"+parametro+"%') ";
			if(!all){				
				sql += " and a.estado = true ";
			}
			sql += " order by a.idarticulo desc ";
			ps = conn.prepareStatement(sql);
			//ps.setString(1, parametro );
		}
		
		

		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Articulo articulo = new Articulo();
			articulo.setIdarticulo(rs.getInt("idarticulo"));
			articulo.setDescripcion(rs.getString("articulo"));
			articulo.setCodigobarra(rs.getString("codigobarra"));
			articulo.setEstado(rs.getBoolean("estado"));
			Tipoimpuesto tipoimpuesto =  new Tipoimpuesto();
			tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
			tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
			articulo.setTipoimpuesto(tipoimpuesto);
			articulo.setIdfamilia(rs.getInt("idfamilia"));
			articulo.setIdunidadmedida(rs.getInt("idunidadmedida"));
			articulo.setIdmarca(rs.getInt("idmarca"));
			articulo.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			articulo.setPreciocosto(rs.getDouble("preciocosto"));
			articulos.add(articulo);
		}
		
		conn.close();
		return articulos;
	}

	@Override
	public List<Articulo> getArticulosByFamilia(Integer idfamilia) throws Exception {
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.idarticulo, a.descripcion as articulo, a.codigobarra, a.estado, a.idmarca, a.idfamilia, a.idunidadmedida, a.idlineaarticulo, a.preciocosto, ";
		sql += " a.idtipoimpuesto, c.descripcion tipoimpuesto ";
		sql += " from articulo a ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = a.idtipoimpuesto ";
		sql += " where a.idfamilia = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idfamilia );
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Articulo articulo = new Articulo();
		
			articulo.setIdarticulo(rs.getInt("idarticulo"));
			articulo.setDescripcion(rs.getString("articulo"));
			articulo.setCodigobarra(rs.getString("codigobarra"));
			articulo.setEstado(rs.getBoolean("estado"));
			Tipoimpuesto tipoimpuesto =  new Tipoimpuesto();
			tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
			tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
			articulo.setTipoimpuesto(tipoimpuesto);
			articulo.setIdfamilia(rs.getInt("idfamilia"));
			articulo.setIdunidadmedida(rs.getInt("idunidadmedida"));
			articulo.setIdmarca(rs.getInt("idmarca"));
			articulo.setIdlineaarticulo(rs.getInt("idlineaarticulo"));
			articulo.setPreciocosto(rs.getDouble("preciocosto"));
			
			articulos.add(articulo);
		}
		
		conn.close();
		
		return articulos;
	}
	
	
	

	

}