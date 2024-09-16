package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.TipoMisionEntity; 
import pe.mil.fap.repository.administration.usp.inf.TipoMisionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class TipoMisionUSPRepositoryImpl implements TipoMisionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoMisionEntity> listarTipoMisiones() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("tipoMision.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<TipoMisionEntity> lstTipoMisiones = new ArrayList<>();

			for (Object[] obj : results) {
				TipoMisionEntity tipoMision = new TipoMisionEntity();

				tipoMision.setIdTipoMision(Integer.parseInt(String.valueOf(obj[0]))); 
				tipoMision.setCoCodigo((String) obj[1]);
				tipoMision.setNoNombre((String) obj[2]);
				tipoMision.setTxDescripcion(String.valueOf(obj[3]));
				tipoMision.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				lstTipoMisiones.add(tipoMision);

			}
			return lstTipoMisiones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(TipoMisionEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("tipoMision.insertar");

			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(TipoMisionEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("tipoMision.actualizar");

			spq.setParameter("P_ID_TIPO_MISION", entity.getIdTipoMision());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion()); 

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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("tipoMision.eliminar");

			spq.setParameter("P_ID_TIPO_MISION", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("tipoMision.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
