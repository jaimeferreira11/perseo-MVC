 package py.com.perseo.venta.services.impl;

 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.reflect.TypeToken;
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
 import py.com.perseo.clientes.entities.Cliente;
 import py.com.perseo.comun.base.LoginData;
 import py.com.perseo.comun.entities.Empresa;
 import py.com.perseo.comun.entities.Sucursal;
 import py.com.perseo.comun.entities.Tipodocumento;
 import py.com.perseo.comun.entities.Usuario;
 import py.com.perseo.comun.services.UsuarioService;
 import py.com.perseo.stock.entities.Articulodeposito;
 import py.com.perseo.stock.entities.Articulomovimiento;
 import py.com.perseo.stock.entities.Deposito;
 import py.com.perseo.stock.services.ArticuloDepositoService;
 import py.com.perseo.stock.services.ArticuloMovimientoService;
 import py.com.perseo.tesoreria.entities.Conceptomov;
 import py.com.perseo.tesoreria.entities.Factura;
 import py.com.perseo.tesoreria.entities.Moneda;
 import py.com.perseo.utilities.n2tDouble;
 import py.com.perseo.venta.beans.FacturacabBean;
 import py.com.perseo.venta.entities.*;
 import py.com.perseo.venta.reports.datasource.JRDataSourceFactura;
 import py.com.perseo.venta.reports.datasource.JRDataSourceLibroIva;
 import py.com.perseo.venta.services.*;

 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import javax.sql.DataSource;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.sql.*;
 import java.sql.Date;
 import java.text.DateFormat;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.*;

