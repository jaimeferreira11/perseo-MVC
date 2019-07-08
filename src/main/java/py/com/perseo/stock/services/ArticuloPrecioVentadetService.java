package py.com.perseo.stock.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.entities.Articuloprecioventadet;

import java.util.List;

public interface ArticuloPrecioVentadetService extends GenericDao<Articuloprecioventadet, Integer>  {

	public List<Articuloprecioventadet> getArticulosPrecioByCab(Integer idArticuloPrecioCab) throws Exception;
	
	public Articuloprecioventadet updatePrecio(Articuloprecioventadet articuloprecioventadet, Usuario usuario) throws Exception;
	
	public void updatePrecioByFamilia(Integer idfamilia, Usuario usuario) throws Exception;
	
	public void updatePrecioByListacab(Usuario usuario, Integer idarticuloprecioventacab, String concepto) throws Exception;
	
	public Articuloprecioventadet addPrecioVentadet(Articuloprecioventadet entity, Usuario usuario) throws Exception ;
	
	public void setPrecioArticulo(Articulo articulo, Integer idprecioventacab, Usuario usuario) throws Exception;
	
	public Articuloprecioventadet getByArticuloAndPrecioVentaCab(Integer idarticulo, Integer idarticuloprecioventacab) throws Exception;

}
