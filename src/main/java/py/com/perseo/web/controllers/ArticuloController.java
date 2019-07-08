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
import py.com.perseo.stock.entities.Articulo;
import py.com.perseo.stock.services.ArticuloService;
import py.com.perseo.stock.services.FamiliaService;
import py.com.perseo.stock.services.MarcaService;
import py.com.perseo.stock.services.UnidadmedidaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticuloController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticuloController.class);

	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private FamiliaService familiaService;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private UnidadmedidaService unidadmedidaService;
	
	
	
	// APIs
	public static final String ARTICULOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulos/";
	public static final String ARTICULO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulos/{idArticulo}";
	public static final String ARTICULOS_BY_EMPRESA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulos/empresa";	
	public static final String ARTICULOS_BY_PARAMS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulos/byParam/{param}/{all}";

	// vistas
	private static final String MANT_ARTICULOS = "/mantarticulos";
	
	
	
	
	
	
	@RequestMapping(value = MANT_ARTICULOS, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
			model.addAttribute("familias",familiaService.getAll());
			model.addAttribute("marcas",marcaService.getAll());
			model.addAttribute("unidades",unidadmedidaService.getAll());
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/articulo/mantarticulos";
	}
	

	/**
	 * Obtiene las articulos por el ID
	 * @param idArticulo
	 * @return
	 */
	@GetMapping(ARTICULO_BY_ID)
	public @ResponseBody Respuesta getArticuloById(@PathVariable("idArticulo") Integer idArticulo) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articuloService.getById(idArticulo));
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
	@PostMapping(ARTICULOS)
	public @ResponseBody Respuesta addArticulo(@RequestBody Articulo articulo, HttpServletRequest request) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			LoginData login = loginService.getLoginCache(request);
			articulo.setUsuario(login.getUsuario());
			articulo.setEmpresa(login.getEmpresa());
			respuesta.setDatos(articuloService.add(articulo));
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
			respuesta.setDatos(articuloService.getAll());
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
	public @ResponseBody Respuesta getArticuloByEmpresa(HttpServletRequest request) {
		logger.info(ARTICULOS_BY_EMPRESA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			
			respuesta.setDatos(articuloService.getArticulosByEmpresa(loginService.getLoginCache(request).getEmpresa().getIdempresa()));
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
	 * Obtiene las articulos por parametros de busqueda
	 * @return
	 */
	@GetMapping(ARTICULOS_BY_PARAMS)
	public @ResponseBody Respuesta getArticuloByparams(@PathVariable("param") String param ,@PathVariable("all") Boolean all , HttpServletRequest request) {
		logger.info(ARTICULOS_BY_PARAMS + " " + param);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articuloService.getArticulosByParams(param, all ,loginService.getLoginCache(request) ));
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
	public @ResponseBody Respuesta updateArticulo(@RequestBody Articulo articulo, HttpServletRequest request) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			LoginData login = loginService.getLoginCache(request);
			articulo.setUsuario(login.getUsuario());
			articulo.setEmpresa(login.getEmpresa());
			respuesta.setDatos(articuloService.update(articulo));
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
	 * @param idArticulo
	 * @return
	 */
	@DeleteMapping(ARTICULO_BY_ID)
	public @ResponseBody Respuesta deleteArticulo(@PathVariable("idArticulo") Integer idArticulo) {
		logger.info(ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articuloService.delete(idArticulo));
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