package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface BancoFaseService {

	List<BancoFaseDTO> listarBancoFases() throws ServiceException;
	MessageDTO guardar(BancoFaseDTO dto) throws ServiceException;
	MessageDTO actualizar(BancoFaseDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
