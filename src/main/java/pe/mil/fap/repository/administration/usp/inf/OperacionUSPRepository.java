package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import pe.mil.fap.entity.administration.OperacionEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface OperacionUSPRepository {

	List<OperacionEntity> listarOperaciones() throws RepositoryException;
	List<OperacionEntity> listarOperacionesPorIdEscuadron(Integer idEscuadron) throws RepositoryException;
	Optional<OperacionEntity> buscarPorId(Integer id) throws RepositoryException;
	String guardar(OperacionEntity entity) throws RepositoryException;
	String actualizar(OperacionEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
