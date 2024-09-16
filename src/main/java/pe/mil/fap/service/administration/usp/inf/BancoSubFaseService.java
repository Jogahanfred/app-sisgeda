package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.BancoSubFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface BancoSubFaseService {

	List<BancoSubFaseDTO> listarBancoSubFases() throws ServiceException;
	MessageDTO guardar(BancoSubFaseDTO dto) throws ServiceException;
	MessageDTO actualizar(BancoSubFaseDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
