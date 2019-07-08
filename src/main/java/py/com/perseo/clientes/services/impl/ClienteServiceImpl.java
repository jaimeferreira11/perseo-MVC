package py.com.perseo.clientes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.clientes.repositories.ClienteRepository;
import py.com.perseo.clientes.services.ClienteService;
import py.com.perseo.comun.entities.*;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.stock.entities.Articuloprecioventacab;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

	DataSource dataSource;
	
	ClienteRepository clienteRepository;
	
	@Autowired
	public ClienteServiceImpl(DataSource dataSource, EntityManager entityManager, ClienteRepository clienteRepository) {
		this.dataSource = dataSource;
		this.clienteRepository = clienteRepository;
	}
	
	@Override
	public List<Cliente> getAllClientes() throws Exception {
		Connection conn = dataSource.getConnection();
		List<Cliente> ret = new ArrayList<Cliente>();
		
		String sSql = " SELECT a.idcliente, a.nombreapellido, a.nrodoc, a.codtipodoc, a.idarticuloprecioventacab, ";
		sSql += " a.sexo, a.lugarnacimiento, a.nacionalidad, a.direccion, a.telefono,  ";
		sSql += " a.iddistrito, b.descripcion as distrito, a.estadocivil, a.lineacredito, ";
		sSql += " a.iddepartamento, c.descripcion as departamento, a.idbarrio, d.descripcion as barrio, ";
		sSql += " a.fecnacimiento, a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
		sSql += " a.idempresa, f.descripcion as empresa, a.idsucursal, g.nombre as sucursal, ";
		sSql += " a.correo, a.web ";
		sSql += " from cliente a  ";
		sSql += " left join distrito b on b.iddistrito = a.iddistrito  ";
		sSql += " left join departamento c on c.iddepartamento = a.iddepartamento ";
		sSql += " left join barrio d on d.idbarrio = a.idbarrio ";
		sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
		sSql += " left join empresa f on f.idempresa = a.idempresa ";
		sSql += " left join sucursal g on g.idsucursal = a.idsucursal ";
		sSql += " order by a.idcliente desc limit 10 ";

		PreparedStatement ps = conn.prepareStatement(sSql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Cliente cliente = new Cliente();
			cliente.setIdcliente(rs.getInt("idcliente"));
			cliente.setNombreapellido(rs.getString("nombreapellido"));
			cliente.setNrodoc(rs.getString("nrodoc"));
			Tipodocumento tipodoc = new Tipodocumento();
			tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
			cliente.setTipodocumento(tipodoc);
			cliente.setSexo(rs.getString("sexo"));
			cliente.setLugarnacimiento(rs.getString("lugarnacimiento"));
			cliente.setNacionalidad(rs.getString("nacionalidad"));
			cliente.setDireccion(rs.getString("direccion"));
			cliente.setTelefono(rs.getString("telefono"));
			cliente.setFecnacimiento(rs.getDate("fecnacimiento"));
			cliente.setEstadocivil(rs.getString("estadocivil"));
			cliente.setLineacredito(rs.getDouble("lineacredito"));
			cliente.setEstado(rs.getBoolean("estado"));
			cliente.setCorreo(rs.getString("correo"));
			cliente.setWeb(rs.getString("web"));
			Articuloprecioventacab apvcab = new Articuloprecioventacab();
			apvcab.setIdarticuloprecioventacab(rs.getInt("idarticuloprecioventacab"));
			cliente.setArticuloprecioventacab(apvcab);
			
			if (rs.getInt("idempresa") > 0) {
				Empresa empresa = new Empresa();
				empresa.setIdempresa(rs.getInt("idempresa"));
				empresa.setDescripcion(rs.getString("empresa"));
				cliente.setEmpresa(empresa);
			}
			
			if (rs.getInt("idsucursal") > 0) {
				Sucursal sucursal = new Sucursal();
				sucursal.setIdsucursal(rs.getInt("idsucursal"));
				sucursal.setNombre(rs.getString("sucursal"));
				cliente.setSucursal(sucursal);
			}
			
			String sql_oficial = " select max(b.idsolicitud) as idsolicitud, c.idusuario, c.nombreapellido as oficial ";
			sql_oficial += " from cliente a ";
			sql_oficial += " join solicitud b on b.idcliente = a.idcliente ";
			sql_oficial += " join usuario c on c.idusuario = b.idoficial ";
			sql_oficial += " where a.idcliente = ? ";
			sql_oficial += " group by c.idusuario, c.nombreapellido, b.idsolicitud ";
			sql_oficial += " order by b.idsolicitud ";

			PreparedStatement ps_oficial = conn.prepareStatement(sql_oficial);
			ps_oficial.setInt(1, rs.getInt("idcliente"));
			ResultSet rs_oficial = ps_oficial.executeQuery();

			while (rs_oficial.next()) {
				Usuario oficial = new Usuario();
				oficial.setIdusuario(rs_oficial.getInt("idusuario"));
				oficial.setNombreapellido(rs_oficial.getString("oficial"));
				cliente.setOficial(oficial);
			}

			if (rs.getInt("idplancuenta") > 0) {
				Plancuenta pc = new Plancuenta();
				pc.setIdplancuenta(rs.getInt("idplancuenta"));
				pc.setDescripcion(rs.getString("descplancuenta"));
				cliente.setPlancuenta(pc);
			}
			
			if (rs.getInt("iddepartamento") > -1) {
				Departamento departamento = new Departamento();
				departamento.setIddepartamento(rs.getInt("iddepartamento"));
				departamento.setDescripcion(rs.getString("departamento"));
				cliente.setDepartamento(departamento);
			}

			if (rs.getInt("iddistrito") > -1) {
				Distrito distrito = new Distrito();
				distrito.setIddistrito(rs.getInt("iddistrito"));
				distrito.setDescripcion(rs.getString("distrito"));
				cliente.setDistrito(distrito);
			}

			if (rs.getInt("idbarrio") > -1) {
				Barrio barrio = new Barrio();
				barrio.setIdbarrio(rs.getInt("idbarrio"));
				barrio.setDescripcion(rs.getString("barrio"));
				cliente.setBarrio(barrio);
			}

			ret.add(cliente);
		}
		
		conn.close();
		
		return ret;
	}

	@Override
	public Cliente getClienteById(Integer idCliente) throws Exception {
		Connection conn = dataSource.getConnection();
		Cliente cliente = new Cliente();
		
		String sSql = " SELECT a.idcliente, a.nombreapellido, a.nrodoc, a.codtipodoc, a.idarticuloprecioventacab, ";
		sSql += " a.sexo, a.lugarnacimiento, a.nacionalidad, a.direccion, a.telefono,  ";
		sSql += " a.iddistrito, b.descripcion as distrito, a.estadocivil, a.lineacredito, ";
		sSql += " a.iddepartamento, c.descripcion as departamento, a.idbarrio, d.descripcion as barrio, ";
		sSql += " a.fecnacimiento, a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
		sSql += " a.idempresa, f.descripcion as empresa, a.idsucursal, g.nombre as sucursal, ";
		sSql += " a.correo, a.web ";
		sSql += " from cliente a  ";
		sSql += " left join distrito b on b.iddistrito = a.iddistrito  ";
		sSql += " left join departamento c on c.iddepartamento = a.iddepartamento ";
		sSql += " left join barrio d on d.idbarrio = a.idbarrio ";
		sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
		sSql += " left join empresa f on f.idempresa = a.idempresa ";
		sSql += " left join sucursal g on g.idsucursal = a.idsucursal ";
		sSql += " where a.idcliente = ? ";

		PreparedStatement ps = conn.prepareStatement(sSql);
		ps.setInt(1, idCliente);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			cliente.setIdcliente(rs.getInt("idcliente"));
			cliente.setNombreapellido(rs.getString("nombreapellido"));
			cliente.setNrodoc(rs.getString("nrodoc"));
			Tipodocumento tipodoc = new Tipodocumento();
			tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
			cliente.setTipodocumento(tipodoc);
			cliente.setSexo(rs.getString("sexo"));
			cliente.setLugarnacimiento(rs.getString("lugarnacimiento"));
			cliente.setNacionalidad(rs.getString("nacionalidad"));
			cliente.setDireccion(rs.getString("direccion"));
			cliente.setTelefono(rs.getString("telefono"));
			cliente.setFecnacimiento(rs.getDate("fecnacimiento"));
			cliente.setEstadocivil(rs.getString("estadocivil"));
			cliente.setLineacredito(rs.getDouble("lineacredito"));
			cliente.setEstado(rs.getBoolean("estado"));
			cliente.setCorreo(rs.getString("correo"));
			cliente.setWeb(rs.getString("web"));
			Articuloprecioventacab apvcab = new Articuloprecioventacab();
			apvcab.setIdarticuloprecioventacab(rs.getInt("idarticuloprecioventacab"));
			cliente.setArticuloprecioventacab(apvcab);
			
			if (rs.getInt("idempresa") > 0) {
				Empresa empresa = new Empresa();
				empresa.setIdempresa(rs.getInt("idempresa"));
				empresa.setDescripcion(rs.getString("empresa"));
				cliente.setEmpresa(empresa);
			}
			
			if (rs.getInt("idsucursal") > 0) {
				Sucursal sucursal = new Sucursal();
				sucursal.setIdsucursal(rs.getInt("idsucursal"));
				sucursal.setNombre(rs.getString("sucursal"));
				cliente.setSucursal(sucursal);
			}
			
			String sql_oficial = " select max(b.idsolicitud) as idsolicitud, c.idusuario, c.nombreapellido as oficial ";
			sql_oficial += " from cliente a ";
			sql_oficial += " join solicitud b on b.idcliente = a.idcliente ";
			sql_oficial += " join usuario c on c.idusuario = b.idoficial ";
			sql_oficial += " where a.idcliente = ? ";
			sql_oficial += " group by c.idusuario, c.nombreapellido, b.idsolicitud ";
			sql_oficial += " order by b.idsolicitud ";

			PreparedStatement ps_oficial = conn.prepareStatement(sql_oficial);
			ps_oficial.setInt(1, rs.getInt("idcliente"));
			ResultSet rs_oficial = ps_oficial.executeQuery();

			while (rs_oficial.next()) {
				Usuario oficial = new Usuario();
				oficial.setIdusuario(rs_oficial.getInt("idusuario"));
				oficial.setNombreapellido(rs_oficial.getString("oficial"));
				cliente.setOficial(oficial);
			}

			if (rs.getInt("idplancuenta") > 0) {
				Plancuenta pc = new Plancuenta();
				pc.setIdplancuenta(rs.getInt("idplancuenta"));
				pc.setDescripcion(rs.getString("descplancuenta"));
				cliente.setPlancuenta(pc);
			}
			
			if (rs.getInt("iddepartamento") > -1) {
				Departamento departamento = new Departamento();
				departamento.setIddepartamento(rs.getInt("iddepartamento"));
				departamento.setDescripcion(rs.getString("departamento"));
				cliente.setDepartamento(departamento);
			}

			if (rs.getInt("iddistrito") > -1) {
				Distrito distrito = new Distrito();
				distrito.setIddistrito(rs.getInt("iddistrito"));
				distrito.setDescripcion(rs.getString("distrito"));
				cliente.setDistrito(distrito);
			}

			if (rs.getInt("idbarrio") > -1) {
				Barrio barrio = new Barrio();
				barrio.setIdbarrio(rs.getInt("idbarrio"));
				barrio.setDescripcion(rs.getString("barrio"));
				cliente.setBarrio(barrio);
			}

		}
		
		conn.close();
		
		return cliente;
	}

	@Override
	public Cliente updateCliente(Cliente cliente) throws Exception {
		
		Cliente existeCliente = clienteRepository.findOne(cliente.getIdcliente());
		if (existeCliente != null) {
			clienteRepository.save(cliente);
		}else{
			throw new CustomMessageException("No se encuentra el cliente");
		}
		
		return cliente;
	}

	@Override
	public String deleteCliente(Integer idCliente) throws Exception {
		Cliente cliente = clienteRepository.findOne(idCliente);
		if (cliente != null) {
			try {
				clienteRepository.delete(cliente);
			} catch (Exception e) {
				//error de integridad
				cliente.setEstado(false);
				clienteRepository.save(cliente);
			}
		}else{
			throw new CustomMessageException("No se encuentra el cliente");
		}
		return "Cliente eliminado correctamente";
	}

	@Override
	public Cliente addCliente(Cliente cliente) throws Exception {
		cliente.setFechaalta(new Timestamp(System.currentTimeMillis()));
		return clienteRepository.save(cliente);
	}

	@Override
	public List<Cliente> getClientesByParams(String tipodoc2, String nrodoc, String nombres) throws Exception {
		
		Connection conn = dataSource.getConnection();
		List<Cliente> ret = new ArrayList<Cliente>();
		
		String sSql = " SELECT a.idcliente, a.nombreapellido, a.nrodoc, a.codtipodoc, a.idarticuloprecioventacab, ";
		sSql += " a.sexo, a.lugarnacimiento, a.nacionalidad, a.direccion, a.telefono,  ";
		sSql += " a.iddistrito, b.descripcion as distrito, a.estadocivil, a.lineacredito, ";
		sSql += " a.iddepartamento, c.descripcion as departamento, a.idbarrio, d.descripcion as barrio, ";
		sSql += " a.fecnacimiento, a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
		sSql += " a.idempresa, f.descripcion as empresa, a.idsucursal, g.nombre as sucursal, ";
		sSql += " a.correo, a.web ";
		sSql += " from cliente a  ";
		sSql += " left join distrito b on b.iddistrito = a.iddistrito  ";
		sSql += " left join departamento c on c.iddepartamento = a.iddepartamento ";
		sSql += " left join barrio d on d.idbarrio = a.idbarrio ";
		sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
		sSql += " left join empresa f on f.idempresa = a.idempresa ";
		sSql += " left join sucursal g on g.idsucursal = a.idsucursal ";
		sSql += " where (codtipodoc = ? and nrodoc = ?) or upper(nombreapellido) like upper('%"+nombres.trim().toUpperCase()+"%') ";
		sSql += " order by a.idcliente desc limit 10 ";
		
		PreparedStatement ps = conn.prepareStatement(sSql);
		ps.setString(1, tipodoc2.trim());
		ps.setString(2, nrodoc.trim());

		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Cliente cliente = new Cliente();
			cliente.setIdcliente(rs.getInt("idcliente"));
			cliente.setNombreapellido(rs.getString("nombreapellido"));
			cliente.setNrodoc(rs.getString("nrodoc"));
			Tipodocumento tipodoc = new Tipodocumento();
			tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
			cliente.setTipodocumento(tipodoc);
			cliente.setSexo(rs.getString("sexo"));
			cliente.setLugarnacimiento(rs.getString("lugarnacimiento"));
			cliente.setNacionalidad(rs.getString("nacionalidad"));
			cliente.setDireccion(rs.getString("direccion"));
			cliente.setTelefono(rs.getString("telefono"));
			cliente.setFecnacimiento(rs.getDate("fecnacimiento"));
			cliente.setEstadocivil(rs.getString("estadocivil"));
			cliente.setLineacredito(rs.getDouble("lineacredito"));
			cliente.setEstado(rs.getBoolean("estado"));
			cliente.setCorreo(rs.getString("correo"));
			cliente.setWeb(rs.getString("web"));
			Articuloprecioventacab apvcab = new Articuloprecioventacab();
			apvcab.setIdarticuloprecioventacab(rs.getInt("idarticuloprecioventacab"));
			cliente.setArticuloprecioventacab(apvcab);
			
			if (rs.getInt("idempresa") > 0) {
				Empresa empresa = new Empresa();
				empresa.setIdempresa(rs.getInt("idempresa"));
				empresa.setDescripcion(rs.getString("empresa"));
				cliente.setEmpresa(empresa);
			}
			
			if (rs.getInt("idsucursal") > 0) {
				Sucursal sucursal = new Sucursal();
				sucursal.setIdsucursal(rs.getInt("idsucursal"));
				sucursal.setNombre(rs.getString("sucursal"));
				cliente.setSucursal(sucursal);
			}
			
			String sql_oficial = " select max(b.idsolicitud) as idsolicitud, c.idusuario, c.nombreapellido as oficial ";
			sql_oficial += " from cliente a ";
			sql_oficial += " join solicitud b on b.idcliente = a.idcliente ";
			sql_oficial += " join usuario c on c.idusuario = b.idoficial ";
			sql_oficial += " where a.idcliente = ? ";
			sql_oficial += " group by c.idusuario, c.nombreapellido, b.idsolicitud ";
			sql_oficial += " order by b.idsolicitud ";

			PreparedStatement ps_oficial = conn.prepareStatement(sql_oficial);
			ps_oficial.setInt(1, rs.getInt("idcliente"));
			ResultSet rs_oficial = ps_oficial.executeQuery();

			while (rs_oficial.next()) {
				Usuario oficial = new Usuario();
				oficial.setIdusuario(rs_oficial.getInt("idusuario"));
				oficial.setNombreapellido(rs_oficial.getString("oficial"));
				cliente.setOficial(oficial);
			}

			if (rs.getInt("idplancuenta") > 0) {
				Plancuenta pc = new Plancuenta();
				pc.setIdplancuenta(rs.getInt("idplancuenta"));
				pc.setDescripcion(rs.getString("descplancuenta"));
				cliente.setPlancuenta(pc);
			}
			
			if (rs.getInt("iddepartamento") > -1) {
				Departamento departamento = new Departamento();
				departamento.setIddepartamento(rs.getInt("iddepartamento"));
				departamento.setDescripcion(rs.getString("departamento"));
				cliente.setDepartamento(departamento);
			}

			if (rs.getInt("iddistrito") > -1) {
				Distrito distrito = new Distrito();
				distrito.setIddistrito(rs.getInt("iddistrito"));
				distrito.setDescripcion(rs.getString("distrito"));
				cliente.setDistrito(distrito);
			}

			if (rs.getInt("idbarrio") > -1) {
				Barrio barrio = new Barrio();
				barrio.setIdbarrio(rs.getInt("idbarrio"));
				barrio.setDescripcion(rs.getString("barrio"));
				cliente.setBarrio(barrio);
			}

			ret.add(cliente);
		}
		
		conn.close();
		
		return ret;
	}

}
