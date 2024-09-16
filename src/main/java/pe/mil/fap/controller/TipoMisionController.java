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

import pe.mil.fap.model.administration.TipoMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.administration.usp.inf.TipoMisionService;

@Controller 
@RequestMapping("/tipoMisiones")
public class TipoMisionController {

	private final TipoMisionService tipoMisionService;

	public TipoMisionController(final TipoMisionService tipoMisionService) {
		super();
		this.tipoMisionService = tipoMisionService;
	}
	
	@GetMapping({"/",""})
	public String index() throws Exception{
		return "apps/gtn-tipo-mision";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<TipoMisionDTO> listar() throws Exception{
		List<TipoMisionDTO> lstTipoMisions =  tipoMisionService.listarTipoMisiones();
		return lstTipoMisions;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody TipoMisionDTO tipoMision) throws Exception{
		return tipoMisionService.guardar(tipoMision);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody TipoMisionDTO tipoMision) throws Exception{ 
		return tipoMisionService.actualizar(tipoMision);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return tipoMisionService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return tipoMisionService.eliminarMultiple(lstId);
	}
	 
}
