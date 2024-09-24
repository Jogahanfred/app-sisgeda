package pe.mil.fap.service.bussiness.usp.inf;
  
import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.model.bussiness.MisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroMisionDTORequest;
import pe.mil.fap.service.exception.ServiceException;

public interface MisionService {
 
	List<MisionDTO> listarMisionesPorIdSubFase(Integer idSubFase) throws ServiceException;
    Optional<MisionDTO> buscarId(Integer id) throws ServiceException;
	MessageDTO registrarTransaccion(RegistroMisionDTORequest dto) throws ServiceException;
	MessageDTO actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar) throws ServiceException;
}
