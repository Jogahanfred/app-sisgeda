package pe.mil.fap.controller; 

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest; 
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
	public MessageDTO  guardar(@RequestBody RegistroCalificacionDTORequest calificacion) throws Exception{
		return calificacionService.registrarTransaccion(calificacion);
	} 
	 	
	@GetMapping("/verificarInscripcionSubFase")
	@ResponseBody
	public List<InscripcionMisionDTO> verificarInscripcionSubFase(@RequestParam(name = "idSubFase", required = true) Integer idSubFase, @RequestParam(name = "lstIdMiembros", required = true) String lstIdMiembros, HttpServletRequest request) throws Exception {
		return calificacionService.verificarInscripcionSubFase(idSubFase, lstIdMiembros);
	}
	
	
	
 
}
