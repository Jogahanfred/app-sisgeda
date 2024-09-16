package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OperacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idOperacion;

	@NotNull(message = "El escuadrón no puede ser vacio")
	private Integer idEscuadron;

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	private Integer flBloqueado;

	private Integer flEstado;

	private String txDescripcionEscuadron;

	public OperacionDTO(Integer idOperacion, @NotNull(message = "El escuadrón no puede ser vacio") Integer idEscuadron,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flBloqueado, Integer flEstado, String txDescripcionEscuadron) {
		super();
		this.idOperacion = idOperacion;
		this.idEscuadron = idEscuadron;
		this.noNombre = noNombre;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

	public OperacionDTO() {
		super();
	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
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

	public String getTxDescripcionEscuadron() {
		return txDescripcionEscuadron;
	}

	public void setTxDescripcionEscuadron(String txDescripcionEscuadron) {
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

}
