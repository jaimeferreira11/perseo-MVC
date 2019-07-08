package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.entities.Empresa;
import py.com.perseo.comun.services.EmpresaService;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmpresaController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private LoginService loginService;

	// APIs
	public static final String EMPRESAS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/empresas/";
	public static final String EMPRESA_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/empresas/{idEmpresa}";
	
	// vistas
	private static final String MANT_EMPRESAS = "/mantempresas";
	
	
	
	@RequestMapping(value = MANT_EMPRESAS, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
		//	perfilService.validarPerfilUsuario(request, MANT_EMPRESAS);
			model.addAttribute("menues", loginService.getLoginCache(request).getMenues());
			model.addAttribute("login", loginService.getLoginCache(request));
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/comun/empresa/mantempresas";
	}
	

	/**
	 * Obtiene las empresas por el ID
	 * @param idEmpresa
	 * @return
	 */
	@GetMapping(EMPRESA_BY_ID)
	public @ResponseBody Respuesta getEmpresaById(@PathVariable("idEmpresa") Integer idEmpresa) {
		logger.info(EMPRESAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(empresaService.getById(idEmpresa));
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
	 * Agrega una empresa
	 * @param empresa
	 * @return
	 */
	@PostMapping(EMPRESAS)
	public @ResponseBody Respuesta addEmpresa(@RequestBody Empresa empresa) {
		logger.info(EMPRESAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(empresaService.add(empresa));
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
	 * Obtiene todos las Empresas
	 * @return
	 */
	@GetMapping(EMPRESAS)
	public @ResponseBody Respuesta getAllEmpresas() {
		logger.info(EMPRESAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(empresaService.getAll());
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
	 * Actualiza una empresa
	 * @param empresa
	 * @return
	 */
	@PutMapping(EMPRESAS)
	public @ResponseBody Respuesta updateEmpresa(@RequestBody Empresa empresa) {
		logger.info(EMPRESAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(empresaService.update(empresa));
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
	 * Desactiva o borra la empresa
	 * @param idEmpresa
	 * @return
	 */
	@DeleteMapping(EMPRESA_BY_ID)
	public @ResponseBody Respuesta deleteEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
		logger.info(EMPRESAS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(empresaService.delete(idEmpresa));
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