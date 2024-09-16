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
import pe.mil.fap.model.administration.RestriccionEstandarDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.RestriccionEstandarService;

@Controller  
@RequestMapping("/restriccionesEstandar")
public class RestriccionEstandarController {
	
	private final RestriccionEstandarService restriccionEstandarService;
	
	public RestriccionEstandarController(final RestriccionEstandarService restriccionEstandarService) {
		super();
		this.restriccionEstandarService = restriccionEstandarService;
	}

	@GetMapping("/listarPorIdDetalleMision")
	@ResponseBody
	public List<RestriccionEstandarDTO> listarPorIdDetalleMision(@RequestParam(name = "idDetalleMision", required = true) Integer idDetalleMision, HttpServletRequest request) throws Exception{
		List<RestriccionEstandarDTO> lstRestricciones =  restriccionEstandarService.listarRestricciones(idDetalleMision);
		return lstRestricciones;
	}
 
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody RestriccionEstandarDTO restriccion) throws Exception { 
		return restriccionEstandarService.guardar(restriccion);
	}

}
