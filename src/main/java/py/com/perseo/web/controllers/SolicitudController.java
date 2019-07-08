package py.com.perseo.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(value="solicitudes", description="Operaciones sobre solicitud de creditos")
public class SolicitudController {
	
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);

	public static final String SOLICITUDES = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/solicitudes/";

	// vistas
	private static final String REGISTRAR_SOLICITUD = "/registrarsolicitud";

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = REGISTRAR_SOLICITUD, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("login", login);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/credito/registrarsolicitud";
	}

	
	/**
	 * Obtiene las solicitudes
	 * @return
	 */
	@GetMapping(SOLICITUDES)
	@ApiOperation(httpMethod = "GET", value = "Obtiene todas las solicitudes")
	public @ResponseBody Respuesta getSolicitudes() {
	
		Respuesta respuesta = new Respuesta("OK");
		try {
			
			LOG.info("ingresando a solicitudes all");
			
//			respuesta.setDatos(clienteService.getClientesByParams(tipodoc, nrodoc, nombre));
//		} catch (CustomMessageException ce) {
//			respuesta.setEstado("ERROR");
//			respuesta.setError(ce.getMessage());
		} catch (Exception e) {
			respuesta.setEstado("ERROR");
			respuesta.setError(ConstantesRestAPI.ERROR_500);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
}
