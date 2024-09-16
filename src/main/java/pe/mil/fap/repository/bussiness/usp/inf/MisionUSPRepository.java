package pe.mil.fap.repository.bussiness.usp.inf;
  
import java.util.List; 

import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface MisionUSPRepository {
 
	Integer insertarCabecera(MisionEntity mision) throws RepositoryException;
	Boolean insertarDetalle(DetalleMisionEntity detalleMision) throws RepositoryException;
	
	String guardarTransaccion(List<MisionEntity> lstMisiones) throws RepositoryException;
	String actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar) throws RepositoryException;
}
