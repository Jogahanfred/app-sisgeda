package pe.mil.fap.controller;
 
import static pe.mil.fap.common.utils.UtilHelpers.getCurrentYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.classic.pattern.Util;
import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.common.constants.Configuracion.Helper;
import pe.mil.fap.common.enums.RolEnum;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.FaseDTO;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.administration.ProgramaDTO;
import pe.mil.fap.model.administration.SubFaseDTO;
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.FaseInscritoDTOResponse;
import pe.mil.fap.model.helpers.MisionInscritoDTOResponse;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.helpers.SubFaseInscritoDTOResponse;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.FaseService;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.ProgramaService;
import pe.mil.fap.service.administration.usp.inf.SubFaseService;
import pe.mil.fap.service.administration.usp.inf.UnidadService;
import pe.mil.fap.service.bussiness.usp.inf.MisionService;

@Controller
@RequestMapping("/redireccion")
public class DireccionController {
	
	private final MiembroService miembroService;
	private final ProgramaService programaService;
	private final FaseService faseService;
	private final SubFaseService subFaseService;
	private final MisionService misionService;
	private final EscuadronService escuadronService;
	private final UnidadService unidadService;
	
	public DireccionController(final UnidadService unidadService, final EscuadronService escuadronService, final MisionService misionService, final SubFaseService subFaseService, final MiembroService miembroService, final ProgramaService programaService, final FaseService faseService) {
		super();
		this.unidadService = unidadService;
		this.misionService = misionService;
		this.escuadronService = escuadronService;
		this.faseService = faseService;
		this.miembroService = miembroService;
		this.programaService = programaService;
		this.subFaseService = subFaseService;
	}

	@GetMapping("/ctrlProgramas")
	public String irPaginaCtrlProgramas(Model model, HttpServletRequest request) { 
		UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		if (usuarioDto != null) {  
			model.addAttribute("saludo", new StringBuilder(UtilHelpers.getGreetingTimeOfDay()).append(", ").append(usuarioDto.getNoNombre()).append("!"));
		} 
		String rolLogeado = (String) request.getSession().getAttribute(Configuracion.Helper.ROL_LOGEADO);
		if (rolLogeado == null || rolLogeado.isEmpty()) {  
			request.getSession().setAttribute(Configuracion.Helper.ROL_LOGEADO, usuarioDto.getLstRoles().get(0).getNoNombre()); 
		}
		return "apps/p-dir-programa";
	}

	@PostMapping("/redirectCtrlPersonasInscritas")
	@ResponseBody
	public String ctrlPersonasInscritas(@RequestBody Map<String, String> body, HttpServletRequest request) throws Exception {
		String noTipoInstruccion = body.get("noTipoInstruccion"); 
		request.getSession().setAttribute("noTipoInstruccion", noTipoInstruccion);
		return "/redireccion/ctrlPersonasInscritas";
	}

