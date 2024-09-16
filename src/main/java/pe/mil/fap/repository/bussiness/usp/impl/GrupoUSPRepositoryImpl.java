package pe.mil.fap.repository.bussiness.usp.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery; 
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.GrupoEntity;
import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.repository.bussiness.usp.inf.GrupoUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class GrupoUSPRepositoryImpl implements GrupoUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoEntity> listar(Integer nuPeriodo, Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("grupo.listar");
			spq.setParameter("P_PERIODO", nuPeriodo);
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<GrupoEntity> lstGrupos = new ArrayList<>();

			for (Object[] obj : results) {
				GrupoEntity grupo = new GrupoEntity();

				grupo.setIdGrupo(Integer.parseInt(String.valueOf(obj[0])));
				grupo.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				grupo.setCoCodigo((String) obj[2]);
				grupo.setNoNombre((String) obj[3]); 
				grupo.setNoSituacion(String.valueOf(obj[4]));
				grupo.setNuPeriodo(Integer.parseInt(String.valueOf(obj[5]))); 
				lstGrupos.add(grupo); 
			}
			return lstGrupos;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoMiembroEntity> listarDetalle(Integer idGrupo) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("grupoMiembro.listarDetalle");
			spq.setParameter("P_ID_GRUPO", idGrupo); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<GrupoMiembroEntity> lstDetalle = new ArrayList<>();

			for (Object[] obj : results) {
				GrupoMiembroEntity miembro = new GrupoMiembroEntity();

				miembro.setIdGrupo(Integer.parseInt(String.valueOf(obj[0])));
				miembro.setIdMiembro(Integer.parseInt(String.valueOf(obj[1])));
				miembro.setNoSituacion((String) obj[2]);
				miembro.setFeRegistro((String) obj[3]); 
				miembro.setFeActualizacion(String.valueOf(obj[4])); 
				lstDetalle.add(miembro); 
			}
			return lstDetalle;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Transactional
	@Override
	public String guardar(GrupoEntity entity, List<Integer> lstIdsMiembros) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("grupo.insertar");
 
			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre()); 
			spq.setParameter("P_SITUACION", entity.getNoSituacion());  
			spq.setParameter("P_PERIODO", entity.getNuPeriodo()); 
			spq.setParameter("P_IDS_MIEMBROS", UtilHelpers.convertListIntegerToString(lstIdsMiembros)); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
 

}
