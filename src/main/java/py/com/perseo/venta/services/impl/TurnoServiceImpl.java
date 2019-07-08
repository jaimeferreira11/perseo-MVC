package py.com.perseo.venta.services.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.stock.entities.Historicoarticulo;
import py.com.perseo.stock.reports.datasourse.JRDataSourceArticulos;
import py.com.perseo.stock.reports.datasourse.JRDataSourceCompras;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.CompraService;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.entities.Facturaformacobro;
import py.com.perseo.venta.entities.Turno;
import py.com.perseo.venta.reports.datasource.JRDataSourceFP;
import py.com.perseo.venta.reports.datasource.JRDataSourceFacturas;
import py.com.perseo.venta.services.FacturaCabService;
import py.com.perseo.venta.services.FacturaDetService;
import py.com.perseo.venta.services.TurnoService;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    FacturaDetService facturaDetService;
    
    @Autowired
    FacturaCabService facturaCabService;
    
    @Autowired
    CompraService compraService;
    
    @Autowired
    ArticuloDepositoService articuloDepositoService;

    private static final Logger logger = LoggerFactory.getLogger(TurnoServiceImpl.class);

	@Override
	public Turno add(Turno entity) throws Exception {
		em.persist(entity);
        return entity;
	}

	@Override
	public Turno update(Turno entity) throws Exception {
			Connection conn = dataSource.getConnection();
			try {

				
				//actualiza el precio de costo en articulo (no se usa)
	            String sql2 = "update turno set estado = ?, fechacierre = ?  where idturno = ?";
	            PreparedStatement ps2 = conn.prepareStatement(sql2);
	            ps2.setBoolean(1, false);
	            ps2.setTimestamp(2, entity.getFechacierre());
	            ps2.setInt(3, entity.getIdturno());
	            ps2.executeUpdate();

	            ps2.close();
	            conn.close();

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return entity;
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Turno getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		Turno entity = new Turno(); 
	    try {

	           String sql = " select a.* , b.nombre as sucursal, c.usuario ";
	            sql += " from Turno a ";
	            sql += " join sucursal b on a.idsucursal = b.idsucursal ";
	            sql += " join usuario c on a.idusuario = c.idusuario ";
	            sql += " where a.idturno = ? ";

	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, key);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	 
	            	 entity.setIdturno(rs.getInt("idturno"));
	            	 entity.setFecha(rs.getDate("fecha"));
	            	 entity.setFechaapertura(rs.getTimestamp("fechaapertura"));
	            	 entity.setFechacierre(rs.getTimestamp("fechacierre"));
	            	 Sucursal sucursal = new Sucursal();
	            	 sucursal.setIdsucursal(rs.getInt("idsucursal"));
	            	 sucursal.setNombre(rs.getString("sucursal"));
	            	 entity.setSucursal(sucursal);
	            	 Usuario usuario = new Usuario();
	            	 usuario.setIdusuario(rs.getInt("idusuario"));
	            	 usuario.setUsuario(rs.getString("usuario"));
	            	 entity.setUsuario(usuario);
	            	 entity.setFechaultapertura(rs.getDate("fechaultapertura"));
	            	 entity.setFechaultcierre(rs.getDate("fechaultcierre"));
	            	 entity.setTotalefectivo(rs.getDouble("totalefectivo"));
	            	 entity.setTotalefectivo(rs.getDouble("totalcheque"));
	            	 entity.setTotaltarjeta(rs.getDouble("totaltarjeta"));
	            	 entity.setCantventa(rs.getDouble("cantventa"));
	            	 entity.setCantventanula(rs.getDouble("cantventanula"));
	            	 entity.setCantcompra(rs.getDouble("cantcompra"));
	            	 entity.setCantcompranula(rs.getDouble("cantcompranula"));
	            	 entity.setEstado(rs.getBoolean("estado"));
	            	 entity.setTipoturno(rs.getString("tipoturno"));
	            	 
	              
	            }
	            ps.close();
	            rs.close();
	            conn.close();
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	    return entity;
	}

	@Override
	public List<Turno> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Turno> getByUsuario(Integer idusuario, Integer limit) throws Exception {
		Connection conn = dataSource.getConnection();
		List<Turno> list = new ArrayList<>();
	    try {

	           String sql = " select a.* , b.nombre as sucursal, c.usuario ";
	            sql += " from Turno a ";
	            sql += " join sucursal b on a.idsucursal = b.idsucursal ";
	            sql += " join usuario c on a.idusuario = c.idusuario ";
	            sql += " where a.idusuario = ? and a.estado = false ";
	            sql += " order by a.idturno desc limit ? ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idusuario);
	            ps.setInt(2, limit);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	 Turno entity = new Turno(); 
	            	 entity.setIdturno(rs.getInt("idturno"));
	            	 entity.setFecha(rs.getDate("fecha"));
	            	 entity.setFechaapertura(rs.getTimestamp("fechaapertura"));
	            	 entity.setFechacierre(rs.getTimestamp("fechacierre"));
	            	 Sucursal sucursal = new Sucursal();
	            	 sucursal.setIdsucursal(rs.getInt("idsucursal"));
	            	 sucursal.setNombre(rs.getString("sucursal"));
	            	 entity.setSucursal(sucursal);
	            	 Usuario usuario = new Usuario();
	            	 usuario.setIdusuario(rs.getInt("idusuario"));
	            	 usuario.setUsuario(rs.getString("usuario"));
	            	 entity.setUsuario(usuario);
	            	 entity.setFechaultapertura(rs.getDate("fechaultapertura"));
	            	 entity.setFechaultcierre(rs.getDate("fechaultcierre"));
	            	 entity.setTotalefectivo(rs.getDouble("totalefectivo"));
	            	 entity.setTotalefectivo(rs.getDouble("totalcheque"));
	            	 entity.setTotaltarjeta(rs.getDouble("totaltarjeta"));
	            	 entity.setCantventa(rs.getDouble("cantventa"));
	            	 entity.setCantventanula(rs.getDouble("cantventanula"));
	            	 entity.setCantcompra(rs.getDouble("cantcompra"));
	            	 entity.setCantcompranula(rs.getDouble("cantcompranula"));
	            	 entity.setEstado(rs.getBoolean("estado"));
	            	 entity.setTipoturno(rs.getString("tipoturno"));
	            	 
	            	 
	            	 list.add(entity);
	              
	            }
	            rs.close();
	            ps.close();
	            conn.close();
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	    return list;
	}

	@Override
	public Turno getByUsuarioActivo(Integer idusuario) throws Exception {
		logger.info("Buscando el turno");
		Connection conn = dataSource.getConnection();
		Turno entity = new Turno(); 
	    try {

	           String sql = " select a.* , b.nombre as sucursal, c.usuario ";
	            sql += " from Turno a ";
	            sql += " join sucursal b on a.idsucursal = b.idsucursal ";
	            sql += " join usuario c on a.idusuario = c.idusuario ";
	            sql += " where a.idusuario = ? and a.estado = true ";

	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idusuario);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	 
	            	 entity.setIdturno(rs.getInt("idturno"));
	            	 entity.setFecha(rs.getDate("fecha"));
	            	 entity.setFechaapertura(rs.getTimestamp("fechaapertura"));
	            	 entity.setFechacierre(rs.getTimestamp("fechacierre"));
	            	 Sucursal sucursal = new Sucursal();
	            	 sucursal.setIdsucursal(rs.getInt("idsucursal"));
	            	 sucursal.setNombre(rs.getString("sucursal"));
	            	 entity.setSucursal(sucursal);
	            	 Usuario usuario = new Usuario();
	            	 usuario.setIdusuario(rs.getInt("idusuario"));
	            	 usuario.setUsuario(rs.getString("usuario"));
	            	 entity.setUsuario(usuario);
	            	 entity.setFechaultapertura(rs.getDate("fechaultapertura"));
	            	 entity.setFechaultcierre(rs.getDate("fechaultcierre"));
	            	 entity.setTotalefectivo(rs.getDouble("totalefectivo"));
	            	 entity.setTotalefectivo(rs.getDouble("totalcheque"));
	            	 entity.setTotaltarjeta(rs.getDouble("totaltarjeta"));
	            	 entity.setCantventa(rs.getDouble("cantventa"));
	            	 entity.setCantventanula(rs.getDouble("cantventanula"));
	            	 entity.setCantcompra(rs.getDouble("cantcompra"));
	            	 entity.setCantcompranula(rs.getDouble("cantcompranula"));
	            	 entity.setEstado(rs.getBoolean("estado"));
	            	 entity.setTipoturno(rs.getString("tipoturno"));
	            	 
	              
	            }
	            rs.close();
	            ps.close();
	            conn.close();
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	    System.out.println("Turno: " + entity.getIdturno());
	    return entity;
	}

	@Override
	public String getTurnoMovimiento(Turno turno, LoginData login) throws Exception {
		String r = "";
		try {
			turno = getById(turno.getIdturno());
			JasperReport jr = null;
			InputStream reporte = null;
			JasperPrint print = null;
			//jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFactura.jasper"));

			reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimiento.jasper");

			JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
			
			//String reporte = "JRTurnoMovimiento";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sTitulo", "MOVIMIENTO DE TURNO");
			parameters.put("turno", turno.getIdturno().toString());
			parameters.put("fechaapertura", turno.getFechaapertura());
			parameters.put("fechacierre", turno.getFechacierre());

			parameters.put("usuariodes", login.getUsuario().getNombreapellido());
			parameters.put("ciuser", login.getUsuario().getNrodoc());

			String responsables = "";

			/*for (int i = 0; i < getJTableResponsables().getRowCount(); i++) {
				responsables += getJTableResponsables().getValueAt(i, 0) + " - ";
			}*/

			//parameters.put("integrantes", responsables);
			parameters.put("integrantes", login.getUsuario().getNombreapellido());

			parameters.put("fechacierre", turno.getFechacierre());

			//traer las facturas por turno
			List<Facturacab> facturas = facturaCabService.getByturno(turno.getIdturno());
	

			List<Historicoarticulo> listArticulos = articuloDepositoService.getArticuloByTurno(turno.getIdturno(), login);
	

			parameters.put("isfactura", true);
			System.out.println("imprimiendo la cantidad de fact.  " + facturas.size());
			reporte = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimiento.jasper");
			

			InputStream reporte_1 = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimientoFactura.jasper");
			JasperReport JRSubFacturas = (JasperReport) JRLoader.loadObject(reporte_1);
			
			parameters.put("JRDataSourceFacturas", new JRDataSourceFacturas(facturas));
			parameters.put("JRSubFacturas", JRSubFacturas);

			//cambiar por movimientos de articulos
			///JasperReport JRSubArticulos = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimientoArticulos.jasper"));
			
			InputStream reporte_2 = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimientoArticulos.jasper");
			JasperReport JRSubArticulos = (JasperReport) JRLoader.loadObject(reporte_2);
			
			parameters.put("JRDataSourceArticulos", new JRDataSourceArticulos(listArticulos));
			parameters.put("JRSubArticulos", JRSubArticulos);

			List<Facturaformacobro> listFormaPago = new ArrayList<Facturaformacobro>();
			for (Facturacab element : facturas) {
				for (Facturaformacobro facturaformacobro : element.getListFormaPago()) {					
					listFormaPago.add(facturaformacobro);
				}
			}
			
			InputStream reporte_3 = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFormapago.jasper");
			JasperReport JRSubFormapago = (JasperReport) JRLoader.loadObject(reporte_3);
			
			//JasperReport JRSubFormapago = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRFormapago.jasper"));
			parameters.put("JRDataSourceFP", new JRDataSourceFP(listFormaPago));
			parameters.put("JRSubFormapago", JRSubFormapago);

			List<Compracab> listGastos = compraService.getByturno(turno.getIdturno());

			InputStream reporte_4 = this.getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimientoCompras.jasper");
			JasperReport JRSubCompras = (JasperReport) JRLoader.loadObject(reporte_4);
			//JasperReport JRSubCompras = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/py/com/perseo/venta/reports/JRTurnoMovimientoCompras.jasper"));
			parameters.put("JRDataSourceCompras", new JRDataSourceCompras(listGastos));
			parameters.put("JRSubCompras", JRSubCompras);

			
			print = JasperFillManager.fillReport(report, parameters, new JRDataSourceArticulos(listArticulos));
			
			byte[] reporteF = null;
			reporteF = JasperExportManager.exportReportToPdf(print);
			File tempFileF = File.createTempFile("Turno-Mov", ".pdf", null);
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
	public String cerrarTurnoShop(Turno turno, LoginData login) throws Exception {
		String res = "";
		Connection conn = null;
		try {
			
			turno.setUsuario(login.getUsuario());
			turno.setCaja(login.getCaja());
			turno.setSucursal(login.getSucursal());
			turno.setFecha(new Date(System.currentTimeMillis()));
			turno.setEstado(false);
			List<Turno> aux = getByUsuario(login.getUsuario().getIdusuario(), 1);
			for (Turno turno2 : aux) {
				turno.setFechaultapertura(new Date(turno2.getFechaapertura().getDate()));
				turno.setFechaultcierre(new Date(turno2.getFechacierre().getDate()));
			}
			 update(turno);
			 
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			// cerrando el turno
			String sql_turno = " update turno set estado = false, fechacierre = ? where idturno = ? ";
			PreparedStatement ps = conn.prepareStatement(sql_turno);
			ps.setTimestamp(1, turno.getFechacierre());
			ps.setInt(2, turno.getIdturno());
			ps.executeUpdate();
			ps.close();
			List<Historicoarticulo> histart = articuloDepositoService.getArticuloByTurno(turno.getIdturno(), login);
			// cerrando el historico
			if(!histart.isEmpty()){
				
				for (Historicoarticulo histo : histart) {
					
					String sql_hist = " insert into historicoarticulo ";
					sql_hist += " (iddeposito,idarticulodeposito ,idturno , ";
					sql_hist += " idsucursal, idempresa ,anterior ,entrada ,venta ,actual , ";
					sql_hist += " preciocosto ,precioventa ) ";
					sql_hist += " values (?,?,?,?,?,?,?,?,?,?,?) ";
					
					PreparedStatement ps_hist = conn.prepareStatement(sql_hist);
					ps_hist.setInt(1, histo.getDeposito().getIddeposito());
					ps_hist.setInt(2, histo.getArticulodeposito().getIdarticulodeposito());
					ps_hist.setInt(3, turno.getIdturno());
					ps_hist.setInt(4, turno.getSucursal().getIdsucursal());
					ps_hist.setInt(5, login.getEmpresa().getIdempresa());
					if (histo.getAnterior() != null) {
						ps_hist.setDouble(6, histo.getAnterior());
					} else {
						ps_hist.setDouble(6, new Double(0));
					}
					ps_hist.setDouble(7, histo.getEntrada());
					ps_hist.setDouble(8, histo.getVenta());
					ps_hist.setDouble(9, histo.getActual());
					ps_hist.setDouble(10, histo.getPreciocosto());
					ps_hist.setDouble(11, histo.getPrecioventa());
					ps_hist.execute();
					
					ps_hist.close();
				}
			}
			res = getTurnoMovimiento(turno, login);
			conn.commit();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
			throw e;
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return res;
		
	}

	


}