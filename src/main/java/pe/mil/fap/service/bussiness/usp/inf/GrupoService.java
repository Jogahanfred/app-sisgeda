package pe.mil.fap.service.bussiness.usp.inf;
  
import java.util.List;
 
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.bussiness.GrupoMiembroDTO;
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.service.exception.ServiceException; 

public interface GrupoService {
 
	List<GrupoDTO> listar(Integer nuPeriodo, Integer idUnidad) throws ServiceException;
	List<GrupoMiembroDTO> listarDetalle(Integer idGrupo) throws ServiceException;
	MessageDTO guardar(GrupoDTO dto) throws ServiceException;
}
