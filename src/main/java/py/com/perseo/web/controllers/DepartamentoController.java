package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.entities.Departamento;
import py.com.perseo.comun.services.DepartamentoService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class DepartamentoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartamentoController.class);

	@Autowired
	private DepartamentoService departamentoService;
	
	// APIs
	public static final String DEPARTAMENTOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/departamentos/";
	public static final String DEPARTAMENTO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/departamentos/{idDepartamento}";
		

	/**
	 * Obtiene los departamentos por el ID
	 * @param idDepartamento
	 * @return
	 */
	@GetMapping(DEPARTAMENTO_BY_ID)
	public @ResponseBody Respuesta getDepartamentoById(@PathVariable("idDepartamento") Integer idDepartamento) {
		logger.info(DEPARTAMENTO_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(departamentoService.getById(idDepartamento));
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
	 * Agrega un departamento
	 * @param departamento
	 * @return
	 */
	@PostMapping(DEPARTAMENTOS)
	public @ResponseBody Respuesta addDepartamento(@RequestBody Departamento departamento) {
		logger.info(DEPARTAMENTOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(departamentoService.add(departamento));
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
	 * Obtiene todos los Departamentos
	 * @return
	 */
	@GetMapping(DEPARTAMENTOS)
	public @ResponseBody Respuesta getAllDepartamentos() {
		logger.info(DEPARTAMENTOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(departamentoService.getAll());
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
	 * Actualiza un departamento
	 * @param departamento
	 * @return
	 */
	@PutMapping(DEPARTAMENTOS)
	public @ResponseBody Respuesta updateDepartamento(@RequestBody Departamento departamento) {
		logger.info(DEPARTAMENTOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(departamentoService.update(departamento));
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
	 * Desactiva o borra el departamento
	 * @param idDepartamento
	 * @return
	 */
	@DeleteMapping(DEPARTAMENTO_BY_ID)
	public @ResponseBody Respuesta deleteDepartamento(@PathVariable("idDepartamento") Integer idDepartamento) {
		logger.info(DEPARTAMENTOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(departamentoService.delete(idDepartamento));
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