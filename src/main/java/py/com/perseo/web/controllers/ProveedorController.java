package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.Proveedor;
import py.com.perseo.tesoreria.services.ProveedorService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private LoginService loginService;


    // APIs
    public static final String PROVEEDORES = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/proveedores/";
    public static final String PROVEEDOR_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/proveedores/{idProveedor}";
    public static final String PROVEEDORES_BY_EMPRESA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/proveedores/empresa/{idEmpresa}";
    public static final String PROVEEDORES_BY_PARAMS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/proveedores/byParam/{param}";
    // vistas
    private static final String MANT_PROVEEDORES = "/mantproveedores";


    @RequestMapping(value = MANT_PROVEEDORES, method = RequestMethod.GET)
    public String mantUsuario(Model model, HttpServletRequest request) {
        try {
            //	perfilService.validarPerfilUsuario(request, MANT_PROVEEDORES);
            model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
            model.addAttribute("login", loginService.getLoginCache(request));
        } catch (CustomMessageException e1) {
            e1.printStackTrace();
            return "redirect:/error/403";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500";
        }
        return "/stock/articulo/mantproveedores";
    }


    /**
     * Obtiene los proveedor por el ID
     *
     * @param idProveedor
     * @return
     */
    @GetMapping(PROVEEDOR_BY_ID)
    public @ResponseBody
    Respuesta getProveedorById(@PathVariable("idProveedor") Integer idProveedor) {
        logger.info(PROVEEDORES);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.getById(idProveedor));
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
     * Agrega un proveedor
     *
     * @param proveedor
     * @return
     */
    @PostMapping(PROVEEDORES)
    public @ResponseBody
    Respuesta addProveedor(@RequestBody Proveedor articulo) {
        logger.info(PROVEEDORES);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.add(articulo));
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
     * Obtiene todos los Proveedores
     *
     * @return
     */
    @GetMapping(PROVEEDORES)
    public @ResponseBody
    Respuesta getAllProveedores() {
        logger.info(PROVEEDORES);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.getAll());
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
     * Obtiene los proveedores por el ID de Empresa
     *
     * @param idEmpresa
     * @return
     */
    @GetMapping(PROVEEDORES_BY_EMPRESA)
    public @ResponseBody
    Respuesta getProveedoresByEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
        logger.info(PROVEEDORES_BY_EMPRESA);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.getProveedoresByEmpresa(idEmpresa));
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
     * Obtiene los proveedores por parametros de busqueda
     *
     * @param
     * @return
     */
    @GetMapping(PROVEEDORES_BY_PARAMS)
    public @ResponseBody
    Respuesta getProveedorByparams(@PathVariable("param") String param, HttpServletRequest request
    ) {
        logger.info(PROVEEDORES_BY_PARAMS);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.getProveedorByParams(param, loginService.getLoginCache(request)));
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
     * Actualiza un proveedor
     *
     * @param proveedor
     * @return
     */
    @PutMapping(PROVEEDORES)
    public @ResponseBody
    Respuesta updateProveedor(@RequestBody Proveedor articulo) {
        logger.info(PROVEEDORES);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.update(articulo));
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
     * Desactiva o borra el proveedor
     *
     * @param idProveedor
     * @return
     */
    @DeleteMapping(PROVEEDOR_BY_ID)
    public @ResponseBody
    Respuesta deleteProveedor(@PathVariable("idProveedor") Integer idProveedor) {
        logger.info(PROVEEDORES);
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(proveedorService.delete(idProveedor));
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