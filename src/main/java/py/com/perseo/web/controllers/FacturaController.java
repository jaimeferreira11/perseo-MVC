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
import py.com.perseo.tesoreria.services.BancoService;
import py.com.perseo.tesoreria.services.CajaCuentaService;
import py.com.perseo.tesoreria.services.ProveedorService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;
import py.com.perseo.venta.entities.Facturacab;
import py.com.perseo.venta.services.FacturaCabService;
import py.com.perseo.venta.services.FacturaDetService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FacturaController {
    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProveedorService proveedorService;
    
    @Autowired
    private CajaCuentaService cajaCuentaService;
    
    @Autowired
    private BancoService bancoService;

    @Autowired
    private FacturaCabService facturaCabService;
    
    @Autowired
    private FacturaDetService facturaDetService;

    //facturacab
    public static final String FACTURACAB = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/facturacab/";
	public static final String FACTURACAB_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/facturacab/{idVentaCab}";
	public static final String FACTURACAB_BY_ESTADO_SUCURSAL = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/facturacab/estado/{allEstados}/{estado}/{allTipos}/{IdTipo}";
	public static final String FACTURACAB_BY_ESTADO_CLIENTE = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/facturacab/cliente/{idCliente}/{all}/{estado}";
	

    //toma la numeracion de factura
    public static final String API_GET_NUMERACION_FACTURA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/factura/numeracion-factura/";
    public static final String API_POST_COBRAR_FACTURA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/factura/cobrar-factura/{idFacturaCab}";

    // vistas
    private static final String COBRO_FACTURA_CONTADO = "/cobrarfactura";
    
    /**
	 * Obtiene la numeracion siguiente del talonario de factura por cajero
	 * @return
	 */
	@GetMapping(API_GET_NUMERACION_FACTURA)
	public @ResponseBody Respuesta getSigteFacturaByCaja(HttpServletRequest request) {
		logger.info(API_GET_NUMERACION_FACTURA);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(facturaCabService.getSigteFacturaByCaja(1, 1, loginService.getLoginCache(request).getUsuario().getCaja().getIdcaja()));
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
    
    
    @RequestMapping(value = COBRO_FACTURA_CONTADO, method = RequestMethod.GET)
    public String cobroVenta(Model model, HttpServletRequest request) {
        try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("proveedores", proveedorService.getAll());
            model.addAttribute("cajacuenta", cajaCuentaService.getAll());
            model.addAttribute("bancocuenta", bancoService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/venta/cobrarfactura";
    }

    
    
    /**
	 * Obtiene los depositos por el ID
	 * @param idDeposito
	 * @return
	 */
	@GetMapping(FACTURACAB_BY_ID)
	public @ResponseBody Respuesta getDepositoById(@PathVariable("idVentaCab") Integer idVentaCab) {
		logger.info(FACTURACAB_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(facturaCabService.getById(idVentaCab));
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
	 * Agrega un facturacab
	 * @param facturacab
	 * @return
	 */
	@PostMapping(FACTURACAB)
	public @ResponseBody Respuesta addDeposito(@RequestBody Facturacab facturacab) {
		logger.info(FACTURACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {

			respuesta.setDatos(facturaCabService.add(facturacab));
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
	 * Obtiene todos las facturacab
	 * @return
	 */
	@GetMapping(FACTURACAB)
	public @ResponseBody Respuesta getAllDepositos() {
		logger.info(FACTURACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(facturaCabService.getAll());
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
	 * Obtiene las facturacab por el ID de sucursal y estado
	 * @param IdEstado
	 * @return
	 */
	@GetMapping(FACTURACAB_BY_ESTADO_SUCURSAL)
	public @ResponseBody Respuesta getDepositoByEmpresa(
			@PathVariable("allEstados") Boolean allEstados,
			@PathVariable("estado") Boolean estado,
			@PathVariable("allTipos") Boolean allTipos,
			@PathVariable("IdTipo") Integer IdTipo,
			
			HttpServletRequest request) {
		logger.info(FACTURACAB_BY_ESTADO_SUCURSAL);
		Respuesta respuesta = new Respuesta("OK");
		try {
	
			respuesta.setDatos(facturaCabService.getBySucursal(allEstados, estado, allTipos, IdTipo, loginService.getLoginCache(request).getUsuario().getSucursal().getIdsucursal()));
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
	 * Obtiene las facturacab por el ID de sucursal y estado
	 * @param IdEstado
	 * @return
	 */
	@GetMapping(FACTURACAB_BY_ESTADO_CLIENTE)
	public @ResponseBody Respuesta getDepositoByCliente(
			@PathVariable("idCliente") Integer idCliente,
			@PathVariable("all") Boolean all,
			@PathVariable("estado") Boolean estado,
			
			HttpServletRequest request) {
		logger.info(FACTURACAB_BY_ESTADO_CLIENTE);
		Respuesta respuesta = new Respuesta("OK");
		try {
	
			respuesta.setDatos(facturaCabService.getByCliente(idCliente, all, estado));
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
	 * Actualiza un facturacab
	 * @param facturacab
	 * @return
	 */
	@PutMapping(FACTURACAB)
	public @ResponseBody Respuesta updateDeposito(@RequestBody Facturacab facturacab) {
		logger.info(FACTURACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(facturaCabService.update(facturacab));
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
	 * Procesa el cobro de la factura credito
	 * @param datos
	 * @param request
	 * @return
	 */
	
	@PostMapping(API_POST_COBRAR_FACTURA)
    public @ResponseBody
    Respuesta registrarNuevaCompra(@PathVariable("idFacturaCab") Integer idFacturaCab, HttpServletRequest request) {
        logger.info(API_POST_COBRAR_FACTURA);
        Respuesta respuesta = new Respuesta("OK");

        try {
            respuesta.setDatos(facturaCabService.cobrarFactura(idFacturaCab,loginService.getLoginCache(request)));
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            e.printStackTrace();
        }
        return respuesta;
    }
    
    
    
    
}
