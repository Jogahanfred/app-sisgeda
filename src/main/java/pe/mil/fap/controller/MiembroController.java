package pe.mil.fap.controller;
   
import static pe.mil.fap.common.utils.UtilHelpers.getCurrentYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import pe.mil.fap.common.constants.Configuracion.Helper;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.administration.usp.inf.UnidadService; 

@Controller
@RequestMapping("/miembros")
public class MiembroController {  
	
	private final MiembroService miembroService;
	private final UnidadService unidadService;
	private final PersonalService personalService;

	public MiembroController(final MiembroService miembroService, final UnidadService unidadService, final PersonalService personalService) {
		super();
		this.miembroService = miembroService;
		this.unidadService = unidadService;
		this.personalService = personalService;
	}
	
	@GetMapping("/instructores")
	public String indexInstructores(Model model, HttpServletRequest request) throws Exception{ 
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
		return "apps/gtn-instructor";
	} 
	
	@GetMapping("/alumnos")
	public String indexAlumnos(Model model, HttpServletRequest request) throws Exception{ 
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
		return "apps/gtn-alumno";
	} 

	@GetMapping("/listarInstructores")
	@ResponseBody
	public List<MiembroDTO> listarInstructores(@RequestParam(name = "nuPeriodo") Integer nuPeriodo) throws Exception{
		List<MiembroDTO> lstMiembros =  miembroService.listarMiembros(nuPeriodo, "INSTRUCTOR");
		return lstMiembros;
	}
	
	@GetMapping("/listarAlumnos")
	@ResponseBody
	public List<MiembroDTO> listarAlumnos(@RequestParam(name = "nuPeriodo") Integer nuPeriodo) throws Exception{
		List<MiembroDTO> lstMiembros =  miembroService.listarMiembros(nuPeriodo, "ALUMNO");
		return lstMiembros;
	}
	
	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody MiembroDTO miembro, HttpServletRequest request) throws Exception{
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		miembro.setIdUnidad(usuarioDTO.getIdUnidad());  
		return (miembro.getNoRol().equals("INSTRUCTOR")) ? miembroService.guardarInstructor(miembro) : miembroService.guardarAlumno(miembro);
	}
	
	@PutMapping("/deshabilitar")
	@ResponseBody
	public MessageDTO deshabilitar(@RequestParam(name = "id") Integer id) throws Exception{ 
		return miembroService.deshabilitar(id);
	}
	
	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception{
		return miembroService.eliminar(id);
	}

	 
}
