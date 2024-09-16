package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface UnidadService {

	List<UnidadDTO> listarUnidades() throws ServiceException;
	List<UnidadDTO> listarUnidadesPorRector(Integer nuCodigoRector) throws ServiceException;
	Optional<UnidadDTO> buscarId(Integer id) throws ServiceException;
	MessageDTO guardar(UnidadDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO actualizar(UnidadDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException; 
}
