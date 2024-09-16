package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.ProgramaEntity; 
import pe.mil.fap.repository.administration.usp.inf.ProgramaUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class ProgramaUSPRepositoryImpl implements ProgramaUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaEntity> listarProgramas() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<ProgramaEntity> lstPrograma = new ArrayList<>();

			for (Object[] obj : results) {
				ProgramaEntity programa = new ProgramaEntity();

				programa.setIdPrograma(Integer.parseInt(String.valueOf(obj[0])));
				programa.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				programa.setNuPeriodo(Integer.parseInt(String.valueOf(obj[2])));
				programa.setNoTipoInstruccion((String) obj[3]);
				programa.setNoNombre((String) obj[4]);
				programa.setTxDescripcion((String) obj[5]);
				programa.setTxFinalidad((String) obj[6]);
				programa.setFlBloqueado(Integer.parseInt(String.valueOf(obj[7])));
				programa.setFlEstado(Integer.parseInt(String.valueOf(obj[8])));
				programa.setTxDescripcionEscuadron(String.valueOf(obj[9]));
				lstPrograma.add(programa);

			}
			return lstPrograma;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaEntity> listarProgramasPorIdUnidad(Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.listarPorIdUnidad");
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<ProgramaEntity> lstPrograma = new ArrayList<>();

			for (Object[] obj : results) {
				ProgramaEntity programa = new ProgramaEntity();

				programa.setIdPrograma(Integer.parseInt(String.valueOf(obj[0])));
				programa.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				programa.setNuPeriodo(Integer.parseInt(String.valueOf(obj[2])));
				programa.setNoTipoInstruccion((String) obj[3]);
				programa.setNoNombre((String) obj[4]);
				programa.setTxDescripcion((String) obj[5]);
				programa.setTxFinalidad((String) obj[6]);
				programa.setFlBloqueado(Integer.parseInt(String.valueOf(obj[7])));
				programa.setFlEstado(Integer.parseInt(String.valueOf(obj[8])));
				programa.setTxDescripcionEscuadron(String.valueOf(obj[9]));
				programa.setIdUnidad(Integer.parseInt(String.valueOf(obj[10])));
				lstPrograma.add(programa);

			}
			return lstPrograma;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaEntity> listarProgramasPorIdEscuadron(Integer idEscuadron) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.listarPorIdEscuadron");
			spq.setParameter("P_ID_ESCUADRON", idEscuadron);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<ProgramaEntity> lstPrograma = new ArrayList<>();

			for (Object[] obj : results) {
				ProgramaEntity programa = new ProgramaEntity();

				programa.setIdPrograma(Integer.parseInt(String.valueOf(obj[0])));
				programa.setIdEscuadron(Integer.parseInt(String.valueOf(obj[1])));
				programa.setNuPeriodo(Integer.parseInt(String.valueOf(obj[2])));
				programa.setNoTipoInstruccion((String) obj[3]);
				programa.setNoNombre((String) obj[4]);
				programa.setTxDescripcion((String) obj[5]);
				programa.setTxFinalidad((String) obj[6]);
				programa.setFlBloqueado(Integer.parseInt(String.valueOf(obj[7])));
				programa.setFlEstado(Integer.parseInt(String.valueOf(obj[8])));
				programa.setTxDescripcionEscuadron(String.valueOf(obj[9]));
				programa.setIdUnidad(Integer.parseInt(String.valueOf(obj[10])));
				lstPrograma.add(programa);

			}
			return lstPrograma;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public String guardar(ProgramaEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.insertar");

			spq.setParameter("P_ID_ESCUADRON", entity.getIdEscuadron());
			spq.setParameter("P_PERIODO", entity.getNuPeriodo());
			spq.setParameter("P_TIPO_INSTRUCCION", entity.getNoTipoInstruccion());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion());
			spq.setParameter("P_FINALIDAD", entity.getTxFinalidad());

			spq.execute(); 

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(ProgramaEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.actualizar");
	 
			spq.setParameter("P_ID_PROGRAMA", entity.getIdPrograma()); 
			spq.setParameter("P_TIPO_INSTRUCCION", entity.getNoTipoInstruccion());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion());
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.eliminar");

			spq.setParameter("P_ID_PROGRAMA", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("programa.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
 
}
