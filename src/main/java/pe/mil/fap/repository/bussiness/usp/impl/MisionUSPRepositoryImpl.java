package pe.mil.fap.repository.bussiness.usp.impl;
 
import java.util.List;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;   
import pe.mil.fap.repository.bussiness.usp.inf.MisionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class MisionUSPRepositoryImpl implements MisionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

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
