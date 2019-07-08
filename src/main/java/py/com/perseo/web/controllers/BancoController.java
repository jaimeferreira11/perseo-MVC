package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.tesoreria.entities.Banco;
import py.com.perseo.tesoreria.services.BancoService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

@Controller
public class BancoController {

    private static final Logger logger = LoggerFactory.getLogger(BancoController.class);

    // APIs
    public static final String BANCO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/banco/";
    public static final String BANCO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/banco/{idBanco}";

    @Autowired
    private BancoService bancoService;

    @Autowired
    private LoginService loginService;

    @GetMapping(BANCO_BY_ID)
    public @ResponseBody
    Respuesta getById(@PathVariable("idBanco") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoService.getById(id));
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

    @PostMapping(BANCO)
    public @ResponseBody
    Respuesta add(@RequestBody Banco item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoService.add(item));
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

    @GetMapping(BANCO)
    public @ResponseBody
    Respuesta getAll() {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoService.getAll());
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

    @PutMapping(BANCO)
    public @ResponseBody
    Respuesta update(@RequestBody Banco item) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoService.update(item));
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

    @DeleteMapping(BANCO_BY_ID)
    public @ResponseBody
    Respuesta delete(@PathVariable("idBanco") Integer id) {
        Respuesta respuesta = new Respuesta("OK");
        try {
            respuesta.setDatos(bancoService.delete(id));
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
