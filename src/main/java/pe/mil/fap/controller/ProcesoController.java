package pe.mil.fap.controller;

import static pe.mil.fap.common.utils.UtilHelpers.getCurrentYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.common.constants.Configuracion.Helper;
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.EstandarDTO;
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.EstandarService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.administration.usp.inf.UnidadService; 

@Controller 
@RequestMapping("/procesos")
public class ProcesoController {
	 
	private final EscuadronService escuadronService; 
	private final EstandarService estandarService;
	private final UnidadService unidadService;
	private final PersonalService personalService;

	public ProcesoController( final EscuadronService escuadronService,  
							  final EstandarService estandarService,
							  final UnidadService unidadService,
							  final PersonalService personalService) {
		super(); 
		this.escuadronService = escuadronService; 
		this.estandarService = estandarService;
		this.unidadService = unidadService;
		this.personalService = personalService;
	} 
	
	@GetMapping("/matricular-sub-fase")
	public String matricular(Model model, HttpServletRequest request) throws Exception{
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);

		List<SelectItemDTO> selectorEscuadron = new ArrayList<>();
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidad(usuarioDTO.getIdUnidad());
		    
		selectorEscuadron.add(SelectItemDTO.createDefaultItem("Escuadron"));
			
		for (EscuadronDTO escuadron : lstEscuadrones) {
			selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(),
												 	escuadron.getCoSigla(), 
												 	false));
		}
		
		model.addAttribute("selectorEscuadron", selectorEscuadron);
		return "apps/gtn-matricular-mision";
	}
	
	@GetMapping("/gestionar-estandar-maniobras")
	public String gestionarEstandarManiobras(Model model, HttpServletRequest request) throws Exception {
		return "apps/gtn-gestionar-sub-fase";
	}
	
	@GetMapping("/agrupar-alumnos")
	public String agruparAlumnos(Model model, HttpServletRequest request) throws Exception {
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);

		List<SelectItemDTO> selectorPersonal = new ArrayList<>(); 
	    
		Optional<UnidadDTO> optUnidad = unidadService.buscarId(usuarioDTO.getIdUnidad());
		List<PersonalDTO> lstPersonal = personalService.listarPorSiglaUnidad(optUnidad.get().getNoNombre());
		selectorPersonal.add(SelectItemDTO.createDefaultItem("Personal del " + optUnidad.get().getNoNombre().toUpperCase()));
		
		int indice = 1;
		for (PersonalDTO personal : lstPersonal) { 
			selectorPersonal.add(new SelectItemDTO(personal.getNsa(),
												   indice + ". " + personal.getDatosCompletos(),
												   false));
			indice++;
		} 
		
		List<SelectItemDTO> selectorPeriodo = new ArrayList<>();
		
		selectorPeriodo.add(SelectItemDTO.createDefaultItem("Periodo"));
		for (int i = -1; i <= Helper.ANIOS_ATRAS_HISTORIAL; i++) { 
			selectorPeriodo.add(new SelectItemDTO(getCurrentYear() - i, 
												  "Periodo " + (getCurrentYear() - i), 
												  (i == 0)));
		} 
		
		model.addAttribute("selectorMdPeriodo", selectorPeriodo);
		model.addAttribute("selectorPeriodo", selectorPeriodo);
        model.addAttribute("selectorMdPersonal", selectorPersonal); 
		return "apps/pro-grupo";
	}
	
	@GetMapping("/gestionar-restriccion-maniobras")
	public String gestionarRestriccionManiobras(Model model, HttpServletRequest request) throws Exception {
		List<SelectItemDTO> selectorEstandar = new ArrayList<>();
	    List<EstandarDTO> lstEstandares = estandarService.listarEstandares();
	    
	    selectorEstandar.add(SelectItemDTO.createDefaultItem("Estándar"));
		
		for (EstandarDTO estandar : lstEstandares) {   
			selectorEstandar.add(new SelectItemDTO(estandar.getIdEstandar(),
												   estandar.getCoCodigo() + " - " + estandar.getNoNombre(), 
												   false));
		}

		model.addAttribute("selectorMdEstandar", selectorEstandar); 
		return "apps/gtn-restriccion-maniobra";
	}
	
	@GetMapping("/programar-mision")
	public String programarMision(Model model, HttpServletRequest request) throws Exception {
		
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		List<SelectItemDTO> selectorPeriodo = new ArrayList<>();
		
		selectorPeriodo.add(SelectItemDTO.createDefaultItem("Periodo"));
		for (int i = -1; i <= Helper.ANIOS_ATRAS_HISTORIAL; i++) { 
			selectorPeriodo.add(new SelectItemDTO(getCurrentYear() - i, 
												  "Periodo " + (getCurrentYear() - i), 
												  (i == 0)));
		} 
		

		List<SelectItemDTO> selectorEscuadron = new ArrayList<>();
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidad(usuarioDTO.getIdUnidad());
		selectorEscuadron.add(SelectItemDTO.createDefaultItem("Escuadron"));

		for (EscuadronDTO escuadron : lstEscuadrones) {  
			selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(),
								  escuadron.getCoSigla(), 
								  false));
		}

		model.addAttribute("selectorEscuadron", selectorEscuadron);		 
		model.addAttribute("selectorPeriodo", selectorPeriodo);
		return "apps/pro-programar-mision";
	}
}
