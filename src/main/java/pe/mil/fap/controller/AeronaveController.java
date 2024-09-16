package pe.mil.fap.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.administration.AeronaveDTO;
import pe.mil.fap.model.administration.FlotaDTO;
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.AeronaveService;
import pe.mil.fap.service.administration.usp.inf.FlotaService;

@Controller 
@RequestMapping("/aeronaves")
public class AeronaveController {

	private final AeronaveService aeronaveService;
	private final FlotaService flotaService;

	public AeronaveController(final AeronaveService aeronaveService, final FlotaService flotaService) {
		super();
		this.aeronaveService = aeronaveService;
		this.flotaService = flotaService;
	}

	@GetMapping({"/",""})
	public String index(Model model) throws Exception{  
		List<SelectItemDTO> selectorFlota = new ArrayList<>(); 
	    List<FlotaDTO> lstFlotas = flotaService.listarFlotas();
	    
	    selectorFlota.add(SelectItemDTO.createDefaultItem("Flota"));
		
		for (FlotaDTO flota : lstFlotas) {  
			selectorFlota.add(new SelectItemDTO(flota.getIdFlota(),
												flota.getCoCodigo() + " - " + flota.getNoNombre(), 
												false));
		}
 
		model.addAttribute("selectorMdFlota", selectorFlota); 
		return "apps/gtn-aeronave";
	}
	
	@PostMapping("/paginar")
	@ResponseBody
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws Exception{
		DataTableDTO dataTable =  aeronaveService.paginar(parametro);
		return dataTable;
	}
	
	@GetMapping("/buscarPorId")
	@ResponseBody
	public Optional<AeronaveDTO> buscarPorId(@RequestParam(name = "idAeronave", required = true) Integer idAeronave, HttpServletRequest request) throws Exception {
		Optional<AeronaveDTO> aeronave = aeronaveService.buscarPorId(idAeronave);
		return aeronave;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(HttpServletRequest request, @RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("aeronave") String aeronaveJson)
			throws Exception { 	
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		Integer idUnidad = usuarioDTO.getIdUnidad();
		
		ObjectMapper objectMapper = new ObjectMapper();
		AeronaveDTO aeronaveDTO = objectMapper.readValue(aeronaveJson, AeronaveDTO.class);
		
		aeronaveDTO.setIdUnidad(idUnidad);
		
		return aeronaveService.guardar(aeronaveDTO, archivo);
	}

	@PostMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(HttpServletRequest request, @RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("aeronave") String aeronaveJson) throws Exception {
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		Integer idUnidad = usuarioDTO.getIdUnidad();

		ObjectMapper objectMapper = new ObjectMapper();
		AeronaveDTO aeronaveDTO = objectMapper.readValue(aeronaveJson, AeronaveDTO.class);

		aeronaveDTO.setIdUnidad(idUnidad);
		
		return aeronaveService.actualizar(aeronaveDTO, archivo);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return aeronaveService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception{
		return aeronaveService.eliminarMultiple(lstId);
	} 
}
