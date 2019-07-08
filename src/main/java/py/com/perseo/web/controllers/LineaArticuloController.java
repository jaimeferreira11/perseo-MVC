package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.stock.entities.Lineaarticulo;
import py.com.perseo.stock.services.LineaarticuloService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class LineaArticuloController {
	
	private static final Logger logger = LoggerFactory.getLogger(LineaArticuloController.class);

	@Autowired
	private LineaarticuloService lineaarticuloService;
	


	// APIs
	public static final String LINEA_ARTICULOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/lineaarticulos/";
	public static final String LINEA_ARTICULO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/lineaarticulos/{idLineaarticulo}";
	public static final String LINEA_ARTICULO_BY_FAMILIA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/lineaarticulos/familia/{idFamilia}";

		

	/**
	 * Obtiene las linea de articulos por el ID de familia
	 * @param idFamilia
	 * @return
	 */
	@GetMapping(LINEA_ARTICULO_BY_FAMILIA)
	public @ResponseBody Respuesta getLineaarticuloByFamilia(@PathVariable("idFamilia") Integer idFamilia) {
		logger.info(LINEA_ARTICULO_BY_FAMILIA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.getByFamilia(idFamilia));
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
	 * Obtiene las linea de articulos por el ID
	 * @param idLineaarticulo
	 * @return
	 */
	@GetMapping(LINEA_ARTICULO_BY_ID)
	public @ResponseBody Respuesta getLineaarticuloById(@PathVariable("idLineaarticulo") Integer idLineaarticulo) {
		logger.info(LINEA_ARTICULO_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.getById(idLineaarticulo));
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
	 * Agrega una linea de articulo
	 * @param lineaarticulo
	 * @return
	 */
	@PostMapping(LINEA_ARTICULOS)
	public @ResponseBody Respuesta addLineaarticulo(@RequestBody Lineaarticulo lineaarticulo) {
		logger.info(LINEA_ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.add(lineaarticulo));
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
	 * Obtiene todos las Linea articulos
	 * @return
	 */
	@GetMapping(LINEA_ARTICULOS)
	public @ResponseBody Respuesta getAllLineaarticulos() {
		logger.info(LINEA_ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.getAll());
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
	 * Actualiza una linea articulo
	 * @param lineaarticulo
	 * @return
	 */
	@PutMapping(LINEA_ARTICULOS)
	public @ResponseBody Respuesta updateLineaarticulo(@RequestBody Lineaarticulo lineaarticulo) {
		logger.info(LINEA_ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.update(lineaarticulo));
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
	 * Desactiva o borra la linea articulo
	 * @param idLineaarticulo
	 * @return
	 */
	@DeleteMapping(LINEA_ARTICULO_BY_ID)
	public @ResponseBody Respuesta deleteLineaarticulo(@PathVariable("idLineaarticulo") Integer idLineaarticulo) {
		logger.info(LINEA_ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(lineaarticuloService.delete(idLineaarticulo));
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