package pe.mil.fap.model.administration;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BancoManiobraDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idBancoManiobra;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	private Integer flEstado;

	public BancoManiobraDTO() {
		super();
	}

	public BancoManiobraDTO(Integer idBancoManiobra,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flEstado) {
		super();
		this.idBancoManiobra = idBancoManiobra;
		this.noNombre = noNombre;
		this.flEstado = flEstado;
	}

	public Integer getIdBancoManiobra() {
		return idBancoManiobra;
	}

	public void setIdBancoManiobra(Integer idBancoManiobra) {
		this.idBancoManiobra = idBancoManiobra;
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
