package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.BancoManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.service.exception.ServiceException; 

public interface BancoManiobraService {

	List<BancoManiobraDTO> listarBancoManiobras() throws ServiceException;
	MessageDTO guardar(BancoManiobraDTO dto) throws ServiceException;
	MessageDTO actualizar(BancoManiobraDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
