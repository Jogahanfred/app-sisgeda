package pe.mil.fap.repository.bussiness.usp.impl;
 
import java.util.List;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.entity.bussiness.CalificacionEntity;   
import pe.mil.fap.repository.bussiness.usp.inf.CalificacionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class CalificacionUSPRepositoryImpl implements CalificacionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Integer insertarCabecera(CalificacionEntity calificacion) throws RepositoryException { 
		Integer idGenerado = 0;
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.insertar");

			spq.setParameter("P_ID_MISION", calificacion.getIdMision());
			spq.setParameter("P_ID_CALIFICADO", calificacion.getIdCalificado()); 

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_CALIFICACION"); 
			return (idGenerado == 0 || idGenerado == null) ? 0 : idGenerado; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar la cabecera");
		}
	}

	@Override
	public Boolean insertarDetalle(DetalleCalificacionEntity detalleCalificacion) throws RepositoryException { 
		Integer idGenerado = 0;		
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("detalleCalificacion.insertar");

			spq.setParameter("ID_CALIFICACION", detalleCalificacion.getIdCalificacion());
			spq.setParameter("ID_MANIOBRA", detalleCalificacion.getIdManiobra()); 

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_DETALLE_CALIFICACION");

			return (idGenerado == 0 || idGenerado == null) ? false : true;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar el detalle");
		}
	}

	@Override
	@Transactional
	public String guardarTransaccion(List<CalificacionEntity> lstCalificaciones) throws RepositoryException {
		try {
			for (CalificacionEntity calificacion : lstCalificaciones) {
				Integer idGenerado = this.insertarCabecera(calificacion);

				if (idGenerado != 0 ? true : false) {
					for (DetalleCalificacionEntity calificacionDetalle : calificacion.getLstDetalleCalificacion()) {
						calificacionDetalle.setIdCalificacion(idGenerado);
						this.insertarDetalle(calificacionDetalle);
					}
				}
			}
			return SeveridadEnum.SUCCESS.getValor() + "|Gracias ! ¡La calificacion ha sido registrada correctamente!";
		} catch (Exception exception) {
			throw new RepositoryException(exception.getMessage());
		}
	}

}
