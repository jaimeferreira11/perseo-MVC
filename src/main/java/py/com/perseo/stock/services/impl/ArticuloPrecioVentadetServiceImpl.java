package py.com.perseo.stock.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.stock.entities.*;
import py.com.perseo.stock.services.ArticuloPrecioVentadetService;
import py.com.perseo.stock.services.ArticuloService;
import py.com.perseo.stock.services.FamiliaService;
import py.com.perseo.stock.services.HistoricoPrecioVentaService;
import py.com.perseo.tesoreria.entities.Tipoimpuesto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticuloPrecioVentadetServiceImpl implements ArticuloPrecioVentadetService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloPrecioVentadetService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloService articuloService;
	
	@Autowired
	FamiliaService familiaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	HistoricoPrecioVentaService historicoPrecioVentaService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Articuloprecioventadet add(Articuloprecioventadet entity) throws Exception {
		em.persist(entity);
		em.flush();
		em.refresh(entity);
		return entity;
	}
	
	@Override
	public Articuloprecioventadet addPrecioVentadet(Articuloprecioventadet entity, Usuario usuario) throws Exception {
		try{
			Long id = usuarioService.getSecuenceFromName("articuloprecioventadet_idarticuloprecioventadet_seq");
			String sql = " insert into articuloprecioventadet (idarticuloprecioventadet, idarticuloprecioventacab,idarticulo, precio  )    " ;
			sql+= " values (?,?,?,?)";
			jdbcTemplate.update(sql, 
					id.intValue(),
					entity.getArticuloprecioventacab().getIdarticuloprecioventacab(),
					entity.getArticulo().getIdarticulo(),
					entity.getPrecio());
			
			entity.setIdarticuloprecioventadet(id.intValue());
			
			Articulo articulo = articuloService.getById(entity.getArticulo().getIdarticulo());
			
			Familia familia = familiaService.getById(articulo.getIdfamilia());
			
			// registrar en historico de precio venta
			Historicoprecioventa historico = new Historicoprecioventa();
			historico.setFecha(new Date(System.currentTimeMillis()));
			historico.setConcepto("Se agrego un registro desde la lista de precios ");
			historico.setPreciocosto(articulo.getPreciocosto());
			historico.setPrecioventa(entity.getPrecio());
			historico.setPorcentaje(familia.getProcentajeganancia());
			historico.setArticuloprecioventadet(entity);
			historico.setUsuario(usuario);
		
			historicoPrecioVentaService.add(historico);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return entity;
	}

	@Override
	public Articuloprecioventadet update(Articuloprecioventadet entity) throws Exception {
		try {
            em.merge(entity);
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar articulopreciodet " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Articuloprecioventadet getById(Integer key) throws Exception {
	
		Articuloprecioventadet ret = new Articuloprecioventadet();
		
		String sql = " select a.*, b.descripcion as articulo, b.codigobarra, b.idfamilia, b.idtipoimpuesto, c.descripcion as tipoimpuesto ";
		sql += " from articuloprecioventadet a ";
		sql += " join articulo b on a.idarticulo = b.idarticulo ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = b.idtipoimpuesto ";
		sql += " where idarticuloprecioventadet = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, key);
		
		for (Map<String, Object> rs : list) {
			ret.setIdarticuloprecioventadet((Integer)rs.get("idarticuloprecioventadet"));
			Articuloprecioventacab apvc = new Articuloprecioventacab();
			apvc.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setArticuloprecioventacab(apvc);
			Articulo a = new Articulo();
			a.setIdarticulo((Integer) rs.get("idarticulo"));
			a.setDescripcion((String) rs.get("articulo"));
			a.setCodigobarra((String) rs.get("codigobarra"));
			a.setIdfamilia((Integer) rs.get("idfamilia"));
			Tipoimpuesto t = new Tipoimpuesto();
			t.setIdtipoimpuesto((Integer) rs.get("idtipoimpuesto"));
			t.setDescripcion((String) rs.get("tipoimpuesto"));
			a.setTipoimpuesto(t);
			
			ret.setArticulo(a);
			ret.setPrecio((Double) rs.get("precio"));
		}
		System.out.println("");
		
		return ret;
	}

	@Override
	public List<Articuloprecioventadet> getAll() throws Exception {
		List<Articuloprecioventadet> res = new ArrayList<>();

		
		String sql = " select a.*, b.descripcion as articulo, b.codigobarra, b.idtipoimpuesto, c.descripcion as tipoimpuesto ";
		sql += " from articuloprecioventadet a ";
		sql += " join articulo b on a.idarticulo = b.idarticulo ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = b.idtipoimpuesto ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> rs : list) {
			Articuloprecioventadet ret = new Articuloprecioventadet();
			ret.setIdarticuloprecioventadet((Integer)rs.get("idarticuloprecioventadet"));
			Articuloprecioventacab apvc = new Articuloprecioventacab();
			apvc.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setArticuloprecioventacab(apvc);
			Articulo a = new Articulo();
			a.setIdarticulo((Integer) rs.get("idarticulo"));
			a.setDescripcion((String) rs.get("articulo"));
			a.setCodigobarra((String) rs.get("codigobarra"));
			Tipoimpuesto t = new Tipoimpuesto();
			t.setIdtipoimpuesto((Integer) rs.get("idtipoimpuesto"));
			t.setDescripcion((String) rs.get("tipoimpuesto"));
			a.setTipoimpuesto(t);
			ret.setArticulo(a);
			ret.setPrecio((Double) rs.get("precio"));
			res.add(ret);
		}
		
		return res;
	}

	@Override
	public List<Articuloprecioventadet> getArticulosPrecioByCab(Integer idArticuloPrecioCab) throws Exception {
	
	
		List<Articuloprecioventadet> res = new ArrayList<>();

		
		String sql = " select a.*, b.descripcion as articulo, b.codigobarra, b.idtipoimpuesto, c.descripcion as tipoimpuesto, b.preciocosto, b.idfamilia ";
		sql += " from articuloprecioventadet a ";
		sql += " join articulo b on a.idarticulo = b.idarticulo ";
		sql += " join tipoimpuesto c on c.idtipoimpuesto = b.idtipoimpuesto ";
		sql += " where idarticuloprecioventacab = ? ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, idArticuloPrecioCab);
		for (Map<String, Object> rs : list) {
			Articuloprecioventadet ret = new Articuloprecioventadet();
			ret.setIdarticuloprecioventadet((Integer)rs.get("idarticuloprecioventadet"));
			Articuloprecioventacab apvc = new Articuloprecioventacab();
			apvc.setIdarticuloprecioventacab((Integer)rs.get("idarticuloprecioventacab"));
			ret.setArticuloprecioventacab(apvc);
			Articulo a = new Articulo();
			Double precioCosto = Double.valueOf(rs.get("preciocosto").toString());
			a.setPreciocosto(precioCosto);
			a.setIdarticulo((Integer) rs.get("idarticulo"));
			a.setDescripcion((String) rs.get("articulo"));
			a.setCodigobarra((String) rs.get("codigobarra"));
			Tipoimpuesto t = new Tipoimpuesto();
			t.setIdtipoimpuesto((Integer) rs.get("idtipoimpuesto"));
			t.setDescripcion((String) rs.get("tipoimpuesto"));
			a.setTipoimpuesto(t);
			a.setIdfamilia((Integer)rs.get("idfamilia"));
			ret.setArticulo(a);
			ret.setPrecio((Double) rs.get("precio"));
			
			res.add(ret);
		}
		
		return res;
	}

	@Override
	public Articuloprecioventadet updatePrecio(Articuloprecioventadet articuloprecioventadet, Usuario usuario) throws Exception {
		try{
			String sql = " update articuloprecioventadet  set precio = ?  " ;
			sql+= " where idarticuloprecioventadet = ? ";
			jdbcTemplate.update(sql, articuloprecioventadet.getPrecio(), articuloprecioventadet.getIdarticuloprecioventadet());
			
			
			Articulo articulo = articuloService.getById(articuloprecioventadet.getArticulo().getIdarticulo());
			
			Familia familia = familiaService.getById(articulo.getIdfamilia());
			
			// registrar en historico de precio venta
			Historicoprecioventa historico = new Historicoprecioventa();
			historico.setFecha(new Date(System.currentTimeMillis()));
			historico.setConcepto("Modificacion del precio de venta en Lista de precios ");
			historico.setPreciocosto(articulo.getPreciocosto());
			historico.setPrecioventa(articuloprecioventadet.getPrecio());
			historico.setPorcentaje(familia.getProcentajeganancia());
			historico.setArticuloprecioventadet(articuloprecioventadet);
			historico.setUsuario(usuario);
		
			historicoPrecioVentaService.add(historico);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return articuloprecioventadet;
	}

	@Override
	public void updatePrecioByFamilia(Integer idfamilia , Usuario usuario) throws Exception {
		logger.info("Actualizando precio de venta para todas las listas: ");
		try {
			List<Articulo> articulos = articuloService.getArticulosByFamilia(idfamilia);
			Familia familia = familiaService.getById(idfamilia);
			
			for (Articulo articulo : articulos) {
				Double precio = articulo.getPreciocosto() + (articulo.getPreciocosto() * familia.getProcentajeganancia() / 100);
				String sql = " update articuloprecioventadet  set precio = ?  " ;
				sql+= " where idarticulo = ? ";
				jdbcTemplate.update(sql, precio, articulo.getIdarticulo());
				
				// registrar en historico de precio venta
				Historicoprecioventa historico = new Historicoprecioventa();
				historico.setFecha(new Date(System.currentTimeMillis()));
				historico.setConcepto("Modificacion del porcentaje de gananancia de la Familia para todas las listas" + familia.getDescripcion());
				historico.setPreciocosto(articulo.getPreciocosto());
				historico.setPrecioventa(precio);
				historico.setPorcentaje(familia.getProcentajeganancia());
				historico.setUsuario(usuario);

				historicoPrecioVentaService.add(historico);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updatePrecioByListacab(Usuario usuario, Integer idarticuloprecioventacab, String concepto)
			throws Exception {
		logger.info("Actualizando precio de venta para la lista: " + idarticuloprecioventacab);
		try {
			List<Articuloprecioventadet> listarticulos = getArticulosPrecioByCab(idarticuloprecioventacab);
			

			for (Articuloprecioventadet articulodet : listarticulos) {
				
				Familia familia = familiaService.getById(articulodet.getArticulo().getIdfamilia());
				
				Double precio = articulodet.getArticulo().getPreciocosto() + (articulodet.getArticulo().getPreciocosto() * familia.getProcentajeganancia() / 100);
				
				String sql = " update articuloprecioventadet  set precio = ?  " ;
				sql+= " where idarticuloprecioventadet = ? ";
				jdbcTemplate.update(sql, precio, articulodet.getIdarticuloprecioventadet());
				
				// registrar en historico de precio venta
				Historicoprecioventa historico = new Historicoprecioventa();
				historico.setFecha(new Date(System.currentTimeMillis()));
				historico.setConcepto(concepto);
				historico.setPreciocosto(articulodet.getArticulo().getPreciocosto());
				historico.setPrecioventa(precio);
				historico.setPorcentaje(familia.getProcentajeganancia());
				historico.setUsuario(usuario);
				historico.setArticuloprecioventadet(articulodet);
				historicoPrecioVentaService.add(historico);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void setPrecioArticulo(Articulo articulo, Integer idprecioventacab, Usuario usuario) throws Exception {
		try {
			Articuloprecioventadet apvd = getByArticuloAndPrecioVentaCab(articulo.getIdarticulo(), idprecioventacab);
			
			if(apvd.getIdarticuloprecioventadet() == null){
				System.out.println("NO EXISTE EL ARTICULO EN LA LISTA DE PRECIO");
				apvd.setArticulo(articulo);
				Articuloprecioventacab apvc = new Articuloprecioventacab();
				apvc.setIdarticuloprecioventacab(idprecioventacab);
				apvd.setArticuloprecioventacab(apvc);
				apvd.setPrecio(new Double(0));
				add(apvd);
				
				// registrar en historico de precio venta
				Historicoprecioventa historico = new Historicoprecioventa();
				historico.setFecha(new Date(System.currentTimeMillis()));
				historico.setConcepto("Se agrego un articulo desde el mantenimiento");
				historico.setPreciocosto(new Double(0));
				historico.setPrecioventa(new Double(0));
				historico.setPorcentaje(new Double(0));
				//historico.setUsuario(usuario);
				historico.setArticuloprecioventadet(apvd);
				historicoPrecioVentaService.add(historico);
			}else{
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public Articuloprecioventadet getByArticuloAndPrecioVentaCab(Integer idarticulo, Integer idarticuloprecioventacab)
			throws Exception {
		Articuloprecioventadet res= new Articuloprecioventadet();
		try {
			String sql = "select * from articuloprecioventadet  ";
			sql += " where idarticulo = ? and idarticuloprecioventacab = ? ";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, idarticulo, idarticuloprecioventacab);
			for (Map<String, Object> rs : list) {
				res.setIdarticuloprecioventadet((Integer) rs.get("idarticuloprecioventadet"));
				Articulo a = new Articulo();
				a.setIdarticulo((Integer) rs.get("idarticulo"));
				res.setArticulo(a);
				Articuloprecioventacab apvc = new Articuloprecioventacab();
				apvc.setIdarticuloprecioventacab((Integer) rs.get("idarticuloprecioventacab"));
				res.setArticuloprecioventacab(apvc);
				res.setPrecio((Double) rs.get("precio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	

}
