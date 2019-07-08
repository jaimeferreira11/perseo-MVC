package py.com.perseo.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.stock.services.MarcaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
@Api(value="marcas", description="Operaciones de marca de articulos")
public class MarcaController {
	
	private static Logger LOG = LoggerFactory.getLogger(MarcaController.class);

	public static final String MARCA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/marcas/{idMarca}";
	
	public static final String MARCAS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/marcas";

	MarcaService marcaService;
	
	
	@Autowired
	public MarcaController(MarcaService marcaService) {
		this.marcaService = marcaService;
	}
	

	/**
	 * Obtiene las marcas por el ID
	 * @param idMarca
	 * @return
	 */
	@GetMapping(MARCA_BY_ID)
	@ApiOperation(httpMethod = "GET", value = "Obtiene las marcas por el ID")
	public @ResponseBody Respuesta getMarcaById(@PathVariable("idMarca") Integer idMarca) {
		LOG.info(MARCA_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(marcaService.getById(idMarca));
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
	 * Obtiene las marcas por el ID
	 * @param idMarca
	 * @return
	 */
	@GetMapping(MARCAS)
	@ApiOperation(httpMethod = "GET", value = "Obtiene las marcas")
	public @ResponseBody Respuesta getAllMarcas() {
		LOG.info(MARCAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(marcaService.getAll());
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
