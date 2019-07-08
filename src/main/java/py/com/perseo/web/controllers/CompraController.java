package py.com.perseo.web.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jettison.json.JSONObject;
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
import py.com.perseo.stock.services.CompraService;
import py.com.perseo.tesoreria.entities.Compracab;
import py.com.perseo.tesoreria.entities.Compradet;
import py.com.perseo.tesoreria.services.BancoService;
import py.com.perseo.tesoreria.services.CajaCuentaService;
import py.com.perseo.tesoreria.services.ProveedorService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CompraController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private CompraService compraService;

   
    @Autowired
    private CajaCuentaService cajaCuentaService;
    

    @Autowired
    private BancoService bancoService;

    public static final String API_AGREGAR_COMPRA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/agregar/";
    public static final String API_OBTENER_COMPRAS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/obtener/";
    public static final String API_OBTENER_ORDENPAGO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/obtenerordenpago/";
    public static final String API_NUEVA_ORDEN_PAGO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/nuevaordenpago/";
    public static final String API_CONFIRMAR_ORDEN_PAGO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/confirmarordenpago/";
    public static final String API_GET_INFORME_LIBRO_COMPRA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/compra/informe/libro-compra/";

    // vistas
    private static final String REGISTRAR_ORDEN_PAGO = "/registrarordenpago";
    private static final String CONFIRMAR_ORDEN_PAGO = "/confirmarordenpago";
    private static final String REGISTRAR_COMPRA = "/registrarcompra";
    private static final String LIBRO_COMPRA = "/librocompra";

    @RequestMapping(value = REGISTRAR_ORDEN_PAGO, method = RequestMethod.GET)
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
        return "/compra/registrarordenpago";
    }

    @RequestMapping(value = REGISTRAR_COMPRA, method = RequestMethod.GET)
    public String mantUsuario(Model model, HttpServletRequest request) {
    	String page = "/compra/registrarcompra";
    	try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("proveedores", proveedorService.getAll());
            //if(login.getTurno() == null){
           	// page = "/error/403";
           //}
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return page;
    }

    @RequestMapping(value = LIBRO_COMPRA, method = RequestMethod.GET)
    public String libroCompra(Model model, HttpServletRequest request) {
        try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()));
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/compra/informe/librocompra";
    }

    @PostMapping(API_GET_INFORME_LIBRO_COMPRA)
    public @ResponseBody
    Respuesta getInformeLibroCenta(@RequestBody String datos, HttpServletRequest request) {
        logger.info(API_GET_INFORME_LIBRO_COMPRA + ": " + datos);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(compraService.getLibroIvaCompra(datos, loginService.getLoginCache(request)));
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

    @PostMapping(API_AGREGAR_COMPRA)
    public @ResponseBody
    Respuesta registrarNuevaCompra(@RequestBody String datos, HttpServletRequest request) {
        logger.info(datos);
        Respuesta respuesta = new Respuesta("OK");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        try {
            JSONObject params = new JSONObject(datos);
            Compracab cab = gson.fromJson(params.getString("compracab"), Compracab.class);
            TypeToken<List<Compradet>> tokenOpcion = new TypeToken<List<Compradet>>() {
            };
            List<Compradet> det = gson.fromJson(params.getString("compradet"), tokenOpcion.getType());
            respuesta.setDatos(compraService.registrarCompra(cab, det, loginService.getLoginCache(request)));
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            logger.error("Error en registrarNuevaCompra: " + e.getMessage());
            e.printStackTrace();
        }
        return respuesta;
    }

    @GetMapping(API_OBTENER_COMPRAS)
    @ApiOperation(httpMethod = "GET", value = "Obtiene las compras por parametros")
    public @ResponseBody
    Respuesta getCompraByParams(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "ini", required = false) String fechaInicio,
            @RequestParam(value = "fin", required = false) String fechaFin,
            @RequestParam(value = "estado", required = false) Integer estado,
            @RequestParam(value = "proveedor", required = false) Object[] proveedor) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date ini, fin;
        String iniDateSql, endDateSql;

        Respuesta respuesta = new Respuesta("OK");
        try {
            if (id != null) {
                respuesta.setDatos(compraService.getById(id));
            } else {
                if (fechaFin.equalsIgnoreCase("") || fechaInicio.equalsIgnoreCase("")) {
                    iniDateSql = null;
                    endDateSql = null;
                } else {
                    ini = df.parse(fechaInicio);
                    fin = df.parse(fechaFin);
                    DateFormat dfSql = new SimpleDateFormat("yyyy-MM-dd");
                    iniDateSql = dfSql.format(ini);
                    endDateSql = dfSql.format(fin);
                }
                respuesta.setDatos(compraService.getCompras(iniDateSql, endDateSql, estado, proveedor));
            }
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            e.printStackTrace();
        }
        return respuesta;
    }

    @PostMapping(API_NUEVA_ORDEN_PAGO)
    public @ResponseBody
    Respuesta registrarNuevaOrdenPago(@RequestBody String datos, HttpServletRequest request) {
        logger.info(datos);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(compraService.nuevaOrdenPago(datos, loginService.getLoginCache(request)));
        } catch (Exception e) {
            if (e instanceof CustomMessageException) {
                respuesta.setError(e.getMessage());
            } else {
                respuesta.setError(ConstantesRestAPI.ERROR_500);
            }
            respuesta.setEstado("ERROR");
            logger.error("Exception en registrarNuevaOrdenPago: " + e.getMessage());
            e.printStackTrace();
        }
        return respuesta;
    }

    @RequestMapping(value = CONFIRMAR_ORDEN_PAGO, method = RequestMethod.GET)
    public String getConfirmarOrdenPago(Model model, HttpServletRequest request) {
        try {
            LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("proveedores", proveedorService.getAll());
            model.addAttribute("cajacuenta", cajaCuentaService.getByCaja(login.getCaja().getIdcaja()));
            model.addAttribute("bancocuenta", bancoService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/compra/confirmarordenpago";
    }

    @GetMapping(API_OBTENER_ORDENPAGO)
    @ApiOperation(httpMethod = "GET", value = "Obtiene las ordenes de pago por parametros")
    public @ResponseBody
    Respuesta getOrdenPagoByParams(
            @RequestParam(value = "ini", required = false) String fechaInicio,
            @RequestParam(value = "fin", required = false) String fechaFin,
            @RequestParam(value = "id", required = false) Integer id) {
        Object[] proveedor = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date ini, fin;
        String iniDateSql, endDateSql;

        Respuesta respuesta = new Respuesta("OK");
        try {
            if (id != null) {
                respuesta.setDatos(compraService.getOrdenInfo(id));
            } else {
                if (fechaFin.equalsIgnoreCase("") || fechaInicio.equalsIgnoreCase("")) {
                    iniDateSql = null;
                    endDateSql = null;
                } else {
                    ini = df.parse(fechaInicio);
                    fin = df.parse(fechaFin);
                    DateFormat dfSql = new SimpleDateFormat("yyyy-MM-dd");
                    iniDateSql = dfSql.format(ini);
                    endDateSql = dfSql.format(fin);
                }
                respuesta.setDatos(compraService.getOrdenesPago(iniDateSql, endDateSql, proveedor));
            }
        } catch (Exception e) {
            respuesta.setEstado("ERROR");
            respuesta.setError(ConstantesRestAPI.ERROR_500);
            e.printStackTrace();
        }
        return respuesta;
    }

    @PostMapping(API_CONFIRMAR_ORDEN_PAGO)
    public @ResponseBody
    Respuesta confirmarOrdenPago(@RequestBody String datos, HttpServletRequest request) {
        logger.info(datos);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(compraService.confirmarOrdenPago(datos, loginService.getLoginCache(request)));
        } catch (Exception e) {
            if (e instanceof CustomMessageException) {
                respuesta.setError(e.getMessage());
            } else {
                respuesta.setError(ConstantesRestAPI.ERROR_500);
            }
            respuesta.setEstado("ERROR");
            logger.error("Exception en confirmarOrdenPago: " + e.getMessage());
            e.printStackTrace();
        }
        return respuesta;
    }
}
