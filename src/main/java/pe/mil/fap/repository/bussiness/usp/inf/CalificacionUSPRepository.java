package pe.mil.fap.repository.bussiness.usp.inf;
   
import java.util.List;
 
import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionACalificarEntity; 
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.entity.bussiness.CalificacionEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface CalificacionUSPRepository {
 
	Integer insertarCabecera(CalificacionEntity mision) throws RepositoryException;
	Boolean insertarDetalle(DetalleCalificacionEntity detalleCalificacion) throws RepositoryException;
	String guardarTransaccion(List<CalificacionEntity> lstCalificaciones, Boolean registroGrupal) throws RepositoryException;
	Integer verificarInscripcionMision(Integer idMision, String lstIds) throws RepositoryException;
	List<InscripcionMisionEntity> verificarInscripcionSubFase(Integer idSubFase, String lstIds) throws RepositoryException;

	String asignarInstructor(Integer idCalificador, Integer idCalificacion) throws RepositoryException;

	List<EjeXEntity> listarEjeX(Integer idCalificado) throws RepositoryException;
	List<EjeYEntity> listarEjeY(Integer idCalificado) throws RepositoryException;
	List<EjeInterseccionACalificarEntity> listarEjeInterseccionACalificar(Integer idCalificado) throws RepositoryException;
	

	List<EjeXEntity> listarEjeXPorIdCalificacion(Integer idCalificado, Integer idCalificacion) throws RepositoryException;
	List<EjeInterseccionACalificarEntity> listarEjeInterseccionACalificarPorIdCalificacion(Integer idCalificado, Integer idCalificacion) throws RepositoryException;
	
	String calificarManiobra(Integer idManiobra, Integer idCalificacion, Integer idEstandarObtenido) throws RepositoryException;
}
