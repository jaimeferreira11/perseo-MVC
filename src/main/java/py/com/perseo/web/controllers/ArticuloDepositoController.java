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
import py.com.perseo.stock.entities.Articulodeposito;
import py.com.perseo.stock.services.ArticuloDepositoService;
import py.com.perseo.stock.services.DepositoService;
import py.com.perseo.stock.services.FamiliaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticuloDepositoController {

    private static final Logger logger = LoggerFactory.getLogger(ArticuloDepositoController.class);

    @Autowired
    private ArticuloDepositoService articuloDepositoService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private DepositoService depositoService;
    
    @Autowired
	private FamiliaService familiaService;


    // APIs
    public static final String ARTICULOS_DEPOSITO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/";
    public static final String ARTICULO_DEPOSITO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/{idArticulodeposito}";
    public static final String ARTICULOS_DEPOSITO_BY_SUCURSAL_DEPOSITO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/{idSucursal}/{idDeposito}/{all}";
    public static final String ARTICULOS_DEPOSITO_BY_PARAMS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/byParam/{idSucursal}/{idDeposito}/{param}";
    public static final String ARTICULOS_VENTA_BY_PARAMS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/venta/byParam/{idArticuloPrecioCab}/{param}";
    public static final String API_GET_INFORME_LISTA_ARTICULOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/articulosdeposito/informe/lista-articulo/";

    
    // vistas
    private static final String MANT_ARTICULOS_DEPOSITO_DEPOSITO = "/mantarticulosdeposito";
    private static final String LISTA_ARTICLO = "/listaarticulos";


    @RequestMapping(value = LISTA_ARTICLO, method = RequestMethod.GET)
	public String listaArticulo(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
			model.addAttribute("familias",familiaService.getAll());
			model.addAttribute("sucursales",sucursalService.getSucursalesByEmpresa(loginService.getLoginCache(request).getEmpresa().getIdempresa()));

		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/informe/listaarticulos";
	}
    @RequestMapping(value = MANT_ARTICULOS_DEPOSITO_DEPOSITO, method = RequestMethod.GET)
    public String mantUsuario(Model model, HttpServletRequest request) {
        try {
            //	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS_DEPOSITO);
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()));
        } catch (CustomMessageException e1) {
            e1.printStackTrace();
            return "redirect:/error/403";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/stock/articulo/mantarticulosdeposito";
    }


    /**
     * Obtiene las articulos por parametros de busqueda
     *
     * @return
     */
    @GetMapping(ARTICULOS_DEPOSITO_BY_PARAMS)
    public @ResponseBody
    Respuesta getArticuloByparams(@PathVariable("idSucursal") Integer idSucursal, @PathVariable("idDeposito") Integer idDeposito, @PathVariable("param") String param, HttpServletRequest request) {
        logger.info(ARTICULOS_DEPOSITO_BY_PARAMS + " " + param);
        Respuesta respuesta = new Respuesta("OK");
        try {
            if (idSucursal == 0) {
                idSucursal = loginService.getLoginCache(request).getUsuario().getSucursal().getIdsucursal();
            }
            respuesta.setDatos(articuloDepositoService.getBySucursalAndDepositoByParams(idSucursal, idDeposito, param, loginService.getLoginCache(request)));
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
     *
     * @return
     */
    @GetMapping(ARTICULOS_VENTA_BY_PARAMS)
    public @ResponseBody
    Respuesta getArticuloVentaByparams(
            @PathVariable("idArticuloPrecioCab") Integer idArticuloPrecioCab,
            @PathVariable("param") String param,
            HttpServletRequest request) {
        logger.info(ARTICULOS_VENTA_BY_PARAMS + " " + param);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.getArticulosVentaByParams(idArticuloPrecioCab, param, loginService.getLoginCache(request)));
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
     * Obtiene las articulos por el ID
     *
     * @param idArticulodeposito
     * @return
     */
    @GetMapping(ARTICULO_DEPOSITO_BY_ID)
    public @ResponseBody
    Respuesta getArticulodepositoById(@PathVariable("idArticulodeposito") Integer idArticulodeposito) {
        logger.info(ARTICULOS_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.getById(idArticulodeposito));
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
     *
     * @param articulo
     * @return
     */
    @PostMapping(ARTICULOS_DEPOSITO)
    public @ResponseBody
    Respuesta addArticulodeposito(@RequestBody Articulodeposito articulodeposito, HttpServletRequest request) {
        logger.info(ARTICULOS_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            System.out.println(articulodeposito.toString());
            respuesta.setDatos(articuloDepositoService.addArticuloAndDeposito(articulodeposito, loginService.getLoginCache(request)));
        } catch (CustomMessageException ce) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ce.getMessage());
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError("Ya existe el articulo en el deposito");
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Obtiene todos las Articulodepositos
     *
     * @return
     */
    @GetMapping(ARTICULOS_DEPOSITO)
    public @ResponseBody
    Respuesta getAllArticulodepositos() {
        logger.info(ARTICULOS_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.getAll());
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
     *
     * @param idEmpresa
     * @return
     */
    @GetMapping(ARTICULOS_DEPOSITO_BY_SUCURSAL_DEPOSITO)
    public @ResponseBody
    Respuesta getArticulodepositoByEmpresa(@PathVariable("idSucursal") Integer idSucursal, @PathVariable("idDeposito") Integer idDeposito,
                                           @PathVariable("all") Boolean all, HttpServletRequest request) {
        logger.info(ARTICULOS_DEPOSITO_BY_SUCURSAL_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            if (idSucursal == 0) {
                idSucursal = loginService.getLoginCache(request).getUsuario().getSucursal().getIdsucursal();
            }
            respuesta.setDatos(articuloDepositoService.getBySucursalAndDeposito(idSucursal, idDeposito, all));
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
     *
     * @param articulo
     * @return
     */
    @PutMapping(ARTICULOS_DEPOSITO)
    public @ResponseBody
    Respuesta updateArticulodeposito(@RequestBody Articulodeposito articulodeposito) {
        logger.info(ARTICULOS_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.update(articulodeposito));
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
     *
     * @param idArticulodeposito
     * @return
     */
    @DeleteMapping(ARTICULO_DEPOSITO_BY_ID)
    public @ResponseBody
    Respuesta deleteArticulodeposito(@PathVariable("idArticulodeposito") Integer idArticulodeposito) {
        logger.info(ARTICULOS_DEPOSITO);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.delete(idArticulodeposito));
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
    
    @PostMapping(API_GET_INFORME_LISTA_ARTICULOS)
    public @ResponseBody
    Respuesta getInformeLibroCenta(@RequestBody String datos, HttpServletRequest request) {
        logger.info(API_GET_INFORME_LISTA_ARTICULOS + ": "+  datos);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(articuloDepositoService.getListaArticulos(datos, loginService.getLoginCache(request)));
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