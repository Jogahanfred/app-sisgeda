package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.FlotaEntity; 
import pe.mil.fap.repository.administration.usp.inf.FlotaUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class FlotaUSPRepositoryImpl implements FlotaUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;
 
	@SuppressWarnings("unchecked")
	@Override
	public List<FlotaEntity> listarFlotas() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("flota.listar");
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<FlotaEntity> lstAeronave = new ArrayList<>();

			for (Object[] obj : results) {
				FlotaEntity flota = new FlotaEntity();

				flota.setIdFlota(Integer.parseInt(String.valueOf(obj[0])));
				flota.setNoTipoFlota((String) obj[1]);
				flota.setCoCodigo((String) obj[2]);
				flota.setNoNombre((String) obj[3]);
				flota.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				flota.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				lstAeronave.add(flota);

			}
			return lstAeronave;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(FlotaEntity entity) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("flota.insertar");

			spq.setParameter("P_TIPO_FLOTA", entity.getNoTipoFlota());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {  
			throw new RepositoryException(e);
		}
	} 

	@Override
	public String actualizar(FlotaEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("flota.actualizar");

			spq.setParameter("P_ID_FLOTA", entity.getIdFlota());
			spq.setParameter("P_TIPO_FLOTA", entity.getNoTipoFlota());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_BLOQUEADO", entity.getFlBloqueado());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) { 
			throw new RepositoryException(e);
		}
	}

	@Override
	public String eliminar(Integer id) throws RepositoryException {	 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("flota.eliminar");

			spq.setParameter("P_ID_FLOTA", id); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) { 
			throw new RepositoryException(e);
		}
	}

	@Override
	public String eliminarMultiple(List<Integer> lstId) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("flota.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje;  
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
