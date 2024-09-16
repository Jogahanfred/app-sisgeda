package pe.mil.fap.model.bussiness;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.mil.fap.model.administration.PersonalDTO;

public class GrupoMiembroDTO implements Serializable{ 
	 
	private static final long serialVersionUID = 1L;
 
 
	private Integer idGrupo;

	@NotNull(message = "El miembro no puede ser vacio") 
	private Integer idMiembro;

	@Size(max = 15, message = "La situación debe tener {max} caracteres")
	@NotEmpty(message = "La situación no puede ser vacio") 
	private String noSituacion;
 
	private String feRegistro;
 
	private String feActualizacion;
	
	private PersonalDTO personal;

	public GrupoMiembroDTO() {
		super();
	}

	public GrupoMiembroDTO(Integer idGrupo, @NotNull(message = "El miembro no puede ser vacio") Integer idMiembro,
			@Size(max = 15, message = "La situación debe tener {max} caracteres") @NotEmpty(message = "La situación no puede ser vacio") String noSituacion,
			String feRegistro, String feActualizacion) {
		super();
		this.idGrupo = idGrupo;
		this.idMiembro = idMiembro;
		this.noSituacion = noSituacion;
		this.feRegistro = feRegistro;
		this.feActualizacion = feActualizacion;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(Integer idMiembro) {
		this.idMiembro = idMiembro;
	}

	public String getNoSituacion() {
		return noSituacion;
	}

	public void setNoSituacion(String noSituacion) {
		this.noSituacion = noSituacion;
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

	public PersonalDTO getPersonal() {
		return personal;
	}

	public void setPersonal(PersonalDTO personal) {
		this.personal = personal;
	}
	

}
