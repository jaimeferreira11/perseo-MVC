package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.tesoreria.entities.Banco;
import py.com.perseo.tesoreria.services.BancoService;

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
public class BancoServiceImpl implements BancoService {

    private static final Logger logger = LoggerFactory.getLogger(BancoServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public Banco add(Banco entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar banco: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Banco update(Banco entity) throws Exception {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar banco: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        Banco entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar banco: " + e.getMessage());
        }
        return "Banco eliminado correctamente";
    }

    @Override
    public Banco getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Banco banco = new Banco();
        String sSql = "SELECT idbanco, descripcion, telefonos, direccion, sitioweb, email, estado FROM banco "
                + "WHERE idbanco = ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            banco.setIdbanco(rs.getInt("idbanco"));
            banco.setDescripcion(rs.getString("descripcion"));
            banco.setTelefonos(rs.getString("telefonos"));
            banco.setDireccion(rs.getString("direccion"));
            banco.setSitioweb(rs.getString("sitioweb"));
            banco.setEmail(rs.getString("email"));
            banco.setEstado(rs.getBoolean("estado"));
        }
        conn.close();
        return banco;
    }

    @Override
    public List<Banco> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT idbanco, descripcion, telefonos, direccion, sitioweb, email, estado FROM banco order by idbanco ASC";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Banco> bancos = new ArrayList<>();

        while (rs.next()) {
            Banco banco = new Banco();
            banco.setIdbanco(rs.getInt("idbanco"));
            banco.setDescripcion(rs.getString("descripcion"));
            banco.setTelefonos(rs.getString("telefonos"));
            banco.setDireccion(rs.getString("direccion"));
            banco.setSitioweb(rs.getString("sitioweb"));
            banco.setEmail(rs.getString("email"));
            banco.setEstado(rs.getBoolean("estado"));
            bancos.add(banco);
        }
        conn.close();
        return bancos;
    }
}
