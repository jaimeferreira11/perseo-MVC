package py.com.perseo.stock.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.perseo.stock.entities.Marca;
import py.com.perseo.stock.services.MarcaService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    DataSource dataSource;

    @Override
    public Marca add(Marca entity) throws Exception {
        return null;
    }

    @Override
    public Marca update(Marca entity) throws Exception {
        return null;
    }

    @Override
    public String delete(Integer key) throws Exception {
        return null;
    }

    @Override
    public Marca getById(Integer key) throws Exception {
    	Connection conn = dataSource.getConnection();
		
		String sql = " select * ";
		sql += " from marca ";
		sql += " where idmarca = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, key);
		
		ResultSet rs = ps.executeQuery();
		
		Marca marca = new Marca();
		while (rs.next()) {
			
			marca.setIdmarca(rs.getInt("idmarca"));
			marca.setDescripcion(rs.getString("descripcion"));
	
		}
		
		conn.close();
		
		return marca;
    }

    @Override
    public List<Marca> getAll() throws Exception {
        Connection conn = dataSource.getConnection();
        List<Marca> marcas = new ArrayList<>();
        String sql = " select * from marca";
        sql += " order by idmarca desc ";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Marca marca = new Marca();
            marca.setIdmarca(rs.getInt("idmarca"));
            marca.setDescripcion(rs.getString("descripcion"));
            marcas.add(marca);
        }

        conn.close();
        return marcas;
    }
}
