package pe.mil.fap.controller;
 
import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody; 

import pe.mil.fap.model.administration.BancoFaseDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.BancoFaseService;

@Controller 
@RequestMapping("/bancoFases")
public class BancoFaseController {

	private final BancoFaseService bancoFaseService;

	public BancoFaseController(final BancoFaseService bancoFaseService) {
		super();
		this.bancoFaseService = bancoFaseService;
	}

	@GetMapping({"/",""})
	public String index(Model model) throws Exception{ 
		return "apps/adm-banco-fase";
	}
	
	@GetMapping("/listar")
	@ResponseBody
	public List<BancoFaseDTO> listar() throws Exception{
		List<BancoFaseDTO> lstBancoFases =  bancoFaseService.listarBancoFases();
		return lstBancoFases;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody BancoFaseDTO bancoFase) throws Exception{
		return bancoFaseService.guardar(bancoFase);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody BancoFaseDTO bancoFase) throws Exception{ 
		return bancoFaseService.actualizar(bancoFase);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return bancoFaseService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return bancoFaseService.eliminarMultiple(lstId);
	} 
}
