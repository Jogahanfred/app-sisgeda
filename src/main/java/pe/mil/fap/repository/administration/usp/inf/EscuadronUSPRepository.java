package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.EscuadronEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface EscuadronUSPRepository {

	List<EscuadronEntity> listarEscuadrones() throws RepositoryException;
	List<EscuadronEntity> listarEscuadronesPorIdUnidadRector(Integer idUnidad) throws RepositoryException;
	List<EscuadronEntity> listarEscuadronesPorIdUnidad(Integer idUnidad) throws RepositoryException;
	Optional<EscuadronEntity> buscarId(Integer id) throws RepositoryException;
	String guardar(EscuadronEntity entity) throws RepositoryException;
	String actualizar(EscuadronEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException; 
}
