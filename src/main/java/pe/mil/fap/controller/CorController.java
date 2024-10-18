package pe.mil.fap.controller;
 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody;  
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
 
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody CorDTO cor) throws Exception { 
		return corService.guardar(cor);
	}

}
