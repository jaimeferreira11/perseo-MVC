package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.clientes.repositories.ClienteRepository;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Tipodocumento;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.tesoreria.entities.Proveedor;
import py.com.perseo.tesoreria.services.ProveedorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    ClienteRepository clienteRepository;

    @Autowired
    public ProveedorServiceImpl(DataSource dataSource, EntityManager entityManager, ClienteRepository clienteRepository) {
        this.dataSource = dataSource;
        this.em = entityManager;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Proveedor add(Proveedor entity) throws Exception {
        try {
            entity.setFechaalta(new Timestamp(System.currentTimeMillis()));
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar proveedor " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Proveedor update(Proveedor entity) throws Exception {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar proveedor " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        Proveedor entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar proveedor " + e.getMessage());
        }
        return "Articulo eliminado correctamente";
    }

    @Override
    public Proveedor getById(Integer key) throws Exception {

        Connection conn = dataSource.getConnection();
        Proveedor proveedor = new Proveedor();

        String sSql = " SELECT a.idproveedor, a.descripcion, a.nrodoc, a.codtipodoc, ";
        sSql += " a.telefono1, a.telefono2, a.fax, a.direccion, a.sitioweb,  ";
        sSql += " a.email, a.fechaalta, a.timbrado, a.venctimbrado, a.idusuario, ";
        sSql += " a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
        sSql += " a.idempresa, f.descripcion as empresa ";
        sSql += " from proveedor a  ";
        sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
        sSql += " left join empresa f on f.idempresa = a.idempresa ";
        sSql += " where a.idproveedor = ? ";

        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            proveedor.setIdproveedor(rs.getInt("idproveedor"));
            Tipodocumento tipodoc = new Tipodocumento();
            tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
            proveedor.setDescripcion(rs.getString("descripcion"));
            proveedor.setTelefono1(rs.getString("telefono1"));
            proveedor.setTelefono2(rs.getString("telefono2"));
            proveedor.setFax(rs.getString("fax"));
            proveedor.setDireccion(rs.getString("direccion"));
            proveedor.setSitioweb(rs.getString("sitioweb"));
            proveedor.setEmail(rs.getString("email"));
            Usuario usuario = new Usuario();
            usuario.setIdusuario(rs.getInt("idusuario"));
            proveedor.setUsuario(usuario);
            proveedor.setFechaalta(rs.getTimestamp("fechaalta"));
            proveedor.setEstado(rs.getBoolean("estado"));
            proveedor.setTimbrado(rs.getString("timbrado"));
            proveedor.setVenctimbrado(rs.getDate("venctimbrado"));

            if (rs.getInt("idempresa") > 0) {
                Empresa empresa = new Empresa();
                empresa.setIdempresa(rs.getInt("idempresa"));
                empresa.setDescripcion(rs.getString("empresa"));
                proveedor.setEmpresa(empresa);
            }

            if (rs.getInt("idplancuenta") > 0) {
                Plancuenta pc = new Plancuenta();
                pc.setIdplancuenta(rs.getInt("idplancuenta"));
                pc.setDescripcion(rs.getString("descplancuenta"));
                proveedor.setPlancuenta(pc);
            }

        }

        conn.close();

        return proveedor;
    }

    @Override
    public List<Proveedor> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        List<Proveedor> ret = new ArrayList<Proveedor>();

        String sSql = " SELECT a.idproveedor, a.descripcion, a.nrodoc, a.codtipodoc, ";
        sSql += " a.telefono1, a.telefono2, a.fax, a.direccion, a.sitioweb,  ";
        sSql += " a.email, a.fechaalta, a.timbrado, a.venctimbrado, a.idusuario, ";
        sSql += " a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
        sSql += " a.idempresa, f.descripcion as empresa ";
        sSql += " from proveedor a  ";
        sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
        sSql += " left join empresa f on f.idempresa = a.idempresa ";
        sSql += " order by idproveedor desc";

        PreparedStatement ps = conn.prepareStatement(sSql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setIdproveedor(rs.getInt("idproveedor"));
            Tipodocumento tipodoc = new Tipodocumento();
            tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
            proveedor.setDescripcion(rs.getString("descripcion"));
            proveedor.setTelefono1(rs.getString("telefono1"));
            proveedor.setTelefono2(rs.getString("telefono2"));
            proveedor.setFax(rs.getString("fax"));
            proveedor.setDireccion(rs.getString("direccion"));
            proveedor.setSitioweb(rs.getString("sitioweb"));
            proveedor.setEmail(rs.getString("email"));
            Usuario usuario = new Usuario();
            usuario.setIdusuario(rs.getInt("idusuario"));
            proveedor.setUsuario(usuario);
            proveedor.setFechaalta(rs.getTimestamp("fechaalta"));
            proveedor.setEstado(rs.getBoolean("estado"));
            proveedor.setTimbrado(rs.getString("timbrado"));
            proveedor.setVenctimbrado(rs.getDate("venctimbrado"));

            if (rs.getInt("idempresa") > 0) {
                Empresa empresa = new Empresa();
                empresa.setIdempresa(rs.getInt("idempresa"));
                empresa.setDescripcion(rs.getString("empresa"));
                proveedor.setEmpresa(empresa);
            }

            if (rs.getInt("idplancuenta") > 0) {
                Plancuenta pc = new Plancuenta();
                pc.setIdplancuenta(rs.getInt("idplancuenta"));
                pc.setDescripcion(rs.getString("descplancuenta"));
                proveedor.setPlancuenta(pc);
            }
            ret.add(proveedor);

        }

        conn.close();

        return ret;
    }

    @Override
    public List<Proveedor> getProveedoresByEmpresa(Integer idEmpresa) throws Exception {
        Connection conn = dataSource.getConnection();
        List<Proveedor> ret = new ArrayList<Proveedor>();

        String sSql = " SELECT a.idproveedor, a.descripcion, a.nrodoc, a.codtipodoc, ";
        sSql += " a.telefono1, a.telefono2, a.fax, a.direccion, a.sitioweb,  ";
        sSql += " a.email, a.fechaalta, a.timbrado, a.venctimbrado, a.idusuario, ";
        sSql += " a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
        sSql += " a.idempresa, f.descripcion as empresa ";
        sSql += " from proveedor a  ";
        sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
        sSql += " left join empresa f on f.idempresa = a.idempresa ";
        sSql += " where a.idempresa = ? ";
        sSql += " order by idproveedor desc";

        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, idEmpresa);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setIdproveedor(rs.getInt("idproveedor"));
            Tipodocumento tipodoc = new Tipodocumento();
            tipodoc.setCodtipodoc(rs.getString("codtipodoc"));
            proveedor.setDescripcion(rs.getString("descripcion"));
            proveedor.setTelefono1(rs.getString("telefono1"));
            proveedor.setTelefono2(rs.getString("telefono2"));
            proveedor.setFax(rs.getString("fax"));
            proveedor.setDireccion(rs.getString("direccion"));
            proveedor.setSitioweb(rs.getString("sitioweb"));
            proveedor.setEmail(rs.getString("email"));
            Usuario usuario = new Usuario();
            usuario.setIdusuario(rs.getInt("idusuario"));
            proveedor.setUsuario(usuario);
            proveedor.setFechaalta(rs.getTimestamp("fechaalta"));
            proveedor.setEstado(rs.getBoolean("estado"));
            proveedor.setTimbrado(rs.getString("timbrado"));
            proveedor.setVenctimbrado(rs.getDate("venctimbrado"));

            if (rs.getInt("idempresa") > 0) {
                Empresa empresa = new Empresa();
                empresa.setIdempresa(rs.getInt("idempresa"));
                empresa.setDescripcion(rs.getString("empresa"));
                proveedor.setEmpresa(empresa);
            }

            if (rs.getInt("idplancuenta") > 0) {
                Plancuenta pc = new Plancuenta();
                pc.setIdplancuenta(rs.getInt("idplancuenta"));
                pc.setDescripcion(rs.getString("descplancuenta"));
                proveedor.setPlancuenta(pc);
            }
            ret.add(proveedor);

        }

        conn.close();

        return ret;
    }

    @Override
    public List<Proveedor> getProveedorByParams(String param, LoginData login) throws Exception {
        try {
            String firstLetter = param.substring(0, 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new Exception("Debe cargar un valor");
        }

        Connection conn = dataSource.getConnection();
        List<Proveedor> ret = new ArrayList<Proveedor>();

        String sSql = " SELECT a.idproveedor, a.descripcion, a.nrodoc, a.codtipodoc, ";
        sSql += " a.telefono1, a.telefono2, a.fax, a.direccion, a.sitioweb,  ";
        sSql += " a.email, a.fechaalta, a.timbrado, a.venctimbrado, a.idusuario, ";
        sSql += " a.idplancuenta, e.descripcion as descplancuenta, a.estado, ";
        sSql += " a.idempresa ";
        sSql += " from proveedor a  ";
        sSql += " left join plancuenta e on e.idplancuenta = a.idplancuenta ";
        sSql += " where (a.nrodoc like upper('%" + param.trim().toUpperCase() + "%')) or upper(a.descripcion) like upper('%" + param.trim().toUpperCase() + "%') ";
        sSql += " and a.idempresa = ? ";
        sSql += " order by a.idproveedor desc limit 10 ";

        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, login.getEmpresa().getIdempresa());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setIdproveedor(rs.getInt("idproveedor"));
            proveedor.setCodtipodoc(rs.getString("codtipodoc"));
            proveedor.setDescripcion(rs.getString("descripcion"));
            proveedor.setTelefono1(rs.getString("telefono1"));
            proveedor.setTelefono2(rs.getString("telefono2"));
            proveedor.setFax(rs.getString("fax"));
            proveedor.setDireccion(rs.getString("direccion"));
            proveedor.setNrodoc(rs.getString("nrodoc"));
            proveedor.setSitioweb(rs.getString("sitioweb"));
            proveedor.setEmail(rs.getString("email"));
            Usuario usuario = new Usuario();
            usuario.setIdusuario(rs.getInt("idusuario"));
            proveedor.setUsuario(usuario);
            proveedor.setFechaalta(rs.getTimestamp("fechaalta"));
            proveedor.setEstado(rs.getBoolean("estado"));
            proveedor.setTimbrado(rs.getString("timbrado"));
            proveedor.setVenctimbrado(rs.getDate("venctimbrado"));

            if (rs.getInt("idempresa") > 0) {
                Empresa empresa = new Empresa();
                empresa.setIdempresa(rs.getInt("idempresa"));
                proveedor.setEmpresa(empresa);
            }

            if (rs.getInt("idplancuenta") > 0) {
                Plancuenta pc = new Plancuenta();
                pc.setIdplancuenta(rs.getInt("idplancuenta"));
                pc.setDescripcion(rs.getString("descplancuenta"));
                proveedor.setPlancuenta(pc);
            }
            ret.add(proveedor);

        }

        conn.close();

        return ret;
    }

}
