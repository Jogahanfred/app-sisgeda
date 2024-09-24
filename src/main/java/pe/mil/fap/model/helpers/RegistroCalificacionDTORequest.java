package pe.mil.fap.model.helpers;

import java.io.Serializable;
import java.util.List;

public class RegistroCalificacionDTORequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idMision; 
	private Integer idInstructor;
	private Integer idGrupo;

	public RegistroCalificacionDTORequest() {
		super();
	}
 
	public Integer getIdMision() {
		return idMision;
	}


	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}


	public Integer getIdInstructor() {
		return idInstructor;
	}

	public void setIdInstructor(Integer idInstructor) {
		this.idInstructor = idInstructor;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
}
