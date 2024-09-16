package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EscuadronDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEscuadron;

	@NotNull(message = "La unidad no puede ser vacio")
	private Integer idUnidad;

	@Size(max = 5, message = "La sigla debe tener máximo {max} caracteres")
	@NotEmpty(message = "La sigla no puede ser vacia")
	private String coSigla;

	@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacia")
	private String txDescripcion;

	private Integer flBloqueado;

	private Integer flEstado;

	private String txRutaLogo;

	private String txInformacion;

	private String txDescripcionUnidad;

	public EscuadronDTO() {
		super();
	}

	public EscuadronDTO(Integer idEscuadron, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@Size(max = 5, message = "La sigla debe tener máximo {max} caracteres") @NotEmpty(message = "La sigla no puede ser vacia") String coSigla,
			@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacia") String txDescripcion,
			Integer flBloqueado, Integer flEstado, String txRutaLogo, String txInformacion,
			String txDescripcionUnidad) {
		super();
		this.idEscuadron = idEscuadron;
		this.idUnidad = idUnidad;
		this.coSigla = coSigla;
		this.txDescripcion = txDescripcion;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txRutaLogo = txRutaLogo;
		this.txInformacion = txInformacion;
		this.txDescripcionUnidad = txDescripcionUnidad;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCoSigla() {
		return coSigla;
	}

	public void setCoSigla(String coSigla) {
		this.coSigla = coSigla;
	}

	public String getTxDescripcion() {
		return txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
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

	public String getTxRutaLogo() {
		return txRutaLogo;
	}

	public void setTxRutaLogo(String txRutaLogo) {
		this.txRutaLogo = txRutaLogo;
	}

	public String getTxInformacion() {
		return txInformacion;
	}

	public void setTxInformacion(String txInformacion) {
		this.txInformacion = txInformacion;
	}

	public String getTxDescripcionUnidad() {
		return txDescripcionUnidad;
	}

	public void setTxDescripcionUnidad(String txDescripcionUnidad) {
		this.txDescripcionUnidad = txDescripcionUnidad;
	}
}
