package py.com.perseo.web.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import py.com.perseo.stock.entities.Articulomovimiento;
import py.com.perseo.stock.entities.Articulotransferenciacab;
import py.com.perseo.stock.entities.Articulotransferenciadet;
import py.com.perseo.stock.services.ArticuloMovimientoService;
import py.com.perseo.stock.services.ArticuloTransferenciaCabService;
import py.com.perseo.tesoreria.services.ConceptomovService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private SucursalService sucursalService;
    
    @Autowired
    private ConceptomovService conceptomovService;
    
    @Autowired
    private ArticuloMovimientoService articuloMovimientoService;
    
    @Autowired
    private ArticuloTransferenciaCabService articuloTransferenciaCabService;
    
 // APIs
 	public static final String API_AJUSTE_STOCK = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/stock/ajuste/";
	public static final String TRANSFERIR_ARTICULOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/stock/transferir/";

 	// vistas
    private static final String AJUSTE_STOCK = "/ajustestock";
	private static final String TRANSFER_ARTICULO = "/transferirarticulo";

    @RequestMapping(value = AJUSTE_STOCK, method = RequestMethod.GET)
    public String mantUsuario(Model model, HttpServletRequest request) {
    	logger.info(AJUSTE_STOCK);
        try {
            //	perfilService.validarPerfilUsuario(request, MANT_ARTICULOS);
        	LoginData login = loginService.getLoginCache(request);
            model.addAttribute("menues", login.getMenues());
            model.addAttribute("login", login);
            model.addAttribute("sucursales", sucursalService.getSucursalesByEmpresa(login.getEmpresa().getIdempresa()));
            model.addAttribute("conceptos", conceptomovService.getAll());
            

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/stock/articulo/ajustestock";
    }
    
    @PostMapping(API_AJUSTE_STOCK)
	public @ResponseBody Respuesta addArticulodeposito(@RequestBody Articulomovimiento articulomovimiento, HttpServletRequest request) {
		logger.info(API_AJUSTE_STOCK);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(articuloMovimientoService.ajusteStock(articulomovimiento, loginService.getLoginCache(request).getUsuario()));
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
	 * Transfiere un articulo de un deposito a otro
	 * @param idArticuloDeposito, idDepositoDestino
	 * @return
	 */
	@PostMapping(TRANSFERIR_ARTICULOS)
	public @ResponseBody Respuesta transferArticulo(@RequestBody String datos, HttpServletRequest request) {
		logger.info(TRANSFERIR_ARTICULOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			JSONObject params = new JSONObject(datos);
			Gson gson =  new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			Articulotransferenciacab cab =gson.fromJson(params.getString("articulotransferenciacab"), Articulotransferenciacab.class);
			
			TypeToken<List<Articulotransferenciadet>> tokenOpcion = new TypeToken<List<Articulotransferenciadet>>() {};
			List<Articulotransferenciadet> det = gson.fromJson(params.getString("detalles"), tokenOpcion.getType());
			
			respuesta.setDatos(articuloTransferenciaCabService.transferirArticulos(cab,det, loginService.getLoginCache(request)));
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

	@RequestMapping(value = TRANSFER_ARTICULO, method = RequestMethod.GET)
	public String getTransferirArticulos(Model model, HttpServletRequest request) {
		try {
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
			model.addAttribute("sucursales", sucursalService.getAllSucursales());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/stock/articulo/transferirarticulo";
	}
}
