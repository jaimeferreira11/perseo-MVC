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
import py.com.perseo.tesoreria.services.BancoService;
import py.com.perseo.tesoreria.services.CajaCuentaService;
import py.com.perseo.tesoreria.services.ProveedorService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;
import py.com.perseo.venta.entities.Ventacab;
import py.com.perseo.venta.services.VentaCabService;
import py.com.perseo.venta.services.VentaDetService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VentaController {
    private static final Logger logger = LoggerFactory.getLogger(VentaController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProveedorService proveedorService;
    
    @Autowired
    private CajaCuentaService cajaCuentaService;
    
    @Autowired
    private BancoService bancoService;

    @Autowired
    private VentaCabService ventaCabService;
    
    @Autowired
    private VentaDetService ventaDetService;
    
    @Autowired
	private SucursalService sucursalService;

  
    //ventacab
    public static final String VENTACAB = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/ventacab/";
	public static final String VENTACAB_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/ventacab/{idVentaCab}";
	public static final String VENTACAB_BY_ESTADO_SUCURSAL = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/ventacab/estado/{idEstado}";

	
    public static final String API_POST_REGISTRAR_PEDIDO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/venta/registrar-pedido/";
    public static final String API_POST_PROCESAR_VENTA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/venta/procesar-venta/";
    public static final String API_GET_INFORME_LIBRO_VENTA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/venta/informe/libro-venta/";

    // vistas
    private static final String REGISTRAR_PEDIDO = "/registrarpedido";
    private static final String REGISTRAR_VENTA = "/registrarventa";
    private static final String INF_LIBRO_VENTA = "/libroventa";

    
    @RequestMapping(value = INF_LIBRO_VENTA, method = RequestMethod.GET)
	public String infVentas(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("sucursales",sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()));

		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "venta/informe/libroventa";
	}

    @RequestMapping(value = REGISTRAR_PEDIDO, method = RequestMethod.GET)
    public String getRegistrarOrdenPago(Model model, HttpServletRequest request) {
        try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("proveedores", proveedorService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/venta/registrarpedido";
    }

    @RequestMapping(value = REGISTRAR_VENTA, method = RequestMethod.GET)
    public String mantUsuario(Model model, HttpServletRequest request) {
    	String page = "/venta/registrarventa";
        try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("cajacuenta", cajaCuentaService.getByCaja(login.getCaja().getIdcaja()));
            model.addAttribute("bancocuenta", bancoService.getAll());
            
            //if(login.getTurno() == null){
            //	 page = "/error/403";
           // }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return page;
    }
        
    
    /**
	 * Obtiene los depositos por el ID
	 * @param idDeposito
	 * @return
	 */
	@GetMapping(VENTACAB_BY_ID)
	public @ResponseBody Respuesta getDepositoById(@PathVariable("idVentaCab") Integer idVentaCab) {
		logger.info(VENTACAB_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(ventaCabService.getById(idVentaCab));
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
	 * Agrega un ventacab
	 * @param ventacab
	 * @return
	 */
	@PostMapping(VENTACAB)
	public @ResponseBody Respuesta addDeposito(@RequestBody Ventacab ventacab) {
		logger.info(VENTACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {

			respuesta.setDatos(ventaCabService.add(ventacab));
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
	 * Obtiene todos las ventacab
	 * @return
	 */
	@GetMapping(VENTACAB)
	public @ResponseBody Respuesta getAllDepositos() {
		logger.info(VENTACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(ventaCabService.getAll());
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
	 * Obtiene las ventacab por el ID de sucursal y estado
	 * @param IdEstado
	 * @return
	 */
	@GetMapping(VENTACAB_BY_ESTADO_SUCURSAL)
	public @ResponseBody Respuesta getDepositoByEmpresa(@PathVariable("idEstado") Integer IdEstado, HttpServletRequest request) {
		logger.info(VENTACAB_BY_ESTADO_SUCURSAL);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(ventaCabService.getByEstadoAndSucursal(IdEstado, loginService.getLoginCache(request).getUsuario()));
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
	 * Actualiza un ventacab
	 * @param ventacab
	 * @return
	 */
	@PutMapping(VENTACAB)
	public @ResponseBody Respuesta updateDeposito(@RequestBody Ventacab ventacab) {
		logger.info(VENTACAB);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(ventaCabService.update(ventacab));
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
	 * Registar el pedido de la venta
	 * @param datos
	 * @param request
	 * @return
	 */
	
	@PostMapping(API_POST_REGISTRAR_PEDIDO)
    public @ResponseBody
    Respuesta registrarNuevaCompra(@RequestBody Ventacab ventacab, HttpServletRequest request) {
        logger.info(API_POST_REGISTRAR_PEDIDO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(ventaCabService.registrarPedido(ventacab,loginService.getLoginCache(request)));
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            e.printStackTrace();
        }
        return respuesta;
    }

	@PostMapping(API_POST_PROCESAR_VENTA)
    public @ResponseBody
    Respuesta procesarVenta(@RequestBody String datos, HttpServletRequest request) {
        logger.info(API_POST_PROCESAR_VENTA );
        Respuesta respuesta = new Respuesta("OK");
       

        try {


            respuesta.setDatos(ventaCabService.grabarVenta(datos, loginService.getLoginCache(request)));
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            e.printStackTrace();
        }
        return respuesta;
    }

	@PostMapping(API_GET_INFORME_LIBRO_VENTA)
	    public @ResponseBody
	    Respuesta getInformeLibroCenta(@RequestBody String datos, HttpServletRequest request) {
	        logger.info(API_GET_INFORME_LIBRO_VENTA + ": "+  datos);
	        Respuesta respuesta = new Respuesta("OK");
	        try {
	            respuesta.setDatos(ventaCabService.getLibroIvaVentaContable(datos, loginService.getLoginCache(request)));
	        } catch (Exception e) {
	            if (e instanceof CustomMessageException) {
	                respuesta.setError(e.getMessage());
	            } else {
	                respuesta.setError(ConstantesRestAPI.ERROR_500);
	            }
	            respuesta.setEstado("ERROR");
	            logger.error("Error generando informe: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return respuesta;
	    }
    
    
}
