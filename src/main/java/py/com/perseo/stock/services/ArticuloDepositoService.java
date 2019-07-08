package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articulodeposito;
import py.com.perseo.stock.entities.Historicoarticulo;

import java.sql.SQLException;
import java.util.List;

public interface ArticuloDepositoService extends GenericDao<Articulodeposito, Integer> {

    public List<Articulodeposito> getBySucursalAndDeposito(Integer idSucursal, Integer idDeposito, Boolean all) throws Exception;

    public List<Articulodeposito> getBySucursalAndDepositoByParams(Integer idSucursal, Integer idDeposito, String param, LoginData loginData) throws Exception;

    public Articulodeposito getByArticuloAndDeposito(Integer idarticulo, Integer idDeposito) throws Exception;

    //public String transferirArticulo (Articulodeposito articuloDeposito,LoginData login) throws Exception;

    public Articulodeposito addArticuloAndDeposito(Articulodeposito articulodeposito, LoginData login) throws Exception;

    public void addArticuloInAllDeposito(Articulo articulo) throws Exception;

    public List<Articulodeposito> getArticulosVentaByParams(Integer idArticuloPrecioCab, String param, LoginData login) throws Exception;

    public void updatePrecioCosto(Articulo articulo) throws Exception;

    void updateStock(int idsucursal, int iddeposito, Integer idarticulo, Double cantidad) throws SQLException;
    
    public String imprimirListaArticulos(List<Articulodeposito> list, LoginData login)  throws Exception;
    
    public String getListaArticulos(String datos, LoginData login) throws Exception;

    Integer getIdByArticuloSucursalDeposito(Integer idarticulo, Integer idsucursal, Integer iddeposito) throws SQLException;
    
    public List<Historicoarticulo> getArticuloByTurno(Integer idturno, LoginData login) throws Exception;
}
