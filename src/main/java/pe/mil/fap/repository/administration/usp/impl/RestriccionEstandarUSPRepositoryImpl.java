package pe.mil.fap.repository.administration.usp.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery; 
import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.repository.administration.usp.inf.RestriccionEstandarUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class RestriccionEstandarUSPRepositoryImpl implements RestriccionEstandarUSPRepository{

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<RestriccionEstandarEntity> listarRestricciones(Integer idDetalleMision) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("restriccionEstandar.listar");
			spq.setParameter("P_ID_DETALLE_MISION", idDetalleMision);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<RestriccionEstandarEntity> lstRestricciones = new ArrayList<>();

			for (Object[] obj : results) {
				RestriccionEstandarEntity restriccion = new RestriccionEstandarEntity();

				restriccion.setIdRestriccionEstandar(Integer.parseInt(String.valueOf(obj[0])));
				restriccion.setIdDetalleMision(Integer.parseInt(String.valueOf(obj[1])));
				restriccion.setIdEstandar(Integer.parseInt(String.valueOf(obj[2])));
				restriccion.setTxMensaje((String) obj[3]);
				restriccion.setTxDescripcionEstandar((String) obj[4]); 
				restriccion.setNuNivelEstandar(Integer.parseInt(String.valueOf(obj[5])));
				
				lstRestricciones.add(restriccion);

			}
			return lstRestricciones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RestriccionEstandarEntity> listarRestriccionesPorIdDetalleCalificacion(Integer idDetalleCalificacion) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("restriccionEstandar.listarPorIdDetalleCalificacion");
			spq.setParameter("P_ID_DETALLE_CALIFICACION", idDetalleCalificacion);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<RestriccionEstandarEntity> lstRestricciones = new ArrayList<>();

			for (Object[] obj : results) {
				RestriccionEstandarEntity restriccion = new RestriccionEstandarEntity();

				restriccion.setIdRestriccionEstandar(Integer.parseInt(String.valueOf(obj[0])));
				restriccion.setIdDetalleMision(Integer.parseInt(String.valueOf(obj[1])));
				restriccion.setIdEstandar(Integer.parseInt(String.valueOf(obj[2])));
				restriccion.setTxMensaje((String) obj[3]);
				restriccion.setTxDescripcionEstandar((String) obj[4]); 
				restriccion.setNuNivelEstandar(Integer.parseInt(String.valueOf(obj[5])));
				
				lstRestricciones.add(restriccion);

			}
			return lstRestricciones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(RestriccionEstandarEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("restriccionEstandar.insertar");

			spq.setParameter("P_ID_DETALLE_MISION", entity.getIdDetalleMision());
			spq.setParameter("P_ID_ESTANDAR", entity.getIdEstandar());
			spq.setParameter("P_TX_MENSAJE", entity.getTxMensaje()); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar restricción estándar");
		}
	}

}
