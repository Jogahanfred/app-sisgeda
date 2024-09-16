package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import pe.mil.fap.model.administration.AeronaveDTO; 
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface AeronaveService {

	DataTableDTO paginar(ParametroDataTableDTO parametro) throws ServiceException; 
	Optional<AeronaveDTO> buscarPorId(Integer id) throws ServiceException;
	MessageDTO guardar(AeronaveDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO actualizar(AeronaveDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
