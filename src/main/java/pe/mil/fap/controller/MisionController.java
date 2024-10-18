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
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.helpers.EjeInterseccionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.MisionInscritoDTOResponse;
import pe.mil.fap.model.helpers.RegistroMisionDTORequest; 
import pe.mil.fap.service.bussiness.usp.inf.MisionService; 

@Controller 
@RequestMapping("/misiones")
public class MisionController {

	private final MisionService misionService; 

	public MisionController(final MisionService misionService) {
		super();
		this.misionService = misionService; 
	}   
 
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO  guardar(@RequestBody RegistroMisionDTORequest mision) throws Exception{
		return misionService.registrarTransaccion(mision);
	} 
	
	@PostMapping("/actualizarEstandar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody EjeInterseccionDTO ejeInterseccion) throws Exception{
		return misionService.actualizarEstandarPorIManiobraIdMision(ejeInterseccion.getIdMision(), ejeInterseccion.getIdManiobra(), ejeInterseccion.getIdEstandarRequerido());
	}  
	
	@GetMapping("/listarMisionesACalificarPorPeriodo")
	@ResponseBody
	public List<MisionInscritoDTOResponse> listarMisionesACalificarPorPeriodo(HttpServletRequest request) throws Exception{
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Integer idSubFase = (Integer) request.getSession().getAttribute("idSubFase");
		return misionService.listarMisionesACalificarPorPeriodo(UtilHelpers.getCurrentYear(), idMiembro, idSubFase);
	}
	
	@GetMapping("/listarMisionInscrito")
	public List<MisionInscritoDTOResponse> listarMisionInscrito(@RequestParam(name = "idMiembro") Integer idMiembro, @RequestParam(name = "idSubFase") Integer idSubFase) throws Exception{
		return misionService.listarMisionesACalificarPorPeriodo(UtilHelpers.getCurrentYear(), idMiembro, idSubFase);
	}
}
