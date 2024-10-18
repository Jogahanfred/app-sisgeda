package pe.mil.fap.repository.administration.usp.inf;
 
import pe.mil.fap.entity.administration.CorEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface CorUSPRepository {

	String guardar(CorEntity entity) throws RepositoryException;
}
