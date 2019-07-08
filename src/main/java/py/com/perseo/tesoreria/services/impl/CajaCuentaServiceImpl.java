package py.com.perseo.tesoreria.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.tesoreria.entities.Banco;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.entities.Cajacuenta;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.services.CajaCuentaService;

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
public class CajaCuentaServiceImpl implements CajaCuentaService {

    private static final Logger logger = LoggerFactory.getLogger(CajaCuentaServiceImpl.class);

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    EntityManager em;

    @Override
    public Cajacuenta add(Cajacuenta entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.error("Error al agregar cajacuenta: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Cajacuenta update(Cajacuenta entity) throws Exception {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar cajacuenta: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        Cajacuenta entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar cajacuenta: " + e.getMessage());
        }
        return "CajaCuenta eliminado correctamente";
    }

    @Override
    public Cajacuenta getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Cajacuenta cajacuenta = new Cajacuenta();
        String sSql = "SELECT idcajacuenta, numero, descripcion, estado, tipo, idcaja, idplancuenta, codmoneda, "
                + "idbanco, cuentabanco FROM cajacuenta WHERE idcajacuenta = ?";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            cajacuenta.setNumero(rs.getInt("numero"));
            cajacuenta.setDescripcion(rs.getString("descripcion"));
            cajacuenta.setEstado(rs.getBoolean("estado"));
            cajacuenta.setTipo(rs.getString("tipo"));
            Banco b = new Banco();
            b.setIdbanco(rs.getInt("idbanco"));
            cajacuenta.setCuentabanco(rs.getString("cuentabanco"));
            cajacuenta.setBanco(b);
            Caja c = new Caja();
            c.setIdcaja(rs.getInt("idcaja"));
            cajacuenta.setCaja(c);
            Plancuenta p = new Plancuenta();
            p.setIdplancuenta(rs.getInt("idplancuenta"));
            cajacuenta.setPlancuenta(p);
            Moneda m = new Moneda();
            m.setCodmoneda(rs.getInt("codmoneda"));
            cajacuenta.setMoneda(m);
        }
        conn.close();
        return cajacuenta;
    }

    @Override
    public List<Cajacuenta> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        String sSql = "select * from cajacuenta";
        PreparedStatement ps = conn.prepareStatement(sSql);
        ResultSet rs = ps.executeQuery();
        List<Cajacuenta> cajacuentas = new ArrayList<>();

        while (rs.next()) {
            Cajacuenta cajacuenta = new Cajacuenta();
            cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            cajacuenta.setNumero(rs.getInt("numero"));
            cajacuenta.setDescripcion(rs.getString("descripcion"));
            cajacuenta.setEstado(rs.getBoolean("estado"));
            cajacuenta.setTipo(rs.getString("tipo"));
            Banco b = new Banco();
            b.setIdbanco(rs.getInt("idbanco"));
            cajacuenta.setCuentabanco(rs.getString("cuentabanco"));
            cajacuenta.setBanco(b);
            Caja c = new Caja();
            c.setIdcaja(rs.getInt("idcaja"));
            cajacuenta.setCaja(c);
            Plancuenta p = new Plancuenta();
            p.setIdplancuenta(rs.getInt("idplancuenta"));
            cajacuenta.setPlancuenta(p);
            Moneda m = new Moneda();
            m.setCodmoneda(rs.getInt("codmoneda"));
            cajacuenta.setMoneda(m);
            cajacuentas.add(cajacuenta);
        }
        conn.close();
        return cajacuentas;
    }

	@Override
	public List<Cajacuenta> getByCaja(Integer idcaja) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Cajacuenta> cajacuentas = new ArrayList<>();
		try {
		        String sSql = "select * from cajacuenta where idcaja = ?";
		        PreparedStatement ps = conn.prepareStatement(sSql);
		        ps.setInt(1, idcaja);
		        ResultSet rs = ps.executeQuery();
		        
		        while (rs.next()) {
		            Cajacuenta cajacuenta = new Cajacuenta();
		            cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
		            cajacuenta.setNumero(rs.getInt("numero"));
		            cajacuenta.setDescripcion(rs.getString("descripcion"));
		            cajacuenta.setEstado(rs.getBoolean("estado"));
		            cajacuenta.setTipo(rs.getString("tipo"));
		            Banco b = new Banco();
		            b.setIdbanco(rs.getInt("idbanco"));
		            cajacuenta.setCuentabanco(rs.getString("cuentabanco"));
		            cajacuenta.setBanco(b);
		            Caja c = new Caja();
		            c.setIdcaja(rs.getInt("idcaja"));
		            cajacuenta.setCaja(c);
		            Plancuenta p = new Plancuenta();
		            p.setIdplancuenta(rs.getInt("idplancuenta"));
		            cajacuenta.setPlancuenta(p);
		            Moneda m = new Moneda();
		            m.setCodmoneda(rs.getInt("codmoneda"));
		            cajacuenta.setMoneda(m);
		            cajacuentas.add(cajacuenta);
		        }
		        conn.close();
		        
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		return cajacuentas;
	}
		
}
