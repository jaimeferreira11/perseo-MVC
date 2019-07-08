package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.entities.Ventacab;

import java.util.List;

public interface VentaCabService extends GenericDao<Ventacab, Integer> {
	

    public List<Ventacab> getByEstadoAndSucursal(Integer estado, Usuario usuario) throws Exception;
    
    public List<Ventacab> getByCliente(Integer idcliente, Boolean all, Integer idestado) throws Exception;
    
    public String registrarPedido(Ventacab ventacab, LoginData login) throws Exception;
    
    public String grabarVenta(String datos, LoginData login) throws Exception;
    
    public String getLibroIvaVentaContable(String datos, LoginData login) throws Exception;
    
    public String imprimirTicket(Facturacab data, LoginData loginData) throws Exception;
    
    public String imprimirNormal(Facturacab data) throws Exception;

}
