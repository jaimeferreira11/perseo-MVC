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
import py.com.perseo.session.entities.Perfil;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.session.services.MenuService;
import py.com.perseo.session.services.PerfilService;
import py.com.perseo.utilities.Respuesta;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;


@Controller
public class PerfilController {

	private static final Logger logger = Logger.getLogger(PerfilController.class.getName());

	@Autowired
	private PerfilService perfilService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginService loginService;

	// vistas
	private static final String MANT_PERFILES = "/mantperfiles";

	// API_ROOT
	private static final String API_ROOT = "/private/perfil";

	// update
	private static final String API_GET_PERFILES = API_ROOT + "/all";
	private static final String API_GET_PERFILES_BY_EMPRESA = API_ROOT + "/empresa/{idEmpresa}";
	private static final String API_UPDATE_PERFIL = API_ROOT + "/update";
	private static final String API_INSERT_PERFIL = API_ROOT + "/insert";
	private static final String API_CHANGE_PERFIL = API_ROOT + "/change";

	@RequestMapping(value = MANT_PERFILES, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {

		try {
			//perfilService.validarPerfilUsuario(request, MANT_PERFILES);
			model.addAttribute("login", loginService.getLoginCache(request));
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}

		return "/session/mantperfiles";
	}

	@RequestMapping(value = API_GET_PERFILES_BY_EMPRESA, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Respuesta test(@PathVariable("idEmpresa") Integer idEmpresa,HttpServletRequest request) {
		logger.info(API_GET_PERFILES_BY_EMPRESA);
		Respuesta respuesta = new Respuesta();
		respuesta.setEstado("OK");
		respuesta.setError(null);
		try {
			if(loginService.getLoginCache(request).getEmpresa().getIdempresa() == idEmpresa){				
				respuesta.setDatos(perfilService.getPerfilesByEmpresa(idEmpresa, true));
			}else{
				respuesta.setEstado("ERROR");
				respuesta.setError("Datos privados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(e.getMessage());
		}
		return respuesta;
	}
	
	@RequestMapping(value = API_GET_PERFILES, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Respuesta test() {
		logger.info(API_GET_PERFILES);
		Respuesta respuesta = new Respuesta();
		respuesta.setEstado("OK");
		respuesta.setError(null);
		try {
			respuesta.setDatos(perfilService.getAll());
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstado("ERROR");
			respuesta.setError(e.getMessage());
		}
		return respuesta;
	}

	@RequestMapping(value = API_INSERT_PERFIL, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta insertUsuario(@WebParam(name = "perfil") String perfil, HttpServletRequest request) {

		logger.info("User {} accessing to {} " + API_INSERT_PERFIL + " " + perfil);

		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Perfil u = gson.fromJson(perfil, Perfil.class);
			u.setIdperfil(null);

			Perfil res = perfilService.add(u);
			respuesta.setDatos(res);

		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError("Error => " + e.getClass().getSimpleName());
			e.printStackTrace();
		}
		return respuesta;
	}

	@RequestMapping(value = API_UPDATE_PERFIL, method = RequestMethod.POST)
	@ResponseBody
	public Respuesta updateUsuario(@WebParam(name = "perfil") String perfil, HttpServletRequest request) {

		logger.info("User {} accessing to {} " + API_UPDATE_PERFIL + " " + perfil);

		Respuesta respuesta = new Respuesta("OK");
		try {
			Gson gson = new GsonBuilder().create();
			Perfil u = gson.fromJson(perfil, Perfil.class);

			Perfil res = perfilService.update(u);
			respuesta.setDatos(res);

		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError("Error => " + e.getClass().getSimpleName());
			e.printStackTrace();
		}
		return respuesta;
	}

	@RequestMapping(value = API_CHANGE_PERFIL, method = RequestMethod.POST)
	public @ResponseBody String perfilesPost(@WebParam(name = "idperfil_") String idperfil_, HttpServletRequest request) {

		try {
			System.out.println("el id perfil es : " + idperfil_);

			LoginData login = (LoginData) loginService.getLoginCache(request);

			login.setPerfilactual(perfilService.getPerfilById(idperfil_));
			request.getSession().setAttribute("loginData", login);
			List<Menu> menuList = menuService.getByPerfil(Integer.parseInt(idperfil_));

			System.out.println("size del menu en el login: " + menuList.size());

			login.setMenues(menuList);

			request.getSession().setAttribute("menues", menuList);
			request.getSession().setAttribute("login", login);

		} catch (Exception e) {
			logger.info("Error: " + e.getMessage());
			return "/pages/error/500";
		}

		return "redirect:/home";

	}

}
