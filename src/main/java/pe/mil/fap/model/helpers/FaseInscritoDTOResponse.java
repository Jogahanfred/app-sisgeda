package pe.mil.fap.model.helpers;

import java.io.Serializable;

public class FaseInscritoDTOResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idFase;
	private String noNombre;
	private Integer nuTotalHora;
	private Integer nuTotalSubFase; 

	public FaseInscritoDTOResponse() {
		super();
	}

	public Integer getIdFase() {
		return idFase;
	}

	public void setIdFase(Integer idFase) {
		this.idFase = idFase;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public Integer getNuTotalHora() {
		return nuTotalHora;
	}

	public void setNuTotalHora(Integer nuTotalHora) {
		this.nuTotalHora = nuTotalHora;
	}

	public Integer getNuTotalSubFase() {
		return nuTotalSubFase;
	}

	public void setNuTotalSubFase(Integer nuTotalSubFase) {
		this.nuTotalSubFase = nuTotalSubFase;
	}
 

}
