package pe.mil.fap.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.administration.BancoSubFaseDTO;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.SubFaseDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.usp.inf.BancoFaseService;
import pe.mil.fap.service.administration.usp.inf.BancoSubFaseService;
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.administration.usp.inf.SubFaseService;

@Controller 
@RequestMapping("/subFases")
public class SubFaseController {

	private final SubFaseService subFaseService;
	private final EscuadronService escuadronService;
	private final BancoSubFaseService bancoSubFaseService;
	private final BancoFaseService bancoFaseService;

	public SubFaseController(final BancoFaseService bancoFaseService, final SubFaseService subFaseService,
			final EscuadronService escuadronService, final BancoSubFaseService bancoSubFaseService) {
		super();
		this.bancoFaseService = bancoFaseService;
		this.subFaseService = subFaseService;
		this.escuadronService = escuadronService;
		this.bancoSubFaseService = bancoSubFaseService;
	}

	@GetMapping({ "/", "" })
	public String index(Model model, HttpServletRequest request) throws Exception {
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute(Configuracion.Helper.USUARIO_LOGEADO);

		List<SelectItemDTO> selectorEscuadron = new ArrayList<>();
		List<EscuadronDTO> lstEscuadrones = escuadronService.listarEscuadronesPorIdUnidad(usuarioDTO.getIdUnidad());
		selectorEscuadron.add(SelectItemDTO.createDefaultItem("Escuadron"));

		for (EscuadronDTO escuadron : lstEscuadrones) {
			boolean isSelected = false;
			selectorEscuadron.add(new SelectItemDTO(escuadron.getIdEscuadron(), escuadron.getCoSigla(), isSelected));
		}

		List<SelectItemDTO> selectorBancoSubFase = new ArrayList<>();
		List<BancoSubFaseDTO> lstBancoSubFases = bancoSubFaseService.listarBancoSubFases();
		selectorBancoSubFase.add(SelectItemDTO.createDefaultItem("Banco SubFases"));

		for (BancoSubFaseDTO banco : lstBancoSubFases) {
			boolean isSelected = false;
			selectorBancoSubFase.add(new SelectItemDTO(banco.getIdBancoSubFase(), banco.getNoNombre(), isSelected));
		}

		List<SelectItemDTO> selectorBancoFase = new ArrayList<>();
		List<BancoFaseDTO> lstBancoFases = bancoFaseService.listarBancoFases();
		selectorBancoFase.add(SelectItemDTO.createDefaultItem("Banco Fases"));

		for (BancoFaseDTO banco : lstBancoFases) {
			boolean isSelected = false;
			selectorBancoFase.add(new SelectItemDTO(banco.getIdBancoFase(), banco.getNoNombre(), isSelected));
		}

		model.addAttribute("selectorMdFase", selectorBancoFase);
		model.addAttribute("selectorEscuadron", selectorEscuadron);
		model.addAttribute("selectorMdEscuadron", selectorEscuadron);
		model.addAttribute("selectorMdBancoSubFase", selectorBancoSubFase);
		return "apps/gtn-sub-fase";
	}
 
	@GetMapping("/listar")
	@ResponseBody
	public List<SubFaseDTO> listar() throws Exception {
		List<SubFaseDTO> lstSubFases = subFaseService.listarSubFases();
		return lstSubFases;
	}

	@GetMapping("/listarPorIdUnidad")
	@ResponseBody
	public List<SubFaseDTO> listarSubFasesPorIdUnidad(
			@RequestParam(name = "idUnidad", required = false) Integer idUnidad, HttpServletRequest request)
			throws Exception {
		if (idUnidad == null) {
			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession()
					.getAttribute(Configuracion.Helper.USUARIO_LOGEADO);
			idUnidad = usuarioDTO.getIdUnidad();
		}
		List<SubFaseDTO> lstSubFases = subFaseService.listarSubFasesPorIdUnidad(idUnidad);
		return lstSubFases;
	}

	@GetMapping("/listarPorIdFase")
	@ResponseBody
	public List<SubFaseDTO> listarSubFasesPorIdFase(@RequestParam(name = "idFase", required = false) Integer idFase,
			HttpServletRequest request) throws Exception {
		List<SubFaseDTO> lstSubFases = subFaseService.listarSubFasesPorIdFase(idFase);
		return lstSubFases;
	}

	@GetMapping("/listarFiltroPeriodo")
	@ResponseBody
	public List<SelectItemDTO> listarFiltroPeriodo(@RequestParam(name = "nuPeriodo", required = true) Integer nuPeriodo)
			throws Exception {
		List<SelectItemDTO> lstSelector = new ArrayList<>();
		List<Map<Integer, String>> lstFiltros = subFaseService.listarFiltroPeriodo(nuPeriodo);

		for (Map<Integer, String> mapa : lstFiltros) { 
			for (Map.Entry<Integer, String> fila : mapa.entrySet()) {
				Integer idSubFase = fila.getKey();
				String noNombre = fila.getValue();
 
				SelectItemDTO selector = new SelectItemDTO(idSubFase, noNombre);
				lstSelector.add(selector);
			}
		}

		return lstSelector;
	} 
	
	@GetMapping("/obtenerMatriz")
	@ResponseBody
	public MessageDTO obtenerMatriz(@RequestParam(name = "idSubFase", required = true) Integer idSubFase) throws Exception {
		MessageDTO msg = subFaseService.obtenerMatriz(idSubFase);
		return msg;
	}
	
	@GetMapping("/obtenerMatrizRestriccion")
	@ResponseBody
	public MessageDTO obtenerMatrizRestriccion(@RequestParam(name = "idSubFase", required = true) Integer idSubFase) throws Exception {
		MessageDTO msg = subFaseService.obtenerMatrizRestriccion(idSubFase);
		return msg;
	}
	 
	@GetMapping("/buscarPorId")
	@ResponseBody
	public Optional<SubFaseDTO> buscarPorId(@RequestParam(name = "id", required = false) Integer id, HttpServletRequest request) throws Exception {
		Optional<SubFaseDTO> subFase = subFaseService.buscarPorId(id);
		return subFase;
	}

	@PostMapping("/guardar")
	@ResponseBody
	public MessageDTO guardar(@RequestBody SubFaseDTO subFase) throws Exception {
		System.out.println(subFase.toString());
		return subFaseService.guardar(subFase);
	}

	@PutMapping("/actualizar")
	@ResponseBody
	public MessageDTO actualizar(@RequestBody SubFaseDTO subFase) throws Exception {
		return subFaseService.actualizar(subFase);
	}

	@DeleteMapping("/eliminar")
	@ResponseBody
	public MessageDTO eliminar(@RequestParam(name = "id") Integer id) throws Exception {
		return subFaseService.eliminar(id);
	}

	@PostMapping("/eliminar-multiple")
	@ResponseBody
	public MessageDTO eliminarMultiple(@RequestBody List<Integer> lstId) throws Exception {
		return subFaseService.eliminarMultiple(lstId);
	}
	 
}
