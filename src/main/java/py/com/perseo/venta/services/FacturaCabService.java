package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.tesoreria.entities.Factura;
import py.com.perseo.venta.entities.Facturacab;

import java.util.List;

public interface FacturaCabService extends GenericDao<Facturacab, Integer> {
	

    public List<Facturacab> getBySucursal( Boolean allEstados, Boolean estado, Boolean allTipos, Integer idtipofactura, Integer idsucursal) throws Exception;
    
    public List<Facturacab> getByCliente(Integer idcliente, Boolean all, Boolean estado) throws Exception;

    public Factura getSigteFacturaByCaja(Integer establecimiento, Integer puntoexpedicion, Integer idcaja) throws Exception;
    
    public String cobrarFactura(Integer idfacturacab,  LoginData login) throws Exception; 
    
    public List<Facturacab> getByturno(Integer idturno) throws Exception;
    
}
