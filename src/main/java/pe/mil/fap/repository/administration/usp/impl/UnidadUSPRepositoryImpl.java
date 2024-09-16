package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.utils.UtilHelpers; 
import pe.mil.fap.entity.administration.UnidadEntity; 
import pe.mil.fap.repository.administration.usp.inf.UnidadUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class UnidadUSPRepositoryImpl implements UnidadUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadEntity> listarUnidades() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<UnidadEntity> lstUnidades = new ArrayList<>();

			for (Object[] obj : results) {
				UnidadEntity unidad = new UnidadEntity();

				unidad.setIdUnidad(Integer.parseInt(String.valueOf(obj[0])));
				unidad.setNuCodigo(Integer.parseInt(String.valueOf(obj[1])));
				unidad.setNoNombre((String) obj[2]);
				unidad.setTxDescripcion((String) obj[3]);
				unidad.setNuNivel(Integer.parseInt(String.valueOf(obj[4])));
				unidad.setNuCodigoRector(Integer.parseInt(String.valueOf(obj[5])));
				unidad.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				unidad.setFlEstado(Integer.parseInt(String.valueOf(obj[7]))); 
				unidad.setTxRutaLogo((String) (obj[8]));
				unidad.setTxUbicacion(String.valueOf(obj[9]));
				unidad.setTxInformacion(String.valueOf(obj[10]));
				unidad.setTxDescripcionOrganoRector(String.valueOf(obj[11]));
				lstUnidades.add(unidad);

			}
			return lstUnidades;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadEntity> listarUnidadesPorRector(Integer nuCodigoRector) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.listarPorRector");
			spq.setParameter("P_CODIGO_RECTOR", nuCodigoRector);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<UnidadEntity> lstUnidades = new ArrayList<>();

			for (Object[] obj : results) {
				UnidadEntity unidad = new UnidadEntity();

				unidad.setIdUnidad(Integer.parseInt(String.valueOf(obj[0])));
				unidad.setNuCodigo(Integer.parseInt(String.valueOf(obj[1])));
				unidad.setNoNombre((String) obj[2]);
				unidad.setTxDescripcion((String) obj[3]);
				unidad.setNuNivel(Integer.parseInt(String.valueOf(obj[4])));
				unidad.setNuCodigoRector(Integer.parseInt(String.valueOf(obj[5])));
				unidad.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				unidad.setFlEstado(Integer.parseInt(String.valueOf(obj[7]))); 
				unidad.setTxRutaLogo((String) (obj[8]));
				unidad.setTxUbicacion(String.valueOf(obj[9]));
				unidad.setTxInformacion(String.valueOf(obj[10]));
				unidad.setTxDescripcionOrganoRector(String.valueOf(obj[11]));
				lstUnidades.add(unidad);

			}
			return lstUnidades;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<UnidadEntity> buscarId(Integer id) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.buscarId");
			spq.setParameter("P_ID_UNIDAD", id); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<UnidadEntity> lstUnidades = new ArrayList<>();

			for (Object[] obj : results) {
				UnidadEntity unidad = new UnidadEntity();

				unidad.setIdUnidad(Integer.parseInt(String.valueOf(obj[0])));
				unidad.setNuCodigo(Integer.parseInt(String.valueOf(obj[1])));
				unidad.setNoNombre((String) obj[2]);
				unidad.setTxDescripcion((String) obj[3]);
				unidad.setNuNivel(Integer.parseInt(String.valueOf(obj[4])));
				unidad.setNuCodigoRector(Integer.parseInt(String.valueOf(obj[5])));
				unidad.setFlBloqueado(Integer.parseInt(String.valueOf(obj[6])));
				unidad.setFlEstado(Integer.parseInt(String.valueOf(obj[7])));
				unidad.setTxRutaLogo(String.valueOf(obj[8]));
				unidad.setTxUbicacion(String.valueOf(obj[9]));
				unidad.setTxInformacion(String.valueOf(obj[10]));
				lstUnidades.add(unidad);

			}
			
			if (lstUnidades.isEmpty()) { 
				return Optional.empty();
			}
			UnidadEntity unidad = (UnidadEntity) lstUnidades.get(0);
			return Optional.of(unidad);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 
	
	@Override
	public String guardar(UnidadEntity entity) throws RepositoryException { 
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.insertar");
 
			spq.setParameter("P_CODIGO", entity.getNuCodigo());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion());
			spq.setParameter("P_NIVEL", entity.getNuNivel());
			spq.setParameter("P_CODIGO_RECTOR", entity.getNuCodigoRector());
			spq.setParameter("P_RUTA_LOGO", entity.getTxRutaLogo());
			spq.setParameter("P_UBICACION", entity.getTxUbicacion());
			spq.setParameter("P_INFORMACION", entity.getTxInformacion());

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String actualizar(UnidadEntity entity) throws RepositoryException {  
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.actualizar");

			spq.setParameter("P_ID_UNIDAD", entity.getIdUnidad());
			spq.setParameter("P_NOMBRE", entity.getNoNombre());
			spq.setParameter("P_DESCRIPCION", entity.getTxDescripcion());
			spq.setParameter("P_NIVEL", entity.getNuNivel());
			spq.setParameter("P_CODIGO_RECTOR", entity.getNuCodigoRector());
			spq.setParameter("P_BLOQUEADO", entity.getFlBloqueado()); 
			spq.setParameter("P_RUTA_LOGO", entity.getTxRutaLogo());
			spq.setParameter("P_UBICACION", entity.getTxUbicacion());
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.eliminar");

			spq.setParameter("P_ID_UNIDAD", id); 
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
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("unidad.eliminarMultiple");
			
			spq.setParameter("P_IDS", UtilHelpers.convertListIntegerToString(lstId)); 
			spq.execute();
 
			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}  
}
