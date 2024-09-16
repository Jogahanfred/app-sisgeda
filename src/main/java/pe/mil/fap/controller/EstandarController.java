package pe.mil.fap.controller;
 
import java.util.List; 

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 
import pe.mil.fap.model.administration.EstandarDTO; 
import pe.mil.fap.service.administration.usp.inf.EstandarService;

@Controller 
@RequestMapping("/estandares")
public class EstandarController {

	private final EstandarService estandarService; 

	public EstandarController(final EstandarService estandarService ) {
		super(); 
		this.estandarService = estandarService;
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<EstandarDTO> listar() throws Exception {
		List<EstandarDTO> lstEstandares = estandarService.listarEstandares();
		return lstEstandares;
	} 
	 
}
