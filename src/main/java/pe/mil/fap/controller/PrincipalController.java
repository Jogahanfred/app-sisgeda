package pe.mil.fap.controller; 

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping; 

//Controlador Principals
@Controller
public class PrincipalController { 
	
	@GetMapping({"/",""})
	public String apps_ecommerce_aeronaves() {
		return "apps/p-dashboard";
	}

	 
}
