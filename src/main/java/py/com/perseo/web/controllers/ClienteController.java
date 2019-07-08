package py.com.perseo.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.clientes.entities.Cliente;
import py.com.perseo.clientes.services.ClienteService;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.comun.services.BarrioService;
import py.com.perseo.comun.services.DepartamentoService;
import py.com.perseo.comun.services.DistritoService;
import py.com.perseo.comun.services.SucursalService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.stock.services.ArticuloPrecioVentacabService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(value="clientes", description="Operaciones del cliente")
public class ClienteController {

	private static Logger logger = LoggerFactory.getLogger(ClienteController.class);

	public static final String CLIENTES = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/clientes/";

	public static final String CLIENTE_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/clientes/{idCliente}";

	public static final String CLIENTES_BY_PARAMS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/clientes/byParam/{tipodoc}/{nrodoc}/{nombre}";
	// vistas
	private static final String MANT_CLIENTES = "/mantclientes";

	ClienteService clienteService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private SucursalService sucursalService;

	@Autowired
	private DistritoService distritoService;

	@Autowired
	private BarrioService barrioService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private ArticuloPrecioVentacabService articuloPrecioVentacabService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@RequestMapping(value = MANT_CLIENTES, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_DEPOSITOS);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()) );
			model.addAttribute("ciudades", distritoService.getAll() );
			model.addAttribute("barrios", barrioService.getAll() );
			model.addAttribute("departamentos", departamentoService.getAll());
			model.addAttribute("listaprecio",articuloPrecioVentacabService.getAll());

		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/cliente/mantclientes";
	}

	/**
	 * Obtiene los clientes por el ID
	 * @param idCliente
	 * @return
	 */
	@GetMapping(CLIENTE_BY_ID)
	@ApiOperation(httpMethod = "GET", value = "Obtiene los clientes por el ID")
	public @ResponseBody Respuesta getClienteById(@PathVariable("idCliente") Integer idCliente) {
		logger.info(CLIENTES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.getClienteById(idCliente));
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
	 * Obtiene los clientes por parametros de busqueda
	 * @param Cliente
	 * @return
	 */
	@GetMapping(CLIENTES_BY_PARAMS)
	@ApiOperation(httpMethod = "GET", value = "Obtiene los clientes por parametros de busqueda")
	public @ResponseBody Respuesta getClienteByParams(
			@PathVariable("tipodoc") String tipodoc,
			@PathVariable("nrodoc") String nrodoc,
			@PathVariable("nombre") String nombre
			) {

		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.getClientesByParams(tipodoc, nrodoc, nombre));
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
	 * Agrega un cliente
	 * @param cliente
	 * @return
	 */
	@PostMapping(CLIENTES)
	public @ResponseBody Respuesta addCliente(@RequestBody Cliente cliente) {
		logger.info(CLIENTES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.addCliente(cliente));
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
	 * Obtiene todos los clientes
	 * @return
	 */
	@GetMapping(CLIENTES)
	public @ResponseBody Respuesta getAllClientes() {
		logger.info(CLIENTES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.getAllClientes());
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
	 * Actualiza un cliente
	 * @param cliente
	 * @return
	 */
	@PutMapping(CLIENTES)
	public @ResponseBody Respuesta updateCliente(@RequestBody Cliente cliente) {
		logger.info(CLIENTES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.updateCliente(cliente));
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
	 * Desactiva o borra el cliente
	 * @param idCliente
	 * @return
	 */
	@DeleteMapping(CLIENTE_BY_ID)
	public @ResponseBody Respuesta deleteCliente(@PathVariable("idCliente") Integer idCliente) {
		logger.info(CLIENTES);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(clienteService.deleteCliente(idCliente));
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
