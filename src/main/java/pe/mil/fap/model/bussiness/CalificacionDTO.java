package pe.mil.fap.model.bussiness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 

public class CalificacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCalificacion;

	@NotNull(message = "La mision no puede ser vacio")
	private Integer idMision;

	private Integer idAeronave;

	@NotNull(message = "El calificado  no puede ser vacia")
	private Integer idCalificado;

	private Integer idCalificador;

	private Double qtNota;

	@Size(max = 50, message = "La calificacion de vuelo debe tener {max} caracteres")
	private String coCalificacionVuelo;

	private String feProgramacion;

	private String feEjecucion;

	private Integer flActualizacion;

	private String txObservacion;

	private String txRecomendacion;
 
	private List<DetalleCalificacionDTO> lstDetalleCalificacion = new ArrayList<>();

	public CalificacionDTO(Integer idCalificacion, @NotNull(message = "La mision no puede ser vacio") Integer idMision,
			Integer idAeronave, @NotNull(message = "El calificado  no puede ser vacia") Integer idCalificado,
			Integer idCalificador, Double qtNota,
			@Size(max = 50, message = "La calificacion de vuelo debe tener {max} caracteres") String coCalificacionVuelo,
			String feProgramacion, String feEjecucion, Integer flActualizacion, String txObservacion,
			String txRecomendacion, List<DetalleCalificacionDTO> lstDetalleCalificacion) {
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

	public CalificacionDTO() {
		super();
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

	public List<DetalleCalificacionDTO> getLstDetalleCalificacion() {
		return lstDetalleCalificacion;
	}

	public void setLstDetalleCalificacion(List<DetalleCalificacionDTO> lstDetalleCalificacion) {
		this.lstDetalleCalificacion = lstDetalleCalificacion;
	}

}
