package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.RestriccionEstandarDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException;

public interface RestriccionEstandarService {

	List<RestriccionEstandarDTO> listarRestricciones(Integer idDetalleMision) throws ServiceException;
	MessageDTO guardar(RestriccionEstandarDTO dto) throws ServiceException;
}
