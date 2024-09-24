package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery; 
import pe.mil.fap.entity.administration.MiembroEntity; 
import pe.mil.fap.repository.administration.usp.inf.MiembroUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class MiembroUSPRepositoryImpl implements MiembroUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<MiembroEntity> listarMiembrosACalificarPorPeriodo(Integer nuPeriodo, String noRol)
			throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.listarCalificarPorPeriodo");
			spq.setParameter("P_ROL", noRol.toUpperCase());
			spq.setParameter("P_PERIODO", nuPeriodo);
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<MiembroEntity> lstMiembros = new ArrayList<>();

			for (Object[] obj : results) {
				MiembroEntity miembro = new MiembroEntity();

				miembro.setIdMiembro(Integer.parseInt(String.valueOf(obj[0])));
				miembro.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				miembro.setCoNsa((String) obj[2]);
				miembro.setNuPeriodo(Integer.parseInt(String.valueOf(obj[3])));
				miembro.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				miembro.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				lstMiembros.add(miembro);

			}
			return lstMiembros;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiembroEntity> listarMiembros(Integer nuPeriodo, String noRol) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.listar");
			spq.setParameter("P_ROL", noRol.toUpperCase());
			spq.setParameter("P_PERIODO", nuPeriodo);
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<MiembroEntity> lstMiembros = new ArrayList<>();

			for (Object[] obj : results) {
				MiembroEntity miembro = new MiembroEntity();

				miembro.setIdMiembro(Integer.parseInt(String.valueOf(obj[0])));
				miembro.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				miembro.setCoNsa((String) obj[2]);
				miembro.setNuPeriodo(Integer.parseInt(String.valueOf(obj[3])));
				miembro.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				miembro.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				lstMiembros.add(miembro);

			}
			return lstMiembros;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<MiembroEntity> buscarPorId(Integer idMiembro) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.buscarPorId");
			spq.setParameter("P_ID_MIEMBRO", idMiembro);  
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<MiembroEntity> lstMiembros = new ArrayList<>();

			for (Object[] obj : results) {
				MiembroEntity miembro = new MiembroEntity();

				miembro.setIdMiembro(Integer.parseInt(String.valueOf(obj[0])));
				miembro.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				miembro.setCoNsa((String) obj[2]);
				miembro.setNuPeriodo(Integer.parseInt(String.valueOf(obj[3])));
				miembro.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				miembro.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				lstMiembros.add(miembro);

			}
			
			if (lstMiembros.isEmpty()) { 
				return Optional.empty();
			}
			MiembroEntity miembro = (MiembroEntity) lstMiembros.get(0);
			return Optional.of(miembro);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<MiembroEntity> buscarPorNsa(String coNsa, Integer nuPeriodo, String noRol) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.buscarPorNsa");
			spq.setParameter("P_NSA", coNsa); 
			spq.setParameter("P_ROL", noRol); 
			spq.setParameter("P_PERIODO", nuPeriodo); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<MiembroEntity> lstMiembros = new ArrayList<>();

			for (Object[] obj : results) {
				MiembroEntity miembro = new MiembroEntity();

				miembro.setIdMiembro(Integer.parseInt(String.valueOf(obj[0])));
				miembro.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				miembro.setCoNsa((String) obj[2]);
				miembro.setNuPeriodo(Integer.parseInt(String.valueOf(obj[3])));
				miembro.setFlBloqueado(Integer.parseInt(String.valueOf(obj[4])));
				miembro.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				lstMiembros.add(miembro);

			}
			
			if (lstMiembros.isEmpty()) { 
				return Optional.empty();
			}
			MiembroEntity miembro = (MiembroEntity) lstMiembros.get(0);
			return Optional.of(miembro);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 

	@Override
	public String guardarInstructor(MiembroEntity entity) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.insertarInstructor");

			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad()); 
			spq.setParameter("P_NSA", entity.getCoNsa());
			spq.setParameter("P_PERIODO", entity.getNuPeriodo());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {  
			throw new RepositoryException(e);
		}
	} 
	
	@Override
	public String guardarAlumno(MiembroEntity entity) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.insertarAlumno");

			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad()); 
			spq.setParameter("P_NSA", entity.getCoNsa());
			spq.setParameter("P_PERIODO", entity.getNuPeriodo());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {  
			throw new RepositoryException(e);
		}
	} 


	@Override
	public String deshabilitar(Integer id) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.deshabilitar");

			spq.setParameter("P_ID_MIEMBRO", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("miembro.eliminar");

			spq.setParameter("P_ID_MIEMBRO", id); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) { 
			throw new RepositoryException(e);
		}
	}
 

}
