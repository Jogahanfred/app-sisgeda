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

import pe.mil.fap.model.administration.BancoSubFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.BancoSubFaseService;

@Controller 
@RequestMapping("/bancoSubFases")
public class BancoSubFaseController {

	private final BancoSubFaseService bancoSubFaseService;

	public BancoSubFaseController(final BancoSubFaseService bancoSubFaseService) {
		super();
		this.bancoSubFaseService = bancoSubFaseService;
	}

	@GetMapping({"/",""})
	public String index(Model model) throws Exception{ 
		return "apps/adm-banco-sub-fase";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<BancoSubFaseDTO> listar() throws Exception{
		List<BancoSubFaseDTO> lstBancoSubFases =  bancoSubFaseService.listarBancoSubFases();
		return lstBancoSubFases;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody BancoSubFaseDTO bancoSubFase) throws Exception{
		return bancoSubFaseService.guardar(bancoSubFase);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody BancoSubFaseDTO bancoSubFase) throws Exception{ 
		return bancoSubFaseService.actualizar(bancoSubFase);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return bancoSubFaseService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return bancoSubFaseService.eliminarMultiple(lstId);
	}
	 
}
