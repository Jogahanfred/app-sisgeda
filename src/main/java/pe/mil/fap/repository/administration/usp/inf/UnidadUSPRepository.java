package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.UnidadEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface UnidadUSPRepository {

	List<UnidadEntity> listarUnidades() throws RepositoryException;
	List<UnidadEntity> listarUnidadesPorRector(Integer nuCodigoRector) throws RepositoryException;
	Optional<UnidadEntity> buscarId(Integer id) throws RepositoryException;
	String guardar(UnidadEntity entity) throws RepositoryException;
	String actualizar(UnidadEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException; 
}
