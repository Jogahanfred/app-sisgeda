package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import pe.mil.fap.model.administration.EscuadronDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface EscuadronService {

	List<EscuadronDTO> listarEscuadrones() throws ServiceException; 
	List<EscuadronDTO> listarEscuadronesPorIdUnidadRector(Integer idUnidad) throws ServiceException;
	List<EscuadronDTO> listarEscuadronesPorIdUnidad(Integer idUnidad) throws ServiceException; 
	Optional<EscuadronDTO> buscarId(Integer id) throws ServiceException;
	MessageDTO guardar(EscuadronDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO actualizar(EscuadronDTO dto, MultipartFile archivo) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException; 
}
