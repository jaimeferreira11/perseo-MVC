package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.stock.entities.Familia;
import py.com.perseo.stock.services.ArticuloPrecioVentacabService;
import py.com.perseo.stock.services.FamiliaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FamiliaController {
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliaController.class);

	@Autowired
	private FamiliaService familiaService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ArticuloPrecioVentacabService articuloPrecioVentacabService;


	// APIs
	public static final String FAMILIAS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/familias/";
	public static final String FAMILIA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/familias/{idFamilia}";
		

	public static final String MANT_FAMILIAS = "/mantfamilias";

	@RequestMapping(value = MANT_FAMILIAS, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
			//	perfilService.validarPerfilUsuario(request, MANT_SUCURSALES);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("listado", familiaService.getAll());
			model.addAttribute("listacab", articuloPrecioVentacabService.getArticulosPrecioCabByEmpresa(login.getEmpresa().getIdempresa(), false, true));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/articulo/mantfamilias";
	}

	/**
	 * Obtiene las familias por el ID
	 * @param idFamilia
	 * @return
	 */
	@GetMapping(FAMILIA_BY_ID)
	public @ResponseBody Respuesta getFamiliaById(@PathVariable("idFamilia") Integer idFamilia) {
		logger.info(FAMILIA_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(familiaService.getById(idFamilia));
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
	 * Agrega una familia
	 * @param familia
	 * @return
	 */
	@PostMapping(FAMILIAS)
	public @ResponseBody Respuesta addFamilia(@RequestBody Familia familia) {
		logger.info(FAMILIAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(familiaService.add(familia));
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
	 * Obtiene todos las Familias
	 * @return
	 */
	@GetMapping(FAMILIAS)
	public @ResponseBody Respuesta getAllFamilias() {
		logger.info(FAMILIAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(familiaService.getAll());
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
	 * Actualiza una familia
	 * @param familia
	 * @return
	 */
	@PutMapping(FAMILIAS)
	public @ResponseBody Respuesta updateFamilia(@RequestBody Familia familia, HttpServletRequest request) {
		logger.info(FAMILIAS + ": " + familia.toString() );
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(familiaService.updateFamilia(familia, loginService.getLoginCache(request).getUsuario()));
			
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
	 * Desactiva o borra la familia
	 * @param idFamilia
	 * @return
	 */
	@DeleteMapping(FAMILIA_BY_ID)
	public @ResponseBody Respuesta deleteFamilia(@PathVariable("idFamilia") Integer idFamilia) {
		logger.info(FAMILIAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(familiaService.delete(idFamilia));
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