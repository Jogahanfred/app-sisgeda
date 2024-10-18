package pe.mil.fap.service.administration.usp.inf;
 

import pe.mil.fap.model.administration.CorDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException;

public interface CorService {

	MessageDTO guardar(CorDTO dto) throws ServiceException;
}
