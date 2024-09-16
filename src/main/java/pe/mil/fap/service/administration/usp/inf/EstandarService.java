package pe.mil.fap.service.administration.usp.inf;

import java.util.List; 

import pe.mil.fap.model.administration.EstandarDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface EstandarService {

	List<EstandarDTO> listarEstandares() throws ServiceException; 
}
