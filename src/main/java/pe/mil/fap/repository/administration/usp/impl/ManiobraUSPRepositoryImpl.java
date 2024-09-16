package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.ManiobraEntity; 
import pe.mil.fap.repository.administration.usp.inf.ManiobraUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class ManiobraUSPRepositoryImpl implements ManiobraUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ManiobraEntity> listarManiobras() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<ManiobraEntity> lstManiobra = new ArrayList<>();

			for (Object[] obj : results) {
				ManiobraEntity maniobra = new ManiobraEntity();

				maniobra.setIdManiobra(Integer.parseInt(String.valueOf(obj[0])));
				maniobra.setIdBancoManiobra(Integer.parseInt(String.valueOf(obj[1])));
				maniobra.setIdOperacion(Integer.parseInt(String.valueOf(obj[2])));
				maniobra.setFlBloqueado(Integer.parseInt(String.valueOf(obj[3])));
				maniobra.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				maniobra.setTxDescripcionOperacion((String) obj[5]);
				maniobra.setTxDescripcionBancoManiobra((String) obj[6]);
				maniobra.setIdEscuadron(Integer.parseInt(String.valueOf(obj[7])));
				maniobra.setTxDescripcionEscuadron((String) obj[8]);
				lstManiobra.add(maniobra);

			}
			return lstManiobra;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManiobraEntity> listarManiobrasPorIdOperacion(Integer idOperacion) throws RepositoryException{
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.listarPorOperacion");
			spq.setParameter("P_ID_OPERACION", idOperacion);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<ManiobraEntity> lstManiobra = new ArrayList<>();

			for (Object[] obj : results) {
				ManiobraEntity maniobra = new ManiobraEntity();

				maniobra.setIdManiobra(Integer.parseInt(String.valueOf(obj[0])));
				maniobra.setIdBancoManiobra(Integer.parseInt(String.valueOf(obj[1])));
				maniobra.setIdOperacion(Integer.parseInt(String.valueOf(obj[2])));
				maniobra.setFlBloqueado(Integer.parseInt(String.valueOf(obj[3])));
				maniobra.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				maniobra.setTxDescripcionOperacion((String) obj[5]);
				maniobra.setTxDescripcionBancoManiobra((String) obj[6]);
				maniobra.setIdEscuadron(Integer.parseInt(String.valueOf(obj[7])));
				maniobra.setTxDescripcionEscuadron((String) obj[8]);
				lstManiobra.add(maniobra);

			}
			return lstManiobra;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public String guardar(ManiobraEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.insertar");

			spq.setParameter("P_ID_OPERACION", entity.getIdOperacion()); 
			spq.setParameter("P_ID_BANCO_MANIOBRA", entity.getIdBancoManiobra());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(ManiobraEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.actualizar");

			spq.setParameter("P_ID_MANIOBRA", entity.getIdManiobra());
			spq.setParameter("P_ID_BANCO_MANIOBRA", entity.getIdBancoManiobra()); 
			spq.setParameter("P_ID_OPERACION", entity.getIdOperacion());
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.eliminar");

			spq.setParameter("P_ID_MANIOBRA", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("maniobra.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
