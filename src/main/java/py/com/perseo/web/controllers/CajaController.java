package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.Caja;
import py.com.perseo.tesoreria.services.CajaService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class CajaController {

    private static final Logger logger = LoggerFactory.getLogger(CajaController.class);

    // APIs
    public static final String CAJA = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/caja/";
    public static final String CAJA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/caja/{idCaja}";

    @Autowired
    private CajaService cajaService;

    @Autowired
    private LoginService loginService;

    @GetMapping(CAJA_BY_ID)
    public @ResponseBody
    Respuesta getById(@PathVariable("idCaja") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaService.getById(id));
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

    @PostMapping(CAJA)
    public @ResponseBody
    Respuesta add(@RequestBody Caja item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaService.add(item));
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

    @GetMapping(CAJA)
    public @ResponseBody
    Respuesta getAll() {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaService.getAll());
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

    @PutMapping(CAJA)
    public @ResponseBody
    Respuesta update(@RequestBody Caja item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaService.update(item));
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

    @DeleteMapping(CAJA_BY_ID)
    public @ResponseBody
    Respuesta delete(@PathVariable("idCaja") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(cajaService.delete(id));
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
