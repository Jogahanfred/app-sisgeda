package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.EstandarEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface EstandarUSPRepository {

	List<EstandarEntity> listarEstandares() throws RepositoryException;  
}
