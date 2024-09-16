package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.FaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface FaseService {

	List<FaseDTO> listarFases() throws ServiceException;
	List<FaseDTO> listarFasesPorIdUnidad(Integer idUnidad) throws ServiceException;
	List<FaseDTO> listarFasesPorIdPrograma(Integer idPrograma) throws ServiceException;
	MessageDTO guardar(FaseDTO dto) throws ServiceException;
	MessageDTO actualizar(FaseDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
