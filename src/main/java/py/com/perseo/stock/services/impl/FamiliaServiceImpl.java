package py.com.perseo.stock.services.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.stock.entities.Familia;
import py.com.perseo.stock.services.ArticuloPrecioVentadetService;
import py.com.perseo.stock.services.FamiliaService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FamiliaServiceImpl implements FamiliaService {

	private static final Logger logger = LoggerFactory.getLogger(FamiliaServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ArticuloPrecioVentadetService articuloPrecioVentadetService;
	
	@Override
	public Familia getById(Integer key) throws Exception {
		Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from familia ";
		sql += " where idfamilia = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Familia familia = new Familia();
		while (rs.next()) {
			
			familia.setIdfamilia(rs.getInt("idfamilia"));
			familia.setDescripcion(rs.getString("descripcion"));
			familia.setEstado(rs.getBoolean("estado"));
			familia.setProcentajeganancia(rs.getDouble("procentajeganancia"));
		}
		
		conn.close();
		
		return familia;
	}

	@Override
	public List<Familia> getAll() throws Exception {

		Connection conn = dataSource.getConnection();
		List<Familia> familias = new ArrayList<Familia>();
		
		String sql = "select * from familia order by idfamilia desc";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Familia familia = new Familia();
			familia.setIdfamilia(rs.getInt("idfamilia"));
			familia.setDescripcion(rs.getString("descripcion"));
			familia.setEstado(rs.getBoolean("estado"));
			familia.setProcentajeganancia(rs.getDouble("procentajeganancia"));
			
			familias.add(familia);
		}
		
		conn.close();
		
		return familias;
		
	}

	@Override
	public Familia add(Familia entity) {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception ex) {
			logger.info("Error al agregar familia " + ex.getMessage());
			throw ex;
		}
	}

	@Override
	public Familia update(Familia entity)  {
		try {
            em.merge(entity);
         
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar familia " + e.getMessage());
        	throw e;
        }
	}
	
	/**
	 * Actualiza la familia
	 * @param entity
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	public Familia updateFamilia(Familia entity, Usuario usuario) throws Exception {
		try {
			Familia old_entity = getById(entity.getIdfamilia());
			update(entity);
			
			int retval = Double.compare(old_entity.getProcentajeganancia(), entity.getProcentajeganancia());
			if(retval != 0) {
				//listas de precios
				List<String> listas = new Gson().fromJson(entity.getListaPrecio(), new TypeToken<List<String>>(){}.getType());
				for (String element : listas) {
					if(element.toLowerCase().trim().equals("todos")){							
						articuloPrecioVentadetService.updatePrecioByFamilia(entity.getIdfamilia(), usuario);
						break;
					}else{	
						articuloPrecioVentadetService.updatePrecioByListacab( usuario,  Integer.parseInt(element), "Modificacion del porcentaje de gananancia de la Familia " + entity.getDescripcion() );
					}
				}
			}else{
				logger.info("No se modifico el porcentaje de ganancia");
			}
			
			
	
            
            return entity;
        } catch (Exception e) {
        	logger.info("Error al actualizar familia " + e.getMessage());
        	throw e;
        }
	}

	@Override
	public String delete(Integer key) throws Exception {
		Familia entity = getById(key);
		try {
			if (!em.contains(entity)) {
				entity = em.merge(entity);
			}
			em.remove(entity);
		} catch (Exception e) {
			logger.info("Error al eliminar familia " + e.getMessage());
		}
		  return entity.getClass().getName() + " eliminado correctamente";
	}

	

}