package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.FlotaDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface FlotaService {

	List<FlotaDTO> listarFlotas() throws ServiceException;
	MessageDTO guardar(FlotaDTO dto) throws ServiceException;
	MessageDTO actualizar(FlotaDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
