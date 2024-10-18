package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCor;

	@NotNull(message = "El Detalle de la calificación no puede ser vacio")
	private Integer idDetalleCalificacion;

	@Size(max = 200, message = "La causa debe tener máximo {max} caracteres")
	@NotEmpty(message = "La causa no puede ser vacio")
	private String txCausa;

	@Size(max = 200, message = "La observación debe tener máximo {max} caracteres")
	@NotEmpty(message = "La observacion no puede ser vacio")
	private String txObservacion;

	@Size(max = 200, message = "La recomendación debe tener máximo {max} caracteres")
	@NotEmpty(message = "La recomendación no puede ser vacio")
	private String txRecomendacion;

	private String feRegistro;

	private String feActualizacion;

	public CorDTO() {
		super();
	}

	public CorDTO(Integer idCor,
			@NotNull(message = "El Detalle de la calificación no puede ser vacio") Integer idDetalleCalificacion,
			@Size(max = 200, message = "La causa debe tener máximo {max} caracteres") @NotEmpty(message = "La causa no puede ser vacio") String txCausa,
			@Size(max = 200, message = "La observación debe tener máximo {max} caracteres") @NotEmpty(message = "La observacion no puede ser vacio") String txObservacion,
			@Size(max = 200, message = "La recomendación debe tener máximo {max} caracteres") @NotEmpty(message = "La recomendación no puede ser vacio") String txRecomendacion,
			String feRegistro, String feActualizacion) {
		super();
		this.idCor = idCor;
		this.idDetalleCalificacion = idDetalleCalificacion;
		this.txCausa = txCausa;
		this.txObservacion = txObservacion;
		this.txRecomendacion = txRecomendacion;
		this.feRegistro = feRegistro;
		this.feActualizacion = feActualizacion;
	}

	public Integer getIdCor() {
		return idCor;
	}

	public void setIdCor(Integer idCor) {
		this.idCor = idCor;
	}

	public Integer getIdDetalleCalificacion() {
		return idDetalleCalificacion;
	}

	public void setIdDetalleCalificacion(Integer idDetalleCalificacion) {
		this.idDetalleCalificacion = idDetalleCalificacion;
	}

	public String getTxCausa() {
		return txCausa;
	}

	public void setTxCausa(String txCausa) {
		this.txCausa = txCausa;
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

	public String getFeRegistro() {
		return feRegistro;
	}

	public void setFeRegistro(String feRegistro) {
		this.feRegistro = feRegistro;
	}

	public String getFeActualizacion() {
		return feActualizacion;
	}

	public void setFeActualizacion(String feActualizacion) {
		this.feActualizacion = feActualizacion;
	}

}
