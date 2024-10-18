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
import pe.mil.fap.model.administration.CorDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.CorService; 

@Controller  
@RequestMapping("/cor")
public class CorController {
	
	private final CorService corService;
	
	public CorController(final CorService corService) {
		super();
		this.corService = corService;
	}
	
	@GetMapping("/listarPorIdDetalleCalificacion")
	@ResponseBody
	public List<CorDTO> listarPorIdDetalleCalificacion(@RequestParam(name = "idDetalleCalificacion", required = true) Integer idDetalleCalificacion, HttpServletRequest request) throws Exception{
		List<CorDTO> lstCOR =  corService.listarCorPorIdDetalleCalificacion(idDetalleCalificacion);
		return lstCOR;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody CorDTO cor) throws Exception { 
		return corService.guardar(cor);
	}

}
