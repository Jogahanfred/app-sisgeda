package pe.mil.fap.repository.bussiness.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.entity.helpers.CalificarMisionEntity;
import pe.mil.fap.repository.bussiness.usp.inf.MisionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class MisionUSPRepositoryImpl implements MisionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<CalificarMisionEntity> listarMisionesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro,
			Integer idSubFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("mision.listarCalificarPorPeriodo");
			spq.setParameter("P_PERIODO",nuPeriodo);
			spq.setParameter("P_ID_CALIFICADO",idMiembro);
			spq.setParameter("P_ID_SUB_FASE",idSubFase);
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<CalificarMisionEntity> lstMisiones = new ArrayList<>();

			for (Object[] obj : results) {
				CalificarMisionEntity mision = new CalificarMisionEntity();

				mision.setIdMision(Integer.parseInt(String.valueOf(obj[0])));
				mision.setTxDescripcionTipoMision((String) obj[1]);
				mision.setTxNombreTipoMision((String) obj[2]);
				mision.setCoCodigo((String) obj[3]);
				mision.setFlChequeo(Integer.parseInt(String.valueOf(obj[4])));
				mision.setIdCalificador(obj[5] != null ? Integer.parseInt(String.valueOf(obj[5])): null);
				mision.setIdCalificacion(Integer.parseInt(String.valueOf(obj[6])));
				
				mision.setQtNota(obj[7] != null ? Double.parseDouble(String.valueOf(obj[7])): null);
				mision.setCoCalificacionVuelo(obj[8] != null ? String.valueOf(obj[8]): null);
				mision.setCoNroCola(obj[9] != null ? String.valueOf(obj[9]): null);
				mision.setFeProgramacion(obj[10] != null ? String.valueOf(obj[10]): null);
				mision.setFeEjecucion(obj[10] != null ? String.valueOf(obj[11]): null);
				
				lstMisiones.add(mision);
			}
			return lstMisiones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MisionEntity> listarMisionesPorIdSubFase(Integer idSubFase) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("mision.listarPorIdSubFase");
			spq.setParameter("P_ID_SUB_FASE",idSubFase);
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<MisionEntity> lstMisiones = new ArrayList<>();

			for (Object[] obj : results) {
				MisionEntity mision = new MisionEntity();

				mision.setIdMision(Integer.parseInt(String.valueOf(obj[0])));
				mision.setIdSubFase(Integer.parseInt(String.valueOf(obj[1])));
				mision.setIdTipoMision(Integer.parseInt(String.valueOf(obj[2])));
				mision.setCoCodigo((String) obj[3]);
				mision.setFlChequeo(Integer.parseInt(String.valueOf(obj[4])));
				mision.setFlBloqueado(Integer.parseInt(String.valueOf(obj[5])));
				mision.setFlEstado(Integer.parseInt(String.valueOf(obj[6])));
				lstMisiones.add(mision);
			}
			return lstMisiones;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleMisionEntity> listarDetalleMisionPorIdMision(Integer idMision) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("detalleMision.listarPorIdMision");
			spq.setParameter("P_ID_MISION",idMision);
			spq.execute();
 
			List<Object[]> results =  spq.getResultList();
			List<DetalleMisionEntity> lstDetalles = new ArrayList<>();

			for (Object[] obj : results) {
				DetalleMisionEntity detalle = new DetalleMisionEntity();

				detalle.setIdDetalleMision(Integer.parseInt(String.valueOf(obj[0])));
				detalle.setIdMision(Integer.parseInt(String.valueOf(obj[1])));
				detalle.setIdManiobra(Integer.parseInt(String.valueOf(obj[2])));
				detalle.setIdEstandarRequerido(Integer.parseInt(String.valueOf(obj[3])));
				detalle.setNuOrden(Integer.parseInt(String.valueOf(obj[4])));
				detalle.setFlBloqueado(Integer.parseInt(String.valueOf(obj[5])));
				detalle.setFlEstado(Integer.parseInt(String.valueOf(obj[6])));
				lstDetalles.add(detalle);
			}
			return lstDetalles;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public Optional<MisionEntity> buscarId(Integer id) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("mision.buscarId");
			spq.setParameter("P_ID_MISION", id); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<MisionEntity> lstMisiones = new ArrayList<>();

			for (Object[] obj : results) {
				MisionEntity mision = new MisionEntity();

				mision.setIdMision(Integer.parseInt(String.valueOf(obj[0])));
				mision.setIdSubFase(Integer.parseInt(String.valueOf(obj[1]))); 
				mision.setIdTipoMision(Integer.parseInt(String.valueOf(obj[2]))); 
				mision.setCoCodigo((String) obj[3]); 
				mision.setFlChequeo(Integer.parseInt(String.valueOf(obj[4]))); 
				mision.setFlBloqueado(Integer.parseInt(String.valueOf(obj[5])));
				mision.setFlEstado(Integer.parseInt(String.valueOf(obj[6]))); 
				lstMisiones.add(mision);

			}
			
			if (lstMisiones.isEmpty()) { 
				return Optional.empty();
			}
			MisionEntity mision = (MisionEntity) lstMisiones.get(0);
			return Optional.of(mision);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public Integer insertarCabecera(MisionEntity mision) throws RepositoryException { 
		Integer idGenerado = 0;
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("mision.insertar");

			spq.setParameter("P_ID_SUB_FASE", mision.getIdSubFase());
			spq.setParameter("P_ID_TIPO_MISION", mision.getIdTipoMision());
			spq.setParameter("P_CODIGO", mision.getCoCodigo());
			spq.setParameter("P_CHEQUEO", mision.getFlChequeo());

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_MISION"); 
			return (idGenerado == 0 || idGenerado == null) ? 0 : idGenerado; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar la cabecera");
		}
	}

	@Override
	public Boolean insertarDetalle(DetalleMisionEntity detalleMision) throws RepositoryException { 
		Integer idGenerado = 0;		
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("detalleMision.insertar");

			spq.setParameter("P_ID_MISION", detalleMision.getIdMision());
			spq.setParameter("P_ID_MANIOBRA", detalleMision.getIdManiobra());
			spq.setParameter("P_ID_ESTANDAR_REQUERIDO", detalleMision.getIdEstandarRequerido());
			spq.setParameter("P_ORDEN", detalleMision.getNuOrden());

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_DETALLE_MISION");

			return (idGenerado == 0 || idGenerado == null) ? false : true;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar el detalle");
		}
	}

	@Override
	@Transactional
	public String guardarTransaccion(List<MisionEntity> lstMisiones) throws RepositoryException {
		try {
			for (MisionEntity mision : lstMisiones) {
				Integer idGenerado = this.insertarCabecera(mision);

				if (idGenerado != 0 ? true : false) {
					for (DetalleMisionEntity misionDetalle : mision.getLstDetalleMision()) {
						misionDetalle.setIdMision(idGenerado);
						this.insertarDetalle(misionDetalle);
					}
				}
			}
			return SeveridadEnum.SUCCESS.getValor() + "|Gracias ! ¡Su misión ha sido registrada correctamente!";
		} catch (Exception exception) {
			throw new RepositoryException(exception.getMessage());
		}
	}

	@Override
	public String actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar)
			throws RepositoryException {
		String mensaje = "";
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("detalleMision.actualizarEstandar");

			spq.setParameter("P_ID_MANIOBRA", idManiobra);
			spq.setParameter("P_ID_MISION", idMision);
			spq.setParameter("P_ID_ESTANDAR", idEstandar); 

			spq.execute();

			mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al actualizar estandar");
		}
	}

}
