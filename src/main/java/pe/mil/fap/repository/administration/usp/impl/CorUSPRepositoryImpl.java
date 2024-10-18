package pe.mil.fap.repository.administration.usp.impl;
 

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.entity.administration.CorEntity;
import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
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

	@Override
	public List<CorEntity> listarCorPorIdDetalleCalificacion(Integer idDetalleCalificacion) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("cor.listar");
			spq.setParameter("P_ID_DETALLE_CALIFICACION", idDetalleCalificacion);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<CorEntity> lstCOR = new ArrayList<>();

			for (Object[] obj : results) {
				CorEntity cor = new CorEntity();

				cor.setIdCor(Integer.parseInt(String.valueOf(obj[0])));
				cor.setIdDetalleCalificacion(Integer.parseInt(String.valueOf(obj[1])));
				cor.setTxCausa((String) obj[2]);
				cor.setTxObservacion((String) obj[3]);
				cor.setTxRecomendacion((String) obj[4]); 
				cor.setFeRegistro(String.valueOf(obj[5]));
				cor.setFeActualizacion(String.valueOf(obj[6]));
				
				lstCOR.add(cor);

			}
			return lstCOR;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
