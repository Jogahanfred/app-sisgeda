package pe.mil.fap.controller;
  
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.bussiness.usp.inf.GrupoService;

@Controller 
@RequestMapping("/grupos")
public class GrupoController {

	private final GrupoService grupoService; 

	public GrupoController(final GrupoService grupoService) {
		super();
		this.grupoService = grupoService;
	}
	
	@GetMapping("/listar")
	@ResponseBody
	public List<GrupoDTO> listar(@RequestParam(name = "nuPeriodo", required = true) Integer nuPeriodo,
						         @RequestParam(name = "idUnidad", required = false) Integer idUnidad,
						         HttpServletRequest request) throws Exception{
		if (idUnidad == null) {			
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
			idUnidad = usuarioDTO.getIdUnidad();
		}
		List<GrupoDTO> lstGrupos =  grupoService.listar(nuPeriodo, idUnidad);
		return lstGrupos;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody GrupoDTO grupo) throws Exception{ 
		return grupoService.guardar(grupo);
	}
	  
}
