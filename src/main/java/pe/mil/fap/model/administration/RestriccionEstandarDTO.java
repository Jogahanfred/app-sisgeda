package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RestriccionEstandarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idRestriccionEstandar;
	private Integer idDetalleMision;
	private Integer idEstandar;

	@Size(max = 200, message = "El mensaje debe ser {max} caracteres")
	@NotEmpty(message = "El mensaje no puede ser vacio")
	private String txMensaje;

	private Integer flBloqueado;
	private Integer flEstado;
	
	private String txDescripcionEstandar;
	private Integer nuNivelEstandar;

	public RestriccionEstandarDTO(Integer idRestriccionEstandar, Integer idDetalleMision, Integer idEstandar,
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

	public RestriccionEstandarDTO() {
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
