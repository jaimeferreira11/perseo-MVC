package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.perseo.tesoreria.entities.Cajacuenta;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagodet;
import py.com.perseo.tesoreria.entities.Ordenpagofp;
import py.com.perseo.tesoreria.services.OrdenPagoDetService;

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
public class OrdenPagoDetServiceImpl implements OrdenPagoDetService {

    private static final Logger logger = LoggerFactory.getLogger(OrdenPagoDetServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public Ordenpagodet add(Ordenpagodet entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar caja: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Ordenpagodet update(Ordenpagodet entity) throws Exception {
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
        Ordenpagodet entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar caja: " + e.getMessage());
        }
        return "Ordenpagodet eliminado correctamente";
    }

    @Override
    public Ordenpagodet getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Ordenpagodet caja = new Ordenpagodet();
        String sSql = "SELECT * FROM ordenpagocab WHERE idordenpagocab = ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            
        }
        conn.close();
        return caja;
    }

    @Override
    public List<Ordenpagodet> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT * FROM ordenpagocab";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Ordenpagodet> cajas = new ArrayList<>();

        while (rs.next()) {
            Ordenpagodet caja = new Ordenpagodet();
           
            cajas.add(caja);
        }
        conn.close();
        return cajas;
    }

	@Override
	public List<Ordenpagodet> getDetByCab(Integer idordepagocab) throws Exception {
		Connection conn = dataSource.getConnection();
        List<Ordenpagodet> list = new ArrayList<>();
        try {

            String sql = " select a.* ";
            sql += " from ordenpagodet a";
            sql += " where a.idordenpagocab = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idordepagocab);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	Ordenpagodet entity = new Ordenpagodet();
            	
            	entity.setIdordenpagodet(rs.getInt("idordenpagodet"));
            	entity.setImporte(rs.getDouble("importe"));
            	entity.setConcepto(rs.getString("concepto"));
            	Ordenpagocab facturacab = new Ordenpagocab();
            	facturacab.setIdordenpagocab(rs.getInt("idordenpagocab"));
            	entity.setOrdenpagocab(facturacab);
     
            	list.add(entity);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
}
