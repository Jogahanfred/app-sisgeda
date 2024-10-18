package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional; 
import pe.mil.fap.entity.administration.ProgramaEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface ProgramaUSPRepository {

	List<ProgramaEntity> listarProgramasACalificarPorPeriodo(Integer nuPeriodo, String noTipoInstruccion, Integer idMiembro) throws RepositoryException;
	List<ProgramaEntity> listarProgramas() throws RepositoryException;
	List<ProgramaEntity> listarProgramasPorIdUnidad(Integer idUnidad) throws RepositoryException;
	List<ProgramaEntity> listarProgramasPorIdEscuadron(Integer idEscuadron) throws RepositoryException;
	Optional<ProgramaEntity> buscarId(Integer id) throws RepositoryException;
	String guardar(ProgramaEntity entity) throws RepositoryException;
	String actualizar(ProgramaEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
