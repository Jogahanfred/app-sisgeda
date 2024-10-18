package pe.mil.fap.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.pattern.Util;
import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.security.UsuarioDTO;

@Controller
public class PrincipalController { 
	
	@GetMapping({"/",""})
	public String index(Model model, HttpServletRequest request) {
		UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		if (usuarioDto != null) {  
			model.addAttribute("saludo", new StringBuilder(UtilHelpers.getGreetingTimeOfDay()).append(", ").append(usuarioDto.getNoNombre()).append("!"));
		} 
		String rolLogeado = (String) request.getSession().getAttribute(Configuracion.Helper.ROL_LOGEADO);
		if (rolLogeado == null || rolLogeado.isEmpty()) {  
			request.getSession().setAttribute(Configuracion.Helper.ROL_LOGEADO, usuarioDto.getLstRoles().get(0).getNoNombre()); 
		}
		return "apps/p-dashboard";
	}

	@PostMapping("/cambiarRol")
	public String seleccionarRol(@RequestParam String rolLogeado, HttpServletRequest request, Model model) { 
	    request.getSession().setAttribute(Configuracion.Helper.ROL_LOGEADO, rolLogeado);
	    return "redirect:/";
	}
}
