package pe.mil.fap.model.helpers;

import java.io.Serializable;

public class SubFaseInscritoDTOResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idSubFase;
	private String noNombre;
	private Integer nuTotalHora;
	private Integer nuTotalMision;
	private Integer nuTotalManiobra;
	private String coCodigo; 

	public SubFaseInscritoDTOResponse() {
		super();
	}

	public Integer getIdSubFase() {
		return idSubFase;
	}

	public void setIdSubFase(Integer idSubFase) {
		this.idSubFase = idSubFase;
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

	public Integer getNuTotalMision() {
		return nuTotalMision;
	}

	public void setNuTotalMision(Integer nuTotalMision) {
		this.nuTotalMision = nuTotalMision;
	}

	public Integer getNuTotalManiobra() {
		return nuTotalManiobra;
	}

	public void setNuTotalManiobra(Integer nuTotalManiobra) {
		this.nuTotalManiobra = nuTotalManiobra;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
	}
 

}
