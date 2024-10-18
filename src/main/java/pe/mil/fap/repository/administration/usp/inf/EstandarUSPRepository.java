package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import pe.mil.fap.entity.administration.EstandarEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface EstandarUSPRepository {

	List<EstandarEntity> listarEstandares() throws RepositoryException;  
	Optional<EstandarEntity> buscarPorCodigo(String coCodigo) throws RepositoryException;  
}
