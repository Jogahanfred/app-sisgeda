package pe.mil.fap.model.administration;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BancoSubFaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idBancoSubFase;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	private Integer flEstado;

	public BancoSubFaseDTO() {
		super();
	}

	public BancoSubFaseDTO(Integer idBancoSubFase,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flEstado) {
		super();
		this.idBancoSubFase = idBancoSubFase;
		this.noNombre = noNombre;
		this.flEstado = flEstado;
	}

	public Integer getIdBancoSubFase() {
		return idBancoSubFase;
	}

	public void setIdBancoSubFase(Integer idBancoSubFase) {
		this.idBancoSubFase = idBancoSubFase;
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
