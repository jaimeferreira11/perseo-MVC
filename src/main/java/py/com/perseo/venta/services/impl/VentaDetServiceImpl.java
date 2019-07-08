package py.com.perseo.venta.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;
import py.com.perseo.venta.entities.Ventacab;
import py.com.perseo.venta.entities.Ventadet;
import py.com.perseo.venta.services.VentaDetService;

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
public class VentaDetServiceImpl implements VentaDetService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    

    private static final Logger logger = LoggerFactory.getLogger(VentaDetServiceImpl.class);


	@Override
	public Ventadet add(Ventadet entity) throws Exception {
		em.persist(entity);
        return entity;
	}


	@Override
	public Ventadet update(Ventadet entity) throws Exception {
		 em.merge(entity);
	     return entity;
	}


	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Ventadet getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
        Ventadet articulo = new Ventadet();
        try {

            String sql = " select a.*, b.descripcion as tipoimpuesto , c.descripcion as articulo ";
            sql += " from ventadet a";
            sql += " join tipoimpuesto b on a.idtipoimpuesto = b.idtipoimpuesto ";
            sql += " join articulo c on a.idarticulo = c.idarticulo ";
            sql += " where idventadet = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                articulo.setIdventadet(rs.getInt("idventadet"));
                Ventacab ventacab = new Ventacab();
                ventacab.setIdventacab(rs.getInt("idventacab"));
                articulo.setVentacab(ventacab);
                Articulo articulo2 = new Articulo();
                articulo2.setIdarticulo(rs.getInt("idarticulo"));
                articulo2.setDescripcion(rs.getString("descripcion"));
                articulo.setArticulo(articulo2);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setImpuesto(rs.getDouble("impuesto"));
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPrecioventa(rs.getDouble("precioventa"));
                articulo.setTasadescuento(rs.getDouble("tasadescuento"));
                articulo.setImportedescuento(rs.getDouble("importedescuento"));
                articulo.setTotal(rs.getDouble("total"));
                Tipoimpuesto tipoimpuesto = new Tipoimpuesto();
                tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
                tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
                articulo.setTipoimpuesto(tipoimpuesto);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articulo;
	}


	@Override
	public List<Ventadet> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Ventadet> getByIdVentaCab(Integer idVentaCab) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Ventadet> list = new ArrayList<>();
        try {

            String sql = " select a.*, b.descripcion as tipoimpuesto, c.descripcion as articulo ";
            sql += " from ventadet a";
            sql += " join tipoimpuesto b on a.idtipoimpuesto = b.idtipoimpuesto ";
            sql += " join articulo c on a.idarticulo = c.idarticulo ";
            sql += " where idventacab = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVentaCab);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	 Ventadet articulo = new Ventadet();
                articulo.setIdventadet(rs.getInt("idventadet"));
                Ventacab ventacab = new Ventacab();
                ventacab.setIdventacab(rs.getInt("idventacab"));
                articulo.setVentacab(ventacab);
                Articulo articulo2 = new Articulo();
                articulo2.setIdarticulo(rs.getInt("idarticulo"));
                articulo2.setDescripcion(rs.getString("articulo"));
                articulo.setArticulo(articulo2);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setImpuesto(rs.getDouble("impuesto"));
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPrecioventa(rs.getDouble("precioventa"));
                articulo.setTasadescuento(rs.getDouble("tasadescuento"));
                articulo.setImportedescuento(rs.getDouble("importedescuento"));
                articulo.setTotal(rs.getDouble("total"));
                Tipoimpuesto tipoimpuesto = new Tipoimpuesto();
                tipoimpuesto.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
                tipoimpuesto.setDescripcion(rs.getString("tipoimpuesto"));
                articulo.setTipoimpuesto(tipoimpuesto);
                
                list.add(articulo);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}

	

   
}