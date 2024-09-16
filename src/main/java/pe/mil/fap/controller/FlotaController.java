package pe.mil.fap.controller;
 
import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
import pe.mil.fap.model.administration.FlotaDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.FlotaService;

@Controller 
@RequestMapping("/flotas")
public class FlotaController {

	private final FlotaService flotaService;

	public FlotaController(final FlotaService flotaService) {
		super();
		this.flotaService = flotaService;
	}
	
	@GetMapping({"/",""})
	public String index() throws Exception{
		return "apps/adm-flota";
	} 
	
	@GetMapping("/listar")
	@ResponseBody
	public List<FlotaDTO> listar() throws Exception{
		List<FlotaDTO> lstFlotas =  flotaService.listarFlotas();
		return lstFlotas;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody FlotaDTO flota) throws Exception{
		return flotaService.guardar(flota);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody FlotaDTO flota) throws Exception{ 
		return flotaService.actualizar(flota);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return flotaService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return flotaService.eliminarMultiple(lstId);
	}

}
