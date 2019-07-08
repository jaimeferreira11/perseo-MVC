package py.com.perseo.comun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.repositories.UsuarioRepository;
import py.com.perseo.comun.services.UsuarioService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.entities.Menu;
import py.com.perseo.session.entities.Perfil;
import py.com.perseo.session.entities.Perfilusuario;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.session.services.MenuService;
import py.com.perseo.session.services.PerfilService;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.sueldos.entities.Claseempleado;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.venta.entities.Turno;
import py.com.perseo.venta.services.TurnoService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TurnoService turnoService;


    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Override
    public Long getSecuenceFromName(String name) throws Exception {
        Connection conn = null;
        Long value = new Long(0);
        try {
            String seq = " select nextval('" + name + "') as seq ";
            List<Map<String, Object>> listaSub = jdbcTemplate.queryForList(seq);

            for (Map<String, Object> rs : listaSub) {
                value = (Long) rs.get("seq");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return value;
    }

    @Override
    public Long getLastSecuenceFromName(String name) throws Exception {
        Connection conn = null;
        Long value = new Long(0);
        try {
            String seq = " SELECT last_value as seq FROM " + name + " as seq ";
            List<Map<String, Object>> listaSub = jdbcTemplate.queryForList(seq);

            for (Map<String, Object> rs : listaSub) {
                value = (Long) rs.get("seq");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return value;
    }

    @Override
    public List<Usuario> getUsuarios() throws Exception {

        List<Usuario> ret = new ArrayList<>();

        String sql = " select a.idusuario, a.usuario, a.activo, ";
        sql += " a.idempresa, b.descripcion as empresa ";
        sql += " from usuario a ";
        sql += " join empresa b on b.idempresa = a.idempresa ";
        sql += " where activo = true ";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> rs : list) {
            Usuario usuario = new Usuario();
            usuario.setIdusuario((Integer) rs.get("idusuario"));
            usuario.setUsuario((String) rs.get("usuario"));
            usuario.setEstado((Boolean) rs.get("activo"));
//			usuario.setIdempleado((Integer) rs.get("idempleado"));
//			usuario.setCodupdate((Integer)rs.get("codupdate"));

            Empresa empresa = new Empresa();
            empresa.setIdempresa((Integer) rs.get("idempresa"));
            empresa.setDescripcion((String) rs.get("empresa"));
            usuario.setEmpresa(empresa);

            //for
            String sql_pu = " select a.idperfil, a.idusuario, ";
            sql_pu += " b.descripcion as perfil ";
            sql_pu += " from perfil_usuario a ";
            sql_pu += " join perfil b on b.idperfil = a.idperfil ";
            sql_pu += " where a.idusuario = ? ";
            List<Perfilusuario> puList = new ArrayList<>();
            List<Map<String, Object>> lPerfilUsuario = jdbcTemplate.queryForList(sql_pu, rs.get("idusuario"));
            for (Map<String, Object> rs1 : lPerfilUsuario) {
                Perfilusuario pu = new Perfilusuario();
                Perfil pe = new Perfil();
                pe.setIdperfil((Integer) rs1.get("idperfil"));
                pe.setDescripcion((String) rs1.get("perfil"));
                pu.setPerfil(pe);
                puList.add(pu);
            }

            if (!puList.isEmpty()) {
                usuario.setPerfilusuario(puList);
            } else {
                usuario.setPerfilusuario(null);
            }

            ret.add(usuario);

        }

        return ret;
    }

    @Override
    public Usuario getUsuarioById(Integer idusuario) throws Exception {
        Usuario usuario = new Usuario();

        String sql = " select a.*, ";
        sql += " b.descripcion as empresa, d.nombre as sucursal, c.descripcion as claseempleado, e.descripcion as deposito ";
        sql += " from usuario a ";
        sql += " join empresa b on b.idempresa = a.idempresa ";
        sql += " join claseempleado c on c.idclaseempleado = a.idclaseempleado ";
        sql += " join sucursal d on d.idsucursal = a.idsucursal ";
        sql += " join deposito e on e.iddeposito = a.iddeposito ";
        sql += " where a.idusuario = ? order by a.idusuario desc";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, idusuario);

        for (Map<String, Object> rs : list) {
            usuario.setIdusuario((Integer) rs.get("idusuario"));
            usuario.setUsuario((String) rs.get("usuario"));
            usuario.setNombreapellido((String) rs.get("nombreapellido"));
            usuario.setDireccion((String) rs.get("direccion"));
            usuario.setSexo((String) rs.get("sexo"));
            usuario.setLugarnacimiento((String) rs.get("lugarnacimiento"));
            usuario.setTelefono((String) rs.get("telefono"));
            usuario.setCelular((String) rs.get("celular"));
            usuario.setCargo((String) rs.get("cargo"));
            usuario.setProfesion((String) rs.get("profesion"));
            usuario.setEstado((Boolean) rs.get("activo"));
            usuario.setClave((String) rs.get("contrasena"));
            Empresa empresa = new Empresa();
            empresa.setIdempresa((Integer) rs.get("idempresa"));
            empresa.setDescripcion((String) rs.get("empresa"));
            usuario.setEmpresa(empresa);
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal((Integer) rs.get("idsucursal"));
            sucursal.setNombre((String) rs.get("sucursal"));
            usuario.setSucursal(sucursal);
            Claseempleado claseempleado = new Claseempleado();
            claseempleado.setIdclaseempleado((Integer) rs.get("idclaseempleado"));
            claseempleado.setDescripcion((String) rs.get("claseempleado"));
            usuario.setClaseempleado(claseempleado);
            usuario.setOficial((Boolean) rs.get("oficial"));
            usuario.setNrodoc((String) rs.get("nrodoc"));
            usuario.setNrooficial((Integer) rs.get("nrooficial"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito((Integer) rs.get("iddeposito"));
            deposito.setDescripcion((String) rs.get("deposito"));
            usuario.setDeposito(deposito);

            //for
            String sql_pu = " select a.idperfil, a.idusuario, ";
            sql_pu += " b.descripcion as perfil ";
            sql_pu += " from perfil_usuario a ";
            sql_pu += " join perfil b on b.idperfil = a.idperfil ";
            sql_pu += " where a.idusuario = ? ";
            List<Perfilusuario> puList = new ArrayList<>();
            List<Map<String, Object>> lPerfilUsuario = jdbcTemplate.queryForList(sql_pu, rs.get("idusuario"));
            for (Map<String, Object> rs1 : lPerfilUsuario) {
                Perfilusuario pu = new Perfilusuario();
                Perfil pe = new Perfil();
                pe.setIdperfil((Integer) rs1.get("idperfil"));
                pe.setDescripcion((String) rs1.get("perfil"));
                pu.setPerfil(pe);
                puList.add(pu);
            }

            if (!puList.isEmpty()) {
                usuario.setPerfilusuario(puList);
            } else {
                usuario.setPerfilusuario(null);
            }


        }
        return usuario;
    }

    @Override
    public Usuario getUsuarioByUsername(String username) {
        Usuario usuario = new Usuario();

        String sql = " select a.*, ";
        sql += " b.descripcion as empresa, d.nombre as sucursal, c.descripcion as claseempleado , e.descripcion as deposito  ";
        sql += " from usuario a ";
        sql += " join empresa b on b.idempresa = a.idempresa ";
        sql += " join claseempleado c on c.idclaseempleado = a.idclaseempleado ";
        sql += " join sucursal d on d.idsucursal = a.idsucursal ";
        sql += " join deposito e on e.iddeposito = a.iddeposito ";
        sql += " where upper(a.usuario) = upper(?) order by a.idusuario desc";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username.trim());

        for (Map<String, Object> rs : list) {
            usuario.setIdusuario((Integer) rs.get("idusuario"));
            usuario.setUsuario((String) rs.get("usuario"));
            usuario.setNombreapellido((String) rs.get("nombreapellido"));
            usuario.setDireccion((String) rs.get("direccion"));
            usuario.setSexo((String) rs.get("sexo"));
            usuario.setLugarnacimiento((String) rs.get("lugarnacimiento"));
            usuario.setTelefono((String) rs.get("telefono"));
            usuario.setCelular((String) rs.get("celular"));
            usuario.setCargo((String) rs.get("cargo"));
            usuario.setProfesion((String) rs.get("profesion"));
            usuario.setEstado((Boolean) rs.get("activo"));
            Empresa empresa = new Empresa();
            empresa.setIdempresa((Integer) rs.get("idempresa"));
            empresa.setDescripcion((String) rs.get("empresa"));
            usuario.setEmpresa(empresa);
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal((Integer) rs.get("idsucursal"));
            sucursal.setNombre((String) rs.get("sucursal"));
            usuario.setSucursal(sucursal);
            Claseempleado claseempleado = new Claseempleado();
            claseempleado.setIdclaseempleado((Integer) rs.get("idclaseempleado"));
            claseempleado.setDescripcion((String) rs.get("claseempleado"));
            usuario.setClaseempleado(claseempleado);
            usuario.setOficial((Boolean) rs.get("oficial"));
            usuario.setNrodoc((String) rs.get("nrodoc"));
            usuario.setNrooficial((Integer) rs.get("nrooficial"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito((Integer) rs.get("iddeposito"));
            deposito.setDescripcion((String) rs.get("deposito"));
            usuario.setDeposito(deposito);

            //for
            String sql_pu = " select a.idperfil, a.idusuario, ";
            sql_pu += " b.descripcion as perfil ";
            sql_pu += " from perfil_usuario a ";
            sql_pu += " join perfil b on b.idperfil = a.idperfil ";
            sql_pu += " where a.idusuario = ? ";
            List<Perfilusuario> puList = new ArrayList<>();
            List<Map<String, Object>> lPerfilUsuario = jdbcTemplate.queryForList(sql_pu, rs.get("idusuario"));
            for (Map<String, Object> rs1 : lPerfilUsuario) {
                Perfilusuario pu = new Perfilusuario();
                Perfil pe = new Perfil();
                pe.setIdperfil((Integer) rs1.get("idperfil"));
                pe.setDescripcion((String) rs1.get("perfil"));
                pu.setPerfil(pe);
                puList.add(pu);
            }

            if (!puList.isEmpty()) {
                usuario.setPerfilusuario(puList);
            } else {
                usuario.setPerfilusuario(null);
            }


        }
        return usuario;
    }

    @Override
    public List<Usuario> getUsuariosByEmpresa(Integer idempresa) {

        List<Usuario> ret = new ArrayList<>();

        String sql = " select a.*, ";
        sql += " b.descripcion as empresa, d.nombre as sucursal, c.descripcion as claseempleado , e.descripcion as deposito  ";
        sql += " from usuario a ";
        sql += " join empresa b on b.idempresa = a.idempresa ";
        sql += " join claseempleado c on c.idclaseempleado = a.idclaseempleado ";
        sql += " join sucursal d on d.idsucursal = a.idsucursal ";
        sql += " join deposito e on e.iddeposito = a.iddeposito ";
        sql += " where a.idempresa = ? and a.activo = true order by a.idusuario desc";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, idempresa);

        for (Map<String, Object> rs : list) {
            Usuario usuario = new Usuario();
            usuario.setIdusuario((Integer) rs.get("idusuario"));
            usuario.setUsuario((String) rs.get("usuario"));
            usuario.setNombreapellido((String) rs.get("nombreapellido"));
            usuario.setDireccion((String) rs.get("direccion"));
            usuario.setSexo((String) rs.get("sexo"));
            usuario.setLugarnacimiento((String) rs.get("lugarnacimiento"));
            usuario.setTelefono((String) rs.get("telefono"));
            usuario.setCelular((String) rs.get("celular"));
            usuario.setCargo((String) rs.get("cargo"));
            usuario.setProfesion((String) rs.get("profesion"));
            usuario.setEstado((Boolean) rs.get("activo"));
            Empresa empresa = new Empresa();
            empresa.setIdempresa((Integer) rs.get("idempresa"));
            empresa.setDescripcion((String) rs.get("empresa"));
            usuario.setEmpresa(empresa);
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal((Integer) rs.get("idsucursal"));
            sucursal.setNombre((String) rs.get("sucursal"));
            usuario.setSucursal(sucursal);
            Claseempleado claseempleado = new Claseempleado();
            claseempleado.setIdclaseempleado((Integer) rs.get("idclaseempleado"));
            claseempleado.setDescripcion((String) rs.get("claseempleado"));
            usuario.setClaseempleado(claseempleado);
            usuario.setOficial((Boolean) rs.get("oficial"));
            usuario.setNrodoc((String) rs.get("nrodoc"));
            usuario.setNrooficial((Integer) rs.get("nrooficial"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito((Integer) rs.get("iddeposito"));
            deposito.setDescripcion((String) rs.get("deposito"));
            usuario.setDeposito(deposito);

            //for
            String sql_pu = " select a.idperfil, a.idusuario, ";
            sql_pu += " b.descripcion as perfil ";
            sql_pu += " from perfil_usuario a ";
            sql_pu += " join perfil b on b.idperfil = a.idperfil ";
            sql_pu += " where a.idusuario = ? ";
            List<Perfilusuario> puList = new ArrayList<>();
            List<Map<String, Object>> lPerfilUsuario = jdbcTemplate.queryForList(sql_pu, rs.get("idusuario"));
            for (Map<String, Object> rs1 : lPerfilUsuario) {
                Perfilusuario pu = new Perfilusuario();
                //Usuario usuario_p = new Usuario();
                //usuario_p.setIdusuario((Integer)rs1.get("idusuario"));
                //pu.setUsuario(usuario_p);
                Perfil pe = new Perfil();
                pe.setIdperfil((Integer) rs1.get("idperfil"));
                pe.setDescripcion((String) rs1.get("perfil"));
                pu.setPerfil(pe);
                puList.add(pu);
            }

            if (!puList.isEmpty()) {
                usuario.setPerfilusuario(puList);
            } else {
                usuario.setPerfilusuario(null);
            }

            ret.add(usuario);

        }

        return ret;

    }

    @Override
    public LoginData getLoginUsuario(String username) throws Exception {

        System.out.println("Buscando informacion del usuario: " + username);

        String sql = "select a.*, b.descripcion as empresa, b.ruc, c.nombre as sucursal, c.telefono1, c.direccion ";
        sql += " from usuario a  ";
        sql += " left join empresa b on b.idempresa = a.idempresa  ";
        sql += " join sucursal c on c.idsucursal = a.idsucursal  ";
      //  sql += " left join turno t on t.idusuario = a.idusuario   ";
        sql += " where lower(usuario) = lower(?) ";

        LoginData loginData = new LoginData();

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username);

        for (Map<String, Object> row : list) {
        	
            Usuario usuarioData = new Usuario();
            usuarioData.setIdusuario((Integer) row.get("idusuario"));
            usuarioData.setUsuario((String) row.get("usuario"));
            Caja caja = new Caja();
            caja.setIdcaja((Integer) row.get("idcaja"));
            usuarioData.setCaja(caja);
            loginData.setCaja(caja);
            
            Empresa empresa = new Empresa();
            empresa.setIdempresa((Integer) row.get("idempresa"));
            empresa.setDescripcion((String) row.get("empresa"));
            empresa.setRuc((String) row.get("ruc"));
            Deposito deposito = new Deposito();
            deposito.setIddeposito((Integer) row.get("iddeposito"));
            usuarioData.setDeposito(deposito);
            usuarioData.setEmpresa(empresa);
            Sucursal sucursal = new Sucursal();
            sucursal.setIdsucursal((Integer) row.get("idsucursal"));
            sucursal.setDescripcion((String) row.get("sucursal"));
            sucursal.setTelefono1((String) row.get("telefono1"));
            sucursal.setDireccion((String) row.get("direccion"));
            usuarioData.setSucursal(sucursal);
            loginData.setSucursal(sucursal);
            loginData.setEmpresa(empresa);
            
            Turno turno = turnoService.getByUsuarioActivo(usuarioData.getIdusuario());
            if(turno != null){   
            	 loginData.setTurno(turno);
            	 logger.info("El login es : " + turno.getIdturno());
            }else{
            	 loginData.setTurno(null);
            	 logger.info("No tiene turno abierto");
            }

            //for
            String sql_pu = " select a.idperfil, a.idusuario, ";
            sql_pu += " b.descripcion as perfil ";
            sql_pu += " from perfil_usuario a ";
            sql_pu += " join perfil b on b.idperfil = a.idperfil ";
            sql_pu += " where a.idusuario = ? ";
            List<Perfilusuario> puList = new ArrayList<>();
            List<Perfil> perfilList = new ArrayList<>();
            List<Map<String, Object>> lPerfilUsuario = jdbcTemplate.queryForList(sql_pu, usuarioData.getIdusuario());
            for (Map<String, Object> rs1 : lPerfilUsuario) {
                Perfilusuario pu = new Perfilusuario();
                //Usuario usuario_p = new Usuario();
                //usuario_p.setIdusuario((Integer)rs1.get("idusuario"));
                //pu.setUsuario(usuario_p);
                Perfil pe = new Perfil();
                pe.setIdperfil((Integer) rs1.get("idperfil"));
                pe.setDescripcion((String) rs1.get("perfil"));
                pu.setPerfil(pe);
                perfilList.add(pe);
                puList.add(pu);
            }

            if (!puList.isEmpty()) {
                logger.info("setenado perfil al usuario");
                usuarioData.setPerfilusuario(puList);
            } else {
                usuarioData.setPerfilusuario(null);
            }
            loginData.setUsuario(usuarioData);
            loginData.setPerfiles(perfilList);

            //menu por usuario
            for (Perfilusuario p1 : puList) {
                logger.info("setenado menu al usuario");
                loginData.setPerfilactual(p1.getPerfil());
                List<Menu> listMenues = menuService.getByPerfil(p1.getPerfil().getIdperfil());
                loginData.setMenues(listMenues);
                break;
            }
        }
        return loginData;

    }


    @Override
    public Usuario add(Usuario entity) throws Exception {
        try {
            em.persist(entity);
            return entity;
        } catch (Exception ex) {
            logger.info("Error al agregar usuario " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Usuario update(Usuario entity) throws Exception {
        try {
            usuarioRepository.save(entity);
            return entity;
        } catch (Exception ex) {
            logger.info("Error al actualizar usuario " + ex.getMessage());
            throw ex;
        }

    }

    @Override
    public String delete(Integer idUsuario) throws Exception {
        Usuario entity = getUsuarioById(idUsuario);
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception e) {
            logger.info("Error al eliminar empresa " + e.getMessage());
        }
        return " Usuario eliminado correctamente";

    }


    @Override
    public Usuario addUsuario(Usuario u) throws Exception {

        try {

            u.setClave(loginService.getHashPassword("temporal"));
            Long idusuario = getSecuenceFromName("usuario_idusuario_seq");
            System.out.println("Idusuario: " + u.toString());

            if (idusuario.intValue() != 0) {

                //define sql String
                String sqlInsert = " insert into usuario (idusuario, usuario, nombreapellido, contrasena, activo, ";
                sqlInsert += "idempresa, idsucursal, cargo, idclaseempleado , iddeposito, idcaja) ";
                sqlInsert += " values (?,?,?,?,?,?,?,?,?,?,?) ";

                Object[] params = new Object[]{idusuario.intValue(),
                        u.getUsuario(),
                        u.getNombreapellido(),
                        u.getClave(),
                        u.getEstado(),
                        u.getEmpresa().getIdempresa(),
                        u.getSucursal().getIdsucursal(),
                        u.getCargo(),
                        u.getClaseempleado().getIdclaseempleado(),
                        u.getDeposito().getIddeposito(), u.getCaja().getIdcaja()};

                //define types for sql insert
                int[] types = new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.BOOLEAN, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                        Types.INTEGER};
                //insert values to jdbcTemplate
                jdbcTemplate.update(sqlInsert, params, types);


                //agregar pefil usuario
                for (Perfilusuario perfil : u.getPerfilusuario()) {
                    String sqlInsert2 = " insert into perfil_usuario (idusuario, idperfil ) ";
                    sqlInsert2 += " values (?,?) ";

                    Object[] params2 = new Object[]{idusuario.intValue(), perfil.getPerfil().getIdperfil()};

                    //define types for sql insert
                    int[] types2 = new int[]{Types.INTEGER, Types.INTEGER};
                    //insert values to jdbcTemplate
                    jdbcTemplate.update(sqlInsert2, params2, types2);
                }

                String sqlInsert3 = " insert into user_authority  ";
                sqlInsert3 += " values (?,?) ";

                Object[] params3 = new Object[]{idusuario.intValue(), "ROLE_USER"};

                //define types for sql insert
                int[] types3 = new int[]{Types.INTEGER, Types.VARCHAR};
                //insert values to jdbcTemplate
                jdbcTemplate.update(sqlInsert3, params3, types3);

            }


        } catch (Exception e) {
            throw e;
        }

        return u;
    }

    @Override
    public Usuario updateUsuario(Usuario u) throws Exception {

        Usuario u2 = getUsuarioById(u.getIdusuario());
        if (u2 != null) {
            u2.setUsuario(u.getUsuario());
            u2.setNombreapellido(u.getNombreapellido());
            u2.setCargo(u.getCargo());
            u2.setEmpresa(u.getEmpresa());
            u2.setClaseempleado(u.getClaseempleado());
            u2.setSucursal(u.getSucursal());
            u2.setDeposito(u.getDeposito());
            u2.setCaja(u.getCaja());
            u2 = update(u2);
        } else {
            throw new CustomMessageException("No se encuentra usuario");
        }

        perfilService.deletePerilByUser(u2.getIdusuario());
        //agregar pefil usuario
        for (Perfilusuario perfil : u.getPerfilusuario()) {


            String sqlInsert2 = " insert into perfil_usuario (idusuario, idperfil ) ";
            sqlInsert2 += " values (?,?) ";

            Object[] params2 = new Object[]{u2.getIdusuario(), perfil.getPerfil().getIdperfil()};

            //define types for sql insert
            int[] types2 = new int[]{Types.INTEGER, Types.INTEGER};
            //insert values to jdbcTemplate
            jdbcTemplate.update(sqlInsert2, params2, types2);
        }
        return u2;
    }

    @Override
    public void changePassword(Integer idusuario, String password) throws Exception {
        try {
            Usuario u = getUsuarioById(idusuario);

            u.setClave(loginService.getHashPassword(password));

            String sqlInsert3 = " update usuario set contrasena = ?  ";
            sqlInsert3 += " where idusuario = ? ";

            Object[] params3 = new Object[]{u.getClave(), idusuario.intValue()};

            //define types for sql insert
            int[] types3 = new int[]{Types.VARCHAR, Types.INTEGER};
            //insert values to jdbcTemplate
            jdbcTemplate.update(sqlInsert3, params3, types3);

            System.out.println("usuario " + u.getUsuario() + " actualizado");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }


    }

    @Override
    public Boolean checkPassword(Integer idusuario, String password) throws Exception {
        Boolean ban = false;
        try {
            Usuario u = getUsuarioById(idusuario);
            ban = loginService.checkHashPassword(u.getClave(), password);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ban;
    }
}