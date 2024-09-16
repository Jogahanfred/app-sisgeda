package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;

import pe.mil.fap.entity.administration.AeronaveEntity; 
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.exception.RepositoryException;

public interface AeronaveUSPRepository {

	DataTableDTO paginar(ParametroDataTableDTO parametro) throws RepositoryException;
	Optional<AeronaveEntity> buscarId(Integer id) throws RepositoryException;
	String guardar(AeronaveEntity entity) throws RepositoryException;
	String actualizar(AeronaveEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
