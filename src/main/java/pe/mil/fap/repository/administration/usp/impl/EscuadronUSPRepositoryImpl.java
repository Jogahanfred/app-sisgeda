package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers; 
import pe.mil.fap.entity.administration.EscuadronEntity; 
import pe.mil.fap.repository.administration.usp.inf.EscuadronUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class EscuadronUSPRepositoryImpl implements EscuadronUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<EscuadronEntity> listarEscuadrones() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EscuadronEntity> lstEscuadrones = new ArrayList<>();

			for (Object[] obj : results) {
				EscuadronEntity escuadron = new EscuadronEntity();

				escuadron.setIdEscuadron(Integer.parseInt(String.valueOf(obj[0])));
				escuadron.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				escuadron.setCoSigla((String) obj[2]);
				escuadron.setTxDescripcion((String) obj[3]); 
				escuadron.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				escuadron.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				escuadron.setTxRutaLogo(String.valueOf(obj[6])); 
				escuadron.setTxInformacion(String.valueOf(obj[7]));
				escuadron.setTxDescripcionUnidad((String) obj[8]); 
				lstEscuadrones.add(escuadron); 
			}
			return lstEscuadrones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EscuadronEntity> listarEscuadronesPorIdUnidadRector(Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.listarPorIdUnidadRector");
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EscuadronEntity> lstEscuadrones = new ArrayList<>();

			for (Object[] obj : results) {
				EscuadronEntity escuadron = new EscuadronEntity();

				escuadron.setIdEscuadron(Integer.parseInt(String.valueOf(obj[0])));
				escuadron.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				escuadron.setCoSigla((String) obj[2]);
				escuadron.setTxDescripcion((String) obj[3]); 
				escuadron.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				escuadron.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				escuadron.setTxRutaLogo(String.valueOf(obj[6])); 
				escuadron.setTxInformacion(String.valueOf(obj[7]));
				escuadron.setTxDescripcionUnidad((String) obj[8]); 
				lstEscuadrones.add(escuadron); 
			}
			return lstEscuadrones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EscuadronEntity> listarEscuadronesPorIdUnidad(Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.listarPorIdUnidad");
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EscuadronEntity> lstEscuadrones = new ArrayList<>();

			for (Object[] obj : results) {
				EscuadronEntity escuadron = new EscuadronEntity();

				escuadron.setIdEscuadron(Integer.parseInt(String.valueOf(obj[0])));
				escuadron.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				escuadron.setCoSigla((String) obj[2]);
				escuadron.setTxDescripcion((String) obj[3]); 
				escuadron.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				escuadron.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				escuadron.setTxRutaLogo(String.valueOf(obj[6])); 
				escuadron.setTxInformacion(String.valueOf(obj[7]));
				escuadron.setTxDescripcionUnidad((String) obj[8]); 
				lstEscuadrones.add(escuadron); 
			}
			return lstEscuadrones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<EscuadronEntity> buscarId(Integer id) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.buscarId");
			spq.setParameter("P_ID_ESCUADRON", id); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EscuadronEntity> lstEscuadrones = new ArrayList<>();

			for (Object[] obj : results) {
				EscuadronEntity escuadron = new EscuadronEntity();

				escuadron.setIdEscuadron(Integer.parseInt(String.valueOf(obj[0])));
				escuadron.setIdUnidad(Integer.parseInt(String.valueOf(obj[1]))); 
				escuadron.setCoSigla((String) obj[2]);
				escuadron.setTxDescripcion((String) obj[3]); 
				escuadron.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				escuadron.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				escuadron.setTxRutaLogo(String.valueOf(obj[6]));
				escuadron.setTxInformacion(String.valueOf(obj[7]));
				escuadron.setTxDescripcionUnidad((String) obj[8]); 
				lstEscuadrones.add(escuadron);

			}
			
			if (lstEscuadrones.isEmpty()) { 
				return Optional.empty();
			}
			EscuadronEntity escuadron = (EscuadronEntity) lstEscuadrones.get(0);
			return Optional.of(escuadron);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 
	
	@Override
	public String guardar(EscuadronEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.insertar");
 
			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad());
			spq.setParameter("P_SIGLA", entity.getCoSigla());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion()); 
			spq.setParameter("P_RUTA_LOGO", entity.getTxRutaLogo());  
			spq.setParameter("P_INFORMACION", entity.getTxInformacion()); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(EscuadronEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.actualizar");

			spq.setParameter("P_ID_ESCUADRON", entity.getIdEscuadron()); 
			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad());
			spq.setParameter("P_SIGLA", entity.getCoSigla());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion()); 
			spq.setParameter("P_BLOQUEADO", entity.getFlBloqueado()); 
			spq.setParameter("P_RUTA_LOGO", entity.getTxRutaLogo());  
			spq.setParameter("P_INFORMACION", entity.getTxInformacion()); 
			
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.eliminar");

			spq.setParameter("P_ID_ESCUADRON", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("escuadron.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 

}
