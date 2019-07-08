package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.Cajacuenta;
import py.com.perseo.tesoreria.services.CajaCuentaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CajaCuentaController {

    private static final Logger logger = LoggerFactory.getLogger(CajaCuentaController.class);

    // APIs
    public static final String CAJACUENTA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/cajacuenta/";
    public static final String CAJACUENTA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/cajacuenta/{idCajaCuenta}";

    @Autowired
    private CajaCuentaService cajaCuentaService;

    @Autowired
    private LoginService loginService;

    @GetMapping(CAJACUENTA_BY_ID)
    public @ResponseBody
    Respuesta getById(@PathVariable("idCajaCuenta") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaCuentaService.getById(id));
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

    @PostMapping(CAJACUENTA)
    public @ResponseBody
    Respuesta add(@RequestBody Cajacuenta item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaCuentaService.add(item));
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

    @GetMapping(CAJACUENTA)
    public @ResponseBody
    Respuesta getAll(HttpServletRequest request) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            LoginData login = loginService.getLoginCache(request);
            respuesta.setDatos(cajaCuentaService.getByCaja(login.getCaja().getIdcaja()));
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

    @PutMapping(CAJACUENTA)
    public @ResponseBody
    Respuesta update(@RequestBody Cajacuenta item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaCuentaService.update(item));
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

    @DeleteMapping(CAJACUENTA_BY_ID)
    public @ResponseBody
    Respuesta delete(@PathVariable("idCajaCuenta") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaCuentaService.delete(id));
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
