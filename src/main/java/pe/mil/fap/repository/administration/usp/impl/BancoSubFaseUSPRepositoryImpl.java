package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.BancoSubFaseEntity; 
import pe.mil.fap.repository.administration.usp.inf.BancoSubFaseUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class BancoSubFaseUSPRepositoryImpl implements BancoSubFaseUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BancoSubFaseEntity> listarBancoSubFases() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoSubFase.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<BancoSubFaseEntity> lstBancoSubFases = new ArrayList<>();

			for (Object[] obj : results) {
				BancoSubFaseEntity banco = new BancoSubFaseEntity();

				banco.setIdBancoSubFase(Integer.parseInt(String.valueOf(obj[0]))); 
				banco.setNoNombre((String) obj[1]); 
				banco.setFlEstado(Integer.parseInt(String.valueOf(obj[2])));
				lstBancoSubFases.add(banco);

			}
			return lstBancoSubFases;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(BancoSubFaseEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoSubFase.insertar");
 
			spq.setParameter("P_NOMBRE", entity.getNoNombre());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(BancoSubFaseEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoSubFase.actualizar");

			spq.setParameter("P_ID_BANCO_SUB_FASE", entity.getIdBancoSubFase()); 
			spq.setParameter("P_NOMBRE", entity.getNoNombre()); 

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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoSubFase.eliminar");

			spq.setParameter("P_ID_BANCO_SUB_FASE", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoSubFase.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
