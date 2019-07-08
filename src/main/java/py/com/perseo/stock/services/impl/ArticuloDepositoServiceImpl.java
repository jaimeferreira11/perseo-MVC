package py.com.perseo.stock.services.impl;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.eess.entities.Tanque;
import py.com.perseo.stock.entities.*;
import py.com.perseo.stock.reports.datasourse.JRDataListaArticulos;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.ArticuloMovimientoService;
import py.com.perseo.stock.services.DepositoService;
import py.com.perseo.stock.services.HistoricoPrecioCostoService;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticuloDepositoServiceImpl implements ArticuloDepositoService {

    private static final Logger logger = LoggerFactory.getLogger(ArticuloDepositoServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    ArticuloMovimientoService articuloMovimientoService;

    @Autowired
    DepositoService depositoService;

    @Autowired
    HistoricoPrecioCostoService historicoPrecioCostoService;

    @Override
    public Articulodeposito getById(Integer key) throws Exception {

        Connection conn = dataSource.getConnection();

        String sql = " select a.idarticulodeposito, a.idsucursal, a.iddeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, a.inventariofisico, ";
        sql += " a.nrolote, a.preciocosto, a.preciocostoeq, a.precioventa, a.precioventaeq, a.idplancuenta, a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo ";
        sql += " from articulodeposito a ";
        sql += " join articulo b on b.idarticulo = a.idarticulo ";
        sql += " where a.idarticulodeposito = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, key);

        ResultSet rs = ps.executeQuery();

        Articulodeposito articulo = new Articulodeposito();
        while (rs.next()) {
            articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal(rs.getInt("idsucursal"));
            articulo.setSucursal(sucursal);
            Articulo a = new Articulo();
            a.setIdarticulo(rs.getInt("idarticulo"));
            a.setDescripcion(rs.getString("articulo"));
            a.setCodigobarra(rs.getString("codigobarra"));
            articulo.setArticulo(a);
            articulo.setEstado(rs.getBoolean("estado"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito(rs.getInt("iddeposito"));
            articulo.setDeposito(deposito);
            articulo.setNrolote(rs.getInt("nrolote"));
            articulo.setPreciocosto(rs.getDouble("preciocosto"));
            articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
            articulo.setPrecioventa(rs.getDouble("precioventa"));
            articulo.setPrecioventaeq(rs.getDouble("precioventaeq"));
            Tanque tanque = new Tanque();
            tanque.setNrotanque(rs.getInt("nrotanque"));
            articulo.setTanque(tanque);
            articulo.setCantidad(rs.getDouble("cantidad"));
            articulo.setCantidadminima(rs.getDouble("cantidadminima"));
            articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
            articulo.setInventariofisico(rs.getDouble("inventariofisico"));
            Plancuenta plancuenta = new Plancuenta();
            plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
            articulo.setPlancuenta(plancuenta);

        }

        conn.close();

        return articulo;
    }

    @Override
    public List<Articulodeposito> getAll() throws Exception {

        Connection conn = dataSource.getConnection();
        List<Articulodeposito> articulos = new ArrayList<Articulodeposito>();

        String sql = "select a.idarticulodeposito, a.idsucursal, a.iddeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, a.inventariofisico, ";
        sql += " a.nrolote, a.preciocosto, a.preciocostoeq, a.precioventa, a.precioventaeq, a.idplancuenta, a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo ";
        sql += " from articulodeposito a ";
        sql += " join articulo b on b.idarticulo = a.idarticulo ";
        sql += " order by a.idarticulodeposito desc ";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Articulodeposito articulo = new Articulodeposito();
            articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal(rs.getInt("idsucursal"));
            articulo.setSucursal(sucursal);
            Articulo a = new Articulo();
            a.setIdarticulo(rs.getInt("idarticulo"));
            a.setDescripcion(rs.getString("articulo"));
            a.setCodigobarra(rs.getString("codigobarra"));
            articulo.setArticulo(a);
            articulo.setEstado(rs.getBoolean("estado"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito(rs.getInt("iddeposito"));
            articulo.setDeposito(deposito);
            articulo.setNrolote(rs.getInt("nrolote"));
            articulo.setPreciocosto(rs.getDouble("preciocosto"));
            articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
            articulo.setPrecioventa(rs.getDouble("precioventa"));
            articulo.setPrecioventaeq(rs.getDouble("precioventaeq"));
            Tanque tanque = new Tanque();
            tanque.setNrotanque(rs.getInt("nrotanque"));
            articulo.setTanque(tanque);
            articulo.setCantidad(rs.getDouble("cantidad"));
            articulo.setCantidadminima(rs.getDouble("cantidadminima"));
            articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
            articulo.setInventariofisico(rs.getDouble("inventariofisico"));
            Plancuenta plancuenta = new Plancuenta();
            plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
            articulo.setPlancuenta(plancuenta);
            articulos.add(articulo);
        }

        if (articulos.isEmpty()) {
            articulos = null;
        }

        conn.close();

        return articulos;
    }

    @Override
    public Articulodeposito add(Articulodeposito entity) {
        try {
            em.persist(entity);
            //em.flush();
            //em.refresh(entity);
            return entity;
        } catch (Exception ex) {
            logger.info("Error al agregar articulo deposito " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Articulodeposito update(Articulodeposito entity) {
        try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.info("Error al actualizar articulo deposito" + e.getMessage());
            throw e;
        }
    }

    @Override
    public String delete(Integer key) throws Exception {
        Articulodeposito entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar articulo deposito" + e.getMessage());
        }
        return "Articulodeposito eliminado correctamente";
    }

    @Override
    public List<Articulodeposito> getBySucursalAndDeposito(Integer idSucursal, Integer idDeposito, Boolean all) throws Exception {

        List<Articulodeposito> articulos = new ArrayList<Articulodeposito>();
        try {

            Connection conn = dataSource.getConnection();

            String sql = "select a.idarticulodeposito, a.idsucursal, a.iddeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, a.inventariofisico, ";
            sql += " a.nrolote, a.preciocosto, a.preciocostoeq, a.precioventa, a.precioventaeq, a.idplancuenta, a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo, b.idunidadmedida ";
            sql += " from articulodeposito a ";
            sql += " join articulo b on b.idarticulo = a.idarticulo ";
            sql += " where a.idsucursal = ? and a.iddeposito = ? ";
            if (!all) {
                sql += " and a.estado = true ";
            }
            sql += " order by a.idarticulodeposito desc ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSucursal);
            ps.setInt(2, idDeposito);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Articulodeposito articulo = new Articulodeposito();
                articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
                Sucursal sucursal = new Sucursal();
                sucursal.setIdsucursal(rs.getInt("idsucursal"));
                articulo.setSucursal(sucursal);
                Articulo a = new Articulo();
                a.setIdarticulo(rs.getInt("idarticulo"));
                a.setDescripcion(rs.getString("articulo"));
                a.setCodigobarra(rs.getString("codigobarra"));
                a.setIdunidadmedida(rs.getInt("idunidadmedida"));
                articulo.setArticulo(a);
                articulo.setEstado(rs.getBoolean("estado"));
                Deposito deposito = new Deposito();
                deposito.setIddeposito(rs.getInt("iddeposito"));
                articulo.setDeposito(deposito);
                articulo.setNrolote(rs.getInt("nrolote"));
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
                articulo.setPrecioventa(rs.getDouble("precioventa"));
                articulo.setPrecioventaeq(rs.getDouble("precioventaeq"));
                Tanque tanque = new Tanque();
                tanque.setNrotanque(rs.getInt("nrotanque"));
                articulo.setTanque(tanque);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setCantidadminima(rs.getDouble("cantidadminima"));
                articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
                articulo.setInventariofisico(rs.getDouble("inventariofisico"));
                Plancuenta plancuenta = new Plancuenta();
                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
                articulo.setPlancuenta(plancuenta);
                articulos.add(articulo);
            }

            logger.info("Size obtenido : " + articulos.size());
            /*if (articulos.isEmpty()) {
                articulos = null;
			}*/
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return articulos;
    }

	/*@Override
    public String transferirArticulo(Articulodeposito articuloDeposito,  LoginData login) throws Exception {
		try {
			Articulodeposito ad_origen = getById(articuloDeposito.getIdarticulodeposito());
			// registrar la salida en articulo mov
			Articulomovimiento salida = new Articulomovimiento();
			salida.setArticulo(ad_origen.getArticulo());
			salida.setFecha(new Date(System.currentTimeMillis()));
			salida.setDeposito(ad_origen.getDeposito());
			Conceptomov c = new Conceptomov();
			c.setIdconceptomov(13);	
			salida.setConceptomov(c);
			salida.setSucursal(ad_origen.getSucursal());
			salida.setUsuario(login.getUsuario());
			salida.setCantidad(articuloDeposito.getCantidad());
			salida.setColumna("E");
			
			// transferir origen
			ad_origen.setCantidad(ad_origen.getCantidad() - articuloDeposito.getCantidad());
			update(ad_origen);
			// transferrir destino
			Deposito deposito_destino = depositoService.getById(articuloDeposito.getDeposito().getIddeposito());
			Articulodeposito ad_destino = getByArticuloAndDeposito(ad_origen.getArticulo().getIdarticulo(), deposito_destino.getIddeposito());
			//agregar el articulo en el deposito
			if(ad_destino.getIdarticulodeposito() == null){
				
				ad_destino = ad_origen;
				ad_destino.setUsuario(login.getUsuario());
				ad_destino.setCantidad(articuloDeposito.getCantidad());
				ad_destino.setDeposito(deposito_destino);
				
				add(ad_destino);
			//actualiza el stock del articulo en el deposito	
			}else{
				ad_destino.setUsuario(login.getUsuario());
				ad_destino.setCantidad(ad_destino.getCantidad() + articuloDeposito.getCantidad());
				update(ad_destino);
			}
			// registrar la entrada en articulo mov
			Articulomovimiento entrada = new Articulomovimiento();
			entrada.setArticulo(ad_destino.getArticulo());
			entrada.setFecha(new Date(System.currentTimeMillis()));
			entrada.setDeposito(ad_destino.getDeposito());
			Conceptomov c1 = new Conceptomov();
			c1.setIdconceptomov(14);	
			entrada.setConceptomov(c1);
			entrada.setSucursal(ad_destino.getSucursal());
			entrada.setUsuario(login.getUsuario());
			entrada.setCantidad(articuloDeposito.getCantidad());
			entrada.setColumna("I");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "Transferencia exitosa";
	}*/

    @Override
    public Articulodeposito getByArticuloAndDeposito(Integer idarticulo, Integer idDeposito) throws Exception {
        Connection conn = dataSource.getConnection();

        Articulodeposito articulo = new Articulodeposito();

        String sql = "select a.idarticulodeposito, a.idsucursal, a.iddeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, a.inventariofisico, ";
        sql += " a.nrolote, a.preciocosto, a.preciocostoeq, a.precioventa, a.precioventaeq, a.idplancuenta, a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo, b.idunidadmedida ";
        sql += " from articulodeposito a ";
        sql += " join articulo b on b.idarticulo = a.idarticulo ";
        sql += " where a.idarticulo = ? and a.iddeposito = ? ";
        sql += " order by a.idarticulodeposito desc ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idarticulo);
        ps.setInt(2, idDeposito);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal(rs.getInt("idsucursal"));
            articulo.setSucursal(sucursal);
            Articulo a = new Articulo();
            a.setIdarticulo(rs.getInt("idarticulo"));
            a.setDescripcion(rs.getString("articulo"));
            a.setCodigobarra(rs.getString("codigobarra"));
            a.setIdunidadmedida(rs.getInt("idunidadmedida"));
            articulo.setArticulo(a);
            articulo.setEstado(rs.getBoolean("estado"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito(rs.getInt("iddeposito"));
            articulo.setDeposito(deposito);
            articulo.setNrolote(rs.getInt("nrolote"));
            articulo.setPreciocosto(rs.getDouble("preciocosto"));
            articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
            articulo.setPrecioventa(rs.getDouble("precioventa"));
            articulo.setPrecioventaeq(rs.getDouble("precioventaeq"));
            Tanque tanque = new Tanque();
            tanque.setNrotanque(rs.getInt("nrotanque"));
            articulo.setTanque(tanque);
            articulo.setCantidad(rs.getDouble("cantidad"));
            articulo.setCantidadminima(rs.getDouble("cantidadminima"));
            articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
            articulo.setInventariofisico(rs.getDouble("inventariofisico"));
            Plancuenta plancuenta = new Plancuenta();
            plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
            articulo.setPlancuenta(plancuenta);

        }


        conn.close();

        return articulo;
    }

    @Override
    public Articulodeposito addArticuloAndDeposito(Articulodeposito articulodeposito, LoginData login)
            throws Exception {
        Articulodeposito res = new Articulodeposito();
        try {
            res = add(articulodeposito);

            // registrar la entrada en articulo mov
            Articulomovimiento entrada = new Articulomovimiento();
            entrada.setArticulo(res.getArticulo());
            entrada.setFecha(new Date(System.currentTimeMillis()));
            entrada.setDeposito(res.getDeposito());
            Conceptomov c1 = new Conceptomov();
            c1.setIdconceptomov(15);
            entrada.setConceptomov(c1);
            entrada.setSucursal(res.getSucursal());
            entrada.setUsuario(login.getUsuario());
            entrada.setCantidad(res.getCantidad());
            entrada.setColumna("I");

            articuloMovimientoService.add(entrada);
        } catch (ConstraintViolationException ce) {
            throw ce;
        } catch (Exception e) {
            //	e.printStackTrace();
            throw e;
        }
        return res;
    }

    @Override
    public List<Articulodeposito> getBySucursalAndDepositoByParams(Integer idSucursal, Integer idDeposito, String param,
                                                                   LoginData loginData) throws Exception {
        // TODO Auto-generated method stub
        List<Articulodeposito> articulos = new ArrayList<Articulodeposito>();
        try {

            String val = param;
            String firstLetter = "";

            try {
                firstLetter = val.substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                throw new Exception("Debe cargar un valor");
            }
            double d = 0D;
            boolean num = true;
            try {
                d = Double.parseDouble(firstLetter);
            } catch (NumberFormatException nfe) {
                num = false;
            }

            Connection conn = dataSource.getConnection();

            String sql = "select a.idarticulodeposito, a.idsucursal, a.iddeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, a.inventariofisico, ";
            sql += " a.nrolote, a.preciocosto, a.preciocostoeq, a.precioventa, a.precioventaeq, a.idplancuenta, a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo, b.idunidadmedida ";
            sql += " from articulodeposito a ";
            sql += " join articulo b on b.idarticulo = a.idarticulo ";
            sql += " where a.idsucursal = ? and a.iddeposito = ? ";

            PreparedStatement ps;

            if (num) {

                sql += " and b.codigobarra like '%" + param + "%' ";
                ps = conn.prepareStatement(sql);

            } else {
                sql += " and upper(b.descripcion) like upper('%" + param + "%') ";

                ps = conn.prepareStatement(sql);
            }
            sql += " order by a.idarticulodeposito desc ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSucursal);
            ps.setInt(2, idDeposito);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Articulodeposito articulo = new Articulodeposito();
                articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
                Sucursal sucursal = new Sucursal();
                sucursal.setIdsucursal(rs.getInt("idsucursal"));
                articulo.setSucursal(sucursal);
                Articulo a = new Articulo();
                a.setIdarticulo(rs.getInt("idarticulo"));
                a.setDescripcion(rs.getString("articulo"));
                a.setCodigobarra(rs.getString("codigobarra"));
                a.setIdunidadmedida(rs.getInt("idunidadmedida"));
                articulo.setArticulo(a);
                articulo.setEstado(rs.getBoolean("estado"));
                Deposito deposito = new Deposito();
                deposito.setIddeposito(rs.getInt("iddeposito"));
                articulo.setDeposito(deposito);
                articulo.setNrolote(rs.getInt("nrolote"));
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
                articulo.setPrecioventa(rs.getDouble("precioventa"));
                articulo.setPrecioventaeq(rs.getDouble("precioventaeq"));
                Tanque tanque = new Tanque();
                tanque.setNrotanque(rs.getInt("nrotanque"));
                articulo.setTanque(tanque);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setCantidadminima(rs.getDouble("cantidadminima"));
                articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
                articulo.setInventariofisico(rs.getDouble("inventariofisico"));
                Plancuenta plancuenta = new Plancuenta();
                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
                articulo.setPlancuenta(plancuenta);
                articulos.add(articulo);
            }

            logger.info("Size obtenido : " + articulos.size());
            /*if (articulos.isEmpty()) {
                articulos = null;
			}*/
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return articulos;
    }


    @Override
    public List<Articulodeposito> getArticulosVentaByParams(Integer idarticuloprecioventacab, String param, LoginData login) throws Exception {
        // TODO Auto-generated method stub
        List<Articulodeposito> articulos = new ArrayList<Articulodeposito>();
        try {

            String val = param;
            String firstLetter = "";

            try {
                firstLetter = val.substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                throw new Exception("Debe cargar un valor");
            }
            double d = 0D;
            boolean num = true;
            try {
                d = Double.parseDouble(firstLetter);
            } catch (NumberFormatException nfe) {
                num = false;
            }

            Connection conn = dataSource.getConnection();

            String sql = "select a.idarticulodeposito, a.nrotanque, a.cantidad, a.cantidadminima, a.cantidadbloqueo, b.idtipoimpuesto, ";
            sql += " a.nrolote, a.preciocosto, a.preciocostoeq,  a.estado, a.idarticulo, b.codigobarra, b.descripcion as articulo, b.idunidadmedida,  c.precio ";
            sql += " from articulodeposito a ";
            sql += " join articulo b on b.idarticulo = a.idarticulo ";
            sql += " join articuloprecioventadet c on b.idarticulo = c.idarticulo ";
            sql += " where a.idsucursal = ? and a.iddeposito = ? and a.estado = true and c.idarticuloprecioventacab = ?";
            sql += " and c.precio > 0 and cantidad > 0";

            PreparedStatement ps;

            if (num) {

                sql += " and b.codigobarra like '%" + param + "%' ";
                ps = conn.prepareStatement(sql);

            } else {
                sql += " and upper(b.descripcion) like upper('%" + param + "%') ";

                ps = conn.prepareStatement(sql);
            }
            sql += " order by a.idarticulodeposito desc ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, login.getUsuario().getSucursal().getIdsucursal());
            ps.setInt(2, login.getUsuario().getDeposito().getIddeposito());
            ps.setInt(3, idarticuloprecioventacab);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Articulodeposito articulo = new Articulodeposito();
                articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));

                Articulo a = new Articulo();
                a.setIdarticulo(rs.getInt("idarticulo"));
                a.setDescripcion(rs.getString("articulo"));
                a.setCodigobarra(rs.getString("codigobarra"));
                a.setIdunidadmedida(rs.getInt("idunidadmedida"));
                Tipoimpuesto ti = new Tipoimpuesto();
                ti.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
                a.setTipoimpuesto(ti);
                articulo.setArticulo(a);
                articulo.setEstado(rs.getBoolean("estado"));
                articulo.setPreciocosto(rs.getDouble("preciocosto"));
                articulo.setPreciocostoeq(rs.getDouble("preciocostoeq"));
                Tanque tanque = new Tanque();
                tanque.setNrotanque(rs.getInt("nrotanque"));
                articulo.setTanque(tanque);
                articulo.setCantidad(rs.getDouble("cantidad"));
                articulo.setCantidadminima(rs.getDouble("cantidadminima"));
                articulo.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
                articulo.setPrecioventa(rs.getDouble("precio"));
                articulos.add(articulo);
            }

            logger.info("Size obtenido : " + articulos.size());
                    /*if (articulos.isEmpty()) {
						articulos = null;
					}*/
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return articulos;
    }

    @Override
    public void addArticuloInAllDeposito(Articulo articulo) throws Exception {
        try {
            List<Deposito> listDepositos = depositoService.getAll();
            for (Deposito deposito : listDepositos) {
                Articulodeposito a = new Articulodeposito();
                a.setArticulo(articulo);
                a.setDeposito(deposito);
                a.setSucursal(deposito.getSucursal());
                a.setEstado(true);
                a.setPreciocosto(new Double(0));
                a.setPreciocostoeq(new Double(0));
                a.setPrecioventa(new Double(0));
                a.setPrecioventaeq(new Double(0));
                a.setNrolote(0);
                a.setUsuario(articulo.getUsuario());
                a.setCantidad(new Double(0));
                a.setCantidadbloqueo(new Double(0));
                a.setCantidadminima(new Double(0));
                a.setCantidadVenta(new Double(0));

                add(a);


            }


        } catch (ConstraintViolationException ce) {
            throw ce;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePrecioCosto(Articulo articulo) throws Exception {
    	logger.info("Actualizando el precio de costo : " + articulo.getIdarticulo());
        Connection conn = dataSource.getConnection();
        try {
            Double prom = historicoPrecioCostoService.getPromedioCostoByArticulo(articulo);
            logger.info("Nuevo precio de costo: " + prom);
            //actualiza el precio de costo en articulodeposito
            String sql = "update articulodeposito set preciocosto = ? where idarticulo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, prom);
            ps.setInt(2, articulo.getIdarticulo());
            ps.executeUpdate();

            //actualiza el precio de costo en articulo (no se usa)
            String sql2 = "update articulo set preciocosto = ? where idarticulo = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setDouble(1, prom);
            ps2.setInt(2, articulo.getIdarticulo());
            ps2.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public void updateStock(int idsucursal, int iddeposito, Integer idarticulo, Double cantidad) throws SQLException {
        Connection conn = null;
        logger.info("Actualizando el stock: " + iddeposito + " art: " + idarticulo + " cant: " + cantidad);
    	try {
            conn = dataSource.getConnection();
            String sql = "update articulodeposito set cantidad = ? where idarticulo = ? and iddeposito = ? and idsucursal = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, cantidad);
            ps.setInt(2, idarticulo);
            ps.setInt(3, iddeposito);
            ps.setInt(4, idsucursal);
            ps.executeUpdate();
            
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String imprimirListaArticulos(List<Articulodeposito> c, LoginData login) throws Exception {
        String res = "";

        try {
            InputStream reporte = null;
            JasperPrint print = null;
            Map<String, Object> parameters = new HashMap<String, Object>();


            if (!c.isEmpty()) {

                reporte = this.getClass().getResourceAsStream("/py/com/perseo/stock/reports/JRListaArticulos.jasper");
                JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
                String sReporte = report.getName().substring(report.getName().lastIndexOf(".") + 1, report.getName().length());

                parameters.put("sReporte", sReporte);
                parameters.put("sEmpresa", login.getEmpresa().getDescripcion());
                parameters.put("sUsuario", login.getUsuario().getUsuario());
                parameters.put("fechahora", new Date(System.currentTimeMillis()));
                //	parameters.put("desde", getJTxtDesde().getValue());
                //	parameters.put("hasta", getJTxtHasta().getValue());
                parameters.put("sTitulo", "Articulos");

                print = JasperFillManager.fillReport(report, parameters, new JRDataListaArticulos(c));

                byte[] reporteF = null;
                reporteF = JasperExportManager.exportReportToPdf(print);
                File tempFileF = File.createTempFile("Arti-Dep", ".pdf", null);
                FileOutputStream fos = new FileOutputStream(tempFileF);
                fos.write(reporteF);

                res = tempFileF.getName();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return res;
    }

    @Override
    public String getListaArticulos(String datos, LoginData login) throws Exception {
        String res = "";
        JSONObject params = new JSONObject(datos);

        Integer iddeposito = params.getInt("iddeposito");
        Integer idsucursal = params.getInt("idsucursal");
        boolean isfamilia = params.getBoolean("familia_todas");
        Integer idfamilia = params.getInt("idfamilia");
        boolean articulo_todas = params.getBoolean("articulo_todas");
        Integer idarticulo = params.getInt("idarticulo");


        Connection conn = null;
        List<Articulodeposito> ret = new ArrayList<Articulodeposito>();
        try {
            conn = dataSource.getConnection();

            // articulo
            String select = " SELECT a.idarticulo, a.descripcion as a_descripcion, a.codigobarra, ";
            select += " a.idfamilia, f.descripcion as desc_familia, a.estado, ";
            select += " um.idunidadmedida, um.descripcion as um_descripcion, ";
            select += " ti.idtipoimpuesto, ti.descripcion as ti_descripcion, ";
            select += " mon.codmoneda, mon.nombre as moneda, ti.tasa, ti.dividendo, ";
            // articulodeposito
            select += " ad.idarticulodeposito, ad.precioventa, ad.idusuario, ad.idsucursal, ad.idarticulo, ad.iddeposito, ad.nrotanque, ad.cantidad, ";
            select += " ad.cantidadminima, ad.cantidadbloqueo, ad.inventariofisico, ad.fechaultinventario, ad.fechavencimiento, ";
            select += " ad.fechaultcompra, ad.fechaultventa, ad.nrolote, ad.preciocosto, ad.preciocostoeq, ad.precioventa, ad.precioventaeq ";
            select += " from articulodeposito ad ";
            select += " join articulo a on a.idarticulo = ad.idarticulo ";
            select += " left join familia f on (f.idfamilia = a.idfamilia) ";
            select += " left join tipoimpuesto ti on (ti.idtipoimpuesto = a.idtipoimpuesto) ";
            select += " left join unidadmedida um on (um.idunidadmedida = a.idunidadmedida) ";
            select += " join moneda mon on (mon.codmoneda = a.codmoneda) ";

            select += " WHERE ad.iddeposito = ? and ad.idsucursal = ? ";

            if (!isfamilia) {
                select += " and a.idfamilia = ? ";
            }
            if (articulo_todas) {
                select += " and a.idarticulo = ? ";
            }

            select += " and a.estado = true and ad.cantidad > 0 ";

            select += " order by a.idfamilia, a.descripcion ";

            PreparedStatement ps = conn.prepareStatement(select);
            ps.setInt(1, iddeposito);
            ps.setInt(2, idsucursal);

            if (!isfamilia) {
                ps.setInt(3, idfamilia);
            }
            if (articulo_todas) {
                ps.setInt(4, idarticulo);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Articulodeposito ad = new Articulodeposito();
                ad.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
                ad.setPrecioventa(rs.getDouble("precioventa"));
                ad.setPrecioventaeq(rs.getDouble("precioventa"));
                ad.setCantidad(rs.getDouble("cantidad"));

                // usuario
                Usuario usuario = new Usuario();
                usuario.setIdusuario(rs.getInt("idusuario"));
                ad.setUsuario(usuario);

                // sucursal
                Sucursal sucursal_ = new Sucursal();
                sucursal_.setIdsucursal(rs.getInt("idsucursal"));
                ad.setSucursal(sucursal_);

                // deposito
                Deposito deposito_ = new Deposito();
                deposito_.setIddeposito(rs.getInt("iddeposito"));
                ad.setDeposito(deposito_);

                // tanque
                if (rs.getInt("nrotanque") > 0) {
                    Tanque tanque = new Tanque();
                    tanque.setNrotanque(rs.getInt("nrotanque"));
                    ad.setTanque(tanque);
                }

                ad.setCantidadbloqueo(rs.getDouble("cantidadbloqueo"));
                ad.setInventariofisico(rs.getDouble("inventariofisico"));
                ad.setFechaultinventario(rs.getDate("fechaultinventario"));
                ad.setFechavencimiento(rs.getDate("fechavencimiento"));
                ad.setFechaultcompra(rs.getDate("fechaultcompra"));
                ad.setFechaultventa(rs.getDate("fechaultventa"));
                ad.setNrolote(rs.getInt("nrolote"));
                ad.setPreciocosto(rs.getDouble("preciocosto"));
                ad.setPreciocostoeq(rs.getDouble("preciocostoeq"));
                ad.setPrecioventa(rs.getDouble("precioventa"));

                // articulo
                Articulo articulo_data = new Articulo();
                articulo_data.setIdarticulo(rs.getInt("idarticulo"));
                articulo_data.setDescripcion(rs.getString("a_descripcion"));
                articulo_data.setCodigobarra(rs.getString("codigobarra"));
                articulo_data.setEstado(rs.getBoolean("estado"));

                // moneda
                Moneda moneda = new Moneda();
                moneda.setCodmoneda(rs.getInt("codmoneda"));
                moneda.setNombre(rs.getString("moneda"));
                //articulo_data.setMoneda(moneda);
                articulo_data.setCodmoneda(moneda.getCodmoneda());

                if (rs.getInt("idunidadmedida") > 0) {
                    Unidadmedida um_data = new Unidadmedida();
                    um_data.setIdunidadmedida(rs.getInt("idunidadmedida"));
                    um_data.setDescripcion(rs.getString("um_descripcion"));
                    //articulo_data.setUnidadmedida(um_data);
                    articulo_data.setIdunidadmedida(um_data.getIdunidadmedida());
                }
                if (rs.getInt("idtipoimpuesto") > 0) {
                    Tipoimpuesto ti_data = new Tipoimpuesto();
                    ti_data.setIdtipoimpuesto(rs.getInt("idtipoimpuesto"));
                    ti_data.setDescripcion(rs.getString("ti_descripcion"));
                    ti_data.setTasa(rs.getDouble("tasa"));
                    ti_data.setDividendo(rs.getDouble("dividendo"));
                    articulo_data.setTipoimpuesto(ti_data);
                }
                // Familia
                if (rs.getInt("idfamilia") > 0) {
                    Familia familia_data = new Familia();
                    familia_data.setIdfamilia(rs.getInt("idfamilia"));
                    familia_data.setDescripcion(rs.getString("desc_familia"));
                    //articulo_data.setFamilia(familia_data);
                    articulo_data.setIdfamilia(familia_data.getIdfamilia());
                }

                ad.setArticulo(articulo_data);

                ret.add(ad);
            }
            rs.close();
            ps.close();

            res = imprimirListaArticulos(ret, login);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return res;
    }

    @Override
    public Integer getIdByArticuloSucursalDeposito(Integer idarticulo, Integer idsucursal, Integer iddeposito) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "select idarticulodeposito from articulodeposito where idarticulo = ? and idsucursal = ? and iddeposito = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idarticulo);
        ps.setInt(2, idsucursal);
        ps.setInt(3, iddeposito);

        ResultSet rs = ps.executeQuery();

        Integer id;
        if (rs.next()) {
            connection.close();
            return rs.getInt("idarticulodeposito");
        } else {
            connection.close();
            return null;
        }
    }
    
    @Override
	public List<Historicoarticulo> getArticuloByTurno(Integer idturno, LoginData login) throws Exception {

		List<Historicoarticulo> list = new ArrayList<Historicoarticulo>();
		Connection conn = null;

		try {

			conn = dataSource.getConnection();

			String sql = " select 'V' as tipo, sum( a.cantidad) as venta, 0 as compra, a.idarticulo, c.descripcion as articulo, a.precioventa, e.preciocosto, c.idfamilia,  ";
			sql += " e.iddeposito, d.descripcion as familia, (a.precioventa*sum( a.cantidad)) as total,  ";
			sql += " f.descripcion as deposito, e.idarticulodeposito, e.cantidad as saldoactual ";
			sql += " from facturadet a ";
			sql += " join facturacab b on b.idfacturacab = a.idfacturacab   ";
			sql += " join articulo c on c.idarticulo = a.idarticulo   ";
			sql += " join familia d on d.idfamilia = c.idfamilia   ";
			sql += " join articulodeposito e on e.idarticulo = a.idarticulo and e.idsucursal = ? ";
			sql += " join deposito f on f.iddeposito = e.iddeposito  ";
			sql += " where b.idturno = ? and b.estado = true and c.idfamilia <> 1000  ";
			sql += " group by a.idarticulo, c.descripcion, c.idfamilia , d.descripcion , e.iddeposito,  ";
			sql += " f.descripcion, a.precioventa, e.idarticulodeposito, e.cantidad, a.preciocosto  ";
			sql += " union all ";
			sql += " select 'C' as tipo, 0 as venta, sum( a.cantidad) as compra, e.idarticulo, c.descripcion as articulo, a.precio, e.preciocosto, c.idfamilia, ";
			sql += " e.iddeposito, d.descripcion as familia, (a.precio*sum( a.cantidad)) as total,  ";
			sql += " f.descripcion as deposito, e.idarticulodeposito, e.cantidad as saldoactual ";
			sql += " from compradet a ";
			sql += " join compracab b on b.idcompracab = a.idcompracab ";
			sql += " join articulodeposito e on e.idarticulodeposito = a.idarticulodeposito and e.idsucursal = ? ";
			sql += " join articulo c on c.idarticulo = e.idarticulo ";
			sql += " join deposito f on f.iddeposito = e.iddeposito ";
			sql += " join familia d on d.idfamilia = c.idfamilia   ";
			sql += " where b.idturno = ? and b.idestadocompra in(1,2) and c.idfamilia <> 1000  ";
			sql += " group by e.idarticulo, c.descripcion, c.idfamilia , d.descripcion , e.iddeposito,  ";
			sql += " f.descripcion, a.precio, e.idarticulodeposito, e.cantidad, a.precio  ";
			sql += " order by idfamilia, articulo ";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, login.getSucursal().getIdsucursal());
			ps.setInt(2, idturno);
			ps.setInt(3, login.getSucursal().getIdsucursal());
			ps.setInt(4, idturno);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Historicoarticulo art = new Historicoarticulo();

				art.setActual(rs.getDouble("saldoactual"));

				// deposito
				Deposito deposito = new Deposito();
				deposito.setIddeposito(rs.getInt("iddeposito"));
				deposito.setDescripcion(rs.getString("deposito"));

				// familia
				Familia familia = new Familia();
				familia.setIdfamilia(rs.getInt("idfamilia"));
				familia.setDescripcion(rs.getString("familia"));

				// articulo
				Articulodeposito articulo = new Articulodeposito();
				articulo.setIdarticulodeposito(rs.getInt("idarticulodeposito"));
				Articulo ar = new Articulo();
				ar.setDescripcion(rs.getString("articulo"));
			//	ar.setFamilia(familia);
				ar.setIdfamilia(familia.getIdfamilia());
				articulo.setArticulo(ar);

				art.setDeposito(deposito);
				art.setArticulodeposito(articulo);

				art.setVenta(rs.getDouble("venta"));
				art.setEntrada(rs.getDouble("compra"));

				art.setPrecioventa(rs.getDouble("precioventa"));
				art.setPreciocosto(rs.getDouble("preciocosto"));

				// controla el anterior via movimiento de articulo
				String sql_hist = " select b.idarticulodeposito, c.descripcion, a.columna, sum(a.cantidad) as cantidad ";
				sql_hist += " from articulomovimiento a ";
				sql_hist += " join articulodeposito b on b.idarticulo = a.idarticulo and b.iddeposito = a.iddeposito ";
				sql_hist += " join articulo c on c.idarticulo = a.idarticulo ";
				sql_hist += " where (a.idturno < ? or a.idturno is null) and b.idarticulodeposito = ? ";
				sql_hist += " group by a.columna, b.idarticulodeposito, c.descripcion ";
				sql_hist += " order by b.idarticulodeposito, a.columna ";
				PreparedStatement ps_histo = conn.prepareStatement(sql_hist);
				ps_histo.setInt(1, idturno);
				ps_histo.setInt(2, rs.getInt("idarticulodeposito"));
				ResultSet rs_histo = ps_histo.executeQuery();
				Double anterior = new Double(0);
				Double ingreso = new Double(0);
				Double egreso = new Double(0);
				while (rs_histo.next()) {
					if (rs_histo.getString("columna").equals("I")) {
						ingreso += rs_histo.getDouble("cantidad");
					} else if (rs_histo.getString("columna").equals("E")) {
						egreso += rs_histo.getDouble("cantidad");
					}
				}
				anterior = ingreso - egreso;
				art.setAnterior(anterior);

				list.add(art);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
		return list;
	}
}