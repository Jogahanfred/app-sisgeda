package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.ProgramaEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface ProgramaUSPRepository {

	List<ProgramaEntity> listarProgramas() throws RepositoryException;
	List<ProgramaEntity> listarProgramasPorIdUnidad(Integer idUnidad) throws RepositoryException;
	List<ProgramaEntity> listarProgramasPorIdEscuadron(Integer idEscuadron) throws RepositoryException;
	String guardar(ProgramaEntity entity) throws RepositoryException;
	String actualizar(ProgramaEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
