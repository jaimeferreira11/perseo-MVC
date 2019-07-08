package py.com.perseo.web.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.entities.Clase;
import py.com.perseo.session.services.ClaseService;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.session.services.PerfilService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class ClaseController {

	private static final Logger logger = Logger.getLogger(ClaseController.class.getName());

	@Autowired
	private ClaseService claseService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private PerfilService perfilService;

	// vistas
	private static final String MANT_CLASE = "/mantprogramas";

	// API_ROOT
	private static final String API_ROOT = "/private/clase";

	// update
	private static final String API_GET_CLASES = API_ROOT + "/all";
	private static final String API_UPDATE_CLASES = API_ROOT + "/update";
	private static final String API_INSERT_CLASES = API_ROOT + "/insert";
	private static final String API_DELETE_CLASES = API_ROOT + "/delete/{idperfil}";

	@RequestMapping(value = MANT_CLASE, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
			//perfilService.validarPerfilUsuario(request, MANT_CLASE);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/session/mantclases";
	}

	@RequestMapping(value = API_GET_CLASES, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Respuesta test(HttpServletRequest request) {
		logger.info(API_GET_CLASES);
		Respuesta respuesta = new Respuesta();
		respuesta.setEstado("OK");
		respuesta.setError(null);
		try {
			respuesta.setDatos(claseService.getClaseByEmpresa(loginService.getLoginCache(request).getEmpresa().getIdempresa(), true));
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

	@RequestMapping(value = API_INSERT_CLASES, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta insertUsuario(@WebParam(name = "clase") String clase, HttpServletRequest request) {
		logger.info("User {} accessing to {} " + API_INSERT_CLASES + " " + clase);
		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Clase u = gson.fromJson(clase, Clase.class);
			u.setIdclase(null);
			Clase res = claseService.add(u);
			respuesta.setDatos(res);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

	@RequestMapping(value = API_UPDATE_CLASES, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta updateUsuario(@WebParam(name = "clase") String clase, HttpServletRequest request) {
		logger.info("User {} accessing to {} " + API_UPDATE_CLASES + " " + clase);
		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Clase u = gson.fromJson(clase, Clase.class);
			Clase res = claseService.update(u);
			respuesta.setDatos(res);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

}
