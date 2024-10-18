package pe.mil.fap.controller; 

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.common.enums.RolEnum;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.administration.SubFaseDTO;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.bussiness.usp.inf.CalificacionService; 

@Controller 
@RequestMapping("/calificaciones")
public class CalificacionController {

	private final CalificacionService calificacionService; 

	public CalificacionController(final CalificacionService calificacionService) {
		super();
		this.calificacionService = calificacionService; 
	}   
 
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody RegistroCalificacionDTORequest calificacion) throws Exception{
		return calificacionService.registrarTransaccion(calificacion);
	}  
	
	@PostMapping("/asignarInstructor")
	@ResponseBody
	public MessageDTO ctrlMisionesInscritas(@RequestBody Map<String, Integer> body, HttpServletRequest request) throws Exception{
		Integer idCalificador = body.get("idCalificador");
		Integer idCalificacion = body.get("idCalificacion");
		return calificacionService.asignarInstructor(idCalificador, idCalificacion);
	}
	
	@GetMapping("/verificarInscripcionSubFase")
	@ResponseBody
	public List<InscripcionMisionDTO> verificarInscripcionSubFase(@RequestParam(name = "idSubFase", required = true) Integer idSubFase, @RequestParam(name = "lstIdMiembros", required = true) String lstIdMiembros, HttpServletRequest request) throws Exception {
		return calificacionService.verificarInscripcionSubFase(idSubFase, lstIdMiembros);
	}
	
	@GetMapping("/obtenerMatriz")
	@ResponseBody
	public MessageDTO obtenerMatriz(HttpServletRequest request) throws Exception {
		String rolLogeado = (String) request.getSession().getAttribute(Configuracion.Helper.ROL_LOGEADO); 
		MessageDTO msg = new MessageDTO();
		Integer idCalificado = (Integer) request.getSession().getAttribute("idMiembro");
		RolEnum rolLogeadoEnum = RolEnum.valueOf(rolLogeado);
		switch (rolLogeadoEnum) {
			case ROLE_JEOPE:
			case ROLE_COESC: {
				Integer idCalificacion = (Integer) request.getSession().getAttribute("idCalificacion");
				msg = calificacionService.obtenerMatrizACalificarPorIdCalificacion(idCalificado, idCalificacion);
				break;
			}
			case ROLE_INSTR: {	
				UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
				msg = calificacionService.obtenerMatrizACalificar(idCalificado, usuarioDTO.getCoNsa());
				break;
			}
			default:
				break;
			}
		return msg;
	}
	
	@PostMapping("/calificarManiobra")
	@ResponseBody
	public MessageDTO calificarManiobra(@RequestBody(required = true) Map<String, String> body) throws Exception {
		String idCalificacion = (String) body.get("idCalificacion");
		String coEstandarObtenido = (String) body.get("coEstandarObtenido");
		String idManiobra = (String) body.get("idManiobra");
		return calificacionService.calificarManiobra(Integer.parseInt(idManiobra), Integer.parseInt(idCalificacion), coEstandarObtenido);
	}
	/*
	@GetMapping("/obtenerMatrizPorIdCalificacion")
	@ResponseBody
	public MessageDTO obtenerMatriz(@RequestParam(name = "idCalificacion", required = true) Integer idCalificacion, HttpServletRequest request) throws Exception {
		Integer idCalificado = (Integer) request.getSession().getAttribute("idMiembro");
		return calificacionService.obtenerMatrizACalificarPorIdCalificacion(idCalificado,idCalificacion);
	}
	*/
	
 
}
