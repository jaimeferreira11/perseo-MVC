package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.entities.Sucursal;
import py.com.perseo.comun.services.DistritoService;
import py.com.perseo.comun.services.SucursalService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SucursalController {
	
	private static final Logger logger = LoggerFactory.getLogger(SucursalController.class);

	@Autowired
	private SucursalService sucursalService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DistritoService distritoService;

	// APIs
	public static final String SUCURSALES = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/sucursales/";
	public static final String SUCURSAL_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/sucursales/{idSucursal}";
	
	// vistas
	private static final String MANT_SUCURSALES = "/mantsucursales";
	
	
	
	@RequestMapping(value = MANT_SUCURSALES, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_SUCURSALES);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("ciudades", distritoService.getAll());
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/comun/sucursal/mantsucursales";
	}
	

	/**
	 * Obtiene las sucursales por el ID
	 * @param idSucursal
	 * @return
	 */
	@GetMapping(SUCURSAL_BY_ID)
	public @ResponseBody Respuesta getSucursalById(@PathVariable("idSucursal") Integer idSucursal) {
		logger.info(SUCURSALES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(sucursalService.getSucursalById(idSucursal));
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
	 * Agrega una sucursal
	 * @param sucursal
	 * @return
	 */
	@PostMapping(SUCURSALES)
	public @ResponseBody Respuesta addSucursal(@RequestBody Sucursal sucursal) {
		logger.info(SUCURSALES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(sucursalService.addSucursal(sucursal));
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
	 * Obtiene todos las Sucursales
	 * @return
	 */
	@GetMapping(SUCURSALES)
	public @ResponseBody Respuesta getAllSucursales() {
		logger.info(SUCURSALES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(sucursalService.getAllSucursales());
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
	 * Actualiza una sucursal
	 * @param sucursal
	 * @return
	 */
	@PutMapping(SUCURSALES)
	public @ResponseBody Respuesta updateSucursal(@RequestBody Sucursal sucursal) {
		logger.info(SUCURSALES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(sucursalService.update(sucursal));
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
	 * Desactiva o borra la sucursal
	 * @param idSucursal
	 * @return
	 */
	@DeleteMapping(SUCURSAL_BY_ID)
	public @ResponseBody Respuesta deleteSucursal(@PathVariable("idSucursal") Integer idSucursal) {
		logger.info(SUCURSALES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(sucursalService.delete(idSucursal));
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