package py.com.perseo.venta.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulodeposito;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.entities.Facturadet;
import py.com.perseo.venta.services.FacturaDetService;

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
public class FacturaDetServiceImpl implements FacturaDetService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    

    private static final Logger logger = LoggerFactory.getLogger(FacturaDetServiceImpl.class);


	@Override
	public Facturadet add(Facturadet entity) throws Exception {
		em.persist(entity);
        return entity;
	}


	@Override
	public Facturadet update(Facturadet entity) throws Exception {
		 em.merge(entity);
	     return entity;
	}


	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Facturadet getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
        Facturadet articulo = new Facturadet();
        try {

            String sql = " select a.*, b.descripcion as tipoimpuesto, c.descripcion as articulo, c.idarticulo  ";
            sql += " from facturadet a";
            sql += " join tipoimpuesto b on a.idtipoimpuesto = b.idtipoimpuesto ";
            sql += " join articulodeposito d on d.idarticulodeposito = a.idarticulodeposito ";
            sql += " join articulo c on c.idarticulo = d.idarticulo ";
            sql += " where idfacturadet = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                articulo.setIdfacturadet(rs.getInt("idfacturadet"));
                Facturacab facturacab = new Facturacab();
                facturacab.setIdfacturacab(rs.getInt("idfacturacab"));
                articulo.setFacturacab(facturacab);
                Articulo articulo2 = new Articulo();
                articulo2.setIdarticulo(rs.getInt("idarticulo"));
                articulo2.setDescripcion(rs.getString("articulo"));
                Articulodeposito articulodeposito = new Articulodeposito();
                articulodeposito.setArticulo(articulo2);
                articulo.setArticulodeposito(articulodeposito);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setConcepto(rs.getString("concepto"));
                articulo.setExenta(rs.getDouble("exenta"));
                articulo.setGravada5(rs.getDouble("gravada5"));
                articulo.setGravada10(rs.getDouble("gravada10"));
                articulo.setIva5(rs.getDouble("iva5"));
                articulo.setIva10(rs.getDouble("iva10"));
                Plancuenta plancuenta = new Plancuenta();
                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
                articulo.setPlancuenta(plancuenta);
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPrecioventa(rs.getDouble("precioventa"));
                articulo.setTotal(rs.getDouble("total"));
                articulo.setDescuento(rs.getDouble("descuento"));
                articulo.setArticulo(articulo2);
                Plancuenta plancuentaiva = new Plancuenta();
                plancuentaiva.setIdplancuenta(rs.getInt("idplancuentaiva"));
                articulo.setPlancuentaiva(plancuentaiva);
                
              

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articulo;
	}


	@Override
	public List<Facturadet> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Facturadet> getByIdFacturaCab(Integer idFacturacab) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Facturadet> list = new ArrayList<>();
        try {

        	String sql = " select a.*,  c.descripcion as articulo, c.idarticulo  ";
            sql += " from facturadet a";
            sql += " left join articulodeposito d on d.idarticulodeposito = a.idarticulodeposito ";
            sql += " left join articulo c on c.idarticulo = a.idarticulo ";
            sql += " where idfacturacab = ?  ";
            sql += " order by a.idfacturadet ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idFacturacab);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	 Facturadet articulo = new Facturadet();
            	 articulo.setIdfacturadet(rs.getInt("idfacturadet"));
                 Facturacab facturacab = new Facturacab();
                 facturacab.setIdfacturacab(rs.getInt("idfacturacab"));
                 articulo.setFacturacab(facturacab);
                 Articulo articulo2 = new Articulo();
                 articulo2.setIdarticulo(rs.getInt("idarticulo"));
                 articulo2.setDescripcion(rs.getString("articulo"));
                 Articulodeposito articulodeposito = new Articulodeposito();
                 articulodeposito.setArticulo(articulo2);
                 articulo.setArticulodeposito(articulodeposito);
                 articulo.setCantidad(rs.getDouble("cantidad"));
                 articulo.setConcepto(rs.getString("concepto"));
                 articulo.setExenta(rs.getDouble("exenta"));
                 articulo.setGravada5(rs.getDouble("gravada5"));
                 articulo.setGravada10(rs.getDouble("gravada10"));
                 articulo.setIva5(rs.getDouble("iva5"));
                 articulo.setIva10(rs.getDouble("iva10"));
                 Plancuenta plancuenta = new Plancuenta();
                 plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
                 articulo.setPlancuenta(plancuenta);
                 articulo.setPreciocosto(rs.getDouble("preciocosto"));
                 articulo.setPrecioventa(rs.getDouble("precioventa"));
                 articulo.setTotal(rs.getDouble("total"));
                 articulo.setDescuento(rs.getDouble("descuento"));
                 articulo.setArticulo(articulo2);
                 Plancuenta plancuentaiva = new Plancuenta();
                 plancuentaiva.setIdplancuenta(rs.getInt("idplancuentaiva"));
                 articulo.setPlancuentaiva(plancuentaiva);
                
                list.add(articulo);

 
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
	}

	

   
}