package pe.mil.fap.repository.bussiness.usp.inf;
  
import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.GrupoEntity;
import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface GrupoUSPRepository {

	List<GrupoEntity> listar(Integer nuPeriodo, Integer idUnidad) throws RepositoryException;
	List<GrupoMiembroEntity> listarDetalle(Integer idGrupo) throws RepositoryException;
	Optional<GrupoEntity> buscarId(Integer id) throws RepositoryException;
	String guardar(GrupoEntity entity, List<Integer> lstIdsMiembros) throws RepositoryException;

}
