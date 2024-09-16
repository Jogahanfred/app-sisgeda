package pe.mil.fap.service.bussiness.usp.inf;
  
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroMisionDTORequest;
import pe.mil.fap.service.exception.ServiceException;

public interface MisionService {
 
	MessageDTO registrarTransaccion(RegistroMisionDTORequest dto) throws ServiceException;
	MessageDTO actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar) throws ServiceException;
}
