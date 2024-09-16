package pe.mil.fap.model.administration;

import java.io.Serializable; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SubFaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	private Integer idSubFase;
	
	@NotNull(message = "El ID banco sub fase no puede ser vacio") 
	private Integer idBancoSubFase;
	
	@NotNull(message = "La fase no puede ser vacio") 
	private Integer idFase;
	
	@NotNull(message = "El número total de horas no puede ser vacio") 
	private Integer nuTotalHora;

	@NotNull(message = "El número total de misiones no puede ser vacio") 
	private Integer nuTotalMision;
	
	@NotNull(message = "El número total de maniobras no puede ser vacio") 
	private Integer nuTotalManiobra;
	
	@Size(max = 10, message = "El código debe tener {max} caracteres")
	@NotEmpty(message = "La código no puede ser vacio") 
	private String coCodigo;
	
	@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad del programa no puede ser vacio") 
	private String txFinalidad;
	 
	private Integer flBloqueado;
	 
	private Integer flEstado;
 
	private String txDescripcionSubFase;
	 
	private String txDescripcionFase;
	 
	private Integer idPrograma;
 
	private String txDescripcionPrograma;

	private Integer idEscuadron;
	
	private Integer idBancoFase;

	public SubFaseDTO(Integer idSubFase,
			@NotNull(message = "El ID banco sub fase no puede ser vacio") Integer idBancoSubFase,
			@NotNull(message = "La fase no puede ser vacio") Integer idFase,
			@NotNull(message = "El número total de horas no puede ser vacio") Integer nuTotalHora,
			@NotNull(message = "El número total de misiones no puede ser vacio") Integer nuTotalMision,
			@NotNull(message = "El número total de maniobras no puede ser vacio") Integer nuTotalManiobra,
			@Size(max = 10, message = "El código debe tener {max} caracteres") @NotEmpty(message = "La código no puede ser vacio") String coCodigo,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionSubFase, String txDescripcionFase,
			Integer idPrograma, String txDescripcionPrograma, Integer idEscuadron, Integer idBancoFase) {
		super();
		this.idSubFase = idSubFase;
		this.idBancoSubFase = idBancoSubFase;
		this.idFase = idFase;
		this.nuTotalHora = nuTotalHora;
		this.nuTotalMision = nuTotalMision;
		this.nuTotalManiobra = nuTotalManiobra;
		this.coCodigo = coCodigo;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionSubFase = txDescripcionSubFase;
		this.txDescripcionFase = txDescripcionFase;
		this.idPrograma = idPrograma;
		this.txDescripcionPrograma = txDescripcionPrograma;
		this.idEscuadron = idEscuadron;
		this.idBancoFase = idBancoFase;
	}

	public SubFaseDTO() {
		super();
	}

	public Integer getIdSubFase() {
		return idSubFase;
	}

	public void setIdSubFase(Integer idSubFase) {
		this.idSubFase = idSubFase;
	}

	public Integer getIdBancoSubFase() {
		return idBancoSubFase;
	}

	public void setIdBancoSubFase(Integer idBancoSubFase) {
		this.idBancoSubFase = idBancoSubFase;
	}

	public Integer getIdFase() {
		return idFase;
	}

	public void setIdFase(Integer idFase) {
		this.idFase = idFase;
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

	public String getTxFinalidad() {
		return txFinalidad;
	}

	public void setTxFinalidad(String txFinalidad) {
		this.txFinalidad = txFinalidad;
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

	public String getTxDescripcionSubFase() {
		return txDescripcionSubFase;
	}

	public void setTxDescripcionSubFase(String txDescripcionSubFase) {
		this.txDescripcionSubFase = txDescripcionSubFase;
	}

	public String getTxDescripcionFase() {
		return txDescripcionFase;
	}

	public void setTxDescripcionFase(String txDescripcionFase) {
		this.txDescripcionFase = txDescripcionFase;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getTxDescripcionPrograma() {
		return txDescripcionPrograma;
	}

	public void setTxDescripcionPrograma(String txDescripcionPrograma) {
		this.txDescripcionPrograma = txDescripcionPrograma;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public Integer getIdBancoFase() {
		return idBancoFase;
	}

	public void setIdBancoFase(Integer idBancoFase) {
		this.idBancoFase = idBancoFase;
	}

	@Override
	public String toString() {
		return "SubFaseDTO [idSubFase=" + idSubFase + ", idBancoSubFase=" + idBancoSubFase + ", idFase=" + idFase
				+ ", nuTotalHora=" + nuTotalHora + ", nuTotalMision=" + nuTotalMision + ", nuTotalManiobra="
				+ nuTotalManiobra + ", coCodigo=" + coCodigo + ", txFinalidad=" + txFinalidad + ", flBloqueado="
				+ flBloqueado + ", flEstado=" + flEstado + ", txDescripcionSubFase=" + txDescripcionSubFase
				+ ", txDescripcionFase=" + txDescripcionFase + ", idPrograma=" + idPrograma + ", txDescripcionPrograma="
				+ txDescripcionPrograma + ", idEscuadron=" + idEscuadron + ", idBancoFase=" + idBancoFase + "]";
	}

}
