package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.BancoFaseEntity; 
import pe.mil.fap.repository.administration.usp.inf.BancoFaseUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class BancoFaseUSPRepositoryImpl implements BancoFaseUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BancoFaseEntity> listarBancoFases() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoFase.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<BancoFaseEntity> lstBancoFases = new ArrayList<>();

			for (Object[] obj : results) {
				BancoFaseEntity banco = new BancoFaseEntity();

				banco.setIdBancoFase(Integer.parseInt(String.valueOf(obj[0]))); 
				banco.setNoNombre((String) obj[1]); 
				banco.setFlEstado(Integer.parseInt(String.valueOf(obj[2])));
				lstBancoFases.add(banco);

			}
			return lstBancoFases;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(BancoFaseEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoFase.insertar");
 
			spq.setParameter("P_NOMBRE", entity.getNoNombre()); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(BancoFaseEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoFase.actualizar");

			spq.setParameter("P_ID_BANCO_FASE", entity.getIdBancoFase()); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoFase.eliminar");

			spq.setParameter("P_ID_BANCO_FASE", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoFase.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
