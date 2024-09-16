package pe.mil.fap.controller;
  
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 
import pe.mil.fap.model.helpers.EjeInterseccionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
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
}
