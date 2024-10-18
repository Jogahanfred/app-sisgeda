package pe.mil.fap.repository.bussiness.usp.inf;
  
import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.entity.helpers.CalificarMisionEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface MisionUSPRepository {
	
	List<CalificarMisionEntity> listarMisionesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro, Integer idSubFase) throws RepositoryException;
	List<MisionEntity> listarMisionesPorIdSubFase(Integer idSubFase) throws RepositoryException;
	List<DetalleMisionEntity> listarDetalleMisionPorIdMision(Integer idMision) throws RepositoryException;
	
	Optional<MisionEntity> buscarId(Integer id) throws RepositoryException;
	
	Integer insertarCabecera(MisionEntity mision) throws RepositoryException;
	Boolean insertarDetalle(DetalleMisionEntity detalleMision) throws RepositoryException;
	
	String guardarTransaccion(List<MisionEntity> lstMisiones) throws RepositoryException;
	String actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar) throws RepositoryException;
}
