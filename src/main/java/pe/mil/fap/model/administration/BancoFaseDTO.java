package pe.mil.fap.model.administration;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BancoFaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idBancoFase;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	private Integer flEstado;

	public BancoFaseDTO() {
		super();
	}

	public BancoFaseDTO(Integer idBancoFase,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flEstado) {
		super();
		this.idBancoFase = idBancoFase;
		this.noNombre = noNombre;
		this.flEstado = flEstado;
	}

	public Integer getIdBancoFase() {
		return idBancoFase;
	}

	public void setIdBancoFase(Integer idBancoFase) {
		this.idBancoFase = idBancoFase;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

}
