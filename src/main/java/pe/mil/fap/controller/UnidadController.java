package pe.mil.fap.controller;

import java.util.List;

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
 
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.service.administration.usp.inf.UnidadService;

@Controller 
@RequestMapping("/unidades")
public class UnidadController {

	private final UnidadService unidadService;

	public UnidadController(final UnidadService unidadService) {
		super();
		this.unidadService = unidadService;
	} 
	
	@GetMapping({"/",""})
	public String index(Model model) throws Exception{
		List<UnidadDTO> lstUnidades = unidadService.listarUnidades();
		model.addAttribute("listaUnidades", lstUnidades);
		return "apps/grl-unidad";
	}
	
	@GetMapping("/listar")
	@ResponseBody
	public List<UnidadDTO> listar() throws Exception {
		List<UnidadDTO> lstUnidades = unidadService.listarUnidades();
		return lstUnidades;
	}

	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("unidad") String unidadJson) throws Exception { 
		ObjectMapper objectMapper = new ObjectMapper();
		UnidadDTO unidadDTO = objectMapper.readValue(unidadJson, UnidadDTO.class);
		return unidadService.guardar(unidadDTO, archivo);
	}

	@PostMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("unidad") String unidadJson) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UnidadDTO unidadDTO = objectMapper.readValue(unidadJson, UnidadDTO.class);
		return unidadService.actualizar(unidadDTO, archivo);
	}

	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception {
		return unidadService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception {
		return unidadService.eliminarMultiple(lstId);
	} 
}
