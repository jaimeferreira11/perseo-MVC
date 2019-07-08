package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.services.CajaService;

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
public class CajaServiceImpl implements CajaService {

    private static final Logger logger = LoggerFactory.getLogger(CajaServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public Caja add(Caja entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar caja: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Caja update(Caja entity) throws Exception {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar caja: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        Caja entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar caja: " + e.getMessage());
        }
        return "Caja eliminado correctamente";
    }

    @Override
    public Caja getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Caja caja = new Caja();
        String sSql = "SELECT idcaja, nrocaja, descripcion, estado, idusuario, idsucursal FROM caja WHERE idcaja = ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            caja.setIdcaja(rs.getInt("idcaja"));
            caja.setNrocaja(rs.getInt("nrocaja"));
            caja.setDescripcion(rs.getString("descripcion"));
            caja.setEstado(rs.getBoolean("estado"));
            Usuario u = new Usuario();
            u.setIdusuario(rs.getInt("idusuario"));
            caja.setUsuario(u);
            Sucursal s = new Sucursal();
            s.setIdsucursal(rs.getInt("idsucursal"));
            caja.setSucursal(s);
        }
        conn.close();
        return caja;
    }

    @Override
    public List<Caja> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT idcaja, nrocaja, descripcion, estado, idusuario, idsucursal FROM caja";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Caja> cajas = new ArrayList<>();

        while (rs.next()) {
            Caja caja = new Caja();
            caja.setIdcaja(rs.getInt("idcaja"));
            caja.setNrocaja(rs.getInt("nrocaja"));
            caja.setDescripcion(rs.getString("descripcion"));
            caja.setEstado(rs.getBoolean("estado"));
            Usuario u = new Usuario();
            u.setIdusuario(rs.getInt("idusuario"));
            caja.setUsuario(u);
            Sucursal s = new Sucursal();
            s.setIdsucursal(rs.getInt("idsucursal"));
            caja.setSucursal(s);
            cajas.add(caja);
        }
        conn.close();
        return cajas;
    }
}
