package pe.mil.fap.service.bussiness.usp.inf;
  
import java.util.List;

import pe.mil.fap.model.helpers.InscripcionMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest;
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.exception.ServiceException;

public interface CalificacionService {
 
	MessageDTO registrarTransaccion(RegistroCalificacionDTORequest dto) throws ServiceException;
	Boolean verificarInscripcionMision(Integer idMision, String lstIds) throws ServiceException; 
	List<InscripcionMisionDTO> verificarInscripcionSubFase(Integer idSubFase, String lstIds) throws ServiceException; 

	MessageDTO asignarInstructor(Integer idCalificador, Integer idCalificacion) throws ServiceException;

	MessageDTO obtenerMatrizACalificar(Integer idCalificado, String coNsaUsuarioLogeado) throws ServiceException;
	MessageDTO obtenerMatrizACalificarPorIdCalificacion(Integer idCalificado, Integer idCalificacion) throws ServiceException;
	
	MessageDTO calificarManiobra(Integer idManiobra, Integer idCalificacion, String coEstandarObtenido) throws ServiceException;
}
