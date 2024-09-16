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
		@NamedStoredProcedureQuery(name = "detalleMision.insertar", 
								   procedureName = "PKG_DETALLE_MISION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MANIOBRA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESTANDAR_REQUERIDO", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ORDEN", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_ID_DETALLE_MISION", type = Integer.class)
		}),
		@NamedStoredProcedureQuery(name = "detalleMision.actualizarEstandar", 
								   procedureName = "PKG_GESTION_SUB_FASE.SP_ACTUALIZAR_ESTANDAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MANIOBRA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESTANDAR", type = Integer.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
})		
})
@Entity(name = "DetalleMisionEntity")
@Table(name = "TBL_DETALLE_MISION")
public class DetalleMisionEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_DETALLE_MISION")
	private Integer idDetalleMision;

	@NotNull(message = "La misión no puede ser vacia")
	@Column(name = "ID_MISION", nullable = false)
	private Integer idMision;

	@NotNull(message = "La maniobra no puede ser vacia")
	@Column(name = "ID_MANIOBRA", nullable = false)
	private Integer idManiobra;

	@NotNull(message = "El estandar no puede ser vacio")
	@Column(name = "ID_ESTANDAR_REQUERIDO", nullable = false)
	private Integer idEstandarRequerido;

	@NotNull(message = "El orden no puede ser vacio")
	@Column(name = "NU_ORDEN", nullable = false)
	private Integer nuOrden;
	 
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public DetalleMisionEntity() {
		super();
	}

	public DetalleMisionEntity(Integer idDetalleMision,
			@NotNull(message = "La misión no puede ser vacia") Integer idMision,
			@NotNull(message = "La maniobra no puede ser vacia") Integer idManiobra,
			@NotNull(message = "El estandar no puede ser vacio") Integer idEstandarRequerido,
			@NotNull(message = "El orden no puede ser vacio") Integer nuOrden, Integer flBloqueado, Integer flEstado) {
		super();
		this.idDetalleMision = idDetalleMision;
		this.idMision = idMision;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.nuOrden = nuOrden;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
	}

	public Integer getIdDetalleMision() {
		return idDetalleMision;
	}

	public void setIdDetalleMision(Integer idDetalleMision) {
		this.idDetalleMision = idDetalleMision;
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
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

	public Integer getNuOrden() {
		return nuOrden;
	}

	public void setNuOrden(Integer nuOrden) {
		this.nuOrden = nuOrden;
	}

	public Integer getFlBloqueado() {
		return flBloqueado;
	}

	public void setFlBloqueado(Integer flBloqueado) {
		this.flBloqueado = flBloqueado;
	}

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

}
