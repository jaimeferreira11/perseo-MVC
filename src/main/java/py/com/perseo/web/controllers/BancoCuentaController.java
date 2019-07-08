package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.BancoCuenta;
import py.com.perseo.tesoreria.services.BancoCuentaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class BancoCuentaController {

    private static final Logger logger = LoggerFactory.getLogger(BancoCuentaController.class);

    // APIs
    public static final String BANCOCUENTA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/bancocuenta/";
    public static final String BANCOCUENTA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/bancocuenta/{idBancoCuenta}";

    @Autowired
    private BancoCuentaService bancoCuentaService;

    @Autowired
    private LoginService loginService;

    @GetMapping(BANCOCUENTA_BY_ID)
    public @ResponseBody
    Respuesta getById(@PathVariable("idBancoCuenta") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoCuentaService.getById(id));
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

    @PostMapping(BANCOCUENTA)
    public @ResponseBody
    Respuesta add(@RequestBody BancoCuenta item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoCuentaService.add(item));
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

    @GetMapping(BANCOCUENTA)
    public @ResponseBody
    Respuesta getAll() {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoCuentaService.getAll());
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

    @PutMapping(BANCOCUENTA)
    public @ResponseBody
    Respuesta update(@RequestBody BancoCuenta item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoCuentaService.update(item));
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

    @DeleteMapping(BANCOCUENTA_BY_ID)
    public @ResponseBody
    Respuesta delete(@PathVariable("idBancoCuenta") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoCuentaService.delete(id));
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
