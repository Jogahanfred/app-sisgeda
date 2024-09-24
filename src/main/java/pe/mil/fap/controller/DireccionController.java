package pe.mil.fap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.ProgramaService;

@Controller
@RequestMapping("/redireccion")
public class DireccionController {
	
	private final MiembroService miembroService;
	private final ProgramaService programaService;
	
	public DireccionController(final MiembroService miembroService, final ProgramaService programaService) {
		super();
		this.miembroService = miembroService;
		this.programaService = programaService;
	}

	@GetMapping("/ctrlProgramas")
	public String nftExplore(Model model, HttpServletRequest request) throws Exception {
		return "apps/p-dir-programa";
	}

	@PostMapping("/redirectCtrlPersonasInscritas")
	@ResponseBody
	public String ctrlPersonasInscritas(@RequestBody Map<String, String> body, HttpServletRequest request) {
		String noTipoInstruccion = body.get("noTipoInstruccion");
		request.getSession().setAttribute("noTipoInstruccion", noTipoInstruccion);
		return "/redireccion/ctrlPersonasInscritas";
	}

	@GetMapping("/ctrlPersonasInscritas")
	public String irPaginaCtrlPersonasInscritas(HttpServletRequest request, ModelMap model) throws Exception {
		String noTipoInstruccion = (String) request.getSession().getAttribute("noTipoInstruccion");
		List<MiembroDTO> lstMiembros = miembroService.listarMiembrosACalificarPorPeriodo(UtilHelpers.getCurrentYear(), noTipoInstruccion.equals("PDE") ? "INSTRUCTOR": "ALUMNO");
		model.addAttribute("lstMiembros", lstMiembros);
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
		String noTipoInstruccion = (String) request.getSession().getAttribute("noTipoInstruccion");
		List<ProgramaInscritoDTOResponse> lstProgramas = programaService.listarProgramasACalificarPorPeriodo(UtilHelpers.getCurrentYear(), noTipoInstruccion, idMiembro);
		model.addAttribute("lstProgramas", lstProgramas);
		return "apps/p-dir-programa-inscrito";
	}

}
