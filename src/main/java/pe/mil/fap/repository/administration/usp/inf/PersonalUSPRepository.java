package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;
import java.util.Optional;
 
import pe.mil.fap.entity.administration.PersonalEntity;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.exception.RepositoryException;

public interface PersonalUSPRepository {
	
	DataTableDTO paginar(ParametroDataTableDTO parametro) throws RepositoryException;
	List<PersonalEntity> listar() throws RepositoryException;
	Optional<PersonalEntity> buscarPorNsa(String nsa) throws RepositoryException;
	String actualizarFotografia(PersonalEntity entity) throws RepositoryException;

}
