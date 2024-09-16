package pe.mil.fap.controller; 
import java.util.ArrayList;
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

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion; 
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.OperacionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.OperacionDTOResponse;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.OperacionService;

@Controller 
@RequestMapping("/operaciones")
public class OperacionController {

	private final OperacionService operacionService;
	private final EscuadronService escuadronService;

	public OperacionController(final OperacionService operacionService, final EscuadronService escuadronService) {
		super();
		this.operacionService = operacionService;
		this.escuadronService = escuadronService;
	}
	
	@GetMapping({"/",""})
	public String index(Model model, HttpServletRequest request) throws Exception{
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);

		List<SelectItemDTO> selectorEscuadron = new ArrayList<>();
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidad(usuarioDTO.getIdUnidad());
		    
		selectorEscuadron.add(SelectItemDTO.createDefaultItem("Escuadron"));
			
		for (EscuadronDTO escuadron : lstEscuadrones) { 
			boolean isSelected = false;
			
			selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(),
												 	escuadron.getCoSigla(), 
												 	isSelected));
		}
 
		model.addAttribute("selectorEscuadron", selectorEscuadron); 
		model.addAttribute("selectorMdEscuadron", selectorEscuadron); 
		return "apps/gtn-operacion";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<OperacionDTO> listar() throws Exception{
		List<OperacionDTO> lstOperaciones =  operacionService.listarOperaciones();
		return lstOperaciones;
	}

	@GetMapping("/listarPorIdEscuadron")
	@ResponseBody
	public List<OperacionDTO> listarPorIdEscuadron(@RequestParam(value = "idEscuadron") Integer idEscuadron) throws Exception{
		List<OperacionDTO> lstOperaciones = operacionService.listarOperacionesPorIdEscuadron(idEscuadron);
		return lstOperaciones;
	}
	
	@GetMapping("/listarDetalleManiobra")
	@ResponseBody
	public List<OperacionDTOResponse> listarOperacionesDetalleManiobra(@RequestParam(value = "lstIds") List<Integer> lstIds) throws Exception{
		lstIds.forEach(id -> {
			System.out.println("ID: " + id);
		});
		List<OperacionDTOResponse> lstOperaciones =  operacionService.listarOperacionesDetalleManiobra(lstIds);
		return lstOperaciones;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody OperacionDTO operacion) throws Exception{
		return operacionService.guardar(operacion);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody OperacionDTO operacion) throws Exception{ 
		return operacionService.actualizar(operacion);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return operacionService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return operacionService.eliminarMultiple(lstId);
	}
	 
}
