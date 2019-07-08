package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.pojos.OrdenInfo;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.tesoreria.entities.Compradet;
import py.com.perseo.tesoreria.entities.Ordenpagocab;
import py.com.perseo.tesoreria.entities.Ordenpagodet;

import java.sql.SQLException;
import java.util.List;

public interface CompraService extends GenericDao<Compracab, Integer> {
    public String registrarCompra(Compracab cab, List<Compradet> detalles, LoginData login) throws Exception;

    public List<Compracab> getCompras(String ini, String fin, Integer estado, Object[] proveedor) throws Exception;

    public String nuevaOrdenPago(String datos, LoginData loginCache) throws Exception;

    public List<Ordenpagocab> getOrdenesPago(String iniDateSql, String endDateSql, Object[] proveedor) throws Exception;

    public String confirmarOrdenPago(String datos, LoginData loginCache) throws Exception;

    public String getLibroIvaCompra(String datos, LoginData login) throws Exception;

    public String ImpresionOrdenPago(Ordenpagocab data, LoginData login) throws Exception;

    public List<Compracab> getByturno(Integer idturno) throws Exception;

    public List<OrdenInfo> getOrdenInfo(Integer id) throws SQLException;
    
    public Ordenpagodet registrarDetallesOrdenPgo(Ordenpagodet ordenpagodet) throws Exception;
}
