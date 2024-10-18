package pe.mil.fap.repository.administration.usp.inf;
 
import java.util.List;

import pe.mil.fap.entity.administration.CorEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface CorUSPRepository {


	List<CorEntity> listarCorPorIdDetalleCalificacion(Integer idDetalleCalificacion) throws RepositoryException;
	String guardar(CorEntity entity) throws RepositoryException;
}
