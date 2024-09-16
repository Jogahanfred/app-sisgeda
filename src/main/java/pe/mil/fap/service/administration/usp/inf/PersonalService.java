package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
 
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.service.exception.ServiceException;

public interface PersonalService {
	
	DataTableDTO paginar(ParametroDataTableDTO parametro) throws ServiceException; 
	List<PersonalDTO> listar() throws ServiceException;
	List<PersonalDTO> listarPorSiglaUnidad(String sigla) throws ServiceException;
	Optional<PersonalDTO> buscarPorNsa(String nsa) throws ServiceException;
	MessageDTO actualizarFotografia(PersonalDTO dto, MultipartFile archivo) throws ServiceException;

}
