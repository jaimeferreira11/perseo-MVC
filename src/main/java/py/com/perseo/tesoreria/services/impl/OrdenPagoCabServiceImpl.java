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

import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.services.OrdenPagoCabService;
import py.com.perseo.tesoreria.services.OrdenPagoDetService;
import py.com.perseo.tesoreria.services.OrdenPagofpService;

@Service
@Transactional
public class OrdenPagoCabServiceImpl implements OrdenPagoCabService {

    private static final Logger logger = LoggerFactory.getLogger(OrdenPagoCabServiceImpl.class);

    @Autowired
    DataSource dataSource;
    
    @Autowired
    OrdenPagofpService ordenPagofpService;
    
    @Autowired
    OrdenPagoDetService ordenPagoDetService;

    @PersistenceContext
    EntityManager em;

    @Override
    public Ordenpagocab add(Ordenpagocab entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar caja: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Ordenpagocab update(Ordenpagocab entity) throws Exception {
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
        Ordenpagocab entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar caja: " + e.getMessage());
        }
        return "Ordenpagocab eliminado correctamente";
    }

    @Override
    public Ordenpagocab getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Ordenpagocab entity = new Ordenpagocab();
        String sSql = "SELECT * FROM ordenpagocab WHERE idordenpagocab = ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
        	entity.setIdordenpagocab(rs.getInt("idordenpagocab"));
        	entity.setImporte(rs.getDouble("importe"));
        	entity.setFecha(rs.getDate("fecha"));
        	entity.setBeneficiario(rs.getString("beneficiario"));
        	entity.setConcepto(rs.getString("concepto"));
        	Usuario usuario = new Usuario();
        	usuario.setIdusuario(rs.getInt("idusuario"));
        	entity.setUsuario(usuario);
        	Moneda moneda = new Moneda();
        	moneda.setCodmoneda(rs.getInt("codmoneda"));
        	entity.setMoneda(moneda);
            entity.setEstado(rs.getString("estado"));
            entity.setRetencion(rs.getBoolean("retencion"));
            entity.setImporteretenido(rs.getDouble("importeretenido"));
            entity.setListFormaPago(ordenPagofpService.getByOdenPagoCab(rs.getInt("idordenpagocab")));
            entity.setDetalleOrden(ordenPagoDetService.getDetByCab(entity.getIdordenpagocab()));
        }
        conn.close();
        return entity;
    }

    @Override
    public List<Ordenpagocab> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "SELECT * FROM ordenpagocab";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Ordenpagocab> cajas = new ArrayList<>();

        while (rs.next()) {
            Ordenpagocab entity = new Ordenpagocab();
            entity.setIdordenpagocab(rs.getInt("idordenpagocab"));
        	entity.setImporte(rs.getDouble("importe"));
        	entity.setFecha(rs.getDate("fecha"));
        	entity.setBeneficiario(rs.getString("beneficiario"));
        	entity.setConcepto(rs.getString("concepto"));
        	Usuario usuario = new Usuario();
        	usuario.setIdusuario(rs.getInt("idusuario"));
        	entity.setUsuario(usuario);
        	Moneda moneda = new Moneda();
        	moneda.setCodmoneda(rs.getInt("codmoneda"));
        	entity.setMoneda(moneda);
            entity.setEstado(rs.getString("estado"));
            entity.setRetencion(rs.getBoolean("retencion"));
            entity.setImporteretenido(rs.getDouble("importeretenido"));
            entity.setListFormaPago(ordenPagofpService.getByOdenPagoCab(rs.getInt("idordenpagocab")));
            entity.setDetalleOrden(ordenPagoDetService.getDetByCab(entity.getIdordenpagocab()));
           
            cajas.add(entity);
        }
        conn.close();
        return cajas;
    }
}
