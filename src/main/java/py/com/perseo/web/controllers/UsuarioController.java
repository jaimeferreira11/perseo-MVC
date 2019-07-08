package py.com.perseo.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Usuario;
import py.com.perseo.comun.services.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.session.services.PerfilService;
import py.com.perseo.sueldos.services.ClaseEmpleadoService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(value="usuarios", description="Operaciones del usuario")
public class UsuarioController {

	private static Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	public static final String USUARIOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/usuarios/";
	
	public static final String USUARIO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/usuarios/{idUsuario}";
	
	public static final String USUARIO_BY_USERNAME = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/usuarios/username/{username}";
	
	public static final String USUARIOS_BY_EMPRESA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/usuarios/empresa/{idEmpresa}";
	
	private static final String API_RESET_PWD = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION +"/usuarios/reset/{idusuario}/{pwd}";
	private static final String API_CHANGE_PWD = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION +"/usuarios/change-pwd/{idusuario}/{password}";
	private static final String API_CECK_PWD = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION +"/usuarios/check-pwd/{idusuario}/{password}";
	// vistas
	private static final String MANT_USUARIOS = "/mantusuarios";
	private static final String RESET_USUARIOS = "/resetusers";
		
	UsuarioService usuarioService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SucursalService sucursalService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private DistritoService distritoService;
	
	@Autowired
	private BarrioService barrioService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private ClaseEmpleadoService claseEmpleadoService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	/*
	 * 
	 */
	
	@RequestMapping(value = MANT_USUARIOS, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_DEPOSITOS);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()) );
			model.addAttribute("claseempleado", claseEmpleadoService.getAll());
			model.addAttribute("perfiles", perfilService.getPerfilesByEmpresa(login.getEmpresa().getIdempresa(), false));
			
			
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/comun/usuario/mantusuarios";
	}
	
	@RequestMapping(value = RESET_USUARIOS, method = RequestMethod.GET)
	public String resetUsuario(Model model, HttpServletRequest request) {
		try {
			//perfilService.validarPerfilUsuario(request, RESET_USUARIOS);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
			
		}catch (CustomMessageException e1) {
			//e1.printStackTrace();
			return "redirect:403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:500";
		}

		return "/comun/usuario/resetusers";
	}
	
	/**
	 * Resetea la contrasena de un usuario
	 * @param idusuario
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = API_RESET_PWD, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta resetUsuario(@PathVariable String idusuario, @PathVariable String pwd, HttpServletRequest request) {
		logger.info( API_RESET_PWD + " " + idusuario);
		Respuesta respuesta = new Respuesta("OK");
		try {
			usuarioService.changePassword(Integer.parseInt(idusuario), pwd);
			respuesta.setDatos(idusuario);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}
	/**
	 * Cambia la contrasena de un usuario
	 * @param idusuario
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = API_CHANGE_PWD, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta changeUsuario(@PathVariable Integer idusuario,@PathVariable String password,  HttpServletRequest request) {
		Respuesta respuesta = new Respuesta("OK");
		try {
			logger.info("User {} accessing to {} " + API_CHANGE_PWD + " " + idusuario + " = " + loginService.getLoginCache(request).getUsuario().getIdusuario());
			
			//if(loginService.getLoginCache(request).getUsuario().getIdusuario() == idusuario){
				usuarioService.changePassword(idusuario, password);
				respuesta.setDatos(idusuario);
			/*}else{				
				respuesta.setEstado("ERROR");
				respuesta.setError("No puedes cambiar la contrasena de otro usuario");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}
	/**
	 * Comprueba la contrasena de un usuario
	 * @param idusuario
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = API_CECK_PWD, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta validateUsuario(@PathVariable Integer idusuario,@PathVariable String password,  HttpServletRequest request) {
		logger.info( API_CECK_PWD + " " + idusuario);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.checkPassword(idusuario, password));
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}
	
	/**
	 * Obtiene los usuarios por el ID
	 * @param idUsuario
	 * @return
	 */
	@GetMapping(USUARIO_BY_ID)
	@ApiOperation(httpMethod = "GET", value = "Obtiene los usuarios por el ID")
	public @ResponseBody Respuesta getUsuarioById(@PathVariable("idUsuario") Integer idUsuario) {
		logger.info(USUARIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.getUsuarioById(idUsuario));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	/**
	 * Obtiene los usuarios por el ID
	 * @param idUsuario
	 * @return
	 */
	@GetMapping(USUARIO_BY_USERNAME)
	@ApiOperation(httpMethod = "GET", value = "Obtiene los usuarios por el ID")
	public @ResponseBody Respuesta getUsuarioByUsername(@PathVariable("username") String username) {
		logger.info(USUARIO_BY_USERNAME);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.getUsuarioByUsername(username));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	/**
	 * Obtiene los usuarios por empresa
	 * @param Usuario
	 * @return
	 */
	@GetMapping(USUARIOS_BY_EMPRESA)
	@ApiOperation(httpMethod = "GET", value = "Obtiene los usuarios por empresa")
	public @ResponseBody Respuesta getUsuarioByParams(@PathVariable("idEmpresa") Integer idEmpresa) {
		logger.info(USUARIOS_BY_EMPRESA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.getUsuariosByEmpresa(idEmpresa));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}

	/**
	 * Agrega un usuario
	 * @param usuario
	 * @return
	 */
	@PostMapping(USUARIOS)
	public @ResponseBody Respuesta addUsuario(@RequestBody Usuario usuario) {
		logger.info(USUARIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.addUsuario(usuario));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	/**
	 * Obtiene todos los usuarios
	 * @return
	 */
	@GetMapping(USUARIOS)
	public @ResponseBody Respuesta getAllUsuarios() {
		logger.info(USUARIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.getUsuarios());
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	/**
	 * Actualiza un usuario
	 * @param usuario
	 * @return
	 */
	@PutMapping(USUARIOS)
	public @ResponseBody Respuesta updateUsuario(@RequestBody Usuario usuario) {
		logger.info(USUARIOS);
		logger.info(usuario.toString());
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.updateUsuario(usuario));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
	/**
	 * Desactiva o borra el usuario
	 * @param idUsuario
	 * @return
	 */
	@DeleteMapping(USUARIOS)
	public @ResponseBody Respuesta deleteUsuario(@PathVariable("idUsuario") Integer idUsuario) {
		logger.info(USUARIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(usuarioService.delete(idUsuario));
		} catch (CustomMessageException ce) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	

}
