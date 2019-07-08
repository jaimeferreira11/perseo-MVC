package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.entities.Barrio;
import py.com.perseo.comun.services.BarrioService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class BarrioController {
	
	private static final Logger logger = LoggerFactory.getLogger(BarrioController.class);

	@Autowired
	private BarrioService barrioService;
	


	// APIs
	public static final String BARRIOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/barrios/";
	public static final String BARRIO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/barrios/{idBarrio}";
		

	/**
	 * Obtiene los barrios por el ID
	 * @param idBarrio
	 * @return
	 */
	@GetMapping(BARRIO_BY_ID)
	public @ResponseBody Respuesta getBarrioById(@PathVariable("idBarrio") Integer idBarrio) {
		logger.info(BARRIO_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(barrioService.getById(idBarrio));
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
	 * Agrega un barrio
	 * @param barrio
	 * @return
	 */
	@PostMapping(BARRIOS)
	public @ResponseBody Respuesta addBarrio(@RequestBody Barrio barrio) {
		logger.info(BARRIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(barrioService.add(barrio));
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
	 * Obtiene todos los Barrios
	 * @return
	 */
	@GetMapping(BARRIOS)
	public @ResponseBody Respuesta getAllBarrios() {
		logger.info(BARRIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(barrioService.getAll());
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
	 * Actualiza un barrio
	 * @param barrio
	 * @return
	 */
	@PutMapping(BARRIOS)
	public @ResponseBody Respuesta updateBarrio(@RequestBody Barrio barrio) {
		logger.info(BARRIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(barrioService.update(barrio));
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
	 * Desactiva o borra el barrio
	 * @param idBarrio
	 * @return
	 */
	@DeleteMapping(BARRIO_BY_ID)
	public @ResponseBody Respuesta deleteBarrio(@PathVariable("idBarrio") Integer idBarrio) {
		logger.info(BARRIOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(barrioService.delete(idBarrio));
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