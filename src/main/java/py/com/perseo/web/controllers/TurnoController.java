package py.com.perseo.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.perseo.comun.base.LoginData;
import py.com.perseo.exceptions.CustomMessageException;
import py.com.perseo.session.services.LoginService;
import py.com.perseo.utilities.ConstantesRestAPI;
import py.com.perseo.utilities.Respuesta;
import py.com.perseo.venta.entities.Turno;
import py.com.perseo.venta.services.TurnoService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@Controller
public class TurnoController {
	
	private static final Logger logger = LoggerFactory.getLogger(TurnoController.class);

	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TurnoService turnoService;


	// APIs
	public static final String TURNOS = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/";
	public static final String TURNO_BY_ID = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/{idTurno}";
	public static final String LAST_TURNOS_BY_USUARIO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/{limit}";
	public static final String TURNO_BY_USUARIO_CURRENT= ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/usuario/";
	public static final String POST_CERRAR_TURNO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/cerrar/";
	public static final String GET_INF_TUTNO_MOVIMIENTO = ConstantesRestAPI.API_NAME + ConstantesRestAPI.API_VERSION + "/turnos/movimientos/";

	
	public static final String ADMIN_TURNO = "/adminturno";

	@RequestMapping(value = ADMIN_TURNO, method = RequestMethod.GET)
	public String mantUsuario(Model model, HttpServletRequest request) {
		try {
			//	perfilService.validarPerfilUsuario(request, MANT_SUCURSALES);
			LoginData login = loginService.getLoginCache(request);
			model.addAttribute("menues", login.getMenues());
			model.addAttribute("turno", turnoService.getByUsuarioActivo(login.getUsuario().getIdusuario()));
			model.addAttribute("login", login);
		}catch (CustomMessageException e1) {
			e1.printStackTrace();
			return "redirect:/error/403";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/500";
		}
		return "/venta/adminturno";
	}

	/**
	 * Obtiene las turnos por el ID
	 * @param idTurno
	 * @return
	 */
	@GetMapping(TURNO_BY_ID)
	public @ResponseBody Respuesta getTurnoById(@PathVariable("idTurno") Integer idTurno) {
		logger.info(TURNO_BY_ID);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(turnoService.getById(idTurno));
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
	 * Obtiene las turnos por el ID
	 * @param idTurno
	 * @return
	 */
	@GetMapping(LAST_TURNOS_BY_USUARIO)
	public @ResponseBody Respuesta getLastTurnos(@PathVariable("limit") Integer limit, HttpServletRequest request) {
		logger.info(LAST_TURNOS_BY_USUARIO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(turnoService.getByUsuario(loginService.getLoginCache(request).getUsuario().getIdusuario(), limit));
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
	 * Obtiene las turnos por el ID
	 * @param idTurno
	 * @return
	 */
	@GetMapping(TURNO_BY_USUARIO_CURRENT)
	public @ResponseBody Respuesta getCurrentTurno(HttpServletRequest request) {
		logger.info(TURNO_BY_USUARIO_CURRENT);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(turnoService.getByUsuarioActivo(loginService.getLoginCache(request).getUsuario().getIdusuario()));
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
	 * Agrega una turno
	 * @param turno
	 * @return
	 */
	@PostMapping(TURNOS)
	public @ResponseBody Respuesta addTurno(@RequestBody Turno turno, HttpServletRequest request) {
		logger.info(TURNOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			LoginData loginData = loginService.getLoginCache(request);
			turno.setUsuario(loginData.getUsuario());
			turno.setCaja(loginData.getCaja());
			turno.setSucursal(loginData.getSucursal());
			turno.setFecha(new Date(System.currentTimeMillis()));
			turno.setEstado(true);
			List<Turno> aux = turnoService.getByUsuario(loginData.getUsuario().getIdusuario(), 1);
			for (Turno turno2 : aux) {
				turno.setFechaultapertura(new Date(turno2.getFechaapertura().getDate()));
				turno.setFechaultcierre(new Date(turno2.getFechacierre().getDate()));
			}
			loginData.setTurno(turnoService.add(turno));
			respuesta.setDatos(loginData.getTurno());
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
	 * Obtiene todos las Turnos
	 * @return
	 */
	@GetMapping(TURNOS)
	public @ResponseBody Respuesta getAllTurnos() {
		logger.info(TURNOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(turnoService.getAll());
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
	 * Actualiza una turno
	 * @param turno
	 * @return
	 */
	@PutMapping(TURNOS)
	public @ResponseBody Respuesta updateTurno(@RequestBody Turno turno, HttpServletRequest request) {
		logger.info(TURNOS + ": " + turno.toString() );
		Respuesta respuesta = new Respuesta("OK");
		try {
			
			respuesta.setDatos(turnoService.update(turno));
			
			
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
	 * Desactiva o borra la turno
	 * @param idTurno
	 * @return
	 */
	@DeleteMapping(TURNO_BY_ID)
	public @ResponseBody Respuesta deleteTurno(@PathVariable("idTurno") Integer idTurno) {
		logger.info(TURNOS);
		Respuesta respuesta = new Respuesta("OK");
		try {
			respuesta.setDatos(turnoService.delete(idTurno));
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
	
	
	@PostMapping(POST_CERRAR_TURNO)
	public @ResponseBody Respuesta cerrarTurno(@RequestBody Turno turno, HttpServletRequest request) {
		logger.info(POST_CERRAR_TURNO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			LoginData loginData = loginService.getLoginCache(request);
	
			respuesta.setDatos(turnoService.cerrarTurnoShop(turno, loginData));
			loginData.setTurno(null);
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
	
	@PostMapping(GET_INF_TUTNO_MOVIMIENTO)
	public @ResponseBody Respuesta infTunoMovimeinto(HttpServletRequest request) {
		logger.info(GET_INF_TUTNO_MOVIMIENTO);
		Respuesta respuesta = new Respuesta("OK");
		try {
			LoginData loginData = loginService.getLoginCache(request);
	
			respuesta.setDatos(turnoService.getTurnoMovimiento(loginData.getTurno(), loginData));
			loginData.setTurno(null);
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