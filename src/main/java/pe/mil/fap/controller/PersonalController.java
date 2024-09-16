package pe.mil.fap.controller;
  
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.administration.EscuadronDTO;
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.administration.SubFaseDTO;
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.model.helpers.SelectItemDTO;
import pe.mil.fap.model.security.UsuarioDTO;
import pe.mil.fap.service.administration.orm.inf.PersonalORMService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.administration.usp.inf.UnidadService;

@Controller
@RequestMapping("/personal")
public class PersonalController {

	private final PersonalORMService personaORMService;
	private final PersonalService personalService;
	

	public PersonalController(final PersonalORMService personaORMService, final PersonalService personalService) {
		super();
		this.personaORMService = personaORMService;
		this.personalService = personalService;
	}

	@GetMapping({"/",""})
	public String index(Model model) throws Exception{ 
		return "apps/gtn-personal";
	}
 
	@PostMapping("/paginar")
	@ResponseBody
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws Exception{
		DataTableDTO dataTable =  personalService.paginar(parametro);
		return dataTable;
	}

	@GetMapping("/buscarPorNsa")
	@ResponseBody
	public Optional<PersonalDTO> buscarPorNsa(@RequestParam(name = "nsa", required = true) String nsa, HttpServletRequest request) throws Exception {
		Optional<PersonalDTO> personal = personalService.buscarPorNsa(nsa);
		return personal;
	}

	@PostMapping("/actualizarFotografia")
	@ResponseBody
	public MessageDTO actualizar(@RequestParam(name = "archivo", required = false) MultipartFile archivo, @RequestParam("personal") String personalJson) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		PersonalDTO personalDTO = objectMapper.readValue(personalJson, PersonalDTO.class);

		return personalService.actualizarFotografia(personalDTO, archivo);
	}
	
	@GetMapping("/executeXLSXToDTO")
	public ResponseEntity<?> savePersonalXLSXToDTO() throws Exception {
		try {

			Boolean resPersonaDTO = personaORMService.savePersonalXLSXToDTO();
			return ResponseEntity.status(HttpStatus.CREATED).body("Okey - " + resPersonaDTO);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
}
