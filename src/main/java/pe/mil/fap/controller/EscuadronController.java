package pe.mil.fap.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO; 
import pe.mil.fap.service.administration.usp.inf.UnidadService;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;

@Controller 
@RequestMapping("/escuadrones")
public class EscuadronController {

	private final EscuadronService escuadronService;
	private final UnidadService unidadService;

	public EscuadronController(final EscuadronService escuadronService, final UnidadService unidadService) {
		super();
		this.unidadService = unidadService;
		this.escuadronService = escuadronService;
	}
	@GetMapping({"/",""})
	public String index(Model model, HttpServletRequest request) throws Exception{ 
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);

		List<SelectItemDTO> selectorUnidad = new ArrayList<>(); 
	    List<UnidadDTO> lstUnidades = unidadService.listarUnidadesPorRector(unidadService.buscarId(usuarioDTO.getIdUnidad()).get().getNuCodigo());
	    
		selectorUnidad.add(SelectItemDTO.createDefaultItem("Unidad"));
		
		for (UnidadDTO unidad : lstUnidades) { 
			boolean isSelected = false;
			if (usuarioDTO.getIdUnidad() != null) {
				if (Objects.equals(unidad.getIdUnidad(), usuarioDTO.getIdUnidad())) {
					isSelected = true;
	            }
	        } 
			selectorUnidad.add(new SelectItemDTO(unidad.getIdUnidad(),
												 unidad.getNoNombre() + " - " + unidad.getTxDescripcion(), 
												 isSelected));
		}

		model.addAttribute("selectorUnidad", selectorUnidad);
		model.addAttribute("selectorMdUnidad", selectorUnidad); 
		return "apps/adm-escuadron";
	}

	@GetMapping("/listar")
	@ResponseBody
	public List<EscuadronDTO> listar() throws Exception {
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadrones();
		return lstEscuadrones;
	} 
	
	@GetMapping("/listarPorIdUnidadRector")
	@ResponseBody
	public List<EscuadronDTO> listarPorIdUnidadRector(@RequestParam(name = "idUnidad", required = false) Integer idUnidad, HttpServletRequest request) throws Exception { 
		if (idUnidad == null) {			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
			idUnidad = usuarioDTO.getIdUnidad();
		}
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidadRector(idUnidad);
		return lstEscuadrones;
	} 
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("escuadron") String escuadronJson)
			throws Exception { 
		ObjectMapper objectMapper = new ObjectMapper();
		EscuadronDTO escuadronDTO = objectMapper.readValue(escuadronJson, EscuadronDTO.class);
		 
		return escuadronService.guardar(escuadronDTO, archivo);
	}

	@PostMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("escuadron") String escuadronJson) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		EscuadronDTO escuadronDTO = objectMapper.readValue(escuadronJson, EscuadronDTO.class);

		return escuadronService.actualizar(escuadronDTO, archivo);
	}

	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception {
		return escuadronService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception {
		return escuadronService.eliminarMultiple(lstId);
	}
 
}
