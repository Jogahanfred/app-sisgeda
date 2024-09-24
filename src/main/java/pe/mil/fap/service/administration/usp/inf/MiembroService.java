package pe.mil.fap.service.administration.usp.inf;

import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.service.exception.ServiceException; 

public interface MiembroService {

	List<MiembroDTO> listarMiembros(Integer nuPeriodo, String noRol) throws ServiceException;
	List<MiembroDTO> listarMiembrosACalificarPorPeriodo(Integer nuPeriodo, String noRol) throws ServiceException;
	Optional<MiembroDTO> buscarPorNsa(String coNsa, Integer nuPeriodo, String noRol) throws ServiceException;
	Optional<MiembroDTO> buscarPorId(Integer idMiembro) throws ServiceException;
	MessageDTO guardarInstructor(MiembroDTO dto) throws ServiceException;
	MessageDTO guardarAlumno(MiembroDTO dto) throws ServiceException;
	MessageDTO deshabilitar(Integer id) throws ServiceException;
	MessageDTO eliminar(Integer id) throws ServiceException; 
}
