package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.SubFaseEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface SubFaseUSPRepository {

	List<SubFaseEntity> listarSubFases() throws RepositoryException;
	List<SubFaseEntity> listarSubFasesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro, Integer idFase) throws RepositoryException;
	List<SubFaseEntity> listarSubFasesPorIdUnidad(Integer idUnidad) throws RepositoryException;
	List<SubFaseEntity> listarSubFasesPorIdFase(Integer idFase) throws RepositoryException;
	List<Map<Integer, String>> listarFiltroPeriodo(Integer nuPeriodo) throws RepositoryException;

	List<EjeXEntity> listarEjeX(Integer idSubFase) throws RepositoryException;
	List<EjeYEntity> listarEjeY(Integer idSubFase) throws RepositoryException;
	List<EjeInterseccionEntity> listarEjeInterseccion(Integer idSubFase) throws RepositoryException;
	
	Optional<SubFaseEntity> buscarPorId(Integer id) throws RepositoryException;
	String guardar(SubFaseEntity entity) throws RepositoryException;
	String actualizar(SubFaseEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
