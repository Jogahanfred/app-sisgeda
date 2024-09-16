package pe.mil.fap.repository.administration.usp.inf;

import java.util.List;

import pe.mil.fap.entity.administration.BancoSubFaseEntity; 
import pe.mil.fap.repository.exception.RepositoryException;

public interface BancoSubFaseUSPRepository {

	List<BancoSubFaseEntity> listarBancoSubFases() throws RepositoryException;
	String guardar(BancoSubFaseEntity entity) throws RepositoryException;
	String actualizar(BancoSubFaseEntity entity) throws RepositoryException;
	String eliminar(Integer id) throws RepositoryException;
	String eliminarMultiple(List<Integer> lstId) throws RepositoryException;
}
