package pe.mil.fap.entity.bussiness;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "detalleCalificacion.insertar", 
								   procedureName = "PKG_DETALLE_CALIFICACION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICACION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MANIOBRA", type = Integer.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_ID_DETALLE_CALIFICACION", type = Integer.class)
		})	
})
@Entity(name = "DetalleCalificacionEntity")
@Table(name = "TBL_DETALLE_CALIFICACION")
public class DetalleCalificacionEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_DETALLE_CALIFICACION")
	private Integer idDetalleCalificacion;

	@NotNull(message = "La cabecera calificación no puede ser vacia")
	@Column(name = "ID_CALIFICACION", nullable = false)
	private Integer idCalificacion;

	@NotNull(message = "La maniobra no puede ser vacia")
	@Column(name = "ID_MANIOBRA", nullable = false)
	private Integer idManiobra;

	@Column(name = "ID_ESTANDAR_OBTENIDO", nullable = true)
	private Integer idEstandarObtenido;

	public DetalleCalificacionEntity() {
		super();
	}

	public DetalleCalificacionEntity(Integer idDetalleCalificacion,
			@NotNull(message = "La cabecera calificación no puede ser vacia") Integer idCalificacion,
			@NotNull(message = "La maniobra no puede ser vacia") Integer idManiobra, Integer idEstandarObtenido) {
		super();
		this.idDetalleCalificacion = idDetalleCalificacion;
		this.idCalificacion = idCalificacion;
		this.idManiobra = idManiobra;
		this.idEstandarObtenido = idEstandarObtenido;
	}

	public Integer getIdDetalleCalificacion() {
		return idDetalleCalificacion;
	}

	public void setIdDetalleCalificacion(Integer idDetalleCalificacion) {
		this.idDetalleCalificacion = idDetalleCalificacion;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdEstandarObtenido() {
		return idEstandarObtenido;
	}

	public void setIdEstandarObtenido(Integer idEstandarObtenido) {
		this.idEstandarObtenido = idEstandarObtenido;
	}
	
	
}