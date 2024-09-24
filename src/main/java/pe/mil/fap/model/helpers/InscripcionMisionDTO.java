package pe.mil.fap.model.helpers;

import java.io.Serializable;

public class InscripcionMisionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idMision;
	private String coCodigo;
	private Boolean flInscripcion;
 
	public InscripcionMisionDTO() {
		super();
	}

	public InscripcionMisionDTO(Integer idMision, String coCodigo, Boolean flInscripcion) {
		super();
		this.idMision = idMision;
		this.coCodigo = coCodigo;
		this.flInscripcion = flInscripcion;
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
	}

	public Boolean getFlInscripcion() {
		return flInscripcion;
	}

	public void setFlInscripcion(Boolean flInscripcion) {
		this.flInscripcion = flInscripcion;
	}

}
