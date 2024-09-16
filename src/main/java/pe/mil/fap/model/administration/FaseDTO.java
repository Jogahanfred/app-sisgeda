package pe.mil.fap.model.administration;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idFase;

	@NotNull(message = "El ID banco fase no puede ser vacio")
	private Integer idBancoFase;

	@NotNull(message = "El programa no puede ser vacio")
	private Integer idPrograma;

	@NotNull(message = "El número total de horas no puede ser vacio")
	private Integer nuTotalHora;

	@NotNull(message = "El número total de subfases no puede ser vacio")
	private Integer nuTotalSubFase;

	@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad del programa no puede ser vacio")
	private String txFinalidad;

	private Integer flBloqueado;

	private Integer flEstado;

	private String txDescripcionFase;

	private String txDescripcionPrograma;
	
	private Integer idEscuadron;
	
	private List<SubFaseDTO> lstSubFases;
	
	public FaseDTO(Integer idFase, @NotNull(message = "El ID banco fase no puede ser vacio") Integer idBancoFase,
			@NotNull(message = "El programa no puede ser vacio") Integer idPrograma,
			@NotNull(message = "El número total de horas no puede ser vacio") Integer nuTotalHora,
			@NotNull(message = "El número total de subfases no puede ser vacio") Integer nuTotalSubFase,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionFase, String txDescripcionPrograma, Integer idEscuadron) {
		super();
		this.idFase = idFase;
		this.idBancoFase = idBancoFase;
		this.idPrograma = idPrograma;
		this.nuTotalHora = nuTotalHora;
		this.nuTotalSubFase = nuTotalSubFase;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionFase = txDescripcionFase;
		this.txDescripcionPrograma = txDescripcionPrograma;
		this.idEscuadron = idEscuadron;
	}

	public FaseDTO() {
		super();
	}

	public Integer getIdFase() {
		return idFase;
	}

	public void setIdFase(Integer idFase) {
		this.idFase = idFase;
	}

	public Integer getIdBancoFase() {
		return idBancoFase;
	}

	public void setIdBancoFase(Integer idBancoFase) {
		this.idBancoFase = idBancoFase;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
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

	public String getTxDescripcionFase() {
		return txDescripcionFase;
	}

	public void setTxDescripcionFase(String txDescripcionFase) {
		this.txDescripcionFase = txDescripcionFase;
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

	public List<SubFaseDTO> getLstSubFases() {
		return lstSubFases;
	}

	public void setLstSubFases(List<SubFaseDTO> lstSubFases) {
		this.lstSubFases = lstSubFases;
	}
	
	
}
