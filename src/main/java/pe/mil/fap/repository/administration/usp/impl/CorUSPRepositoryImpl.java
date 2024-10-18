package pe.mil.fap.repository.administration.usp.impl;
 

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.entity.administration.CorEntity; 
import pe.mil.fap.repository.administration.usp.inf.CorUSPRepository; 
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class CorUSPRepositoryImpl implements CorUSPRepository{

	@PersistenceContext
	private EntityManager entityManager;
 
	@Override
	public String guardar(CorEntity entity) throws RepositoryException {  
		try {
			System.out.println(entity.toString());
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("cor.insertar");

			spq.setParameter("P_ID_DETALLE_CALIFICACION", entity.getIdDetalleCalificacion());
			spq.setParameter("P_TX_CAUSA", entity.getTxCausa());
			spq.setParameter("P_TX_OBSERVACION", entity.getTxObservacion()); 
			spq.setParameter("P_TX_RECOMENDACION", entity.getTxRecomendacion()); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar la calificación");
		}
	}

}
