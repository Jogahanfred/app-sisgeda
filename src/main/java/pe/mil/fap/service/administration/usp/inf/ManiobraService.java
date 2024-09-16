package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.ManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface ManiobraService {

	List<ManiobraDTO> listarManiobras() throws ServiceException;
	List<ManiobraDTO> listarManiobrasPorIdOperacion(Integer idOperacion) throws ServiceException;
	MessageDTO guardar(ManiobraDTO dto) throws ServiceException;
	MessageDTO actualizar(ManiobraDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
