package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.SubFaseEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.repository.administration.usp.inf.SubFaseUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class SubFaseUSPRepositoryImpl implements SubFaseUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<SubFaseEntity> listarSubFases() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<SubFaseEntity> lstSubFase = new ArrayList<>();

			for (Object[] obj : results) {
				SubFaseEntity fase = new SubFaseEntity();

				fase.setIdSubFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoSubFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdFase(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalMision(Integer.parseInt(String.valueOf(obj[4])));
				fase.setNuTotalManiobra(Integer.parseInt(String.valueOf(obj[5])));
				fase.setCoCodigo(String.valueOf(obj[6]));
				fase.setTxFinalidad((String) obj[7]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[8])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[9])));
				fase.setTxDescripcionSubFase((String) obj[10]);
				fase.setTxDescripcionFase((String) obj[11]);
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[12])));
				fase.setTxDescripcionPrograma((String) obj[13]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[14])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[15])));
				lstSubFase.add(fase);

			}
			return lstSubFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubFaseEntity> listarSubFasesPorIdUnidad(Integer idUnidad) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.listarPorUnidad");
			spq.setParameter("P_ID_UNIDAD", idUnidad);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<SubFaseEntity> lstSubFase = new ArrayList<>();

			for (Object[] obj : results) {
				SubFaseEntity fase = new SubFaseEntity();

				fase.setIdSubFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoSubFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdFase(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalMision(Integer.parseInt(String.valueOf(obj[4])));
				fase.setNuTotalManiobra(Integer.parseInt(String.valueOf(obj[5])));
				fase.setCoCodigo(String.valueOf(obj[6]));
				fase.setTxFinalidad((String) obj[7]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[8])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[9])));
				fase.setTxDescripcionSubFase((String) obj[10]);
				fase.setTxDescripcionFase((String) obj[11]);
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[12])));
				fase.setTxDescripcionPrograma((String) obj[13]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[14])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[15])));
				lstSubFase.add(fase);

			}
			return lstSubFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubFaseEntity> listarSubFasesPorIdFase(Integer idFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.listarPorFase");
			spq.setParameter("P_ID_FASE", idFase);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<SubFaseEntity> lstSubFase = new ArrayList<>();

			for (Object[] obj : results) {
				SubFaseEntity fase = new SubFaseEntity();

				fase.setIdSubFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoSubFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdFase(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalMision(Integer.parseInt(String.valueOf(obj[4])));
				fase.setNuTotalManiobra(Integer.parseInt(String.valueOf(obj[5])));
				fase.setCoCodigo(String.valueOf(obj[6]));
				fase.setTxFinalidad((String) obj[7]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[8])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[9])));
				fase.setTxDescripcionSubFase((String) obj[10]);
				fase.setTxDescripcionFase((String) obj[11]);
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[12])));
				fase.setTxDescripcionPrograma((String) obj[13]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[14])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[15])));
				lstSubFase.add(fase);

			}
			return lstSubFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<Integer, String>> listarFiltroPeriodo(Integer nuPeriodo) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.filtroPorPeriodo");
			spq.setParameter("P_PERIODO", nuPeriodo);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<Map<Integer, String>> lstFiltros = new ArrayList<>();

			for (Object[] obj : results) {
				Map<Integer, String> filtro = new HashMap<>();
				filtro.put(Integer.parseInt(String.valueOf(obj[0])), String.valueOf(obj[1]));
				lstFiltros.add(filtro); 

			}
			return lstFiltros;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeXEntity> listarEjeX(Integer idSubFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("gestionSubFase.listarEjeX");
			spq.setParameter("P_ID_SUB_FASE", idSubFase);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EjeXEntity> lstEjeX = new ArrayList<>();

			for (Object[] obj : results) {
				EjeXEntity ejeX = new EjeXEntity();

				ejeX.setIdMision(Integer.parseInt(String.valueOf(obj[0])));
				ejeX.setCoCodigo(String.valueOf(obj[1]));
				ejeX.setIdTipoMision(Integer.parseInt(String.valueOf(obj[2])));
				ejeX.setCoCodigoTipoMision(String.valueOf(obj[3]));
				ejeX.setNoNombreTipoMision(String.valueOf(obj[4]));
				 
				lstEjeX.add(ejeX);

			}
			return lstEjeX;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeYEntity> listarEjeY(Integer idSubFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("gestionSubFase.listarEjeY");
			spq.setParameter("P_ID_SUB_FASE", idSubFase);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EjeYEntity> lstEjeY = new ArrayList<>();

			for (Object[] obj : results) {
				EjeYEntity ejeY = new EjeYEntity();

				ejeY.setIdManiobra(Integer.parseInt(String.valueOf(obj[0])));
				ejeY.setNoNombreManiobra(String.valueOf(obj[1]));
				ejeY.setIdOperacion(Integer.parseInt(String.valueOf(obj[2]))); 
				ejeY.setNoNombreOperacion(String.valueOf(obj[3]));
				 
				lstEjeY.add(ejeY);

			}
			return lstEjeY;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeInterseccionEntity> listarEjeInterseccion(Integer idSubFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("gestionSubFase.listarInterseccion");
			spq.setParameter("P_ID_SUB_FASE", idSubFase);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EjeInterseccionEntity> lstEjeInterseccion = new ArrayList<>();

			for (Object[] obj : results) {
				EjeInterseccionEntity ejeInterseccion = new EjeInterseccionEntity();
				
				ejeInterseccion.setIdDetalleMision(Integer.parseInt(String.valueOf(obj[0])));
				ejeInterseccion.setIdMision(Integer.parseInt(String.valueOf(obj[1])));
				ejeInterseccion.setIdManiobra(Integer.parseInt(String.valueOf(obj[2])));
				ejeInterseccion.setIdEstandarRequerido(Integer.parseInt(String.valueOf(obj[3])));
				ejeInterseccion.setCoCodigoEstandar(String.valueOf(obj[4]));
				 
				lstEjeInterseccion.add(ejeInterseccion);

			}
			return lstEjeInterseccion;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<SubFaseEntity> buscarPorId(Integer id) throws RepositoryException{
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.buscarPorId");
			spq.setParameter("P_ID_SUB_FASE", id); 
			spq.execute(); 

			List<Object[]> results = spq.getResultList();  
			List<SubFaseEntity> lstSubFase = new ArrayList<>();
			

			for (Object[] obj : results) {
				
				SubFaseEntity fase = new SubFaseEntity();

				fase.setIdSubFase(Integer.parseInt(String.valueOf(obj[0])));
				fase.setIdBancoSubFase(Integer.parseInt(String.valueOf(obj[1])));
				fase.setIdFase(Integer.parseInt(String.valueOf(obj[2])));
				fase.setNuTotalHora(Integer.parseInt(String.valueOf(obj[3])));
				fase.setNuTotalMision(Integer.parseInt(String.valueOf(obj[4])));
				fase.setNuTotalManiobra(Integer.parseInt(String.valueOf(obj[5])));
				fase.setCoCodigo(String.valueOf(obj[6]));
				fase.setTxFinalidad((String) obj[7]);
				fase.setFlBloqueado(Integer.parseInt(String.valueOf(obj[8])));
				fase.setFlEstado(Integer.parseInt(String.valueOf(obj[9])));
				fase.setTxDescripcionSubFase((String) obj[10]);
				fase.setTxDescripcionFase((String) obj[11]);
				fase.setIdPrograma(Integer.parseInt(String.valueOf(obj[12])));
				fase.setTxDescripcionPrograma((String) obj[13]);
				fase.setIdEscuadron(Integer.parseInt(String.valueOf(obj[14])));
				fase.setIdBancoFase(Integer.parseInt(String.valueOf(obj[15])));
				lstSubFase.add(fase);
			
			}

			if (lstSubFase.isEmpty()) { 
				return Optional.empty();
			}

			SubFaseEntity subFase = (SubFaseEntity) lstSubFase.get(0);  
			return Optional.of(subFase);
		} catch (Exception e) {  
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public String guardar(SubFaseEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.insertar");
			System.out.println(entity.toString());
			spq.setParameter("P_ID_ESCUADRON", entity.getIdEscuadron());
			spq.setParameter("P_ID_PROGRAMA", entity.getIdPrograma());
			spq.setParameter("P_ID_BANCO_FASE", entity.getIdBancoFase());
			spq.setParameter("P_ID_BANCO_SUB_FASE", entity.getIdBancoSubFase());
			spq.setParameter("P_TOTAL_HORA", entity.getNuTotalHora());
			spq.setParameter("P_TOTAL_MISION", entity.getNuTotalMision());
			spq.setParameter("P_TOTAL_MANIOBRA", entity.getNuTotalManiobra());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
			spq.setParameter("P_FINALIDAD", entity.getTxFinalidad());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(SubFaseEntity entity) throws RepositoryException {  
		try {
			System.out.println(entity.toString());
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.actualizar");

			spq.setParameter("P_ID_SUB_FASE", entity.getIdSubFase()); 
			spq.setParameter("P_ID_FASE", entity.getIdFase());
			spq.setParameter("P_TOTAL_HORA", entity.getNuTotalHora());
			spq.setParameter("P_TOTAL_MISION", entity.getNuTotalMision());
			spq.setParameter("P_TOTAL_MANIOBRA", entity.getNuTotalManiobra());
			spq.setParameter("P_CODIGO", entity.getCoCodigo());
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.eliminar");

			spq.setParameter("P_ID_SUB_FASE", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("subFase.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 
}
