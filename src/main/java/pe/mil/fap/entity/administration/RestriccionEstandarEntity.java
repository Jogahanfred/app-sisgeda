			package pe.mil.fap.entity.administration;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "restriccionEstandar.listar", 
								   procedureName = "PKG_RESTRICCION_ESTANDAR.SP_LISTAR", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_DETALLE_MISION", type = Integer.class),
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "restriccionEstandar.insertar", 
								   procedureName = "PKG_RESTRICCION_ESTANDAR.SP_INSERTAR_RESTRICCION", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_DETALLE_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESTANDAR", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TX_MENSAJE", type = String.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "RestriccionEstandarEntity")
@Table(name = "TBL_RESTRICCION_ESTANDAR")
public class RestriccionEstandarEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_RESTRICCION_ESTANDAR")
	private Integer idRestriccionEstandar;

	@Column(name = "ID_DETALLE_MISION")
	private Integer idDetalleMision;
	
	@Column(name = "ID_ESTANDAR")
	private Integer idEstandar;
	
	@Size(max = 200, message = "El mensaje debe ser {max} caracteres")
	@NotEmpty(message = "El mensaje no puede ser vacio")
	@Column(name = "TX_MENSAJE", nullable = false)
	private String txMensaje;

	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Transient
	private String txDescripcionEstandar;
	
	@Transient
	private Integer nuNivelEstandar;

	public RestriccionEstandarEntity(Integer idRestriccionEstandar, Integer idDetalleMision, Integer idEstandar,
			@Size(max = 200, message = "El mensaje debe ser {max} caracteres") @NotEmpty(message = "El mensaje no puede ser vacio") String txMensaje,
			Integer flBloqueado, Integer flEstado, String txDescripcionEstandar, Integer nuNivelEstandar) {
		super();
		this.idRestriccionEstandar = idRestriccionEstandar;
		this.idDetalleMision = idDetalleMision;
		this.idEstandar = idEstandar;
		this.txMensaje = txMensaje;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionEstandar = txDescripcionEstandar;
		this.nuNivelEstandar = nuNivelEstandar;
	}

	public RestriccionEstandarEntity() {
		super();
	}

	public Integer getIdRestriccionEstandar() {
		return idRestriccionEstandar;
	}

	public void setIdRestriccionEstandar(Integer idRestriccionEstandar) {
		this.idRestriccionEstandar = idRestriccionEstandar;
	}

	public Integer getIdDetalleMision() {
		return idDetalleMision;
	}

	public void setIdDetalleMision(Integer idDetalleMision) {
		this.idDetalleMision = idDetalleMision;
	}

	public Integer getIdEstandar() {
		return idEstandar;
	}

	public void setIdEstandar(Integer idEstandar) {
		this.idEstandar = idEstandar;
	}

	public String getTxMensaje() {
		return txMensaje;
	}

	public void setTxMensaje(String txMensaje) {
		this.txMensaje = txMensaje;
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

	public String getTxDescripcionEstandar() {
		return txDescripcionEstandar;
	}

	public void setTxDescripcionEstandar(String txDescripcionEstandar) {
		this.txDescripcionEstandar = txDescripcionEstandar;
	}

	public Integer getNuNivelEstandar() {
		return nuNivelEstandar;
	}

	public void setNuNivelEstandar(Integer nuNivelEstandar) {
		this.nuNivelEstandar = nuNivelEstandar;
	}


}
