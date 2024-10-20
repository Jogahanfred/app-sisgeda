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
import pe.mil.fap.entity.helpers.EjeInterseccionACalificarEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity;
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

	@Override
	public String asignarInstructor(Integer idCalificador, Integer idCalificacion) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.asignarInstructor");

			spq.setParameter("P_ID_CALIFICACION", idCalificacion);
			spq.setParameter("P_ID_CALIFICADOR", idCalificador); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE");

			return mensaje;
		} catch (Exception exception) {
			throw new RepositoryException(exception.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeXEntity> listarEjeX(Integer idCalificado) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificarMision.listarEjeX");
			spq.setParameter("P_ID_CALIFICADO", idCalificado);
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
				ejeX.setIdCalificacion(Integer.parseInt(String.valueOf(obj[5])));
				ejeX.setIdCalificador(obj[6] != null ? Integer.parseInt(String.valueOf(obj[6])): null);
				
				 
				lstEjeX.add(ejeX);

			}
			return lstEjeX;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<EjeYEntity> listarEjeY(Integer idCalificado) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificarMision.listarEjeY");
			spq.setParameter("P_ID_CALIFICADO", idCalificado);
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
	public List<EjeInterseccionACalificarEntity> listarEjeInterseccionACalificar(Integer idCalificado) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificarMision.listarInterseccion");
			spq.setParameter("P_ID_CALIFICADO", idCalificado); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EjeInterseccionACalificarEntity> lstEjeInterseccion = new ArrayList<>();

			for (Object[] obj : results) {
				EjeInterseccionACalificarEntity ejeInterseccion = new EjeInterseccionACalificarEntity();
				
				ejeInterseccion.setIdCalificacion(Integer.parseInt(String.valueOf(obj[0])));
				ejeInterseccion.setIdDetalleCalificacion(Integer.parseInt(String.valueOf(obj[1])));
				ejeInterseccion.setIdManiobra(Integer.parseInt(String.valueOf(obj[2])));
				ejeInterseccion.setIdEstandarRequerido(Integer.parseInt(String.valueOf(obj[3])));
				ejeInterseccion.setCoCodigoEstandarRequerido(String.valueOf(obj[4]));
				ejeInterseccion.setIdEstandarObtenido(obj[5] != null ? Integer.parseInt(String.valueOf(obj[5])): null);
				ejeInterseccion.setCoCodigoEstandarObtenido(obj[6] != null ? String.valueOf(obj[6]): null);
				 
				lstEjeInterseccion.add(ejeInterseccion);

			}
			return lstEjeInterseccion;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeXEntity> listarEjeXPorIdCalificacion(Integer idCalificado, Integer idCalificacion)
			throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificarMision.listarEjeXPorIdCalificacion");
			spq.setParameter("P_ID_CALIFICADO", idCalificado);
			spq.setParameter("P_ID_CALIFICACION", idCalificacion);
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
				ejeX.setIdCalificacion(Integer.parseInt(String.valueOf(obj[5])));
				 
				lstEjeX.add(ejeX);

			}
			return lstEjeX;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EjeInterseccionACalificarEntity> listarEjeInterseccionACalificarPorIdCalificacion(Integer idCalificado,
			Integer idCalificacion) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificarMision.listarInterseccionPorIdCalificacion");
			spq.setParameter("P_ID_CALIFICADO", idCalificado);
			spq.setParameter("P_ID_CALIFICACION", idCalificacion);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EjeInterseccionACalificarEntity> lstEjeInterseccion = new ArrayList<>();

			for (Object[] obj : results) {
				EjeInterseccionACalificarEntity ejeInterseccion = new EjeInterseccionACalificarEntity();
				
				ejeInterseccion.setIdCalificacion(Integer.parseInt(String.valueOf(obj[0])));
				ejeInterseccion.setIdDetalleCalificacion(Integer.parseInt(String.valueOf(obj[1])));
				ejeInterseccion.setIdManiobra(Integer.parseInt(String.valueOf(obj[2])));
				ejeInterseccion.setIdEstandarRequerido(Integer.parseInt(String.valueOf(obj[3])));
				ejeInterseccion.setCoCodigoEstandarRequerido(String.valueOf(obj[4]));
				ejeInterseccion.setIdEstandarObtenido(obj[5] != null ? Integer.parseInt(String.valueOf(obj[5])): null);
				ejeInterseccion.setCoCodigoEstandarObtenido(obj[6] != null ? String.valueOf(obj[6]): null);
				 
				lstEjeInterseccion.add(ejeInterseccion);

			}
			return lstEjeInterseccion;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public String calificarManiobra(Integer idManiobra, Integer idCalificacion, Integer idEstandarObtenido)
			throws RepositoryException {
		String mensaje = "";
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("calificacion.calificarManiobra");

			spq.setParameter("P_ID_MANIOBRA", idManiobra);
			spq.setParameter("P_ID_CALIFICACION", idCalificacion);
			spq.setParameter("P_ID_ESTANDAR", idEstandarObtenido); 

			spq.execute();

			mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RepositoryException("Error al calificar maniobra");
		}
	}

}
