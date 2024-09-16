package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.FaseEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface FaseUSPRepository {

	List<FaseEntity> listarFases() throws RepositoryException;
	List<FaseEntity> listarFasesPorIdUnidad(Integer idUnidad) throws RepositoryException;
	List<FaseEntity> listarFasesPorIdPrograma(Integer idPrograma) throws RepositoryException;
	String guardar(FaseEntity entity) throws RepositoryException;
	String actualizar(FaseEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
