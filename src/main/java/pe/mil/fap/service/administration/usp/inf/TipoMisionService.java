package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.TipoMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface TipoMisionService {

	List<TipoMisionDTO> listarTipoMisiones() throws ServiceException;
	MessageDTO guardar(TipoMisionDTO dto) throws ServiceException;
	MessageDTO actualizar(TipoMisionDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
