package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import pe.mil.fap.model.administration.EstandarDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface EstandarService {

	List<EstandarDTO> listarEstandares() throws ServiceException; 
	Optional<EstandarDTO> buscarPorCodigo(String coCodigo) throws ServiceException;
}
