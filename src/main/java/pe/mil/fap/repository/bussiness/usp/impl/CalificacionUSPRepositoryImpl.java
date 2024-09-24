package pe.mil.fap.repository.bussiness.usp.impl;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.entity.bussiness.CalificacionEntity;   
import pe.mil.fap.repository.bussiness.usp.inf.CalificacionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class CalificacionUSPRepositoryImpl implements CalificacionUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Integer insertarCabecera(CalificacionEntity calificacion) throws RepositoryException { 
		Integer idGenerado = 0;
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.insertar");

			spq.setParameter("P_ID_MISION", calificacion.getIdMision());
			spq.setParameter("P_ID_CALIFICADO", calificacion.getIdCalificado()); 

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_CALIFICACION"); 
			return (idGenerado == 0 || idGenerado == null) ? 0 : idGenerado; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar la cabecera");
		}
	}

	@Override
	public Boolean insertarDetalle(DetalleCalificacionEntity detalleCalificacion) throws RepositoryException { 
		Integer idGenerado = 0;		
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("detalleCalificacion.insertar");

			spq.setParameter("P_ID_CALIFICACION", detalleCalificacion.getIdCalificacion());
			spq.setParameter("P_ID_MANIOBRA", detalleCalificacion.getIdManiobra()); 

			spq.execute();

			idGenerado = (Integer) spq.getOutputParameterValue("P_ID_DETALLE_CALIFICACION");

			return (idGenerado == 0 || idGenerado == null) ? false : true;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al insertar el detalle");
		}
	}

	@Override
	@Transactional
	public String guardarTransaccion(List<CalificacionEntity> lstCalificaciones, Boolean registroGrupal) throws RepositoryException {
		try {
			for (CalificacionEntity calificacion : lstCalificaciones) {
				Integer idGenerado = this.insertarCabecera(calificacion);

				if (idGenerado != 0 ? true : false) {
					for (DetalleCalificacionEntity calificacionDetalle : calificacion.getLstDetalleCalificacion()) {
						calificacionDetalle.setIdCalificacion(idGenerado);
						this.insertarDetalle(calificacionDetalle);
					}
				}
			}

			String mensaje = "";
			if (registroGrupal) {
				mensaje = "Ud. ha matriculado al grupo satisfactoriamente en la misión seleccionada";
			}else {
				mensaje = "El instructor ha sido matriculado satisfactoriamente en la misión";
			}
			return SeveridadEnum.SUCCESS.getValor() + "|" + mensaje;
		} catch (Exception exception) {
			throw new RepositoryException(exception.getMessage());
		}
	}

	@Override
	public Integer verificarInscripcionMision(Integer idMision, String lstIds) throws RepositoryException {
		Integer inscripcionGenerada = 0;		
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.verificarInscripcionMision");

			spq.setParameter("P_ID_MISION", idMision);
			spq.setParameter("P_IDS", lstIds); 

			spq.execute();

			inscripcionGenerada = (Integer) spq.getOutputParameterValue("P_INSCRIPCION");

			return inscripcionGenerada;
		} catch (Exception exception) {
			throw new RepositoryException(exception.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionMisionEntity> verificarInscripcionSubFase(Integer idSubFase, String lstIds)
			throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.verificarInscripcionSubFase");

			spq.setParameter("P_ID_SUB_FASE", idSubFase);
			spq.setParameter("P_IDS", lstIds); 
			
			spq.execute();

			List<Object[]> results = spq.getResultList();
			
			List<InscripcionMisionEntity> lstVerificacionSubFase = new ArrayList<>();

			for (Object[] obj : results) {
				InscripcionMisionEntity inscripcion = new InscripcionMisionEntity();

				inscripcion.setIdMision(Integer.parseInt(String.valueOf(obj[0])));
				inscripcion.setCoCodigo(String.valueOf(obj[1]));
				inscripcion.setFlInscripcion(Integer.parseInt(String.valueOf(obj[2])) == 1 ? true : false);
				lstVerificacionSubFase.add(inscripcion);
			}
			
			return lstVerificacionSubFase;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}
