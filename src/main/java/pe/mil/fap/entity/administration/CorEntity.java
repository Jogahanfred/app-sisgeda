package pe.mil.fap.entity.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;  
import java.io.Serializable;

import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.ParameterMode;

@NamedStoredProcedureQueries({ 
		@NamedStoredProcedureQuery(name = "cor.insertar", 
								   procedureName = "PKG_COR.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_DETALLE_CALIFICACION", type = Integer.class),  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TX_CAUSA", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TX_OBSERVACION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TX_RECOMENDACION", type = String.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "cor.listar", 
								   procedureName = "PKG_COR.SP_LISTAR", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_DETALLE_CALIFICACION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
				
})

@Entity(name = "CorEntity")
@Table(name = "TBL_COR")
public class CorEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_COR")
	private Integer idCor;

	@NotNull(message = "El Detalle de la calificación no puede ser vacio")
	@Column(name = "ID_DETALLE_CALIFICACION", nullable = false)
	private Integer idDetalleCalificacion; 

	@Size(max = 200, message = "La causa debe tener máximo {max} caracteres")
	@NotEmpty(message = "La causa no puede ser vacio")
	@Column(name = "TX_CAUSA", nullable = false)
	private String txCausa;

	@Size(max = 200, message = "La observación debe tener máximo {max} caracteres")
	@NotEmpty(message = "La observacion no puede ser vacio")
	@Column(name = "TX_OBSERVACION", nullable = false)
	private String txObservacion;

	@Size(max = 200, message = "La recomendación debe tener máximo {max} caracteres")
	@NotEmpty(message = "La recomendación no puede ser vacio")
	@Column(name = "TX_RECOMENDACION", nullable = false)
	private String txRecomendacion;

	@Column(name = "FE_REGISTRO", nullable = true)
	private String feRegistro;

	@Column(name = "FE_ACTUALIZACION", nullable = true)
	private String feActualizacion;
	 

	public CorEntity() {
	} 

	public CorEntity(Integer idCor,
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

	@Override
	public String toString() {
		return "CorEntity [idCor=" + idCor + ", idDetalleCalificacion=" + idDetalleCalificacion + ", txCausa=" + txCausa
				+ ", txObservacion=" + txObservacion + ", txRecomendacion=" + txRecomendacion + ", feRegistro="
				+ feRegistro + ", feActualizacion=" + feActualizacion + "]";
	}
  

}
