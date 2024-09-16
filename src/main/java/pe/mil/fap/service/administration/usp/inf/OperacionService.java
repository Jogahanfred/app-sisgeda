package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import pe.mil.fap.model.administration.OperacionDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.OperacionDTOResponse;
import pe.mil.fap.service.exception.ServiceException; 

public interface OperacionService {

	List<OperacionDTO> listarOperaciones() throws ServiceException;
	List<OperacionDTO> listarOperacionesPorIdEscuadron(Integer idEscuadron) throws ServiceException;
	List<OperacionDTOResponse> listarOperacionesDetalleManiobra(List<Integer> lstIdOperaciones) throws ServiceException;
	Optional<OperacionDTO> buscarPorId(Integer id) throws ServiceException;
	MessageDTO guardar(OperacionDTO dto) throws ServiceException;
	MessageDTO actualizar(OperacionDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
