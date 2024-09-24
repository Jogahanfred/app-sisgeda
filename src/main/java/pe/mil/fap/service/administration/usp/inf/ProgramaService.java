package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
 
import pe.mil.fap.model.administration.ProgramaDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
import pe.mil.fap.service.exception.ServiceException; 

public interface ProgramaService {

	List<ProgramaInscritoDTOResponse> listarProgramasACalificarPorPeriodo(Integer nuPeriodo, String noTipoInstruccion, Integer idMiembro) throws ServiceException;
	List<ProgramaDTO> listarProgramas() throws ServiceException;
	List<ProgramaDTO> listarProgramasPorIdUnidad(Integer idUnidad) throws ServiceException;
	List<ProgramaDTO> listarProgramasPorIdEscuadron(Integer idEscuadron) throws ServiceException;
	MessageDTO guardar(ProgramaDTO dto) throws ServiceException;
	MessageDTO actualizar(ProgramaDTO dto) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException;
	MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException;
}
