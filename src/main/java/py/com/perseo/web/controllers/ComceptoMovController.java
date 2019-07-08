package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.Conceptomov;
import py.com.perseo.tesoreria.services.ConceptomovService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ComceptoMovController {
	
	private static final Logger logger = LoggerFactory.getLogger(ComceptoMovController.class);

	@Autowired
	private ConceptomovService conceptomovService;
	
	@Autowired
	private LoginService loginService;
	

	// APIs
	public static final String CONCEPTO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/conceptomov/";
	public static final String CONCEPTO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/conceptomov/{idConceptMov";
		




	/**
	 * Obtiene las familias por el ID
	 * @param idFamilia
	 * @return
	 */
	@GetMapping(CONCEPTO_BY_ID)
	public @ResponseBody Respuesta getFamiliaById(@PathVariable("idConceptMov") Integer idConceptMov) {
		logger.info(CONCEPTO_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(conceptomovService.getById(idConceptMov));
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
	@PostMapping(CONCEPTO)
	public @ResponseBody Respuesta addFamilia(@RequestBody Conceptomov conceptomov) {
		logger.info(CONCEPTO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(conceptomovService.add(conceptomov));
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
	@GetMapping(CONCEPTO)
	public @ResponseBody Respuesta getAllFamilias() {
		logger.info(CONCEPTO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(conceptomovService.getAll());
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
	@PutMapping(CONCEPTO)
	public @ResponseBody Respuesta updateFamilia(@RequestBody Conceptomov conceptomov, HttpServletRequest request) {
		logger.info(CONCEPTO );
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(conceptomovService.update(conceptomov));
			
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
	@DeleteMapping(CONCEPTO)
	public @ResponseBody Respuesta deleteFamilia(@PathVariable("idConceptMov") Integer idConceptMov) {
		logger.info(CONCEPTO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(conceptomovService.delete(idConceptMov));
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