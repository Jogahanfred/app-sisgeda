package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import pe.mil.fap.model.administration.SubFaseDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SubFaseInscritoDTOResponse;
import pe.mil.fap.service.exception.ServiceException; 

public interface SubFaseService {

	List<SubFaseDTO> listarSubFases() throws ServiceException;
	List<SubFaseInscritoDTOResponse> listarSubFasesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro, Integer idFase) throws ServiceException;
	List<SubFaseDTO> listarSubFasesPorIdUnidad(Integer idUnidad) throws ServiceException;
	List<SubFaseDTO> listarSubFasesPorIdFase(Integer idFase) throws ServiceException;
	List<Map<Integer, String>> listarFiltroPeriodo(Integer nuPeriodo) throws ServiceException;

	MessageDTO obtenerMatriz(Integer id) throws ServiceException;
	MessageDTO obtenerMatrizRestriccion(Integer id) throws ServiceException;

	Optional<SubFaseDTO> buscarPorId(Integer id) throws ServiceException;
	MessageDTO guardar(SubFaseDTO dto) throws ServiceException;
	MessageDTO actualizar(SubFaseDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
	
}
