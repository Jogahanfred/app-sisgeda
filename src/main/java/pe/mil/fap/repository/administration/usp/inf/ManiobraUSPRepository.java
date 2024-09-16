package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.ManiobraEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface ManiobraUSPRepository {

	List<ManiobraEntity> listarManiobras() throws RepositoryException;
	List<ManiobraEntity> listarManiobrasPorIdOperacion(Integer idOperacion) throws RepositoryException;
	String guardar(ManiobraEntity entity) throws RepositoryException;
	String actualizar(ManiobraEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
