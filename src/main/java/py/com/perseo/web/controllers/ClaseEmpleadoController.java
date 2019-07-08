package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.sueldos.entities.Claseempleado;
import py.com.perseo.sueldos.services.ClaseEmpleadoService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ClaseEmpleadoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClaseEmpleadoController.class);

	@Autowired
	private ClaseEmpleadoService claseEmpleadoService;
	
	@Autowired
	private LoginService loginService;

	// vistas
	private static final String MANT_DISTRITO = "/mantclasesempleado";
	
	// APIs
	public static final String CLASE_EMPLEADO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/clasesempleado/";
	public static final String CLASE_EMPLEADO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/clasesempleado/{idClaseempleado}";
		
	
	
	@RequestMapping(value = MANT_DISTRITO, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_CLASE_EMPLEADO);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/comun/distrito/mantclasesempleado";
	}
	

	/**
	 * Obtiene los clasesempleado por el ID
	 * @param idClaseempleado
	 * @return
	 */
	@GetMapping(CLASE_EMPLEADO_BY_ID)
	public @ResponseBody Respuesta getClaseempleadoById(@PathVariable("idClaseempleado") Integer idClaseempleado) {
		logger.info(CLASE_EMPLEADO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(claseEmpleadoService.getById(idClaseempleado));
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
	 * Agrega un claseEmpleado
	 * @param claseEmpleado
	 * @return
	 */
	@PostMapping(CLASE_EMPLEADO)
	public @ResponseBody Respuesta addClaseempleado(@RequestBody Claseempleado claseEmpleado) {
		logger.info(CLASE_EMPLEADO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(claseEmpleadoService.add(claseEmpleado));
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
	 * Obtiene todos los Claseempleados
	 * @return
	 */
	@GetMapping(CLASE_EMPLEADO)
	public @ResponseBody Respuesta getAllClaseempleados() {
		logger.info(CLASE_EMPLEADO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(claseEmpleadoService.getAll());
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
	 * Actualiza un claseEmpleado
	 * @param claseEmpleado
	 * @return
	 */
	@PutMapping(CLASE_EMPLEADO)
	public @ResponseBody Respuesta updateClaseempleado(@RequestBody Claseempleado claseEmpleado) {
		logger.info(CLASE_EMPLEADO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(claseEmpleadoService.update(claseEmpleado));
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
	 * Desactiva o borra el claseEmpleado
	 * @param idClaseempleado
	 * @return
	 */
	@DeleteMapping(CLASE_EMPLEADO)
	public @ResponseBody Respuesta deleteClaseempleado(@PathVariable("idClaseempleado") Integer idClaseempleado) {
		logger.info(CLASE_EMPLEADO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(claseEmpleadoService.delete(idClaseempleado));
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