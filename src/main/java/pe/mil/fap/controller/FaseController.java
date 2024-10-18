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
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.FaseDTO;
import pe.mil.fap.model.helpers.FaseInscritoDTOResponse;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.BancoFaseService;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.FaseService;

@Controller 
@RequestMapping("/fases")
public class FaseController {

	private final FaseService faseService;
	private final EscuadronService escuadronService;
	private final BancoFaseService bancoFaseService;

	public FaseController(final FaseService faseService, final EscuadronService escuadronService, final BancoFaseService bancoFaseService) {
		super();
		this.faseService = faseService;
		this.escuadronService = escuadronService;
		this.bancoFaseService = bancoFaseService;
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
		
		List<SelectItemDTO> selectorBancoFase = new ArrayList<>();
		List<BancoFaseDTO> lstBancoFases = bancoFaseService.listarBancoFases();
		selectorBancoFase.add(SelectItemDTO.createDefaultItem("Banco Fase"));

		for (BancoFaseDTO banco : lstBancoFases) { 
			boolean isSelected = false; 
			selectorBancoFase.add(new SelectItemDTO(banco.getIdBancoFase(),
					banco.getNoNombre(), 
					isSelected));
		}
  
		model.addAttribute("selectorEscuadron", selectorEscuadron);
		model.addAttribute("selectorMdEscuadron", selectorEscuadron); 
		model.addAttribute("selectorMdBancoFase", selectorBancoFase); 
		return "apps/gtn-fase";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<FaseDTO> listar() throws Exception{
		List<FaseDTO> lstFases =  faseService.listarFases();
		return lstFases;
	}
	
	@GetMapping("/listarPorIdUnidad")
	@ResponseBody
	public List<FaseDTO> listarFasesPorIdUnidad(@RequestParam(name = "idUnidad", required = false) Integer idUnidad, HttpServletRequest request) throws Exception { 
		if (idUnidad == null) {			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
			idUnidad = usuarioDTO.getIdUnidad();
		}
		List<FaseDTO> lstFases = faseService.listarFasesPorIdUnidad(idUnidad);
		return lstFases;
	} 
	
	@GetMapping("/listarPorIdPrograma")
	@ResponseBody
	public List<FaseDTO> listarFasesPorIdPrograma(@RequestParam(name = "idPrograma", required = false) Integer idPrograma, HttpServletRequest request) throws Exception { 
		List<FaseDTO> lstFases = faseService.listarFasesPorIdPrograma(idPrograma);
		return lstFases;
	} 
	
	@GetMapping("/listarFasesACalificarPorPeriodo")
	@ResponseBody
	public List<FaseInscritoDTOResponse> listarFasesACalificarPorPeriodo(HttpServletRequest request) throws Exception { 
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Integer idPrograma = (Integer) request.getSession().getAttribute("idPrograma");
		return faseService.listarFasesACalificarPorPeriodo(UtilHelpers.getCurrentYear(), idMiembro, idPrograma);
	} 
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody FaseDTO fase) throws Exception{
		return faseService.guardar(fase);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody FaseDTO fase) throws Exception{ 
		return faseService.actualizar(fase);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return faseService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return faseService.eliminarMultiple(lstId);
	}
	 
}
