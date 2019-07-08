package py.com.perseo.venta.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.tesoreria.entities.Cajacuenta;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.entities.Facturaformacobro;
import py.com.perseo.venta.services.FacturaFormaCobroService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FacturaFormaCobroServiceImpl implements FacturaFormaCobroService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    

    private static final Logger logger = LoggerFactory.getLogger(FacturaFormaCobroServiceImpl.class);


	@Override
	public Facturaformacobro add(Facturaformacobro entity) throws Exception {
		em.persist(entity);
        return entity;
	}


	@Override
	public Facturaformacobro update(Facturaformacobro entity) throws Exception {
		 em.merge(entity);
	     return entity;
	}


	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Facturaformacobro getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		Facturaformacobro entity = new Facturaformacobro();
        try {

            String sql = " select a.*, b.descripcion as cajacuenta";
            sql += " from facturaformacobro a";
            sql += " join cajacuenta b on a.idcajacuenta = b.idcajacuenta ";
            sql += " where a.idfacturaformacobro = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

            	entity.setIdfacturaformacobro(rs.getInt("idfacturaformacobro"));
            	entity.setImporte(rs.getDouble("importe"));
            	entity.setEstado(rs.getBoolean("estado"));
            	Cajacuenta cajacuenta = new Cajacuenta();
            	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
            	entity.setCajacuenta(cajacuenta);
            	Facturacab facturacab = new Facturacab();
            	facturacab.setIdfacturacab(rs.getInt("idfacturacab"));
            	entity.setFacturacab(facturacab);

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
	}


	@Override
	public List<Facturaformacobro> getAll() throws Exception {
		Connection conn = dataSource.getConnection();
        List<Facturaformacobro> list = new ArrayList<>();
        try {

            String sql = " select a.*, b.descripcion as cajacuenta";
            sql += " from facturaformacobro a";
            sql += " join cajacuenta b on a.idcajacuenta = b.idcajacuenta ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	Facturaformacobro entity = new Facturaformacobro();
            	
            	entity.setIdfacturaformacobro(rs.getInt("idfacturaformacobro"));
            	entity.setImporte(rs.getDouble("importe"));
            	entity.setEstado(rs.getBoolean("estado"));
            	Cajacuenta cajacuenta = new Cajacuenta();
            	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
            	entity.setCajacuenta(cajacuenta);
            	Facturacab facturacab = new Facturacab();
            	facturacab.setIdfacturacab(rs.getInt("idfacturacab"));
            	entity.setFacturacab(facturacab);
            	list.add(entity);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}


	@Override
	public List<Facturaformacobro> getByFacturacab(Integer idfacturacab) throws Exception {
		Connection conn = dataSource.getConnection();
        List<Facturaformacobro> list = new ArrayList<>();
        try {

            String sql = " select a.*, b.descripcion as cajacuenta";
            sql += " from facturaformacobro a";
            sql += " join cajacuenta b on a.idcajacuenta = b.idcajacuenta ";
            sql += " where a.idfacturacab = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idfacturacab);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	Facturaformacobro entity = new Facturaformacobro();
            	
            	entity.setIdfacturaformacobro(rs.getInt("idfacturaformacobro"));
            	entity.setImporte(rs.getDouble("importe"));
            	entity.setEstado(rs.getBoolean("estado"));
            	Cajacuenta cajacuenta = new Cajacuenta();
            	cajacuenta.setIdcajacuenta(rs.getInt("idcajacuenta"));
            	cajacuenta.setDescripcion(rs.getString("cajacuenta"));
            	entity.setCajacuenta(cajacuenta);
            	Facturacab facturacab = new Facturacab();
            	facturacab.setIdfacturacab(rs.getInt("idfacturacab"));
            	entity.setFacturacab(facturacab);
            	list.add(entity);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}


	

   
}