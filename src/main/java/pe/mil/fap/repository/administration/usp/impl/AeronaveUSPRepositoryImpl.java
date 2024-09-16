package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.administration.AeronaveEntity;
import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.administration.usp.inf.AeronaveUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class AeronaveUSPRepositoryImpl implements AeronaveUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws RepositoryException {
		try { 
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.paginar");
			spq.setParameter("P_PAGE", parametro.getInicio());
			spq.setParameter("P_SIZE", parametro.getTamanio());
			spq.setParameter("P_FILTER", parametro.getFiltro());
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<AeronaveEntity> lstAeronave = new ArrayList<>();
			Integer totalRegistros = 0;
			
			for (Object[] obj : results) {
				AeronaveEntity aeronave = new AeronaveEntity();
				totalRegistros = ((Number)obj[0]).intValue(); 
				aeronave.setIdAeronave(Integer.parseInt(String.valueOf(obj[1])));
				aeronave.setIdUnidad(Integer.parseInt(String.valueOf(obj[2])));
				aeronave.setIdFlota(Integer.parseInt(String.valueOf(obj[3])));
				aeronave.setCoNroCola(String.valueOf(obj[4]));
				aeronave.setFlEstado(Integer.parseInt(String.valueOf(obj[5])));
				aeronave.setTxRutaImagen((String) obj[6]);
				aeronave.setTxDescripcionUnidad(String.valueOf(obj[7]));
				aeronave.setTxDescripcionFlota(String.valueOf(obj[8])); 
				lstAeronave.add(aeronave);				
			}
			return DataTableDTO.builder()
		                       .iTotalRecords(totalRegistros) 
		                       .iTotalDisplayRecords(totalRegistros) 
			                   .iDisplayRecords(lstAeronave.size()) 
		                       .aaData(lstAeronave)
		                       .build();
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<AeronaveEntity> buscarId(Integer id) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.buscarId");
			spq.setParameter("P_ID_AERONAVE", id); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<AeronaveEntity> lstAeronave = new ArrayList<>();

			for (Object[] obj : results) {
				AeronaveEntity aeronave = new AeronaveEntity();
				aeronave.setIdAeronave(Integer.parseInt(String.valueOf(obj[0])));
				aeronave.setIdUnidad(Integer.parseInt(String.valueOf(obj[1])));
				aeronave.setIdFlota(Integer.parseInt(String.valueOf(obj[2])));
				aeronave.setCoNroCola(String.valueOf(obj[3]));
				aeronave.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				aeronave.setTxRutaImagen((String) obj[5]);
				aeronave.setTxDescripcionUnidad(String.valueOf(obj[6]));
				aeronave.setTxDescripcionFlota(String.valueOf(obj[7])); 
				lstAeronave.add(aeronave);	

			}
			
			if (lstAeronave.isEmpty()) { 
				return Optional.empty();
			}
			AeronaveEntity aeronave = (AeronaveEntity) lstAeronave.get(0);
			return Optional.of(aeronave);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String guardar(AeronaveEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.insertar");
 
			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad());
			spq.setParameter("P_ID_FLOTA", entity.getIdFlota());
			spq.setParameter("P_NRO_COLA", entity.getCoNroCola());
			spq.setParameter("P_RUTA_IMAGEN", entity.getTxRutaImagen());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(AeronaveEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.actualizar");

			spq.setParameter("P_ID_AERONAVE", entity.getIdAeronave()); 
			spq.setParameter("P_ID_FLOTA", entity.getIdFlota()); 
			spq.setParameter("P_NRO_COLA", entity.getCoNroCola()); 
			spq.setParameter("P_RUTA_IMAGEN", entity.getTxRutaImagen()); 

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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.eliminar");

			spq.setParameter("P_ID_AERONAVE", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("aeronave.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
