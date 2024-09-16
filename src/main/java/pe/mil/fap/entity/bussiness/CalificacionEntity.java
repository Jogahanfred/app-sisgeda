package pe.mil.fap.entity.bussiness;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient; 
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "calificacion.insertar", 
								   procedureName = "PKG_CALIFICACION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICADO", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_ID_CALIFICACION", type = Integer.class)
		})
})

@Entity(name = "CalificacionEntity")
@Table(name = "TBL_CALIFICACION")
public class CalificacionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CALIFICACION")
	private Integer idCalificacion;

	@NotNull(message = "La mision no puede ser vacio")
	@Column(name = "ID_MISION", nullable = false)
	private Integer idMision;

	@Column(name = "ID_AERONAVE", nullable = true)
	private Integer idAeronave;

	@NotNull(message = "El calificado  no puede ser vacia")
	@Column(name = "ID_CALIFICADO", nullable = false)
	private Integer idCalificado;

	@Column(name = "ID_CALIFICADOR", nullable = true)
	private Integer idCalificador;
	
	@Column(name = "QT_NOTA", nullable = true)
	private Double qtNota;
	
	@Size(max = 50, message = "La calificacion de vuelo debe tener {max} caracteres")
	@Column(name = "CO_CALIFICACION_VUELO", nullable = true)
	private String coCalificacionVuelo;

	@Column(name = "FE_PROGRAMACION", nullable = true)
	private String feProgramacion;
	
	@Column(name = "FE_EJECUCION", nullable = true)
	private String feEjecucion;
		
	@Column(name = "FL_ACTUALIZACION", nullable = false)
	private Integer flActualizacion;
	
	@Column(name = "TX_OBSERVACION", nullable = true)
	private String txObservacion;

	@Column(name = "TX_RECOMENDACION", nullable = true)
	private String txRecomendacion;
	
	@Transient
	private List<DetalleCalificacionEntity> lstDetalleCalificacion;

	public CalificacionEntity() {
		super();
	}

	public CalificacionEntity(Integer idCalificacion,
			@NotNull(message = "La mision no puede ser vacio") Integer idMision, Integer idAeronave,
			@NotNull(message = "El calificado  no puede ser vacia") Integer idCalificado, Integer idCalificador,
			Double qtNota,
			@Size(max = 50, message = "La calificacion de vuelo debe tener {max} caracteres") String coCalificacionVuelo,
			String feProgramacion, String feEjecucion, Integer flActualizacion, String txObservacion,
			String txRecomendacion, List<DetalleCalificacionEntity> lstDetalleCalificacion) {
		super();
		this.idCalificacion = idCalificacion;
		this.idMision = idMision;
		this.idAeronave = idAeronave;
		this.idCalificado = idCalificado;
		this.idCalificador = idCalificador;
		this.qtNota = qtNota;
		this.coCalificacionVuelo = coCalificacionVuelo;
		this.feProgramacion = feProgramacion;
		this.feEjecucion = feEjecucion;
		this.flActualizacion = flActualizacion;
		this.txObservacion = txObservacion;
		this.txRecomendacion = txRecomendacion;
		this.lstDetalleCalificacion = lstDetalleCalificacion;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public Integer getIdAeronave() {
		return idAeronave;
	}

	public void setIdAeronave(Integer idAeronave) {
		this.idAeronave = idAeronave;
	}

	public Integer getIdCalificado() {
		return idCalificado;
	}

	public void setIdCalificado(Integer idCalificado) {
		this.idCalificado = idCalificado;
	}

	public Integer getIdCalificador() {
		return idCalificador;
	}

	public void setIdCalificador(Integer idCalificador) {
		this.idCalificador = idCalificador;
	}

	public Double getQtNota() {
		return qtNota;
	}

	public void setQtNota(Double qtNota) {
		this.qtNota = qtNota;
	}

	public String getCoCalificacionVuelo() {
		return coCalificacionVuelo;
	}

	public void setCoCalificacionVuelo(String coCalificacionVuelo) {
		this.coCalificacionVuelo = coCalificacionVuelo;
	}

	public String getFeProgramacion() {
		return feProgramacion;
	}

	public void setFeProgramacion(String feProgramacion) {
		this.feProgramacion = feProgramacion;
	}

	public String getFeEjecucion() {
		return feEjecucion;
	}

	public void setFeEjecucion(String feEjecucion) {
		this.feEjecucion = feEjecucion;
	}

	public Integer getFlActualizacion() {
		return flActualizacion;
	}

	public void setFlActualizacion(Integer flActualizacion) {
		this.flActualizacion = flActualizacion;
	}

	public String getTxObservacion() {
		return txObservacion;
	}

	public void setTxObservacion(String txObservacion) {
		this.txObservacion = txObservacion;
	}

	public String getTxRecomendacion() {
		return txRecomendacion;
	}

	public void setTxRecomendacion(String txRecomendacion) {
		this.txRecomendacion = txRecomendacion;
	}

	public List<DetalleCalificacionEntity> getLstDetalleCalificacion() {
		return lstDetalleCalificacion;
	}

	public void setLstDetalleCalificacion(List<DetalleCalificacionEntity> lstDetalleCalificacion) {
		this.lstDetalleCalificacion = lstDetalleCalificacion;
	}
	
}