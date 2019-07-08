package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.stock.services.DepositoService;

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
public class DepositoServiceImpl implements DepositoService {

	private static final Logger logger = LoggerFactory.getLogger(DepositoServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Deposito getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.iddeposito, a.descripcion as deposito, a.estado, ";
		sql += " a.idempresa, b.nombre as empresa, a.idsucursal, ";
		sql += " c.nombre as sucursal ";
		sql += " from deposito a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " join sucursal c on c.idsucursal = a.idsucursal ";
		sql += " where a.idempresa = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Deposito deposito = new Deposito();
		while (rs.next()) {
			deposito.setIddeposito(rs.getInt("iddeposito"));
			deposito.setDescripcion(rs.getString("deposito"));
			deposito.setEstado(rs.getBoolean("estado"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setNombre(rs.getString("empresa"));
			deposito.setEmpresa(empresa);
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("sucursal"));
			deposito.setSucursal(sucursal);
		}
		
		conn.close();
		
		return deposito;
	}

	@Override
	public List<Deposito> getAll() throws Exception {

		Connection conn = dataSource.getConnection();
		List<Deposito> depositos = new ArrayList<Deposito>();
		
		String sql = " select a.iddeposito, a.descripcion as deposito, a.estado, ";
		sql += " a.idempresa, b.nombre as empresa, a.idsucursal, ";
		sql += " c.nombre as sucursal ";
		sql += " from deposito a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " join sucursal c on c.idsucursal = a.idsucursal ";
		sql += " order by a.iddeposito desc ";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Deposito deposito = new Deposito();
			deposito.setIddeposito(rs.getInt("iddeposito"));
			deposito.setDescripcion(rs.getString("deposito"));
			deposito.setEstado(rs.getBoolean("estado"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setNombre(rs.getString("empresa"));
			deposito.setEmpresa(empresa);
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("sucursal"));
			deposito.setSucursal(sucursal);
			
			depositos.add(deposito);
		}
		
		conn.close();
		
		return depositos;
		
	}

	@Override
	public Deposito add(Deposito entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar deposito " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Deposito update(Deposito entity) {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar deposito " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Deposito entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar deposito " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

	@Override
	public List<Deposito> getDepositoByEmpresa(Integer idEmpresa) throws Exception {
		
		Connection conn = dataSource.getConnection();
		List<Deposito> depositos = new ArrayList<Deposito>();
		
		String sql = " select a.iddeposito, a.descripcion as deposito, a.estado, ";
		sql += " a.idempresa, b.nombre as empresa, a.idsucursal, ";
		sql += " c.nombre as sucursal ";
		sql += " from deposito a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " join sucursal c on c.idsucursal = a.idsucursal ";
		sql += " where a.idempresa = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idEmpresa);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Deposito deposito = new Deposito();
			deposito.setIddeposito(rs.getInt("iddeposito"));
			deposito.setDescripcion(rs.getString("deposito"));
			deposito.setEstado(rs.getBoolean("estado"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setNombre(rs.getString("empresa"));
			deposito.setEmpresa(empresa);
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("sucursal"));
			deposito.setSucursal(sucursal);
			
			depositos.add(deposito);
		}
		
		conn.close();
		
		return depositos;
	}

	@Override
	public List<Deposito> getDepositoBySucursal(Integer idSucursal) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Deposito> depositos = new ArrayList<Deposito>();
		
		String sql = " select a.iddeposito, a.descripcion as deposito, a.estado, ";
		sql += " a.idempresa, b.nombre as empresa, a.idsucursal, ";
		sql += " c.nombre as sucursal ";
		sql += " from deposito a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " join sucursal c on c.idsucursal = a.idsucursal ";
		sql += " where a.idsucursal = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idSucursal);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Deposito deposito = new Deposito();
			deposito.setIddeposito(rs.getInt("iddeposito"));
			deposito.setDescripcion(rs.getString("deposito"));
			deposito.setEstado(rs.getBoolean("estado"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setNombre(rs.getString("empresa"));
			deposito.setEmpresa(empresa);
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("sucursal"));
			deposito.setSucursal(sucursal);
			
			depositos.add(deposito);
		}
		
		conn.close();
		
		return depositos;
	}

}