package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.services.SucursalService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.stock.entities.Deposito;
import py.com.perseo.stock.services.DepositoService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DepositoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepositoController.class);

	@Autowired
	private DepositoService depositoService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SucursalService sucursalService;

	// APIs
	public static final String DEPOSITOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/depositos/";
	public static final String DEPOSITO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/depositos/{idDeposito}";
	public static final String DEPOSITOS_BY_EMPRESA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/depositos/empresa/{idEmpresa}";
	public static final String DEPOSITOS_BY_SUCURSAL = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/depositos/sucursal/{idSucursal}";
	// vistas
	private static final String MANT_DEPOSITOS = "/mantdepositos";
	
	
	
	@RequestMapping(value = MANT_DEPOSITOS, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_DEPOSITOS);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()) );
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/deposito/mantdepositos";
	}
	

	/**
	 * Obtiene los depositos por el ID
	 * @param idDeposito
	 * @return
	 */
	@GetMapping(DEPOSITO_BY_ID)
	public @ResponseBody Respuesta getDepositoById(@PathVariable("idDeposito") Integer idDeposito) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.getById(idDeposito));
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
	 * Agrega un deposito
	 * @param deposito
	 * @return
	 */
	@PostMapping(DEPOSITOS)
	public @ResponseBody Respuesta addDeposito(@RequestBody Deposito deposito) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {

			respuesta.setDatos(depositoService.add(deposito));
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
	 * Obtiene todos los Depositos
	 * @return
	 */
	@GetMapping(DEPOSITOS)
	public @ResponseBody Respuesta getAllDepositos() {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.getAll());
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
	 * Obtiene los depositos por el ID de Empresa
	 * @param idEmpresa
	 * @return
	 */
	@GetMapping(DEPOSITOS_BY_EMPRESA)
	public @ResponseBody Respuesta getDepositoByEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.getDepositoByEmpresa(idEmpresa));
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
	 * Obtiene los depositos por el ID de la Sucursal
	 * @param idSucursal
	 * @return
	 */
	@GetMapping(DEPOSITOS_BY_SUCURSAL)
	public @ResponseBody Respuesta getDepositoBySucursal(@PathVariable("idSucursal") Integer idSucursal) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.getDepositoBySucursal(idSucursal));
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
	 * Actualiza un deposito
	 * @param deposito
	 * @return
	 */
	@PutMapping(DEPOSITOS)
	public @ResponseBody Respuesta updateDeposito(@RequestBody Deposito deposito) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.update(deposito));
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
	 * Desactiva o borra el deposito
	 * @param idDeposito
	 * @return
	 */
	@DeleteMapping(DEPOSITO_BY_ID)
	public @ResponseBody Respuesta deleteDeposito(@PathVariable("idDeposito") Integer idDeposito) {
		logger.info(DEPOSITOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(depositoService.delete(idDeposito));
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