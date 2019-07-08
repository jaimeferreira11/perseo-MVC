package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.stock.entities.Unidadmedida;
import py.com.perseo.stock.services.UnidadmedidaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class UnidadmedidaController {
	
	private static final Logger logger = LoggerFactory.getLogger(UnidadmedidaController.class);

	@Autowired
	private UnidadmedidaService unidadmedidaService;
	


	// APIs
	public static final String UNIDADES_MEDIDA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/unidadmedidas/";
	public static final String UNIDAD_MEDIDA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/unidadmedidas/{idUnidadmedida}";
		

	/**
	 * Obtiene los unidadmedidas por el ID
	 * @param idUnidadmedida
	 * @return
	 */
	@GetMapping(UNIDAD_MEDIDA_BY_ID)
	public @ResponseBody Respuesta getUnidadmedidaById(@PathVariable("idUnidadmedida") Integer idUnidadmedida) {
		logger.info(UNIDAD_MEDIDA_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(unidadmedidaService.getById(idUnidadmedida));
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
	 * Agrega un unidadmedida
	 * @param unidadmedida
	 * @return
	 */
	@PostMapping(UNIDADES_MEDIDA)
	public @ResponseBody Respuesta addUnidadmedida(@RequestBody Unidadmedida unidadmedida) {
		logger.info(UNIDADES_MEDIDA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(unidadmedidaService.add(unidadmedida));
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
	 * Obtiene todos los Unidadmedidas
	 * @return
	 */
	@GetMapping(UNIDADES_MEDIDA)
	public @ResponseBody Respuesta getAllUnidadmedidas() {
		logger.info(UNIDADES_MEDIDA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(unidadmedidaService.getAll());
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
	 * Actualiza un unidadmedida
	 * @param unidadmedida
	 * @return
	 */
	@PutMapping(UNIDADES_MEDIDA)
	public @ResponseBody Respuesta updateUnidadmedida(@RequestBody Unidadmedida unidadmedida) {
		logger.info(UNIDADES_MEDIDA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(unidadmedidaService.update(unidadmedida));
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
	 * Desactiva o borra el unidadmedida
	 * @param idUnidadmedida
	 * @return
	 */
	@DeleteMapping(UNIDAD_MEDIDA_BY_ID)
	public @ResponseBody Respuesta deleteUnidadmedida(@PathVariable("idUnidadmedida") Integer idUnidadmedida) {
		logger.info(UNIDADES_MEDIDA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(unidadmedidaService.delete(idUnidadmedida));
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