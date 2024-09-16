package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.BancoFaseEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface BancoFaseUSPRepository {

	List<BancoFaseEntity> listarBancoFases() throws RepositoryException;
	String guardar(BancoFaseEntity entity) throws RepositoryException;
	String actualizar(BancoFaseEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
