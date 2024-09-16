package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FlotaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idFlota;

	@Size(min = 4, max = 20, message = "El tipo de aeronave debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El tipo de aeronave no puede ser vacio")
	private String noTipoFlota;

	@Size(min = 4, max = 10, message = "El código de flota debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código de flota no puede ser vacio")
	private String coCodigo;

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	private Integer flBloqueado;

	private Integer flEstado;

	public FlotaDTO() {
		super();
	}

	public FlotaDTO(Integer idFlota,
			@Size(min = 4, max = 20, message = "El tipo de aeronave debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El tipo de aeronave no puede ser vacio") String noTipoFlota,
			@Size(min = 4, max = 10, message = "El código de flota debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código de flota no puede ser vacio") String coCodigo,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flBloqueado, Integer flEstado) {
		super();
		this.idFlota = idFlota;
		this.noTipoFlota = noTipoFlota;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
	}

	public Integer getIdFlota() {
		return idFlota;
	}

	public void setIdFlota(Integer idFlota) {
		this.idFlota = idFlota;
	}

	public String getNoTipoFlota() {
		return noTipoFlota;
	}

	public void setNoTipoFlota(String noTipoFlota) {
		this.noTipoFlota = noTipoFlota;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
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

}
