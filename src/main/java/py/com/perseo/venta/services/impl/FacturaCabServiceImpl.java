package py.com.perseo.venta.services.impl;


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
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.contabilidad.entities.Asientocab;
import py.com.perseo.contabilidad.entities.Plancuenta;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.entities.Factura;
import py.com.perseo.tesoreria.entities.Metodocobro;
import py.com.perseo.tesoreria.entities.Moneda;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.entities.Tipofactura;
import py.com.perseo.venta.services.FacturaCabService;
import py.com.perseo.venta.services.FacturaDetService;
import py.com.perseo.venta.services.FacturaFormaCobroService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FacturaCabServiceImpl implements FacturaCabService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    FacturaDetService facturaDetService;
    
    @Autowired
    FacturaFormaCobroService facturaFormaCobroService;

    private static final Logger logger = LoggerFactory.getLogger(FacturaCabServiceImpl.class);

	@Override
	public Facturacab add(Facturacab entity) throws Exception {
		em.persist(entity);
        return entity;
	}

	@Override
	public Facturacab update(Facturacab entity) throws Exception {
		em.merge(entity);
	     return entity;
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facturacab getById(Integer key) throws Exception {
		 Connection conn = dataSource.getConnection();
		 Facturacab articulo = new Facturacab();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as metodocobro, e.descripcion as tipofactura ";
	            sql += " from Facturacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " join metodocobro f on a.idmetodocobro = f.idmetodocobro ";
	            sql += " join tipofactura e on a.idtipofactura = e.idtipofactura ";
	            sql += " where idfacturacab = ?  ";
	            sql += " order by a.idfacturacab desc ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, key);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                articulo.setIdfacturacab(rs.getInt("idfacturacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Metodocobro metodocobro = new Metodocobro();
	                metodocobro.setIdmetodocobro(rs.getInt("idmetodocobro"));
	                metodocobro.setDescripcion(rs.getString("metodocobro"));
	                articulo.setMetodocobro(metodocobro);
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setEstablecimiento(rs.getInt("establecimiento"));
	                articulo.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
	                articulo.setNrofactura(rs.getInt("nrofactura"));
	                articulo.setSaldo(rs.getDouble("saldo"));
	                Plancuenta plancuenta = new Plancuenta();
	                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
	                articulo.setPlancuenta(plancuenta);
	                Asientocab asientocab = new Asientocab();
	                asientocab.setIdasientocab(rs.getInt("idasientocab"));
	                articulo.setAsientocab(asientocab);
	                articulo.setImpreso(rs.getBoolean("impreso"));
	                Tipofactura tipofactura = new Tipofactura();
	                tipofactura.setIdtipofactura(rs.getInt("idtipofactura"));
	                articulo.setTipofactura(tipofactura);
	                articulo.setObservacion(rs.getString("observacion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                articulo.setClasefactura(rs.getString("clasefactura"));
	       
	                articulo.setDetalleFactura(facturaDetService.getByIdFacturaCab(articulo.getIdfacturacab()));
	                articulo.setListFormaPago(facturaFormaCobroService.getByFacturacab(articulo.getIdfacturacab()));
	              
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return articulo;
	}

	@Override
	public List<Facturacab> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facturacab> getBySucursal(Boolean allEstados, Boolean estado, Boolean allTipos, Integer idtipofactura,
			Integer idsucursal) throws Exception {
		 Connection conn = dataSource.getConnection();
		 List<Facturacab> list = new ArrayList<>();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as metodocobro, e.descripcion as tipofactura ";
	            sql += " from Facturacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " join metodocobro f on a.idmetodocobro = f.idmetodocobro ";
	            sql += " join tipofactura e on a.idtipofactura = e.idtipofactura ";
	            sql += " where a.idsucursal = ?  ";
	            if(!allEstados){
	            	sql += " and a.estado =  " + estado;
	            }
	            if(!allTipos){
	            	sql += " and a.idtipofactura =  " + idtipofactura;
	            }
	            sql += " order by a.idfacturacab desc ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idsucursal);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	Facturacab articulo = new Facturacab();
	                articulo.setIdfacturacab(rs.getInt("idfacturacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Metodocobro metodocobro = new Metodocobro();
	                metodocobro.setIdmetodocobro(rs.getInt("idmetodocobro"));
	                metodocobro.setDescripcion(rs.getString("metodocobro"));
	                articulo.setMetodocobro(metodocobro);
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setEstablecimiento(rs.getInt("establecimiento"));
	                articulo.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
	                articulo.setNrofactura(rs.getInt("nrofactura"));
	                articulo.setSaldo(rs.getDouble("saldo"));
	                Plancuenta plancuenta = new Plancuenta();
	                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
	                articulo.setPlancuenta(plancuenta);
	                Asientocab asientocab = new Asientocab();
	                asientocab.setIdasientocab(rs.getInt("idasientocab"));
	                articulo.setAsientocab(asientocab);
	                articulo.setImpreso(rs.getBoolean("impreso"));
	                Tipofactura tipofactura = new Tipofactura();
	                tipofactura.setIdtipofactura(rs.getInt("idtipofactura"));
	                articulo.setTipofactura(tipofactura);
	                articulo.setObservacion(rs.getString("observacion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                articulo.setClasefactura(rs.getString("clasefactura"));
	       
	                articulo.setDetalleFactura(facturaDetService.getByIdFacturaCab(articulo.getIdfacturacab()));
	                articulo.setListFormaPago(facturaFormaCobroService.getByFacturacab(articulo.getIdfacturacab()));
	                
	                list.add(articulo);
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	}

	@Override
	public List<Facturacab> getByCliente(Integer idcliente, Boolean all, Boolean estado) throws Exception {
		Connection conn = dataSource.getConnection();
		 List<Facturacab> list = new ArrayList<>();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as metodocobro, e.descripcion as tipofactura ";
	            sql += " from Facturacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " left join metodocobro f on a.idmetodocobro = f.idmetodocobro ";
	            sql += " join tipofactura e on a.idtipofactura = e.idtipofactura ";
	            sql += " where a.idcliente = ?  ";
	            if(!all){
	            	sql += " and a.estado =  " + estado;
	            }
	
	            sql += " order by a.idfacturacab desc ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idcliente);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	Facturacab articulo = new Facturacab();
	                articulo.setIdfacturacab(rs.getInt("idfacturacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Metodocobro metodocobro = new Metodocobro();
	                metodocobro.setIdmetodocobro(rs.getInt("idmetodocobro"));
	                metodocobro.setDescripcion(rs.getString("metodocobro"));
	                articulo.setMetodocobro(metodocobro);
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setEstablecimiento(rs.getInt("establecimiento"));
	                articulo.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
	                articulo.setNrofactura(rs.getInt("nrofactura"));
	                articulo.setSaldo(rs.getDouble("saldo"));
	                Plancuenta plancuenta = new Plancuenta();
	                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
	                articulo.setPlancuenta(plancuenta);
	                Asientocab asientocab = new Asientocab();
	                asientocab.setIdasientocab(rs.getInt("idasientocab"));
	                articulo.setAsientocab(asientocab);
	                articulo.setImpreso(rs.getBoolean("impreso"));
	                Tipofactura tipofactura = new Tipofactura();
	                tipofactura.setIdtipofactura(rs.getInt("idtipofactura"));
	                articulo.setTipofactura(tipofactura);
	                articulo.setObservacion(rs.getString("observacion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                articulo.setClasefactura(rs.getString("clasefactura"));
	       
	                articulo.setDetalleFactura(facturaDetService.getByIdFacturaCab(articulo.getIdfacturacab()));
	                articulo.setListFormaPago(facturaFormaCobroService.getByFacturacab(articulo.getIdfacturacab()));
	                
	                list.add(articulo);
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	}

	@Override
	public Factura getSigteFacturaByCaja(Integer establecimiento, Integer puntoexpedicion, Integer idcaja) throws Exception {
		Connection conn = null;
		Factura data = null;
		try {

			conn = dataSource.getConnection();
			
			String sSql = " Select nrofactura, establecimiento, puntoexpedicion, ";
			sSql += " vigenciadesde, vigenciahasta, timbrado, estado, idcaja ";
			sSql += " from factura  ";
			sSql += " where  ";
			sSql += "     idcaja = ? and estado = 'D' and timbrado > 0 ";
			if (establecimiento.intValue() > 0) {
				sSql += " and establecimiento = ? ";
			}
			if (puntoexpedicion.intValue() > 0) {
				sSql += " and puntoexpedicion = ? ";
			}
			sSql += " order by nrofactura limit 1 ";

			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setInt(1, idcaja);
			
			int count = 2;
			
			if (establecimiento.intValue() > 0) {
				ps.setInt(count++, establecimiento);	
			}
			if (puntoexpedicion.intValue() > 0) {
				ps.setInt(count++, puntoexpedicion);
			}
			
			ResultSet rs = ps.executeQuery();

			boolean entro = false;

			if (rs.next()) {
				
				entro = true;
				
				data = new Factura();
				data.setNrofactura(rs.getInt("nrofactura"));
				data.setEstablecimiento(rs.getInt("establecimiento"));
				data.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
				data.setEstado(rs.getString("estado"));
				data.setVigenciahasta(rs.getDate("vigenciadesde"));
				data.setVigenciahasta(rs.getDate("vigenciahasta"));
				data.setTimbrado(rs.getInt("timbrado"));
				Caja caja_data = new Caja();
				caja_data.setIdcaja(idcaja);
				data.setCaja(caja_data);
			
			}

			if (!entro) {
				throw new Exception("Caja sin factura disponible, favor registre el talonario de facturas");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return data;
	}

	@Override
	public String cobrarFactura(Integer idfacturacab, LoginData login) throws Exception {
		String res = "";
		Connection conn = dataSource.getConnection();
		try {

			
			//actualiza el precio de costo en articulo (no se usa)
            String sql2 = "update facturacab set estado = ?, fecha = ?  where idfacturacab = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setBoolean(1, true);
            ps2.setDate(2, new Date(System.currentTimeMillis()));
            ps2.setInt(3, idfacturacab);
            ps2.executeUpdate();

			res = "Factura nro. " +idfacturacab + " cobrado correctamente";

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	@Override
	public List<Facturacab> getByturno(Integer idturno) throws Exception {
		Connection conn = dataSource.getConnection();
		 List<Facturacab> list = new ArrayList<>();
	        try {

	            String sql = " select a.*, s.nombre as sucursal, c.nombreapellido, c.codtipodoc, c.nrodoc, f.descripcion as metodocobro, e.descripcion as tipofactura ";
	            sql += " from Facturacab a";
	            sql += " join sucursal s on a.idsucursal = s.idsucursal ";
	            sql += " join cliente c on a.idcliente = c.idcliente ";
	            sql += " left join metodocobro f on a.idmetodocobro = f.idmetodocobro ";
	            sql += " join tipofactura e on a.idtipofactura = e.idtipofactura ";
	            sql += " where a.idturno = ?  ";
	            sql += " order by a.idfacturacab desc ";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, idturno);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	Facturacab articulo = new Facturacab();
	                articulo.setIdfacturacab(rs.getInt("idfacturacab"));
	                articulo.setFecha(rs.getDate("fecha"));
	                Sucursal sucursal = new Sucursal();
	                sucursal.setIdsucursal(rs.getInt("idsucursal"));
	                sucursal.setDescripcion(rs.getString("sucursal"));
	                articulo.setSucursal(sucursal);
	                Cliente cliente = new Cliente();
	                cliente.setIdcliente(rs.getInt("idcliente"));
	                cliente.setNombreapellido(rs.getString("nombreapellido"));
	                Tipodocumento tipodocumento = new Tipodocumento();
	                tipodocumento.setCodtipodoc(rs.getString("codtipodoc"));
	                cliente.setTipodocumento(tipodocumento);
	                cliente.setNrodoc(rs.getString("nrodoc"));
	                articulo.setCliente(cliente);
	                Metodocobro metodocobro = new Metodocobro();
	                metodocobro.setIdmetodocobro(rs.getInt("idmetodocobro"));
	                metodocobro.setDescripcion(rs.getString("metodocobro"));
	                articulo.setMetodocobro(metodocobro);
	                articulo.setImporte(rs.getDouble("importe"));
	                articulo.setEstablecimiento(rs.getInt("establecimiento"));
	                articulo.setPuntoexpedicion(rs.getInt("puntoexpedicion"));
	                articulo.setNrofactura(rs.getInt("nrofactura"));
	                articulo.setSaldo(rs.getDouble("saldo"));
	                Plancuenta plancuenta = new Plancuenta();
	                plancuenta.setIdplancuenta(rs.getInt("idplancuenta"));
	                articulo.setPlancuenta(plancuenta);
	                Asientocab asientocab = new Asientocab();
	                asientocab.setIdasientocab(rs.getInt("idasientocab"));
	                articulo.setAsientocab(asientocab);
	                articulo.setImpreso(rs.getBoolean("impreso"));
	                Tipofactura tipofactura = new Tipofactura();
	                tipofactura.setIdtipofactura(rs.getInt("idtipofactura"));
	                articulo.setTipofactura(tipofactura);
	                articulo.setObservacion(rs.getString("observacion"));
	                Empresa empresa = new Empresa();
	                empresa.setIdempresa(rs.getInt("idempresa"));
	                articulo.setEmpresa(empresa);
	                Moneda moneda = new Moneda();
	                moneda.setCodmoneda(rs.getInt("codmoneda"));
	                articulo.setMoneda(moneda);
	                articulo.setClasefactura(rs.getString("clasefactura"));
	       
	                articulo.setDetalleFactura(facturaDetService.getByIdFacturaCab(articulo.getIdfacturacab()));
	                articulo.setListFormaPago(facturaFormaCobroService.getByFacturacab(articulo.getIdfacturacab()));
	                
	                list.add(articulo);
	            }
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	}


}