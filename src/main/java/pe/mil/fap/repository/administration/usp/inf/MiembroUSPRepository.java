package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.MiembroEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface MiembroUSPRepository {

	List<MiembroEntity> listarMiembros(Integer nuPeriodo, String noRol) throws RepositoryException;
	List<MiembroEntity> listarMiembrosACalificarPorPeriodo(Integer idEscuadron, Integer nuPeriodo, String noTipoInstruccion) throws RepositoryException;
	Optional<MiembroEntity> buscarPorNsaPorRolPeriodo(String coNsa, Integer nuPeriodo, String noRol) throws RepositoryException;
	Optional<MiembroEntity> buscarPorNsa(String coNsa) throws RepositoryException;
	Optional<MiembroEntity> buscarPorId(Integer idMiembro) throws RepositoryException;
	String guardarInstructor(MiembroEntity entity) throws RepositoryException;			
	String guardarAlumno(MiembroEntity entity) throws RepositoryException;			
	String deshabilitar(Integer id) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException; 
}
