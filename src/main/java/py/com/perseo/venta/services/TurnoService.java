package py.com.perseo.venta.services;

import py.com.perseo.comun.base.GenericDao;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.venta.entities.Turno;

import java.util.List;

public interface TurnoService extends GenericDao<Turno, Integer> {
	

    public List<Turno> getByUsuario(Integer idusuario, Integer limit) throws Exception;
    
    public Turno getByUsuarioActivo(Integer idusuario) throws Exception;
    
    public String getTurnoMovimiento(Turno idturno, LoginData login) throws Exception;
    
    public String cerrarTurnoShop (Turno turno, LoginData login) throws Exception ;

    
}
