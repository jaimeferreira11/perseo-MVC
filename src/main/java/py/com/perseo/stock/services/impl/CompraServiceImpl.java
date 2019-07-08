package py.com.perseo.stock.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.pojos.OrdenInfo;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulodeposito;
import py.com.perseo.stock.entities.Articulomovimiento;
import py.com.perseo.stock.entities.Articuloprecioventadet;
import py.com.perseo.stock.entities.Historicopreciocosto;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.ArticuloMovimientoService;
import py.com.perseo.stock.services.ArticuloPrecioVentadetService;
import py.com.perseo.stock.services.ArticuloService;
import py.com.perseo.stock.services.CompraService;
import py.com.perseo.stock.services.FamiliaService;
import py.com.perseo.stock.services.HistoricoPrecioCostoService;
import py.com.perseo.tesoreria.beans.CompracabBean;
import py.com.perseo.tesoreria.datasources.JRDataSourceFormaPago;
import py.com.perseo.tesoreria.datasources.JRDataSourceOPDetalle;
import py.com.perseo.tesoreria.datasources.JRDataSourceOrdenPago;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.tesoreria.entities.Compradet;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.tesoreria.entities.Estadocompra;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagodet;
import py.com.perseo.tesoreria.entities.Ordenpagofp;
import py.com.perseo.tesoreria.entities.Proveedor;
import py.com.perseo.tesoreria.services.OrdenPagoCabService;
import py.com.perseo.tesoreria.services.OrdenPagofpService;
import py.com.perseo.tesoreria.services.ProveedorService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.n2tDouble;
import py.com.perseo.venta.entities.Turno;
import py.com.perseo.venta.reports.datasource.JRDataSourceLibroIvaCompra;


