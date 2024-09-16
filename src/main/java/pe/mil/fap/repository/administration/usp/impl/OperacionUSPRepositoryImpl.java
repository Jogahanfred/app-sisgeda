package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.OperacionEntity; 
import pe.mil.fap.repository.administration.usp.inf.OperacionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class OperacionUSPRepositoryImpl implements OperacionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<OperacionEntity> listarOperaciones() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<OperacionEntity> lstOperacion = new ArrayList<>();

			for (Object[] obj : results) {
				OperacionEntity operacion = new OperacionEntity();

				operacion.setIdOperacion(Integer.parseInt(String.valueOf(obj[0])));
				operacion.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				operacion.setNoNombre((String) obj[2]);
				operacion.setFlBloqueado(Integer.parseInt(String.valueOf(obj[3])));
				operacion.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				operacion.setTxDescripcionEscuadron((String) obj[5]);
				lstOperacion.add(operacion);

			}
			return lstOperacion;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperacionEntity> listarOperacionesPorIdEscuadron(Integer idEscuadron) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.listarPorIdEscuadron");
			spq.setParameter("P_ID_ESCUADRON", idEscuadron);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<OperacionEntity> lstOperacion = new ArrayList<>();

			for (Object[] obj : results) {
				OperacionEntity operacion = new OperacionEntity();

				operacion.setIdOperacion(Integer.parseInt(String.valueOf(obj[0])));
				operacion.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				operacion.setNoNombre((String) obj[2]);
				operacion.setFlBloqueado(Integer.parseInt(String.valueOf(obj[3])));
				operacion.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				operacion.setTxDescripcionEscuadron((String) obj[5]);
				lstOperacion.add(operacion);

			}
			return lstOperacion;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<OperacionEntity> buscarPorId(Integer id) throws RepositoryException{
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.buscarPorId");
			spq.setParameter("P_ID_OPERACION", id); 
			spq.execute(); 

			List<Object[]> results = spq.getResultList();  
			List<OperacionEntity> lstOperacion = new ArrayList<>();
			

			for (Object[] obj : results) {
				
				OperacionEntity operacion = new OperacionEntity();

				operacion.setIdOperacion(Integer.parseInt(String.valueOf(obj[0])));
				operacion.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				operacion.setNoNombre((String) obj[2]);
				operacion.setFlBloqueado(Integer.parseInt(String.valueOf(obj[3])));
				operacion.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				operacion.setTxDescripcionEscuadron((String) obj[5]);
				lstOperacion.add(operacion);
			
			}

			if (lstOperacion.isEmpty()) { 
				return Optional.empty();
			}

			OperacionEntity operacion = (OperacionEntity) lstOperacion.get(0);  
			return Optional.of(operacion);
		} catch (Exception e) {  
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public String guardar(OperacionEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.insertar");

			spq.setParameter("P_ID_ESCUADRON", entity.getIdEscuadron()); 
			spq.setParameter("P_NOMBRE", entity.getNoNombre());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(OperacionEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.actualizar");

			spq.setParameter("P_ID_OPERACION", entity.getIdOperacion());
			spq.setParameter("P_ID_ESCUADRON", entity.getIdEscuadron()); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.eliminar");

			spq.setParameter("P_ID_OPERACION", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("operacion.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
