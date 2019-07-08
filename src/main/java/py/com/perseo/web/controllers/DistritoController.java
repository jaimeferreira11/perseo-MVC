package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.entities.Distrito;
import py.com.perseo.comun.services.DistritoService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DistritoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DistritoController.class);

	@Autowired
	private DistritoService distritoService;
	
	@Autowired
	private LoginService loginService;

	// vistas
	private static final String MANT_DISTRITO = "/mantdistritos";
	
	// APIs
	public static final String DISTRITOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/distritos/";
	public static final String DISTRITO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/distritos/{idDistrito}";
		
	
	
	@RequestMapping(value = MANT_DISTRITO, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_DISTRITOS);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/comun/distrito/mantdistritos";
	}
	

	/**
	 * Obtiene los distritos por el ID
	 * @param idDistrito
	 * @return
	 */
	@GetMapping(DISTRITO_BY_ID)
	public @ResponseBody Respuesta getDistritoById(@PathVariable("idDistrito") Integer idDistrito) {
		logger.info(DISTRITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(distritoService.getById(idDistrito));
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
	 * Agrega un distrito
	 * @param distrito
	 * @return
	 */
	@PostMapping(DISTRITOS)
	public @ResponseBody Respuesta addDistrito(@RequestBody Distrito distrito) {
		logger.info(DISTRITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(distritoService.add(distrito));
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
	 * Obtiene todos los Distritos
	 * @return
	 */
	@GetMapping(DISTRITOS)
	public @ResponseBody Respuesta getAllDistritos() {
		logger.info(DISTRITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(distritoService.getAll());
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
	 * Actualiza un distrito
	 * @param distrito
	 * @return
	 */
	@PutMapping(DISTRITOS)
	public @ResponseBody Respuesta updateDistrito(@RequestBody Distrito distrito) {
		logger.info(DISTRITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(distritoService.update(distrito));
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
	 * Desactiva o borra el distrito
	 * @param idDistrito
	 * @return
	 */
	@DeleteMapping(DISTRITO_BY_ID)
	public @ResponseBody Respuesta deleteDistrito(@PathVariable("idDistrito") Integer idDistrito) {
		logger.info(DISTRITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(distritoService.delete(idDistrito));
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