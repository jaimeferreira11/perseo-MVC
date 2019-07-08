package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Banco;
import py.com.perseo.tesoreria.entities.BancoCuenta;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.services.BancoCuentaService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BancoCuentaServiceImpl implements BancoCuentaService {

    private static final Logger logger = LoggerFactory.getLogger(BancoCuentaServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public BancoCuenta add(BancoCuenta entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar bancocuenta: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public BancoCuenta update(BancoCuenta entity) throws Exception {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar bancocuenta: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        BancoCuenta entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar bancocuenta: " + e.getMessage());
        }
        return "BancoCuenta eliminado correctamente";
    }

    @Override
    public BancoCuenta getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        BancoCuenta bancoCuenta = new BancoCuenta();
        String sSql = "SELECT idbancocuenta, idbanco, codmoneda, fechaoperacion, fechacomprobante, "
                + "concepto, debe, haber, tipo, idmovimiento, formapago, estado, "
                + "cotizacion, idasientocab, idusuario, idempresa, idsucursal, comprobante, "
                + "nrocuenta, fechaelim, idusuarioelim "
                + "FROM bancocuenta WHERE idbancocuenta = ? ";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            bancoCuenta.setIdbancocuenta(rs.getInt("idbancocuenta"));
            bancoCuenta.setComprobante(rs.getInt("comprobante"));
            bancoCuenta.setNrocuenta(rs.getString("nrocuenta"));
            bancoCuenta.setIdmovimiento(rs.getInt("idmovimiento"));
            bancoCuenta.setFormapago(rs.getString("formapago"));
            bancoCuenta.setEstado(rs.getBoolean("estado"));
            bancoCuenta.setCotizacion(rs.getDouble("cotizacion"));
            bancoCuenta.setConcepto(rs.getString("concepto"));
            bancoCuenta.setDebe(rs.getDouble("debe"));
            bancoCuenta.setHaber(rs.getDouble("haber"));
            bancoCuenta.setTipo(rs.getString("tipo"));
            Banco b = new Banco();
            b.setIdbanco(rs.getInt("idbanco"));
            bancoCuenta.setBanco(b);
            Moneda m = new Moneda();
            m.setCodmoneda(rs.getInt("codmoneda"));
            bancoCuenta.setMoneda(m);
            Usuario u = new Usuario();
            u.setIdusuario(rs.getInt("idusuario"));
            bancoCuenta.setUsuario(u);
            Usuario ue = new Usuario();
            u.setIdusuario(rs.getInt("idusuarioelim"));
            bancoCuenta.setUsuario(ue);
            Sucursal s = new Sucursal();
            s.setIdsucursal(rs.getInt("idsucursal"));
            bancoCuenta.setSucursal(s);
            bancoCuenta.setFechaoperacion(rs.getDate("fechaoperacion"));
            bancoCuenta.setFechacomprobante(rs.getDate("fechacomprobante"));
            bancoCuenta.setFechaelim(rs.getDate("fechaelim"));
        }
        conn.close();
        return bancoCuenta;
    }

    @Override
    public List<BancoCuenta> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT idbancocuenta, idbanco, codmoneda, fechaoperacion, fechacomprobante, "
                + "concepto, debe, haber, tipo, idmovimiento, formapago, estado, "
                + "cotizacion, idasientocab, idusuario, idempresa, idsucursal, comprobante, "
                + "nrocuenta, fechaelim, idusuarioelim "
                + "FROM bancocuenta";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<BancoCuenta> bancoCuentas = new ArrayList<>();

        while (rs.next()) {
            BancoCuenta bancoCuenta = new BancoCuenta();
            bancoCuenta.setIdbancocuenta(rs.getInt("idbancocuenta"));
            bancoCuenta.setComprobante(rs.getInt("comprobante"));
            bancoCuenta.setNrocuenta(rs.getString("nrocuenta"));
            bancoCuenta.setIdmovimiento(rs.getInt("idmovimiento"));
            bancoCuenta.setFormapago(rs.getString("formapago"));
            bancoCuenta.setEstado(rs.getBoolean("estado"));
            bancoCuenta.setCotizacion(rs.getDouble("cotizacion"));
            bancoCuenta.setConcepto(rs.getString("concepto"));
            bancoCuenta.setDebe(rs.getDouble("debe"));
            bancoCuenta.setHaber(rs.getDouble("haber"));
            bancoCuenta.setTipo(rs.getString("tipo"));
            Banco b = new Banco();
            b.setIdbanco(rs.getInt("idbanco"));
            bancoCuenta.setBanco(b);
            Moneda m = new Moneda();
            m.setCodmoneda(rs.getInt("codmoneda"));
            bancoCuenta.setMoneda(m);
            Usuario u = new Usuario();
            u.setIdusuario(rs.getInt("idusuario"));
            bancoCuenta.setUsuario(u);
            Usuario ue = new Usuario();
            u.setIdusuario(rs.getInt("idusuarioelim"));
            bancoCuenta.setUsuario(ue);
            Sucursal s = new Sucursal();
            s.setIdsucursal(rs.getInt("idsucursal"));
            bancoCuenta.setSucursal(s);
            bancoCuenta.setFechaoperacion(rs.getDate("fechaoperacion"));
            bancoCuenta.setFechacomprobante(rs.getDate("fechacomprobante"));
            bancoCuenta.setFechaelim(rs.getDate("fechaelim"));
            bancoCuentas.add(bancoCuenta);
        }
        conn.close();
        return bancoCuentas;
    }
}
