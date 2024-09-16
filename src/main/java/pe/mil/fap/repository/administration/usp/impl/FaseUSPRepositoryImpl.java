package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.FaseEntity; 
import pe.mil.fap.repository.administration.usp.inf.FaseUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class FaseUSPRepositoryImpl implements FaseUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<FaseEntity> listarFases() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<FaseEntity> lstFase = new ArrayList<>();

			for (Object[] obj : results) {
				FaseEntity fase = new FaseEntity();

				fase.setIdFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalSubFase(Integer.parseInt(String.valueOf(obj[4])));
				fase.setTxFinalidad((String) obj[5]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[7])));
				fase.setTxDescripcionFase((String) obj[8]);
				fase.setTxDescripcionPrograma((String) obj[9]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[10])));
				lstFase.add(fase);

			}
			return lstFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FaseEntity> listarFasesPorIdUnidad(Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.listarPorUnidad");
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<FaseEntity> lstFase = new ArrayList<>();

			for (Object[] obj : results) {
				FaseEntity fase = new FaseEntity();

				fase.setIdFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalSubFase(Integer.parseInt(String.valueOf(obj[4])));
				fase.setTxFinalidad((String) obj[5]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[7])));
				fase.setTxDescripcionFase((String) obj[8]);
				fase.setTxDescripcionPrograma((String) obj[9]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[10])));
				lstFase.add(fase);

			}
			return lstFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FaseEntity> listarFasesPorIdPrograma(Integer idPrograma) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.listarPorPrograma");
			spq.setParameter("P_ID_PROGRAMA", idPrograma);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<FaseEntity> lstFase = new ArrayList<>();

			for (Object[] obj : results) {
				FaseEntity fase = new FaseEntity();

				fase.setIdFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalSubFase(Integer.parseInt(String.valueOf(obj[4])));
				fase.setTxFinalidad((String) obj[5]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[7])));
				fase.setTxDescripcionFase((String) obj[8]);
				fase.setTxDescripcionPrograma((String) obj[9]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[10])));
				lstFase.add(fase);

			}
			return lstFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public String guardar(FaseEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.insertar");

			spq.setParameter("P_ID_BANCO_FASE", entity.getIdBancoFase());
			spq.setParameter("P_ID_PROGRAMA", entity.getIdPrograma());
			spq.setParameter("P_TOTAL_HORA", entity.getNuTotalHora());
			spq.setParameter("P_TOTAL_SUB_FASE", entity.getNuTotalSubFase());
			spq.setParameter("P_FINALIDAD", entity.getTxFinalidad());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(FaseEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.actualizar");

			spq.setParameter("P_ID_FASE", entity.getIdFase());
			spq.setParameter("P_TOTAL_HORA", entity.getNuTotalHora());
			spq.setParameter("P_TOTAL_SUB_FASE", entity.getNuTotalSubFase());
			spq.setParameter("P_FINALIDAD", entity.getTxFinalidad());
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.eliminar");

			spq.setParameter("P_ID_FASE", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("fase.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
