package pe.mil.fap.service.administration.usp.inf;
 

import java.util.List;
  
import pe.mil.fap.model.administration.CorDTO; 
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.service.exception.ServiceException;

public interface CorService {

	List<CorDTO> listarCorPorIdDetalleCalificacion(Integer idDetalleCalificacion) throws ServiceException;
	MessageDTO guardar(CorDTO dto) throws ServiceException;
}