@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ArticuloMovimientoService articuloMovimientoService;

    @Autowired
    HistoricoPrecioCostoService historicoPrecioCostoService;

    @Autowired
    ArticuloDepositoService articuloDepositoService;
    
    @Autowired
    ArticuloService articuloService;

    @Autowired
    OrdenPagoCabService ordenPagoCabService;

    @Autowired
    OrdenPagofpService ordenPagofpService;
    
    @Autowired
	ArticuloPrecioVentadetService articuloPrecioVentadetService;
    
    @Autowired
	ProveedorService proveedorService;

    private static final Logger logger = LoggerFactory.getLogger(CompraServiceImpl.class);

    @Override
    public Compracab add(Compracab entity) throws Exception {
        em.persist(entity);
        return entity;
    }

    @Override
    public Compracab update(Compracab entity) throws Exception {
        em.merge(entity);
        return entity;
    }

    @Override
    public String delete(Integer key) throws Exception {
        Compracab entity = getById(key);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar el entity" + e.getMessage());
        }
        return "entity eliminado correctamente";

    }

    @Override
    public Compracab getById(Integer key) throws Exception {
        Connection conn = dataSource.getConnection();
        Compracab articulo = new Compracab();
        try {

            String sql = " select * ";
            sql += " from compracab ";
            sql += " where idcompracab = ?  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                articulo.setIdcompracab(rs.getInt("idcompracab"));
                articulo.setFecha(rs.getDate("fecha"));
                articulo.setTipo(rs.getString("tipo"));
                articulo.setComprobante(rs.getString("comprobante"));
                Proveedor p = new Proveedor();
                p.setIdproveedor(rs.getInt("idproveedor"));
                articulo.setProveedor(p);
                articulo.setGravada5(rs.getDouble("gravada5"));
                articulo.setGravada10(rs.getDouble("gravada10"));
                articulo.setIva5(rs.getDouble("iva5"));
                articulo.setIva10(rs.getDouble("iva10"));
                articulo.setExenta(rs.getDouble("exenta"));
                articulo.setCondicion(rs.getString("condicion"));
                articulo.setTimbrado(rs.getString("timbrado"));
                articulo.setFecvenctimbrado(rs.getDate("fecvenctimbrado"));
                articulo.setObservaciones(rs.getString("observaciones"));
                articulo.setImporte(rs.getDouble("importe"));
                articulo.setPagado(rs.getDouble("pagado"));
                Estadocompra estado = new Estadocompra();
                estado.setIdestadocompra(rs.getInt("idestadocompra"));
                articulo.setEstadocompra(estado);
                Moneda moneda = new Moneda();
                moneda.setCodmoneda(rs.getInt("codmoneda"));
                articulo.setMoneda(moneda);
                Usuario u = new Usuario();
                u.setIdusuario(rs.getInt("idusuario"));
                articulo.setUsuario(u);
                Turno turno = new Turno();
                turno.setIdturno(rs.getInt("idturno"));
                articulo.setTurno(turno);
                articulo.setCompradet(getDetallesByCompraCabId(articulo.getIdcompracab()));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articulo;
    }

    private Set<Compradet> getDetallesByCompraCabId(Integer idcompracab) throws SQLException {
        Set<Compradet> compradets = new HashSet<>();
        Connection conn = dataSource.getConnection();

        String sql = "SELECT concepto, cantidad, precio, ivaporcentaje, gravada, exenta, ivaimporte " +
                "FROM compradet where idcompracab = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idcompracab);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Compradet articulo = new Compradet();
            articulo.setConcepto(rs.getString("concepto"));
            articulo.setCantidad(rs.getDouble("cantidad"));
            articulo.setPrecio(rs.getDouble("precio"));
            articulo.setIvaporcentaje(rs.getDouble("ivaporcentaje"));
            articulo.setGravada(rs.getDouble("gravada"));
            articulo.setExenta(rs.getDouble("exenta"));
            articulo.setIvaimporte(rs.getDouble("ivaimporte"));
            compradets.add(articulo);
        }
        conn.close();

        return compradets;
    }

    @Override
    public List<Compracab> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        List<Compracab> list = new ArrayList<>();
        try {

            String sql = " select * ";
            sql += " from compracab ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Compracab articulo = new Compracab();
                articulo.setIdcompracab(rs.getInt("idcompracab"));
                articulo.setFecha(rs.getDate("fecha"));
                articulo.setTipo(rs.getString("tipo"));
                articulo.setComprobante(rs.getString("comprobante"));
                Proveedor p = new Proveedor();
                p.setIdproveedor(rs.getInt("idproveedor"));
                articulo.setProveedor(p);
                articulo.setGravada5(rs.getDouble("gravada5"));
                articulo.setGravada10(rs.getDouble("gravada10"));
                articulo.setIva5(rs.getDouble("iva5"));
                articulo.setIva10(rs.getDouble("iva10"));
                articulo.setExenta(rs.getDouble("exenta"));
                articulo.setCondicion(rs.getString("condicion"));
                articulo.setTimbrado(rs.getString("timbrado"));
                articulo.setFecvenctimbrado(rs.getDate("fecvenctimbrado"));
                articulo.setObservaciones(rs.getString("observaciones"));
                Usuario u = new Usuario();
                u.setIdusuario(rs.getInt("idusuario"));
                articulo.setUsuario(u);
                list.add(articulo);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String registrarCompra(Compracab cab, List<Compradet> detalles, LoginData login) throws Exception {
        String res = "";
        try {
            cab.setFechalog(new Timestamp(System.currentTimeMillis()));
            Estadocompra ec = new Estadocompra();
            ec.setIdestadocompra(1);
            cab.setEstadocompra(ec);
            Moneda moneda = new Moneda();
            moneda.setCodmoneda(1);
            cab.setMoneda(moneda);
            cab.setPagado(0.0);
            cab.setTurno(login.getTurno());
            cab.setSucursal(login.getSucursal());
            add(cab);
            Long id_cab = usuarioService.getLastSecuenceFromName("compracab_idcompracab_seq");
            cab.setIdcompracab(id_cab.intValue());

            int idsucursal = login.getUsuario().getSucursal().getIdsucursal();
            int iddeposito = login.getUsuario().getDeposito().getIddeposito();

            for (Compradet element : detalles) {
                Articulo articulo = articuloService.getById(element.getArticulodeposito().getArticulo().getIdarticulo()) ;
                Integer idarticulo = articulo.getIdarticulo();
                element.setCompracab(cab);
                Articulodeposito ard = new Articulodeposito();
                ard.setIdarticulodeposito(articuloDepositoService.getIdByArticuloSucursalDeposito(idarticulo, idsucursal, iddeposito));
                element.setArticulodeposito(ard);
                addCompraDet(element);
                logger.info("El id del articulo es: " + idarticulo);
                if (idarticulo > 0) {
                    articuloDepositoService.updateStock(idsucursal, iddeposito, idarticulo, element.getCantidad());
                    // registrar el movimiento del articulo
                    Articulomovimiento am = new Articulomovimiento();
                    am.setArticulo(articulo);
                    am.setDeposito(login.getUsuario().getDeposito());
                    am.setCantidad(element.getCantidad());
                    am.setColumna("I");
                    Conceptomov conceptomov = new Conceptomov();
                    conceptomov.setIdconceptomov(6);
                    am.setConceptomov(conceptomov);
                    am.setSucursal(login.getUsuario().getSucursal());
                    am.setUsuario(login.getUsuario());
                    am.setFecha(new Date(System.currentTimeMillis()));
                    am.setCompracab(cab);
                    am.setTurno(login.getTurno());
   
                    articuloMovimientoService.add(am);
                    // registrar el precio de costo
                   /* Historicopreciocosto historico = new Historicopreciocosto();
                    historico.setArticulo(articulo);
                    historico.setConcepto("Compra");
                    historico.setFecha();
                    historico.setPreciocosto();
                    historico.setUsuario(login.getUsuario());
                    historicoPrecioCostoService.add(historico);*/
                    //actualiza el precio de costo
                    Connection conn = dataSource.getConnection();
                    String sql = "insert into historicopreciocosto( fecha, concepto, preciocosto, idarticulo, idusuario) values (?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDate(1, new Date(System.currentTimeMillis()));
                    ps.setString(2, "Regitro de compra");
                    ps.setDouble(3, element.getPrecio());
                    ps.setInt(4, articulo.getIdarticulo());
                    ps.setInt(5, login.getUsuario().getIdusuario());
      
                    ps.executeUpdate();
                    ps.close();
                    conn.close();
   
                    articuloDepositoService.updatePrecioCosto(articulo);
                    
                    //actualiza el precio de venta
                    articuloPrecioVentadetService.updatePrecioByFamilia(articulo.getIdfamilia(), login.getUsuario());
                }
            }

            res = "Compra registrada correctamente: " + cab.getIdcompracab();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return res;
    }

    private void addCompraDet(Compradet element) {
        try {
            em.persist(element);
        } catch (Exception ex) {
            logger.info("Error al agregar detalle de compra " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Compracab> getCompras(String ini, String fin, Integer estado, Object[] proveedor) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select cab.*, u.usuario, p.descripcion as pdescripcion, ec.descripcion as ecdescripcion from compracab cab ");
        sql.append("join usuario u on cab.idusuario = u.idusuario ");
        sql.append("join proveedor p on cab.idproveedor = p.idproveedor ");
        sql.append("join estadocompra ec on cab.idestadocompra = ec.idestadocompra ");
        PreparedStatement ps;
        Connection conn = dataSource.getConnection();
        List<Compracab> compras = new ArrayList<>();

        if (ini != null || fin != null || estado != null || (proveedor != null && proveedor.length > 0)) {
            sql.append("where ");
            boolean appendAnd = false;
            if (ini != null && fin != null) {
                sql.append("cab.fecha between ? ::DATE and ? ::DATE ");
                appendAnd = true;
            }
            if (estado != null) {
                sql.append(appendAnd ? "and " : "");
                sql.append("cab.idestadocompra = ? ");
                appendAnd = true;
            }
            if (proveedor != null && proveedor.length > 0) {
                sql.append(appendAnd ? "and " : "");
                sql.append("cab.idproveedor in (");
                int idx = 0;
                for (Object o : proveedor) {
                    if (idx == 0) {
                        sql.append(o.toString());
                    } else {
                        sql.append(",");
                        sql.append(o.toString());
                    }
                    idx++;
                }
                sql.append(") ");
            }
        }

        sql.append("order by cab.fecha DESC");
        ps = conn.prepareStatement(sql.toString());
        int i = 1;

        if (ini != null || fin != null || estado != null || proveedor != null) {
            if (ini != null && fin != null) {
                ps.setString(i++, ini);
                ps.setString(i++, fin);
            }
            if (estado != null) {
                ps.setInt(i++, estado);
            }
        }

        logger.info(sql.toString());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Compracab c = new Compracab();
            c.setIdcompracab(rs.getInt("idcompracab"));
            Proveedor p = new Proveedor();
            p.setIdproveedor(rs.getInt("idproveedor"));
            p.setDescripcion(rs.getString("pdescripcion"));
            c.setProveedor(p);
            c.setImporte(rs.getDouble("importe"));
            c.setFecha(rs.getDate("fecha"));
            c.setTipo(rs.getString("tipo"));
            Usuario u = new Usuario();
            u.setUsuario(rs.getString("usuario"));
            u.setIdusuario(rs.getInt("idusuario"));
            c.setUsuario(u);
            Estadocompra ec = new Estadocompra();
            ec.setIdestadocompra(rs.getInt("idestadocompra"));
            ec.setDescripcion(rs.getString("ecdescripcion"));
            c.setEstadocompra(ec);
            c.setPagado(rs.getDouble("pagado"));
            c.setComprobante(rs.getString("comprobante"));
            compras.add(c);
        }

        conn.close();
        return compras;
    }

    @Override
    public String nuevaOrdenPago(String datos, LoginData loginCache) throws Exception {
        List<Compracab> compracabs = new ArrayList<>();
        try {
            JSONObject params = new JSONObject(datos);
            JSONArray compras = params.getJSONArray("compras");
            String beneficiario = "";
            for (int i = 0; i < compras.length(); i++) {
                JSONObject compra = compras.getJSONObject(i);
                int compraId = compra.getInt("id");
                double monto = compra.getInt("monto");
                Compracab compracab = getById(compraId);

                double saldo = compracab.getImporte() - compracab.getPagado();
                if (saldo < monto) {
                    throw new CustomMessageException("Monto a pagar es mayor que el saldo");
                } else if (saldo == monto) {
                    Estadocompra estado = new Estadocompra();
                    estado.setIdestadocompra(2);
                    compracab.setEstadocompra(estado);
                }
                compracab.setFechalog(new Timestamp(System.currentTimeMillis()));
                compracab.setPagado(compracab.getPagado() + monto);
                compracab.setMontoOcasional(monto);
                compracabs.add(compracab);
                Proveedor proveedor = proveedorService.getById(compracab.getProveedor().getIdproveedor());
                beneficiario = proveedor.getDescripcion() ;
            }

            String concepto = params.getString("concepto");
            double total = params.getInt("total");
            double importeRetencion = params.getInt("importeretencion");
            Boolean retencion = params.getBoolean("retencion");

            Ordenpagocab entity = new Ordenpagocab();
            entity.setConcepto(concepto);
            entity.setImporte(total);
            entity.setImporteretenido(importeRetencion);
            entity.setRetencion(retencion);
            entity.setFecha(new Date(System.currentTimeMillis()));
            entity.setUsuario(loginCache.getUsuario());
            entity.setEstado(Ordenpagocab.PENDIENTE);
            Moneda moneda = new Moneda();
            moneda.setCodmoneda(1);
            moneda.setNombre("Guaranies");
            entity.setMoneda(moneda);
            entity.setBeneficiario(beneficiario);
            entity = registrarOrdenPago(entity);
            logger.info("Nueva orden pago con ID: " + entity.getIdordenpagocab());
            
            for (Compracab compracab : compracabs) {
            	for (Compradet compradet : compracab.getCompradet()) {
            		Ordenpagodet ordenpagodet = new Ordenpagodet();
            		ordenpagodet.setOrdenpagocab(entity);
            		ordenpagodet.setConcepto(compradet.getConcepto());
            		ordenpagodet.setImporte(compradet.getCantidad() * compradet.getPrecio());
            		registrarDetallesOrdenPgo(ordenpagodet);
            		
            		
				}
				
			}
           
            
            for (Compracab c : compracabs) {
                update(c);
                insertOrdenPagoCompra(entity.getIdordenpagocab(), c.getIdcompracab(), c.getMontoOcasional());
            }

        } catch (JSONException e) {
            logger.error("JSONException en nuevaOrdenPago: " + e.getMessage());
            e.printStackTrace();
            throw new CustomMessageException(ConstantesRestAPI.ERROR_500);
        } catch (Exception e) {
            logger.error("Exception en nuevaOrdenPago: " + e.getMessage());
            e.printStackTrace();
            if (e instanceof CustomMessageException) {
                throw e;
            } else {
                throw new CustomMessageException(ConstantesRestAPI.ERROR_500);
            }
        }

        return null;
    }

    private void insertOrdenPagoCompra(Integer idordenpagocab, Integer idcompracab, double montoOcasional) throws SQLException {
        try {
            String sql = "INSERT INTO ordenpagocompra(idordenpagocab, idcompracab, importe) VALUES(?, ?, ?)";
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idordenpagocab);
            ps.setInt(2, idcompracab);
            ps.setDouble(3, montoOcasional);
            ps.execute();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Ordenpagocab registrarOrdenPago(Ordenpagocab entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Override
    public List<Ordenpagocab> getOrdenesPago(String ini, String fin, Object[] proveedor) throws SQLException {
       System.out.println("Ini: " + ini + " fin: " + fin);
    	StringBuilder sql = new StringBuilder();
        sql.append("select cab.*, u.idusuario, u.nombreapellido from ordenpagocab cab ");
        sql.append("join usuario u on cab.idusuario = u.idusuario ");
        sql.append("where cab.estado = 'PE' ");
        PreparedStatement ps;
        Connection conn = dataSource.getConnection();
        List<Ordenpagocab> ordenpagocabs = new ArrayList<>();

        if (ini != null || fin != null || (proveedor != null && proveedor.length > 0)) {
            boolean appendAnd = false;
            if (ini != null && fin != null) {
                sql.append(" and cab.fecha between ? ::DATE and ? ::DATE ");
                appendAnd = true;
            }
            /*if (proveedor != null && proveedor.length > 0) {
                sql.append(appendAnd ? "and " : "");
                sql.append("cab.idproveedor in (");
                int idx = 0;
                for (Object o : proveedor) {
                    if (idx == 0) {
                        sql.append(o.toString());
                    } else {
                        sql.append(",");
                        sql.append(o.toString());
                    }
                    idx++;
                }
                sql.append(") ");
            }*/
        }

        sql.append(" order by cab.fecha DESC, cab.idordenpagocab DESC ");
        System.out.println(sql.toString());
        ps = conn.prepareStatement(sql.toString());
        int i = 1;

        if (ini != null || fin != null || proveedor != null) {
            if (ini != null && fin != null) {
                ps.setString(i++, ini);
                ps.setString(i++, fin);
            }
        }

        logger.info(sql.toString());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Ordenpagocab c = new Ordenpagocab();
            c.setIdordenpagocab(rs.getInt("idordenpagocab"));
            c.setFecha(rs.getDate("fecha"));
            c.setImporte(rs.getDouble("importe"));
            c.setImporteretenido(rs.getDouble("importeretenido"));
            Usuario u = new Usuario();
            u.setIdusuario(rs.getInt("idusuario"));
            u.setNombreapellido(rs.getString("nombreapellido"));
            c.setUsuario(u);
            ordenpagocabs.add(c);
        }

        conn.close();
        return ordenpagocabs;
    }

    @Override
    public String confirmarOrdenPago(String datos, LoginData loginCache) throws CustomMessageException {
        String res = "";
        /* Parametros:
         * }
         * idordepagocab: 1
         * listFormaPago: [{ordenpagofp: 1 , importe: 150000, idcajacuenta: 1}],
         * }
         */
        try {
            /*JSONArray ordenes = new JSONArray(datos);
            logger.info(datos);
            logger.info(ordenes.toString());
            for (int i = 0; i < ordenes.length(); i++) {
                Integer id = Integer.valueOf(ordenes.getString(i));
                updateOrdenPagoStatus(id, Ordenpagocab.PAGADO);
            }*/
            
	         JSONObject params = new JSONObject(datos);
	   		 Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
	   	     int idordepagocab = params.getInt("idordepagocab");
	   	     TypeToken<List<Ordenpagofp>> tokenOpcion = new TypeToken<List<Ordenpagofp>>() {};
	         List<Ordenpagofp> listFormaPago = gson.fromJson(params.getString("listFormaPago"), tokenOpcion.getType());
	        //se actualiza el estado
	         updateOrdenPagoStatus(idordepagocab, Ordenpagocab.PAGADO);   
	         //se setea las formas de pago
	         for (Ordenpagofp ordenpagofp : listFormaPago) {
	        	 ordenPagofpService.add(ordenpagofp);
	  
			}

	         Ordenpagocab ordendepagocab = ordenPagoCabService.getById(idordepagocab);

            //impresion del comprobante
            res =  ImpresionOrdenPago(ordendepagocab, loginCache);

        } catch (JSONException e) {
            logger.error("JSONException en confirmarOrdenPago: " + e.getMessage());
            e.printStackTrace();
            throw new CustomMessageException(ConstantesRestAPI.ERROR_500);
        } catch (Exception e) {
            logger.error("Exception en confirmarOrdenPago: " + e.getMessage());
            e.printStackTrace();
            throw new CustomMessageException(ConstantesRestAPI.ERROR_500);
        }
        return res;
    }

    private void updateOrdenPagoStatus(Integer id, String estado) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("update ordenpagocab set estado = ? where idordenpagocab = ?");
        ps.setString(1, estado);
        ps.setInt(2, id);
        ps.execute();
        conn.close();
    }

    @Override
    public String getLibroIvaCompra(String datos, LoginData login) throws Exception {
        String res = "";

        // obtener los parametros del json enviados desde el frontend
        /*
         * {
		 * fechadesde: 11/11/2017,
		 * fechahasta: 11/11/2017,
		 * suc_todas: false,
		 * idsucursal: 1
		 * }
		 */

        JSONObject params = new JSONObject(datos);

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        // boolean iscliente = params.getBoolean("iscliente");
        // Integer puntoexpedicion= params.getInt("puntoexpedicion");
        String string_fechadesde = params.getString("fechadesde");
        Date fechadesde = new Date(sdf.parse(string_fechadesde).getTime());
        String string_fechahasta = params.getString("fechahasta");
        Date fechahasta = new Date(sdf.parse(string_fechahasta).getTime());
        // Cliente cliente = gson.fromJson(params.getString("cliente"), Cliente.class);

        Integer idsucursal = params.getInt("idsucursal");
        boolean suc_todas = params.getBoolean("suc_todas");
        // boolean factura = params.getBoolean("factura");
        // boolean remision = params.getBoolean("remision");
        // String doc = params.getString("tipo");

        Connection conn = null;
        List<CompracabBean> col = new ArrayList<CompracabBean>();

        try {

            conn = dataSource.getConnection();

            String select = " select a.idcompracab,a.comprobante,a.idasientocab, a.fecha, a.tipo,  ";
            select += " a.codmoneda, a.cotizacion, a.condicion, a.tipo, a.idproveedor, b.descripcion as proveedor, b.nrodoc, ";
            select += " a.idsucursal, c.nombre as sucursal ";
            select += " from compracab a   ";
            select += " join proveedor b on b.idproveedor = a.idproveedor ";
            select += " join sucursal c on c.idsucursal = a.idsucursal ";
            select += " where fecha between ? and ? and a.idestadocompra in (1,2) and a.tipo in ( 'FA' , 'NC', 'AU' ) ";
            if (!suc_todas) {
                select += " and a.idsucursal =  " + idsucursal;
            }
            select += " order by a.idsucursal, a.tipo, a.fecha, a.comprobante ";

            PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setDate(1, fechadesde);
            ps.setDate(2, fechahasta);


            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            int p = 0;
            while (rs.next()) {
                p++;
                CompracabBean compra_data = new CompracabBean();
                compra_data.setIdcompracab(rs.getInt("idcompracab"));
                compra_data.setComprobante(rs.getString("comprobante"));
                compra_data.setFecha(rs.getDate("fecha"));
                compra_data.setCondicion(rs.getString("condicion"));
                compra_data.setTipocompra(rs.getString("tipo"));

                Proveedor proveedor = new Proveedor();
                proveedor.setIdproveedor(rs.getInt("idproveedor"));
                proveedor.setDescripcion(rs.getString("proveedor"));
                proveedor.setNrodoc(rs.getString("nrodoc"));

                compra_data.setProveedordata(proveedor);

                // Sucursal
                Sucursal sucursal = new Sucursal();
                sucursal.setIdsucursal(rs.getInt("idsucursal"));
                sucursal.setNombre(rs.getString("sucursal"));
                compra_data.setSucursal(sucursal);

                double importe = 0;
                double gravada10 = 0;
                double gravada5 = 0;
                double iva10 = 0;
                double iva5 = 0;
                double exenta = 0;

                PreparedStatement psx = conn.prepareStatement("Select * from compradet where idcompracab = ? ");
                psx.setInt(1, rs.getInt("idcompracab"));
                ResultSet rsx = psx.executeQuery();
                int cantidad = 0;

                while (rsx.next()) {
                    if (rsx.getInt("cantidad") > 0) {
                        cantidad += rsx.getInt("cantidad");

                        importe += (rsx.getDouble("precio") * cantidad);
                        if (rsx.getDouble("ivaporcentaje") == 10) {
                            gravada10 += rsx.getDouble("gravada");
                            iva10 += rsx.getDouble("ivaimporte");
                        } else if (rsx.getDouble("ivaporcentaje") == 5) {
                            gravada5 += rsx.getDouble("gravada");
                            iva5 += rsx.getDouble("ivaimporte");
                        } else {
                            exenta += rsx.getDouble("exenta");
                        }
                    }
                }
                psx.close();
                rsx.close();

                compra_data.setImporte(importe);
                compra_data.setGravada10(gravada10);
                compra_data.setGravada5(gravada5);
                compra_data.setIva10(iva10);
                compra_data.setIva5(iva5);
                compra_data.setExenta(exenta);
                if (cantidad > 0) {
                    col.add(compra_data);
                }
            }

            rs.close();
            ps.close();

            InputStream reporte = null;
            JasperPrint print = null;
            Map<String, Object> parameters = new HashMap<String, Object>();

            if (!col.isEmpty()) {

                reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRLibroIvaCompra.jasper");

                JasperReport report = (JasperReport) JRLoader.loadObject(reporte);

                String sReporte = report.getName().substring(report.getName().lastIndexOf(".") + 1, report.getName().length());

                parameters.put("sReporte", sReporte);
                parameters.put("dFechadesde", fechadesde);
                parameters.put("dFechahasta", fechahasta);
                parameters.put("sTitulo", "LIBRO IVA COMPRA");
                parameters.put("dFecha", login.getFecha());


                //	res = Utilities.generarReporte("LibroCompra", jr, parameters, new JRDataSourceLibroIvaCompra(col));

                print = JasperFillManager.fillReport(report, parameters, new JRDataSourceLibroIvaCompra(col));

                byte[] reporteF = null;
                reporteF = JasperExportManager.exportReportToPdf(print);
                File tempFileF = File.createTempFile("Libro-Compra", ".pdf", null);
                FileOutputStream fos = new FileOutputStream(tempFileF);
                fos.write(reporteF);

                res = tempFileF.getName();


            } else {

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return res;
    }

    @Override
    public String ImpresionOrdenPago(Ordenpagocab data, LoginData loginData) throws Exception {
        String res = "";
        try {

            InputStream reporte = null;
            JasperPrint print = null;
            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("estado", data.getEstado());

            parameters.put("dFechaPago", data.getFecha());
            
           

            reporte = this.getClass().getResourceAsStream("/py/com/perseo/tesoreria/reports/JROrdenPagoProveedores.jasper");

            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);

            parameters.put("dFechaPago", new Date(System.currentTimeMillis()));
            parameters.put("fechahora", new Date(System.currentTimeMillis()));
            parameters.put("sProveedor", data.getBeneficiario());
            parameters.put("nOrdenNro", data.getIdordenpagocab());
            parameters.put("nTotalAPagar", data.getImporte());
            parameters.put("sMoneda", data.getMoneda().getNombre());
            parameters.put("sObservaciones", data.getConcepto());
            parameters.put("sPersonaUsuario", loginData.getUsuario().getNombreapellido());
            parameters.put("print", true);


            n2tDouble numero;
            String let;
            numero = new n2tDouble();
            let = numero.convertirLetras(data.getImporte().intValue());

            String x = "";
            Integer xx = new Integer(0);
            String letraDecimal = "";

           /* if (data.getMoneda().getCodmoneda().intValue() != loginData.getEmpresa().getMonedalocal().getCodmoneda().intValue()) {
                x = getDecimales(data.getImporte(), 2);
                xx = Integer.parseInt(x);
                n2tDouble decimal;
                decimal = new n2tDouble();
                letraDecimal = decimal.convertirLetras(xx.intValue());
            }*/

            if (letraDecimal == null || letraDecimal.equals("")) {
                letraDecimal = "0 (CERO)";
            }

            //	if (data.getMoneda().getCodmoneda().intValue() != loginData.getEmpresa().getMonedalocal().getCodmoneda().intValue()) {
            //		parameters.put("nTotalAPagarenLetras", let.toUpperCase() + " CON " + letraDecimal.toUpperCase() + " CENTAVOS ");
            //	} else {
            parameters.put("nTotalAPagarenLetras", let.toUpperCase());
            //	}

           // List<Ordenpagofp> colFormasPagos = new ArrayList<>();
           /// colFormasPagos = ordenPagofpService.getByOdenPagoCab(data.getIdordenpagocab());


            // SUBREPORT
            JasperReport JRFormasPagos = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/tesoreria/reports/JRFormasPagos.jasper"));
            parameters.put("JRDataSourceFormaPago", new JRDataSourceFormaPago(data.getListFormaPago(), data));
            parameters.put("JRFormasPagos", JRFormasPagos);

            // SUBREPORT OP DETALLE
            JasperReport JROpDetalle = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/tesoreria/reports/JROpDetalle.jasper"));
            parameters.put("JRDataSourceOPDetalle", new JRDataSourceOPDetalle(data.getDetalleOrden(), data));
            parameters.put("JROpDetalle", JROpDetalle);


            print = JasperFillManager.fillReport(report, parameters, new JRDataSourceOrdenPago(data.getDetalleOrden(), data));

            byte[] reporteF = null;
            reporteF = JasperExportManager.exportReportToPdf(print);
            File tempFileF = File.createTempFile("Confir-OP", ".pdf", null);
            FileOutputStream fos = new FileOutputStream(tempFileF);
            fos.write(reporteF);

            res = tempFileF.getName();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return res;

    }

    private String getDecimales(Double importe, int i) {

        Integer x = importe.intValue();
        Double result = importe - x;
        String value = result.toString();
        return value;

    }

    @Override
    public List<Compracab> getByturno(Integer idturno) throws Exception {
        Connection conn = dataSource.getConnection();
        List<Compracab> list = new ArrayList<>();
        try {

            String sql = " select a.*, p.descripcion as proveedor ";
            sql += " from compracab a";
            sql += " join proveedor p on p.idproveedor = a.idproveedor ";
            sql += " where a.idturno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idturno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Compracab articulo = new Compracab();
                articulo.setImporte(rs.getDouble("importe"));
                articulo.setIdcompracab(rs.getInt("idcompracab"));
                articulo.setFecha(rs.getDate("fecha"));
                articulo.setTipo(rs.getString("tipo"));
                articulo.setComprobante(rs.getString("comprobante"));
                Proveedor p = new Proveedor();
                p.setIdproveedor(rs.getInt("idproveedor"));
                p.setDescripcion(rs.getString("proveedor"));
                articulo.setProveedor(p);
                articulo.setGravada5(rs.getDouble("gravada5"));
                articulo.setGravada10(rs.getDouble("gravada10"));
                articulo.setIva5(rs.getDouble("iva5"));
                articulo.setIva10(rs.getDouble("iva10"));
                articulo.setExenta(rs.getDouble("exenta"));
                articulo.setCondicion(rs.getString("condicion"));
                articulo.setTimbrado(rs.getString("timbrado"));
                articulo.setFecvenctimbrado(rs.getDate("fecvenctimbrado"));
                articulo.setObservaciones(rs.getString("observaciones"));
                Turno turno = new Turno();
                turno.setIdturno(rs.getInt("idturno"));
                articulo.setTurno(turno);
                Usuario u = new Usuario();
                u.setIdusuario(rs.getInt("idusuario"));
                articulo.setUsuario(u);
                list.add(articulo);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrdenInfo> getOrdenInfo(Integer id) throws SQLException {
        Connection conn = dataSource.getConnection();
        List<OrdenInfo> list = new ArrayList<>();
        try {

            String sql = "SELECT opc.importe as importe_orden, cc.fecha, cc.comprobante, cc.tipo, " +
                    "cc.importe as importe_compra from ordenpagocompra opc " +
                    "join compracab cc on cc.idcompracab = opc.idcompracab where opc.idordenpagocab = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrdenInfo info = new OrdenInfo();
                info.setImporteCompra(rs.getDouble("importe_compra"));
                info.setImporteOrden(rs.getDouble("importe_orden"));
                info.setFecha(rs.getDate("fecha"));
                info.setTipo(rs.getString("tipo"));
                info.setComprobante(rs.getString("comprobante"));
                list.add(info);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	@Override
	public Ordenpagodet registrarDetallesOrdenPgo(Ordenpagodet ordenpagodet) throws Exception {
		 em.persist(ordenpagodet);
	     em.flush();
	     return ordenpagodet;
	}
}