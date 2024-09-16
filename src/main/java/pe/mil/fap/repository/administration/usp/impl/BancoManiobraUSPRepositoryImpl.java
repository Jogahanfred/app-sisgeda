package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.BancoManiobraEntity; 
import pe.mil.fap.repository.administration.usp.inf.BancoManiobraUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class BancoManiobraUSPRepositoryImpl implements BancoManiobraUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<BancoManiobraEntity> listarBancoManiobras() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoManiobra.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<BancoManiobraEntity> lstBancoManiobras = new ArrayList<>();

			for (Object[] obj : results) {
				BancoManiobraEntity banco = new BancoManiobraEntity();

				banco.setIdBancoManiobra(Integer.parseInt(String.valueOf(obj[0]))); 
				banco.setNoNombre((String) obj[1]); 
				banco.setFlEstado(Integer.parseInt(String.valueOf(obj[2])));
				lstBancoManiobras.add(banco);

			}
			return lstBancoManiobras;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(BancoManiobraEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoManiobra.insertar");
 
			spq.setParameter("P_NOMBRE", entity.getNoNombre());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(BancoManiobraEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoManiobra.actualizar");

			spq.setParameter("P_ID_BANCO_MANIOBRA", entity.getIdBancoManiobra()); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoManiobra.eliminar");

			spq.setParameter("P_ID_BANCO_MANIOBRA", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("bancoManiobra.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
