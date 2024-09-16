package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MiembroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idMiembro;

	@NotNull(message = "La unidad no puede ser vacio")
	private Integer idUnidad;

	@Size(max = 10, message = "El rol debe tener {max} caracteres")
	@NotEmpty(message = "El rol no puede ser vacio")
	private String noRol;
	
	@Size(max = 8, message = "El nsa debe tener {max} caracteres")
	@NotEmpty(message = "El nsa no puede ser vacio")
	private String coNsa;
	
	@NotNull(message = "El periodo no puede ser vacio")
	private Integer nuPeriodo;

	private Integer flBloqueado;
	
	private Integer flEstado;
	
	@Transient
	private PersonalDTO personal;

	public MiembroDTO() {
		super();
	}
 
	public MiembroDTO(Integer idMiembro, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@Size(max = 10, message = "El rol debe tener {max} caracteres") @NotEmpty(message = "El rol no puede ser vacio") String noRol,
			@Size(max = 8, message = "El nsa debe tener {max} caracteres") @NotEmpty(message = "El nsa no puede ser vacio") String coNsa,
			@NotNull(message = "El periodo no puede ser vacio") Integer nuPeriodo, Integer flBloqueado,
			Integer flEstado) {
		super();
		this.idMiembro = idMiembro;
		this.idUnidad = idUnidad;
		this.noRol = noRol;
		this.coNsa = coNsa;
		this.nuPeriodo = nuPeriodo;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado; 
	}

	public Integer getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(Integer idMiembro) {
		this.idMiembro = idMiembro;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCoNsa() {
		return coNsa;
	}

	public void setCoNsa(String coNsa) {
		this.coNsa = coNsa;
	}

	public Integer getNuPeriodo() {
		return nuPeriodo;
	}

	public void setNuPeriodo(Integer nuPeriodo) {
		this.nuPeriodo = nuPeriodo;
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

	public PersonalDTO getPersonal() {
		return personal;
	}

	public void setPersonal(PersonalDTO personal) {
		this.personal = personal;
	}

	public String getNoRol() {
		return noRol;
	}

	public void setNoRol(String noRol) {
		this.noRol = noRol;
	}

	@Override
	public String toString() {
		return "MiembroDTO [idMiembro=" + idMiembro + ", idUnidad=" + idUnidad + ", coNsa=" + coNsa
				+ ", nuPeriodo=" + nuPeriodo + ", flBloqueado=" + flBloqueado + ", flEstado=" + flEstado + "]";
	}
	
	

}
