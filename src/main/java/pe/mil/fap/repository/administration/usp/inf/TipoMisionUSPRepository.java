package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.TipoMisionEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface TipoMisionUSPRepository {

	List<TipoMisionEntity> listarTipoMisiones() throws RepositoryException;
	String guardar(TipoMisionEntity entity) throws RepositoryException;
	String actualizar(TipoMisionEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