@Service
@Transactional
public class VentaCabServiceImpl implements VentaCabService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    VentaDetService ventaDetService;
    
    @Autowired
    FacturaCabService facturaCabService;
      
    @Autowired
    FacturaDetService factureDetService;
    
    @Autowired
    FacturaFormaCobroService facturaFormaCobroService;
    

   
    @Autowired
    ArticuloMovimientoService articuloMovimientoService;
    
    @Autowired
    ArticuloDepositoService articuloDepositoService;

    private static final Logger logger = LoggerFactory.getLogger(VentaCabServiceImpl.class);

	@Override
	public Ventacab add(Ventacab entity) throws Exception {
		em.persist(entity);
		//em.flush();
		//em.refresh(entity);
        return entity;
	}

	@Override
	public Ventacab update(Ventacab entity) throws Exception {
		 em.merge(entity);
	     return entity;
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ventacab getById(Integer key) throws Exception {
		 Connection conn = dataSource.getConnection();
	        Ventacab articulo = new Ventacab();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as formapago, e.descripcion as estado, u.usuario, c.idcliente as idcli";
	            sql += " from ventacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " left join formapago f on a.idformapago = f.idformapago ";
	            sql += " join estadoventa e on a.idestadoventa = e.idestadoventa ";
	            sql += " join usuario u on a.idusuario = u.idusuario ";
	            sql += " where idventacab = ?  ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, key);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                articulo.setIdventacab(rs.getInt("idventacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                articulo.setFechafacturacion(rs.getDate("fechafacturacion"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Deposito deposito = new Deposito();
	                deposito.setIddeposito(rs.getInt("iddeposito"));
	                articulo.setDeposito(deposito);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcli"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Formapago formapago = new Formapago();
	                formapago.setIdformapago(rs.getInt("idformapago"));
	                formapago.setTipo(rs.getString("formapago"));
	                articulo.setFormapago(formapago);
	                articulo.setIdventacab(rs.getInt("idasientocab"));
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setImpuesto(rs.getDouble("impuesto"));
	                articulo.setCantidadtotal(rs.getDouble("cantidadtotal"));
	                articulo.setNroloteventa(rs.getInt("nroloteventa"));
	                articulo.setPromediodescuento(rs.getDouble("promediodescuento"));
	                articulo.setObservacion(rs.getString("observacion"));
	                articulo.setCantidadcaja(rs.getInt("cantidadcaja"));
	                articulo.setRetenido(rs.getBoolean("retenido"));
	                articulo.setMotivoretencion(rs.getString("motivoretencion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Estadoventa estado = new Estadoventa();
	                estado.setIdestadoventa(rs.getInt("idestadoventa"));
	                estado.setDescripcion(rs.getString("estado"));
	                articulo.setEstadoventa(estado);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                Usuario u = new Usuario();
	                u.setIdusuario(rs.getInt("idusuario"));
	                u.setUsuario(rs.getString("usuario"));
	                articulo.setUsuario(u);
	                articulo.setDetalleVenta(ventaDetService.getByIdVentaCab(articulo.getIdventacab()));
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return articulo;
	}

	@Override
	public List<Ventacab> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ventacab> getByEstadoAndSucursal(Integer idestado, Usuario usuario) throws Exception {
		 Connection conn = dataSource.getConnection();
	     List<Ventacab> list = new ArrayList<>();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as formapago, e.descripcion as estado, u.usuario";
	            sql += " from ventacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " left join formapago f on a.idformapago = f.idformapago ";
	            sql += " join estadoventa e on a.idestadoventa = e.idestadoventa ";
	            sql += " join usuario u on a.idusuario = u.idusuario ";
	            sql += " where a.idestadoventa = ? and a.idsucursal = ?   ";
	            sql += " order by a.idventacab desc   ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idestado);
	            ps.setInt(2, usuario.getSucursal().getIdsucursal());

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	Ventacab articulo = new Ventacab();
	                articulo.setIdventacab(rs.getInt("idventacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                articulo.setFechafacturacion(rs.getDate("fechafacturacion"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Deposito deposito = new Deposito();
	                deposito.setIddeposito(rs.getInt("iddeposito"));
	                articulo.setDeposito(deposito);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Formapago formapago = new Formapago();
	                formapago.setIdformapago(rs.getInt("idformapago"));
	                formapago.setTipo(rs.getString("formapago"));
	                articulo.setFormapago(formapago);
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setImpuesto(rs.getDouble("impuesto"));
	                articulo.setCantidadtotal(rs.getDouble("cantidadtotal"));
	                articulo.setNroloteventa(rs.getInt("nroloteventa"));
	                articulo.setPromediodescuento(rs.getDouble("promediodescuento"));
	                articulo.setObservacion(rs.getString("observacion"));
	                articulo.setCantidadcaja(rs.getInt("cantidadcaja"));
	                articulo.setRetenido(rs.getBoolean("retenido"));
	                articulo.setMotivoretencion(rs.getString("motivoretencion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Estadoventa estado = new Estadoventa();
	                estado.setIdestadoventa(rs.getInt("idestadoventa"));
	                estado.setDescripcion(rs.getString("estado"));
	                articulo.setEstadoventa(estado);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                Usuario u = new Usuario();
	                u.setIdusuario(rs.getInt("idusuario"));
	                u.setUsuario(rs.getString("usuario"));
	                articulo.setUsuario(u);
	                articulo.setDetalleVenta(ventaDetService.getByIdVentaCab(articulo.getIdventacab()));
	                
	                list.add(articulo);
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	}

	@Override
	public List<Ventacab> getByCliente(Integer idcliente, Boolean all, Integer idestado) throws Exception {
		 Connection conn = dataSource.getConnection();
	     List<Ventacab> list = new ArrayList<>();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, f.descripcion as formapago, e.descripcion as estado, u.usuario";
	            sql += " from ventacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " join formapago f on a.idformapago = f.idformapago ";
	            sql += " join estadoventa e on a.idestadoventa = e.idestadoventa ";
	            sql += " join usuario u on a.idusuario = u.idusuario ";
	            sql += " where a.idcliente = ?  ";
	            if(!all){
	            	sql += " and a.idestadoventa =   " + idestado;
	            }
	            sql += " order by a.idventacab desc   ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idcliente);


	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	Ventacab articulo = new Ventacab();
	                articulo.setIdventacab(rs.getInt("idventacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                articulo.setFechafacturacion(rs.getDate("fechafacturacion"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Deposito deposito = new Deposito();
	                deposito.setIddeposito(rs.getInt("iddeposito"));
	                articulo.setDeposito(deposito);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                articulo.setCliente(cliente);
	                Formapago formapago = new Formapago();
	                formapago.setIdformapago(rs.getInt("idformapago"));
	                formapago.setTipo(rs.getString("formapago"));
	                articulo.setIdventacab(rs.getInt("idasientocab"));
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setImpuesto(rs.getDouble("impuesto"));
	                articulo.setCantidadtotal(rs.getDouble("cantidadtotal"));
	                articulo.setNroloteventa(rs.getInt("nroloteventa"));
	                articulo.setPromediodescuento(rs.getDouble("promediodescuento"));
	                articulo.setObservacion(rs.getString("observacion"));
	                articulo.setCantidadcaja(rs.getInt("cantidadcaja"));
	                articulo.setRetenido(rs.getBoolean("retenido"));
	                articulo.setMotivoretencion(rs.getString("motivoretencion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	               
	                Estadoventa estado = new Estadoventa();
	                estado.setIdestadoventa(rs.getInt("idestadoventa"));
	                estado.setDescripcion(rs.getString("estado"));
	                articulo.setEstadoventa(estado);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                Usuario u = new Usuario();
	                u.setIdusuario(rs.getInt("idusuario"));
	                u.setUsuario(rs.getString("usuario"));
	                articulo.setUsuario(u);
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	}

	@Override
	public String registrarPedido(Ventacab ventacab, LoginData login) throws Exception {
		String res = "";
		try {
			ventacab.setUsuario(login.getUsuario());
			ventacab.setEmpresa(login.getEmpresa());
			ventacab.setFecha(new Date(System.currentTimeMillis()));
			ventacab.setSucursal(login.getUsuario().getSucursal());
			ventacab.setDeposito(login.getUsuario().getDeposito());
			Estadoventa estado = new Estadoventa();
			estado.setIdestadoventa(1);
			ventacab.setEstadoventa(estado);
			Moneda moneda = new Moneda();
			moneda.setCodmoneda(1);
			ventacab.setMoneda(moneda);
			ventacab.setTurno(login.getTurno());
			
			add(ventacab);
			
			Long id_cab = usuarioService.getLastSecuenceFromName("ventacab_idventacab_seq");
			ventacab.setIdventacab(id_cab.intValue());
			
			 for (Ventadet element : ventacab.getDetalleVenta()) {
		            element.setVentacab(ventacab);
		           ventaDetService.add(element);
		           
		          
	    			
		        }
			 res= "Pedido nro "+ventacab.getIdventacab() + " registrada correctamente";

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	@Override
	public String grabarVenta(String datos, LoginData login) throws Exception {
		String res = "";
		Connection conn = dataSource.getConnection();
		
		 JSONObject params = new JSONObject(datos);
		 Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
	     int idtipofactura = params.getInt("condicion");
	     int idventacab = params.getInt("idventacab");
	     TypeToken<List<Facturaformacobro>> tokenOpcion = new TypeToken<List<Facturaformacobro>>() {};
         List<Facturaformacobro> lisFacturafp = gson.fromJson(params.getString("listFormaPago"), tokenOpcion.getType());
         
         
         
		try {
			Ventacab ventacab = getById(idventacab);
			ventacab.setIdventacab(idventacab);
			System.out.println("ID CLIENTE: " + ventacab.getCliente().getIdcliente());
			Facturacab facturacab = new Facturacab();
			facturacab.setFecha(new Date(System.currentTimeMillis()));
			facturacab.setCliente(ventacab.getCliente());
			Tipofactura tipofactura =  new Tipofactura();
			tipofactura.setIdtipofactura(idtipofactura);
			facturacab.setTipofactura(tipofactura);
			facturacab.setClasefactura(params.getString("clasefactura"));
			facturacab.setMoneda(ventacab.getMoneda());
			facturacab.setSucursal(ventacab.getSucursal());
			Factura fac = facturaCabService.getSigteFacturaByCaja(1, 1, login.getUsuario().getCaja().getIdcaja());
			facturacab.setEstablecimiento(fac.getEstablecimiento());
			facturacab.setPuntoexpedicion(fac.getPuntoexpedicion());
			facturacab.setNrofactura(fac.getNrofactura());
			facturacab.setImporte(ventacab.getImporte());
			facturacab.setEmpresa(ventacab.getEmpresa());
			facturacab.setObservacion(ventacab.getObservacion());
			facturacab.setSaldo(new Double(0));
			facturacab.setVentacab(ventacab);
			facturacab.setUsuario(ventacab.getUsuario());
			facturacab.setTurno(login.getTurno());
			
			Factura factura = facturaCabService.getSigteFacturaByCaja(1, 1, login.getUsuario().getCaja().getIdcaja());
			facturacab.setTimbrado(factura.getTimbrado());
			facturacab.setVigenciatimbrado(factura.getVigenciahasta());
			
			
		//	facturacab.setMetodocobro(metodocobro);
			if(idtipofactura == 1){				
				facturacab.setEstado(true);
			}else{
				facturacab.setEstado(false);
			}
			
			// insertar facturacab
			facturaCabService.add(facturacab);
			
			Long id_cab = usuarioService.getLastSecuenceFromName("facturacab_idfacturacab_seq");
			facturacab.setIdfacturacab(id_cab.intValue());
			
			//insertar las formas de pago
			for (Facturaformacobro facturaformacobro : lisFacturafp) {
				facturaformacobro.setFacturacab(facturacab);
	        	 facturaFormaCobroService.add(facturaformacobro);
	         }
			
			//insertar detalles de la factura
			List<Ventadet> detalles = ventaDetService.getByIdVentaCab(ventacab.getIdventacab());
			List<Facturadet> detalleFactura =  new ArrayList<>();
			for (Ventadet element : detalles) {
				Facturadet facturadet = new Facturadet();
				facturadet.setFacturacab(facturacab);
				facturadet.setArticulo(element.getArticulo());
				facturadet.getArticulo().setTipoimpuesto(element.getTipoimpuesto());
				facturadet.setCantidad(element.getCantidad());
				facturadet.setPrecioventa(element.getPrecioventa());
				facturadet.setPreciocosto(element.getPreciocosto());
				facturadet.setTotal(element.getTotal());
				facturadet.setDescuento(element.getImportedescuento());
				if(element.getTipoimpuesto().getIdtipoimpuesto() == 5){
					
					//element.getTipoimpuesto().setTasa(new Double(5));
					facturadet.setIva5(element.getImpuesto());
					facturadet.setGravada5((element.getTotal() - element.getImpuesto()));
					//cerar demas
					facturadet.setIva10(new Double(0));
					facturadet.setGravada10(new Double(0));
					facturadet.setExenta(new Double(0));
				}else if(element.getTipoimpuesto().getIdtipoimpuesto() == 10){
					//element.getTipoimpuesto().setTasa(new Double(10));
					facturadet.setIva10(element.getImpuesto());
					facturadet.setGravada10((element.getTotal() - element.getImpuesto()));
					//
					facturadet.setIva5(new Double(0));
					facturadet.setGravada5(new Double(0));
					facturadet.setExenta(new Double(0));
				}else if(element.getTipoimpuesto().getIdtipoimpuesto() == 100){
					//element.getTipoimpuesto().setTasa(new Double(0));
					facturadet.setExenta(element.getTotal());
					//
					facturadet.setIva5(new Double(0));
					facturadet.setGravada5(new Double(0));
					facturadet.setIva10(new Double(0));
					facturadet.setGravada10(new Double(0));
				}
				detalleFactura.add(facturadet);
				factureDetService.add(facturadet);
				
				 //registra el movimiento
		        Articulomovimiento am = new Articulomovimiento();
	    		am.setArticulo(element.getArticulo());
	    		am.setDeposito(login.getUsuario().getDeposito());
	    		am.setCantidad(element.getCantidad());
	    		am.setColumna("E");
	    		Conceptomov conceptomov = new Conceptomov();
	    		conceptomov.setIdconceptomov(5);
	    		am.setConceptomov(conceptomov);
	    		am.setSucursal(login.getUsuario().getSucursal());
	    		am.setUsuario(login.getUsuario());
	    		am.setTurno(login.getTurno());
	    		am.setFecha(new Date(System.currentTimeMillis()));
	    		am.setVentacab(ventacab);
	    		articuloMovimientoService.add(am);
	    			
	    		//descuenta el stock
	    		Articulodeposito articulodeposito = articuloDepositoService.getByArticuloAndDeposito(element.getArticulo().getIdarticulo(), login.getUsuario().getDeposito().getIddeposito());
	    		articulodeposito.setCantidad(articulodeposito.getCantidad() - element.getCantidad());
	    		//articuloDepositoService.update(articulodeposito);
	    		 String sql3 = "update articulodeposito set cantidad = ?  where idarticulodeposito = ?";
	             PreparedStatement ps3 = conn.prepareStatement(sql3);
	             ps3.setDouble(1, articulodeposito.getCantidad());
	             ps3.setInt(2, articulodeposito.getIdarticulodeposito());
	             ps3.executeUpdate();
				
			}
			facturacab.setDetalleFactura(detalleFactura);
			//actualizar la ventacab
			ventacab.setFechafacturacion(new Date(System.currentTimeMillis()));
			Estadoventa estadoventa = new Estadoventa();
			estadoventa.setIdestadoventa(2);
			ventacab.setEstadoventa(estadoventa);
			//update(ventacab);
			//actualiza el precio de costo en articulo (no se usa)
            String sql2 = "update ventacab set fechafacturacion = ?, idestadoventa = ?  where idventacab = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setDate(1, ventacab.getFechafacturacion());
            ps2.setInt(2, ventacab.getEstadoventa().getIdestadoventa());
            ps2.setInt(3, idventacab);
            ps2.executeUpdate();

			
			//res = "Venta nro. " +facturacab.getIdfacturacab() + " facturada correctamente";
			res = imprimirTicket(facturacab, login);
			//res = imprimirNormal(facturacab);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	
	@Override
	public String getLibroIvaVentaContable(String datos, LoginData login) throws Exception {
		
		String res= "";
		OutputStream outputStream = null;
		InputStream in = null;
		
		 JSONObject params = new JSONObject(datos);
		 
		 DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		 
		 boolean iscliente = params.getBoolean("iscliente");
		 Integer puntoexpedicion= params.getInt("puntoexpedicion");
		 String string_fechadesde  = params.getString("fechadesde");
		 Date fechadesde = new Date(sdf.parse(string_fechadesde).getTime());
		 String string_fechahasta  = params.getString("fechahasta");
		 Date fechahasta = new Date(sdf.parse(string_fechahasta).getTime());
		 Cliente cliente = gson.fromJson(params.getString("cliente"), Cliente.class);
		 
		 Integer suc_desde = params.getInt("idsucursal");
		 boolean suc_todas = params.getBoolean("suc_todas");
		 boolean factura = params.getBoolean("factura");
		 boolean remision = params.getBoolean("remision");
		 String doc = params.getString("tipo");
		
		Connection conn = null;
		List<FacturacabBean> col = new ArrayList<FacturacabBean>();

		try {

			conn = dataSource.getConnection();

			String select = " select a.estado, a.idfacturacab, a.fecha, b.nombreapellido, a.codmoneda, a.establecimiento, a.puntoexpedicion, ";
			select += " a.nrofactura, a.importe, a.idtipofactura, a.idcliente, b.nrodoc, a.idsucursal, c.nombre as sucursal, a.cotizacion ";
			select += " from facturacab a  ";
			select += " join cliente b on b.idcliente = a.idcliente ";
			select += " join sucursal c on c.idsucursal = a.idsucursal ";
			select += " where a.fecha between ? and ?  ";
			if(!suc_todas){
				select += " and a.idsucursal =  " + suc_desde;
			}
			if (factura && remision) {
				select += " and a.clasefactura in( 'F','R','N') ";
			}
			if (remision && !factura) {
				select += " and a.clasefactura = 'R' ";
			}
			if (!remision && factura) {
				select += " and a.clasefactura in ( 'F', 'N' ) ";
			}

			if (puntoexpedicion.intValue() > 0) {
				select += " and a.puntoexpedicion = ? ";
			}

			//select += " and a.estado = true ";

			if (iscliente) {
				select += " and a.idcliente = ? ";
			}

			select += " order by a.idsucursal, a.fecha, a.idtipofactura, a.puntoexpedicion, a.nrofactura ";

			PreparedStatement ps = conn.prepareStatement(select);
			ps.setDate(1, fechadesde);
			ps.setDate(2, fechahasta);
			//ps.setInt(3, suc_desde);
			int count = 3;
			if (puntoexpedicion.intValue() > 0) {
				ps.setInt(count++, puntoexpedicion);
			}
			if (iscliente) {
				ps.setInt(count++, cliente.getIdcliente());
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				FacturacabBean bean = new FacturacabBean();

				bean.setEstado(rs.getBoolean("estado"));
				bean.setIdfacturacab(rs.getInt("idfacturacab"));
				bean.setFecha(rs.getDate("fecha"));
				bean.setNrodocumento(rs.getString("nrodoc"));
				
				if (!rs.getBoolean("estado")) {
					bean.setPersona("ANULADO");
				}else{
					bean.setPersona(rs.getString("nombreapellido"));
				}
				
				Moneda moneda = new Moneda();
				moneda.setCodmoneda(rs.getInt("codmoneda"));
				bean.setMoneda(moneda);
				bean.setEstablecimiento(rs.getInt("establecimiento"));
				bean.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
				bean.setNrofactura(rs.getLong("nrofactura"));
				Tipofactura tipofactura = new Tipofactura();
				tipofactura.setIdtipofactura(rs.getInt("idtipofactura"));
				bean.setTipofactura(tipofactura);
				Sucursal sucursal = new Sucursal();
				sucursal.setIdsucursal(rs.getInt("idsucursal"));
				sucursal.setNombre(rs.getString("sucursal"));
				bean.setSucursal(sucursal);

				// Busca el detalle
				String detalle = " Select gravada5, iva5, gravada10, iva10, exenta ";
				detalle += " from facturadet ";
				detalle += " where idfacturacab = ? ";

				PreparedStatement psx = conn.prepareStatement(detalle);
				psx.setInt(1, rs.getInt("idfacturacab"));
				ResultSet rsx = psx.executeQuery();

				Double gravada10 = new Double(0);
				Double gravada5 = new Double(0);
				Double iva10 = new Double(0);
				Double iva5 = new Double(0);
				Double exenta = new Double(0);

				while (rsx.next()) {
					if (rs.getInt("codmoneda") == 1) {
						iva10 += rsx.getDouble("iva10");
						gravada10 += rsx.getDouble("gravada10");
						iva5 += rsx.getDouble("iva5");
						gravada5 += rsx.getDouble("gravada5");
						exenta += rsx.getDouble("exenta");
					}else{
						iva10 += Math.round((rsx.getDouble("iva10") * rs.getDouble("cotizacion"))) ;
						gravada10 += Math.round(rsx.getDouble("gravada10") * rs.getDouble("cotizacion"));
						iva5 += Math.round(rsx.getDouble("iva5") * rs.getDouble("cotizacion"));
						gravada5 += Math.round(rsx.getDouble("gravada5") * rs.getDouble("cotizacion"));
						exenta += Math.round(rsx.getDouble("exenta") * rs.getDouble("cotizacion"));
					}
				}

				psx.close();
				rsx.close();

				bean.setIva10(iva10);
				bean.setIva5(iva5);
				bean.setExenta(exenta);
				bean.setGravada10(gravada10);
				bean.setGravada5(gravada5);

				bean.setImporte(iva10 + iva5 + exenta + gravada10 + gravada5);

				col.add(bean);
				

			}

			ps.close();
			rs.close();
			InputStream reporte = null;
			JasperPrint print = null;
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			if (!col.isEmpty()) {
				
				if (doc.equals("P")) {
					//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("JRLibroIva.jasper"));
					 reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRLibroIVAVenta.jasper");
				}else if (doc.equals("E")) {
					//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("LibroVentaExcel.jasper"));
					reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/LibroVentaExcel.jasper");
				}
				JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
				
				String sReporte = report.getName().substring(report.getName().lastIndexOf(".") + 1, report.getName().length());
				parameters.put("sReporte", sReporte);
				parameters.put("dFechadesde", fechadesde);
				parameters.put("dFechahasta", fechahasta);
				parameters.put("sTitulo", "LIBRO VENTAS");
				parameters.put("dFecha", login.getFecha());
				String tipo = "";
				if (factura) {
					tipo = "Facturas ";
				}
				if (remision) {
					tipo = tipo + " - Remisiones";
				}
				
				parameters.put("tipo", tipo);
			
				
				print = JasperFillManager.fillReport(report, parameters, new JRDataSourceLibroIva(col));
				
				byte[] reporteF = null;
				reporteF = JasperExportManager.exportReportToPdf(print);
				File tempFileF = File.createTempFile("Libro-venta", ".pdf", null);
				FileOutputStream fos = new FileOutputStream(tempFileF);
				fos.write(reporteF);
				
				res= tempFileF.getName();
				
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
	public String imprimirTicket(Facturacab data, LoginData loginData) throws Exception{
		String r = "";
		try {
			JasperReport jr = null;
			InputStream reporte = null;
			JasperPrint print = null;
			System.out.println("TIPO FACTURA: " + data.getTipofactura().getIdtipofactura());
			if (data.getTipofactura().getIdtipofactura() == 2) {
				reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFacturaTicketCred.jasper");
				//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFacturaTicketCred.jasper"));
			} else {
				//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFacturaTicketII.jasper"));
				reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFacturaTicketII.jasper");
			}
			JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			// datos de la empresa
			parameters.put("empresa", loginData.getEmpresa().getDescripcion());
			parameters.put("rucemp", loginData.getEmpresa().getRuc());
			parameters.put("direccion", loginData.getSucursal().getDireccion());
			parameters.put("telefono", loginData.getSucursal().getTelefono1());
			parameters.put("timbrado", data.getTimbrado().toString());
			parameters.put("timbradofin", data.getVigenciatimbrado());
			parameters.put("sRazonSocial", data.getCliente().getNombreapellido());
			parameters.put("sDocumento", data.getCliente().getNrodoc());
			parameters.put("usuario", data.getUsuario().getUsuario());
			String tipo = "";
			if (data.getTipofactura().getIdtipofactura() == 1) {
				tipo = "CONTADO";
			} else if (data.getTipofactura().getIdtipofactura() == 2) {
				tipo = "CREDITO";
			}
			parameters.put("tipo", tipo);
			parameters.put("sFecha", data.getFecha());
			// format comprobante
			DecimalFormat df = new DecimalFormat("000");
			DecimalFormat dfnro = new DecimalFormat("0000000");
			Integer valor = new Integer(data.getEstablecimiento());
			String establecimiento = df.format(valor);
			String puntoexpedicion = df.format(data.getPuntoexpedicion());
			String nrofactura = dfnro.format(data.getNrofactura());
			parameters.put("sNroFactura", establecimiento + " - " + puntoexpedicion + " - " + nrofactura);
			Double sub_total_10 = new Double(0);
			Double sub_total_5 = new Double(0);
			Double sub_total_Ex = new Double(0);
			Double iva10 = new Double(0);
			Double iva5 = new Double(0);
			Double total_iva = new Double(0);
			for (Iterator iterator = data.getDetalleFactura().iterator(); iterator.hasNext();) {
				Facturadet det = (Facturadet) iterator.next();
				iva10 += det.getIva10();
				iva5 += det.getIva5();
				sub_total_10 += det.getGravada10() + det.getIva10();
				sub_total_5 += det.getGravada5() + det.getIva5();
				sub_total_Ex += det.getExenta();
			}
			parameters.put("nSubTotalEx", sub_total_Ex);
			parameters.put("nSubTotal5", sub_total_5);
			parameters.put("nSubTotal10", sub_total_10);
			parameters.put("hora", new Timestamp(System.currentTimeMillis()));
			parameters.put("nIva5", iva5);
			parameters.put("nIva10", iva10);
			parameters.put("nTotalIva", iva5 + iva10);
			parameters.put("nTotal", data.getImporte());
			String res = "";
			n2tDouble numero;
			numero = new n2tDouble();
			String let;
			res = numero.convertirLetras(data.getImporte().intValue());
			parameters.put("sLetras", res.toUpperCase() + "###");
			
			print = JasperFillManager.fillReport(report, parameters, new JRDataSourceFactura(data.getDetalleFactura()));
			
			byte[] reporteF = null;
			reporteF = JasperExportManager.exportReportToPdf(print);
			File tempFileF = File.createTempFile("Fact", ".pdf", null);
			FileOutputStream fos = new FileOutputStream(tempFileF);
			fos.write(reporteF);
			
			r= tempFileF.getName();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return r;
	}
	
	@Override
	public String imprimirNormal(Facturacab data) throws Exception{
		String r = "";
		try {

			JasperReport jr = null;
			InputStream reporte = null;
			JasperPrint print = null;
			//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFactura.jasper"));

			reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFactura.jasper");

			JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sRazonSocial", data.getCliente().getNombreapellido());
			parameters.put("sDocumento", data.getCliente().getNrodoc());

			if (data.getTipofactura().getIdtipofactura() == 1) {
				parameters.put("sCredito", "");
				parameters.put("sContado", "X");
			} else if (data.getTipofactura().getIdtipofactura() == 2) {
				parameters.put("sContado", "");
				parameters.put("sCredito", "X");
			}

			SimpleDateFormat formato = new SimpleDateFormat("dd ' de ' MMMMM ' de ' yyyy");
			String cadenaFecha = formato.format(data.getFecha());
			parameters.put("sFecha", cadenaFecha);
			parameters.put("sNroFactura", data.getEstablecimiento() + "-" + data.getPuntoexpedicion() + "-" + data.getNrofactura());

			Double sub_total_10 = new Double(0);
			Double sub_total_5 = new Double(0);
			Double sub_total_Ex = new Double(0);

			Double iva10 = new Double(0);
			Double iva5 = new Double(0);
			Double total_iva = new Double(0);

			for (Iterator iterator = data.getDetalleFactura().iterator(); iterator.hasNext();) {
				Facturadet det = (Facturadet) iterator.next();
				iva10 += det.getIva10();
				iva5 += det.getIva5();
				sub_total_10 += (det.getGravada10() + det.getIva10());
				sub_total_5 += (det.getGravada5() + det.getIva5());
				sub_total_Ex += det.getExenta();
			}

			parameters.put("nSubTotalEx", sub_total_Ex);
			parameters.put("nSubTotal5", sub_total_5);
			parameters.put("nSubTotal10", sub_total_10);

			parameters.put("nIva5", iva5);
			parameters.put("nIva10", iva10);
			parameters.put("nTotalIva", iva5 + iva10);

			parameters.put("nTotal", data.getImporte());

			String res = "";
			n2tDouble numero;
			numero = new n2tDouble();
			String let;
			res = numero.convertirLetras(data.getImporte().intValue());
			parameters.put("sLetras", res.toUpperCase() + "###");
			
			print = JasperFillManager.fillReport(report, parameters, new JRDataSourceFactura(data.getDetalleFactura()));
			
			byte[] reporteF = null;
			reporteF = JasperExportManager.exportReportToPdf(print);
			File tempFileF = File.createTempFile("Fact", ".pdf", null);
			FileOutputStream fos = new FileOutputStream(tempFileF);
			fos.write(reporteF);
			
			r= tempFileF.getName();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return r;
	}

   
}