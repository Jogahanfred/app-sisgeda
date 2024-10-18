package pe.mil.fap.entity.helpers;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Transient;
import pe.mil.fap.entity.administration.RestriccionEstandarEntity;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "calificarMision.listarInterseccion", 
							   procedureName = "PKG_CALIFICAR_MISION.SP_LISTAR_INTERSECCION", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICADO", type = Integer.class),   
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}),
	@NamedStoredProcedureQuery(name = "calificarMision.listarInterseccionPorIdCalificacion", 
	   procedureName = "PKG_CALIFICAR_MISION.SP_LISTAR_INTERSECCION_POR_ID_CALIFICACION", 
	   parameters = {						
		    @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICADO", type = Integer.class),   
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICACION", type = Integer.class),   
				
			@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
})
})

@Entity(name = "EjeInterseccionACalificarEntity")
public class EjeInterseccionACalificarEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idCalificacion;
	private Integer idDetalleCalificacion;
	private Integer idManiobra;
	private Integer idEstandarRequerido;
	private String coCodigoEstandarRequerido;
	private Integer idEstandarObtenido;
	private String coCodigoEstandarObtenido;
	
	@Transient
	private List<RestriccionEstandarEntity> lstRestricciones;
	
	public EjeInterseccionACalificarEntity() {
		super();
	}

	public EjeInterseccionACalificarEntity(Integer idCalificacion, Integer idDetalleCalificacion, Integer idManiobra,
			Integer idEstandarRequerido, String coCodigoEstandarRequerido, Integer idEstandarObtenido,
			String coCodigoEstandarObtenido, List<RestriccionEstandarEntity> lstRestricciones) {
		super();
		this.idCalificacion = idCalificacion;
		this.idDetalleCalificacion = idDetalleCalificacion;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.coCodigoEstandarRequerido = coCodigoEstandarRequerido;
		this.idEstandarObtenido = idEstandarObtenido;
		this.coCodigoEstandarObtenido = coCodigoEstandarObtenido;
		this.lstRestricciones = lstRestricciones;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdDetalleCalificacion() {
		return idDetalleCalificacion;
	}

	public void setIdDetalleCalificacion(Integer idDetalleCalificacion) {
		this.idDetalleCalificacion = idDetalleCalificacion;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdEstandarRequerido() {
		return idEstandarRequerido;
	}

	public void setIdEstandarRequerido(Integer idEstandarRequerido) {
		this.idEstandarRequerido = idEstandarRequerido;
	}

	public String getCoCodigoEstandarRequerido() {
		return coCodigoEstandarRequerido;
	}

	public void setCoCodigoEstandarRequerido(String coCodigoEstandarRequerido) {
		this.coCodigoEstandarRequerido = coCodigoEstandarRequerido;
	}

	public Integer getIdEstandarObtenido() {
		return idEstandarObtenido;
	}

	public void setIdEstandarObtenido(Integer idEstandarObtenido) {
		this.idEstandarObtenido = idEstandarObtenido;
	}

	public String getCoCodigoEstandarObtenido() {
		return coCodigoEstandarObtenido;
	}

	public void setCoCodigoEstandarObtenido(String coCodigoEstandarObtenido) {
		this.coCodigoEstandarObtenido = coCodigoEstandarObtenido;
	}

	public List<RestriccionEstandarEntity> getLstRestricciones() {
		return lstRestricciones;
	}

	public void setLstRestricciones(List<RestriccionEstandarEntity> lstRestricciones) {
		this.lstRestricciones = lstRestricciones;
	}
 
}