	@GetMapping("/ctrlPersonasInscritas")
	public String irPaginaCtrlPersonasInscritas(HttpServletRequest request, ModelMap model) throws Exception {
		String rolLogeado = (String) request.getSession().getAttribute(Configuracion.Helper.ROL_LOGEADO); 
		
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
		
		List<SelectItemDTO> selectorEscuadron = new ArrayList<>();
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidad(usuarioDTO.getIdUnidad());

		List<SelectItemDTO> selectorUnidad = new ArrayList<>(); 
		List<UnidadDTO> lstUnidades = unidadService.listarUnidades(); 
		
		RolEnum rolLogeadoEnum = RolEnum.valueOf(rolLogeado);
		switch (rolLogeadoEnum) {
	    	case ROLE_ADMIN: 
	    		selectorUnidad.add(SelectItemDTO.createDefaultItem("Unidad"));	    		
	    		for (UnidadDTO unidad : lstUnidades) {   
	    			selectorUnidad.add(new SelectItemDTO(unidad.getIdUnidad(),
	    												 unidad.getNoNombre() + " - " + unidad.getTxDescripcion(), 
	    												 false));
	    		}
		        model.addAttribute("selectorUnidad", selectorUnidad);
		        break;
	    	case ROLE_JEOPE:     		
	    		for (UnidadDTO unidad : lstUnidades) {   
	    			if (unidad.getIdUnidad() != null) {
						if (Objects.equals(unidad.getIdUnidad(), usuarioDTO.getIdUnidad())) {
			    			selectorUnidad.add(new SelectItemDTO(unidad.getIdUnidad(),
			    												 unidad.getNoNombre() + " - " + unidad.getTxDescripcion(), 
			    												 true));
						}
					}
	    		}
		        model.addAttribute("selectorUnidad", selectorUnidad);
		         
		        for (EscuadronDTO escuadron : lstEscuadrones) {  
		            selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(),
		                    escuadron.getCoSigla(), 
		                    false));
		        }
		        model.addAttribute("selectorEscuadron", selectorEscuadron);
		        break;	
	    	case ROLE_COESC:
		    case ROLE_JEINS:
		    case ROLE_INSTR:    		
	    		for (UnidadDTO unidad : lstUnidades) {   
	    			if (unidad.getIdUnidad() != null) {
						if (Objects.equals(unidad.getIdUnidad(), usuarioDTO.getIdUnidad())) {
			    			selectorUnidad.add(new SelectItemDTO(unidad.getIdUnidad(),
			    												 unidad.getNoNombre() + " - " + unidad.getTxDescripcion(), 
			    												 true));
						}
					}
	    		}
		        model.addAttribute("selectorUnidad", selectorUnidad);
		        
