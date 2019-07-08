package py.com.perseo.web.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.entities.Menu;
import py.com.perseo.session.services.ClaseService;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.session.services.MenuService;
import py.com.perseo.session.services.PerfilService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class MenuController {

	private static final Logger logger = Logger.getLogger(MenuController.class.getName());

	@Autowired
	private MenuService menuService;
	@Autowired
	private ClaseService claseService;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private LoginService loginService;

	// vistas
	private static final String MANT_MENU = "/mantmenu";

	// API_ROOT
	private static final String API_ROOT = "/private/menu";

	// update
	private static final String API_GET_MENU = API_ROOT + "/all";
	private static final String API_GET_MENU_BY_PERFIL = API_ROOT + "/menuByPerfil/{idperfil}";
	private static final String API_UPDATE_MENU = API_ROOT + "/update";
	private static final String API_INSERT_MENU = API_ROOT + "/insert";
//	private static final String API_DELETE_MENU = API_ROOT + "/delete/{idperfil}";

	@RequestMapping(value = MANT_MENU, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
			//perfilService.validarPerfilUsuario(request, MANT_MENU);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("login", login);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("menusinperfil", menuService.getMenuByEmpresa(login.getEmpresa().getIdempresa(), false));
			model.addAttribute("clases", claseService.getClaseByEmpresa(login.getEmpresa().getIdempresa(), false));
			model.addAttribute("perfiles", perfilService.getPerfilesByEmpresa(login.getEmpresa().getIdempresa(), false));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/session/mantmenu";
	}

	@RequestMapping(value = API_GET_MENU_BY_PERFIL, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Respuesta getUsers(@PathVariable String idperfil, HttpServletRequest request) {
		logger.info(API_GET_MENU_BY_PERFIL + " " + idperfil);
		Respuesta respuesta = new Respuesta();
		respuesta.setEstado("OK");
		respuesta.setError(null);
		try {
			respuesta.setDatos(menuService.getAllByPerfil(Integer.parseInt(idperfil.trim())));
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

	@RequestMapping(value = API_GET_MENU, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Respuesta test( HttpServletRequest request) {
		logger.info(API_GET_MENU);
		Respuesta respuesta = new Respuesta();
		respuesta.setEstado("OK");
		respuesta.setError(null);
		try {
			LoginData login = loginService.getLoginCache(request);
			respuesta.setDatos(menuService.getMenuByEmpresa(login.getEmpresa().getIdempresa(), true));
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

	@RequestMapping(value = API_INSERT_MENU, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta insertUsuario(@WebParam(name = "menu") String menu, HttpServletRequest request) {
		logger.info("User {} accessing to {} " + API_INSERT_MENU + " " + menu);
		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Menu u = gson.fromJson(menu, Menu.class);
			Menu res = menuService.add(u);
			
			respuesta.setDatos(res);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}

	@RequestMapping(value = API_UPDATE_MENU, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta updateUsuario(@WebParam(name = "menu") String menu, HttpServletRequest request) {
		logger.info("User {} accessing to {} " + API_UPDATE_MENU + " " + menu);
		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Menu u = gson.fromJson(menu, Menu.class);
			Menu res = menuService.update(u);
			respuesta.setDatos(res);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
		}
		return respuesta;
	}
}