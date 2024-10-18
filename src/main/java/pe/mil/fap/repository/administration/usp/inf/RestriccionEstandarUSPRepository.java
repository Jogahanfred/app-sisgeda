package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface RestriccionEstandarUSPRepository {

	List<RestriccionEstandarEntity> listarRestricciones(Integer idDetalleMision) throws RepositoryException;
	List<RestriccionEstandarEntity> listarRestriccionesPorIdDetalleCalificacion(Integer idDetalleCalificacion) throws RepositoryException;
	String guardar(RestriccionEstandarEntity entity) throws RepositoryException;
}
