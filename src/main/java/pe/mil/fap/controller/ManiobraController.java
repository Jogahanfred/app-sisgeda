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
import pe.mil.fap.model.administration.BancoManiobraDTO;
import pe.mil.fap.model.administration.EscuadronDTO; 
import pe.mil.fap.model.administration.ManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.BancoManiobraService;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.ManiobraService;

@Controller 
@RequestMapping("/maniobras")
public class ManiobraController {

	private final ManiobraService maniobraService;
	private final EscuadronService escuadronService;
	private final BancoManiobraService bancoManiobraService;

	public ManiobraController(final ManiobraService maniobraService, final EscuadronService escuadronService, final BancoManiobraService bancoManiobraService) {
		super();
		this.maniobraService = maniobraService;
		this.escuadronService = escuadronService;
		this.bancoManiobraService = bancoManiobraService;
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
		
		List<SelectItemDTO> selectorBancoManiobra = new ArrayList<>();
		List<BancoManiobraDTO> lstBancoManiobras = bancoManiobraService.listarBancoManiobras();
		    
		selectorBancoManiobra.add(SelectItemDTO.createDefaultItem("Banco Maniobra"));
			
		for (BancoManiobraDTO banco: lstBancoManiobras) { 
			boolean isSelected = false;
			
			selectorBancoManiobra.add(new SelectItemDTO(banco.getIdBancoManiobra(),
												 		banco.getNoNombre(), 
												 		isSelected));
		}
 
		model.addAttribute("selectorEscuadron", selectorEscuadron); 
		model.addAttribute("selectorMdEscuadron", selectorEscuadron);
		model.addAttribute("selectorMdBancoManiobra", selectorBancoManiobra); 
		return "apps/gtn-maniobra";
	}
	 
	@GetMapping("/listar")
	@ResponseBody
	public List<ManiobraDTO> listar() throws Exception{
		List<ManiobraDTO> lstManiobras =  maniobraService.listarManiobras();
		return lstManiobras;
	}
	
	@GetMapping("/listarPorIdOperacion")
	@ResponseBody
	public List<ManiobraDTO> listarManiobrasPorIdOperacion(@RequestParam(name = "idOperacion", required = false) Integer idOperacion, HttpServletRequest request) throws Exception { 
		List<ManiobraDTO> lstManiobras = maniobraService.listarManiobrasPorIdOperacion(idOperacion);
		return lstManiobras;
	} 
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody ManiobraDTO maniobra) throws Exception{
		return maniobraService.guardar(maniobra);
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody ManiobraDTO maniobra) throws Exception{ 
		return maniobraService.actualizar(maniobra);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return maniobraService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return maniobraService.eliminarMultiple(lstId);
	}
	 
}
