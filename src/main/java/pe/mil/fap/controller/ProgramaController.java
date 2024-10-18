package pe.mil.fap.controller; 

import static pe.mil.fap.common.constants.Configuracion.Helper;
import static pe.mil.fap.common.utils.UtilHelpers.getCurrentYear;

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
import pe.mil.fap.common.enums.RolEnum;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.administration.ProgramaDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.ProgramaService; 

@Controller 
@RequestMapping("/programas")
public class ProgramaController {

	private final ProgramaService programaService; 
	private final EscuadronService escuadronService;

	public ProgramaController(final ProgramaService programaService, final EscuadronService escuadronService) {
		super();
		this.programaService = programaService; 
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

		List<SelectItemDTO> selectorPeriodo = new ArrayList<>();
		
		selectorPeriodo.add(SelectItemDTO.createDefaultItem("Periodo"));
		for (int i = -1; i <= Helper.ANIOS_ATRAS_HISTORIAL; i++) { 
			selectorPeriodo.add(new SelectItemDTO(getCurrentYear() - i, "Periodo " + (getCurrentYear() - i), (i == 0)));
		}
		model.addAttribute("selectorPeriodo", selectorPeriodo);
		model.addAttribute("selectorEscuadron", selectorEscuadron);

		model.addAttribute("selectorMdPeriodo", selectorPeriodo);
		model.addAttribute("selectorMdEscuadron", selectorEscuadron); 
		return "apps/gtn-programa";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<ProgramaDTO> listar() throws Exception{
		List<ProgramaDTO> lstProgramas =  programaService.listarProgramas();
		return lstProgramas;
	}
	
	@GetMapping("/listarPorIdUnidad")
	@ResponseBody
	public List<ProgramaDTO> listarProgramasPorIdUnidad(@RequestParam(name = "idUnidad", required = false) Integer idUnidad, HttpServletRequest request) throws Exception { 
		if (idUnidad == null) {			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
			idUnidad = usuarioDTO.getIdUnidad();
		}
		List<ProgramaDTO> lstProgramas = programaService.listarProgramasPorIdUnidad(idUnidad);
		return lstProgramas;
	} 
	
	@GetMapping("/listarPorIdEscuadron")
	@ResponseBody
	public List<ProgramaDTO> listarProgramasPorIdEscuadron(@RequestParam(name = "idEscuadron", required = false) Integer idEscuadron) throws Exception { 
		List<ProgramaDTO> lstProgramas = programaService.listarProgramasPorIdEscuadron(idEscuadron);
		return lstProgramas;
	} 
	
	@GetMapping("/listarProgramasACalificarPorPeriodo")
	@ResponseBody
	public List<ProgramaInscritoDTOResponse> listarProgramasACalificarPorPeriodo(HttpServletRequest request) throws Exception{
		String noTipoInstruccion = (String) request.getSession().getAttribute("noTipoInstruccion");
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
	 	return programaService.listarProgramasACalificarPorPeriodo(UtilHelpers.getCurrentYear(), noTipoInstruccion, idMiembro);
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody ProgramaDTO programa) throws Exception{
		return programaService.guardar(programa);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody ProgramaDTO programa) throws Exception{ 
		return programaService.actualizar(programa);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return programaService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return programaService.eliminarMultiple(lstId);
	}
 
}
