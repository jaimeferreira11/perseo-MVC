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
import py.com.perseo.stock.entities.Articuloprecioventacab;
import py.com.perseo.stock.entities.Articuloprecioventadet;
import py.com.perseo.stock.services.ArticuloPrecioVentacabService;
import py.com.perseo.stock.services.ArticuloPrecioVentadetService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticuloPrecioVentaController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloPrecioVentaController.class);

	@Autowired
	private ArticuloPrecioVentacabService articulocabService;
	@Autowired
	private ArticuloPrecioVentadetService articulodetService;
	
	@Autowired
	private LoginService loginService;

	
	// APIs
	// articuloprecioventacab
	public static final String ARTICULOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventacab/";
	public static final String ARTICULO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventacab/{idArticuloPrecioCab}";
	public static final String ARTICULOS_BY_EMPRESA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventacab/empresa/{idEmpresa}";
	// articuloprecioventadet
	public static final String ARTICULOSDET = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventadet/";
	public static final String ARTICULO_DET_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventadet/{idArticuloPrecioDet}";
	public static final String ARTICULOS_DET_BY_CAB = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventadet/cab/{idArticuloPrecioCab}";
	public static final String ARTICULOS_DET_UPDATE_PRECIO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articuloprecioventadet/precio/";
	// vistas
	private static final String LISTA_PRECIO = "/listaprecio";
	
	
	
	@RequestMapping(value = LISTA_PRECIO, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("articuloprecioventacab", articulocabService.getArticulosPrecioCabByEmpresa(login.getEmpresa().getIdempresa(), false, true));

		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/articulo/listaprecio";
	}
	

	/**
	 * Obtiene las articulospreciocab por el ID
	 * @param idArticuloPrecioCab
	 * @return
	 */
	@GetMapping(ARTICULO_BY_ID)
	public @ResponseBody Respuesta getArticuloById(@PathVariable("idArticuloPrecioCab") Integer idArticuloPrecioCab) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.getById(idArticuloPrecioCab));
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
	 * Agrega una articulo precio venta cab
	 * @param articulo
	 * @return
	 */
	@PostMapping(ARTICULOS)
	public @ResponseBody Respuesta addArticulo(@RequestBody Articuloprecioventacab articulo) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.add(articulo));
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
	 * Obtiene todos las Articulos
	 * @return
	 */
	@GetMapping(ARTICULOS)
	public @ResponseBody Respuesta getAllArticulos() {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.getAll());
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
	 * Obtiene las articulos por el ID de Empresa
	 * @param idEmpresa
	 * @return
	 */
	@GetMapping(ARTICULOS_BY_EMPRESA)
	public @ResponseBody Respuesta getArticuloByEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
		logger.info(ARTICULOS_BY_EMPRESA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.getArticulosPrecioCabByEmpresa(idEmpresa, false, true));
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
	 * Actualiza una articulo
	 * @param articulo
	 * @return
	 */
	@PutMapping(ARTICULOS)
	public @ResponseBody Respuesta updateArticulo(@RequestBody Articuloprecioventacab articulo) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.update(articulo));
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
	 * Desactiva o borra la articulo
	 * @param idArticuloPrecioCab
	 * @return
	 */
	@DeleteMapping(ARTICULO_BY_ID)
	public @ResponseBody Respuesta deleteArticulo(@PathVariable("idArticuloPrecioCab") Integer idArticuloPrecioCab) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulocabService.delete(idArticuloPrecioCab));
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
	
	//ARTICULO PRECIO VENTA DET
	
	/**
	 * Obtiene las articulospreciocab por el ID
	 * @param idArticuloPrecioCab
	 * @return
	 */
	@GetMapping(ARTICULO_DET_BY_ID)
	public @ResponseBody Respuesta getArticulodetById(@PathVariable("idArticuloPrecioDet") Integer idArticuloPrecioDet) {
		logger.info(ARTICULO_DET_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulodetService.getById(idArticuloPrecioDet));
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
	 * Agrega una articulo
	 * @param articulo
	 * @return
	 */
	@PostMapping(ARTICULOSDET)
	public @ResponseBody Respuesta addArticulodet(@RequestBody Articuloprecioventadet articulo, HttpServletRequest request) {
		logger.info(ARTICULOSDET);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulodetService.addPrecioVentadet(articulo, loginService.getLoginCache(request).getUsuario()));
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
	 * Obtiene todos las Articulos
	 * @return
	 */
	@GetMapping(ARTICULOSDET)
	public @ResponseBody Respuesta getAllArticulosdet() {
		logger.info(ARTICULOSDET);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulodetService.getAll());
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
	 * Obtiene las articulos por el ID de Cab
	 * @return
	 */
	@GetMapping(ARTICULOS_DET_BY_CAB)
	public @ResponseBody Respuesta getArticulodetByEmpresa(@PathVariable("idArticuloPrecioCab") Integer idArticuloPrecioCab) {
		logger.info(ARTICULOS_DET_BY_CAB);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulodetService.getArticulosPrecioByCab(idArticuloPrecioCab));
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
	 * Actualiza una articulo
	 * @param articulo
	 * @return
	 */
	@PutMapping(ARTICULOSDET)
	public @ResponseBody Respuesta updateArticulodet(@RequestBody Articuloprecioventadet articulo, HttpServletRequest request) {
		logger.info(ARTICULOSDET + ": " + articulo);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articulodetService.updatePrecio(articulo, loginService.getLoginCache(request).getUsuario()));
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