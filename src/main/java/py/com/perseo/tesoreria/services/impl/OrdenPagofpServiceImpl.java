package py.com.perseo.tesoreria.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.perseo.tesoreria.entities.Cajacuenta;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagofp;
import py.com.perseo.tesoreria.services.OrdenPagofpService;


@Service
@Transactional
public class OrdenPagofpServiceImpl implements OrdenPagofpService {

    private static final Logger logger = LoggerFactory.getLogger(OrdenPagofpServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public Ordenpagofp add(Ordenpagofp entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar caja: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Ordenpagofp update(Ordenpagofp entity) throws Exception {
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
        Ordenpagofp entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar caja: " + e.getMessage());
        }
        return "Ordenpagofp eliminado correctamente";
    }

    @Override
    public Ordenpagofp getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Ordenpagofp entity = new Ordenpagofp();
        String sSql = "SELECT * FROM Ordenpagofp WHERE idordenpagofp= ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
        	entity.setIdordenpagofp(rs.getInt("idordenpagofp"));
        	entity.setImporte(rs.getDouble("importe"));
        	Cajacuenta cajacuenta = new Cajacuenta();
        	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
        	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
        	entity.setCajacuenta(cajacuenta);
        	Ordenpagocab facturacab = new Ordenpagocab();
        	facturacab.setIdordenpagocab(rs.getInt("idordenpagocab"));
        	entity.setOrdenpagocab(facturacab);
        	entity.setTransaccion(rs.getString("transaccion"));
        	entity.setCotizacion(rs.getDouble("cotizacion"));
            
        }
        conn.close();
        return entity;
    }

    @Override
    public List<Ordenpagofp> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT * FROM Ordenpagofp";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Ordenpagofp> cajas = new ArrayList<>();

        while (rs.next()) {
            Ordenpagofp entity = new Ordenpagofp();
            entity.setIdordenpagofp(rs.getInt("idordenpagofp"));
        	entity.setImporte(rs.getDouble("importe"));
        	Cajacuenta cajacuenta = new Cajacuenta();
        	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
        	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
        	entity.setCajacuenta(cajacuenta);
        	Ordenpagocab facturacab = new Ordenpagocab();
        	facturacab.setIdordenpagocab(rs.getInt("idordenpagocab"));
        	entity.setOrdenpagocab(facturacab);
        	entity.setTransaccion(rs.getString("transaccion"));
        	entity.setCotizacion(rs.getDouble("cotizacion"));
           
            cajas.add(entity);
        }
        conn.close();
        return cajas;
    }

	@Override
	public List<Ordenpagofp> getByOdenPagoCab(Integer idordepagocab) throws Exception {
		Connection conn = dataSource.getConnection();
        List<Ordenpagofp> list = new ArrayList<>();
        try {

            String sql = " select a.*, b.descripcion as cajacuenta, b.tipo ";
            sql += " from ordenpagofp a";
            sql += " join cajacuenta b on a.idcajacuenta = b.idcajacuenta ";
            sql += " where a.idordenpagocab = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idordepagocab);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	Ordenpagofp entity = new Ordenpagofp();
            	
            	entity.setIdordenpagofp(rs.getInt("idordenpagofp"));
            	entity.setImporte(rs.getDouble("importe"));
            	Cajacuenta cajacuenta = new Cajacuenta();
            	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
            	cajacuenta.setTipo(rs.getString("tipo"));
            	entity.setCajacuenta(cajacuenta);
            	Ordenpagocab facturacab = new Ordenpagocab();
            	facturacab.setIdordenpagocab(rs.getInt("idordenpagocab"));
            	entity.setOrdenpagocab(facturacab);
            	entity.setTransaccion(rs.getString("transaccion"));
            	entity.setCotizacion(rs.getDouble("cotizacion"));
            	list.add(entity);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
}
