package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.FlotaEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface FlotaUSPRepository {

	List<FlotaEntity> listarFlotas() throws RepositoryException;
	String guardar(FlotaEntity entity) throws RepositoryException;
	String actualizar(FlotaEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
