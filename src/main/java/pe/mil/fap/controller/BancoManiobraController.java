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

import pe.mil.fap.model.administration.BancoManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.BancoManiobraService;

@Controller  
@RequestMapping("/bancoManiobras")
public class BancoManiobraController {

	private final BancoManiobraService bancoManiobraService;

	public BancoManiobraController(final BancoManiobraService bancoManiobraService) {
		super();
		this.bancoManiobraService = bancoManiobraService;
	}

	@GetMapping({"/",""})
	public String index(Model model) throws Exception{ 
		return "apps/adm-banco-maniobra";
	} 
	
	@GetMapping("/listar")
	@ResponseBody
	public List<BancoManiobraDTO> listar() throws Exception{
		List<BancoManiobraDTO> lstBancoManiobras =  bancoManiobraService.listarBancoManiobras();
		return lstBancoManiobras;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody BancoManiobraDTO bancoManiobra) throws Exception{
		return bancoManiobraService.guardar(bancoManiobra);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody BancoManiobraDTO bancoManiobra) throws Exception{ 
		return bancoManiobraService.actualizar(bancoManiobra);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return bancoManiobraService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return bancoManiobraService.eliminarMultiple(lstId);
	} 
}
