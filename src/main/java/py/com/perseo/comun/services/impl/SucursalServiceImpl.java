package py.com.perseo.comun.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.repositories.SucursalRepository;
import py.com.perseo.comun.services.SucursalService;
import py.com.perseo.exceptions.CustomMessageException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SucursalServiceImpl implements SucursalService {

	private static final Logger logger = LoggerFactory.getLogger(SucursalServiceImpl.class);

	DataSource dataSource;
	SucursalRepository sucursalRepository;
	
	@Autowired
	public SucursalServiceImpl(SucursalRepository sucursalRepository, DataSource dataSource) {
		this.dataSource = dataSource;
		this.sucursalRepository = sucursalRepository;
	}
	
	
	@Override
	public Sucursal getSucursalById(Integer idSucursal) throws Exception {
		
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.idsucursal, a.nombre, a.codigo, a.telefono1,  ";
		sql += " a.telefono2, a.direccion, a.descripcion, ";
		sql += " a.idempresa, a.ciudad, a.pais, a.matriz, a.idempresa, b.descripcion as empresa ";
		sql += " from sucursal a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " where a.idsucursal = ? ";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idSucursal);
		ResultSet rs = ps.executeQuery();
		
		Sucursal sucursal = new Sucursal();
		while (rs.next()) {
			logger.debug("Se encontro la sucursal " + rs.getInt("idsucursal"));
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("nombre"));
			sucursal.setCodigo(rs.getString("codigo"));
			sucursal.setDireccion(rs.getString("direccion"));
			sucursal.setTelefono1(rs.getString("telefono1"));
			sucursal.setTelefono2(rs.getString("telefono2"));
			sucursal.setDescripcion(rs.getString("descripcion"));
			sucursal.setCiudad(rs.getString("ciudad"));
			sucursal.setPais(rs.getString("pais"));
			sucursal.setMatriz(rs.getBoolean("matriz"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setDescripcion(rs.getString("empresa"));
			sucursal.setEmpresa(empresa);
		}
		conn.close();
		return sucursal;
	}

	@Override
	public List<Sucursal> getAllSucursales() throws Exception {
		List<Sucursal> ret = new ArrayList<>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.idsucursal, a.nombre, a.codigo, a.telefono1,  ";
		sql += " a.telefono2, a.direccion, a.descripcion, ";
		sql += " a.idempresa, a.ciudad, a.pais, a.matriz, a.idempresa, b.descripcion as empresa ";
		sql += " from sucursal a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("nombre"));
			sucursal.setCodigo(rs.getString("codigo"));
			sucursal.setDireccion(rs.getString("direccion"));
			sucursal.setTelefono1(rs.getString("telefono1"));
			sucursal.setTelefono2(rs.getString("telefono2"));
			sucursal.setDescripcion(rs.getString("descripcion"));
			sucursal.setCiudad(rs.getString("ciudad"));
			sucursal.setPais(rs.getString("pais"));
			sucursal.setMatriz(rs.getBoolean("matriz"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setDescripcion(rs.getString("empresa"));
			sucursal.setEmpresa(empresa);
			ret.add(sucursal);
		}
		conn.close();
		return ret;
	}

	@Override
	public Sucursal addSucursal(Sucursal sucursal) {
		return sucursalRepository.save(sucursal);
	}

	@Override
	public Sucursal update(Sucursal sucursal) throws Exception {
		Sucursal existeSucursal = sucursalRepository.findOne(sucursal.getIdsucursal());
		if (existeSucursal != null) {
			sucursalRepository.save(sucursal);
		}else{
			throw new CustomMessageException("No se encuentra la sucursal");
		}
		return sucursal;
	}

	@Override
	public String delete(Integer idSucursal) throws Exception {
		Sucursal sucursal = sucursalRepository.findOne(idSucursal);
		if (sucursal != null) {
			try {
				sucursalRepository.delete(sucursal);
			} catch (Exception e) {
				throw new CustomMessageException("No se puede eliminar la sucursal, error de integridad referencial");
			}
		}else{
			throw new CustomMessageException("No se encuentra el cliente");
		}
		return "Sucursal eliminado correctamente";
	}

	@Override
	public List<Sucursal> getSucursalesByEmpresa(Integer idEmpresa) throws Exception {
		List<Sucursal> ret = new ArrayList<>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = " select a.idsucursal, a.nombre, a.codigo, a.telefono1,  ";
		sql += " a.telefono2, a.direccion, a.descripcion, ";
		sql += " a.idempresa, a.ciudad, a.pais, a.matriz, a.idempresa, b.descripcion as empresa ";
		sql += " from sucursal a ";
		sql += " join empresa b on b.idempresa = a.idempresa ";
		sql += " where a.idempresa = ? ";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, idEmpresa);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Sucursal sucursal = new Sucursal();
			sucursal.setIdsucursal(rs.getInt("idsucursal"));
			sucursal.setNombre(rs.getString("nombre"));
			sucursal.setCodigo(rs.getString("codigo"));
			sucursal.setTelefono1(rs.getString("telefono1"));
			sucursal.setCiudad(rs.getString("ciudad"));
			sucursal.setPais(rs.getString("pais"));
			sucursal.setMatriz(rs.getBoolean("matriz"));
			Empresa empresa = new Empresa();
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setDescripcion(rs.getString("empresa"));
			sucursal.setEmpresa(empresa);
			ret.add(sucursal);
		}
		conn.close();
		return ret;
	}

}