		        for (EscuadronDTO escuadron : lstEscuadrones) { 
		            if (usuarioDTO.getIdEscuadron() != null) {
		                if (Objects.equals(escuadron.getIdEscuadron(), usuarioDTO.getIdEscuadron())) {		                    
		                	selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(),
		                			escuadron.getCoSigla(), 
		                			true));
		    				request.getSession().setAttribute("idEscuadron", escuadron.getIdEscuadron());
		                }
		            }  
		        }
		        model.addAttribute("selectorEscuadron", selectorEscuadron);
		        break; 
		    default: 
		        break;
		}
		String descripcionMiembro = (String) request.getSession().getAttribute("noTipoInstruccion");
		model.addAttribute("subTitulo", new StringBuilder().append("¿Que").append(" ").append(descripcionMiembro.equalsIgnoreCase("PDE") ? "Instructor": "Alumno").append(" ").append("desea seleccionar?"));
		return "apps/p-dir-persona";
	}
	
	@PostMapping("/redirectCtrlProgramasInscritos")
	@ResponseBody
	public String ctrlProgramasInscritos(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
		Integer idMiembro = body.get("idMiembro");
		request.getSession().setAttribute("idMiembro", idMiembro);
		return "/redireccion/ctrlProgramasInscritos";
	}

	@GetMapping("/ctrlProgramasInscritos")
	public String irPaginaCtrlProgramasInscritos(HttpServletRequest request, ModelMap model) throws Exception {
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Optional<MiembroDTO> miembro = miembroService.buscarPorId(idMiembro);
		model.addAttribute("miembro", miembro.get()); 
		return "apps/p-dir-programa-inscrito";
	}

	@PostMapping("/redirectCtrlFasesInscritas")
	@ResponseBody
	public String ctrlFasesInscritas(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
		Integer idPrograma = body.get("idPrograma");
		request.getSession().setAttribute("idPrograma", idPrograma);
		return "/redireccion/ctrlFasesInscritas";
	}

	@GetMapping("/ctrlFasesInscritas")
	public String irPaginaCtrlFasesInscritas(HttpServletRequest request, ModelMap model) throws Exception {
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Integer idPrograma = (Integer) request.getSession().getAttribute("idPrograma");
		
		Optional<MiembroDTO> optMiembro = miembroService.buscarPorId(idMiembro);
		Optional<ProgramaDTO> optPrograma = programaService.buscarId(idPrograma);
		
		model.addAttribute("miembro", optMiembro.get());
		model.addAttribute("programa", optPrograma.get()); 
		return "apps/p-dir-fase-inscrito";
	}

	@PostMapping("/redirectCtrlSubFasesInscritas")
	@ResponseBody
	public String ctrlSubFasesInscritas(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
		Integer idFase = body.get("idFase");
		request.getSession().setAttribute("idFase", idFase);
		return "/redireccion/ctrlSubFasesInscritas";
	}

	@GetMapping("/ctrlSubFasesInscritas")
	public String irPaginaCtrlSubFasesInscritas(HttpServletRequest request, ModelMap model) throws Exception {
		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Integer idFase = (Integer) request.getSession().getAttribute("idFase");

		Optional<MiembroDTO> optMiembro = miembroService.buscarPorId(idMiembro);
		Optional<FaseDTO> optFase = faseService.buscarPorId(idFase);
	 
		model.addAttribute("miembro", optMiembro.get());
		model.addAttribute("fase", optFase.get()); 
		return "apps/p-dir-sub-fase-inscrito";
	}
	
	@PostMapping("/redirectCtrlMisionesInscritas")
	@ResponseBody
	public String ctrlMisionesInscritas(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
		Integer idSubFase = body.get("idSubFase");
		request.getSession().setAttribute("idSubFase", idSubFase);
		return "/redireccion/ctrlMisionesInscritas";
	}

	@GetMapping("/ctrlMisionesInscritas")
	public String irPaginaCtrlMisionesInscritas(HttpServletRequest request, ModelMap model) throws Exception {
		String rolLogeado = (String) request.getSession().getAttribute(Configuracion.Helper.ROL_LOGEADO); 

		Integer idMiembro = (Integer) request.getSession().getAttribute("idMiembro");
		Integer idSubFase = (Integer) request.getSession().getAttribute("idSubFase");
		
		Optional<SubFaseDTO> optSubFase = subFaseService.buscarPorId(idSubFase);
		Optional<MiembroDTO> optMiembro = miembroService.buscarPorId(idMiembro); 
		
		String url = "";
		RolEnum rolLogeadoEnum = RolEnum.valueOf(rolLogeado);
		switch (rolLogeadoEnum) {
	    	case ROLE_ADMIN:  
		    	url = "apps/p-dir-mision-inscrito2";
		        break;
	    	case ROLE_JEOPE: 
	    	case ROLE_COESC:    
		    	url = "apps/p-dir-mision-inscrito-enlace";		 
		        break;	 
		    case ROLE_JEINS:
		    	UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
				if (usuarioDto != null) {  				
					Optional<MiembroDTO> optInstructor = miembroService.buscarPorNsaPorRolPeriodo(usuarioDto.getCoNsa(), UtilHelpers.getCurrentYear(), "INSTRUCTOR"); 
					model.addAttribute("instructor", optInstructor.get());
					url = "apps/p-dir-mision-inscrito-asignar-instructor";   	 
				} 
		        break; 
		    case ROLE_INSTR: 
		    	url = "apps/p-dir-mision-a-calificar";		 
		        break;		
		    default: 
		        break;
		}
	 
		model.addAttribute("miembro", optMiembro.get());
		model.addAttribute("subFase", optSubFase.get()); 
		return url;
	}
	
	@PostMapping("/redirectCtrlMisionAVisualizar")
	@ResponseBody
	public String ctrlMisionAVisualizar(@RequestBody Map<String, Integer> body, HttpServletRequest request) {
		Integer idCalificacion = body.get("idCalificacion");
		request.getSession().setAttribute("idCalificacion", idCalificacion);
		return "/redireccion/ctrlMisionAVisualizar";
	}

	@GetMapping("/ctrlMisionAVisualizar")
	public String irPaginaCtrlMisionAVisualizar(HttpServletRequest request, ModelMap model) throws Exception {
		return "apps/p-dir-mision-a-visualizar";
	}
}
