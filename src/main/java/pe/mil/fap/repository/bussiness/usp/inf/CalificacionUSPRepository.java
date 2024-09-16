package pe.mil.fap.repository.bussiness.usp.inf;
  
import java.util.List; 

import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.entity.bussiness.CalificacionEntity;
import pe.mil.fap.repository.exception.RepositoryException;

public interface CalificacionUSPRepository {
 
	Integer insertarCabecera(CalificacionEntity mision) throws RepositoryException;
	Boolean insertarDetalle(DetalleCalificacionEntity detalleCalificacion) throws RepositoryException;
	
	String guardarTransaccion(List<CalificacionEntity> lstCalificaciones) throws RepositoryException;
}
