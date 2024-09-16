package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UnidadDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idUnidad;

	@NotNull(message = "El código no puede ser vacio")
	private Integer nuCodigo;

	@Size(min = 1, max = 5, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	@Size(min = 5, max = 70, message = "La descripción debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacio")
	private String txDescripcion;

	@NotNull(message = "El nivel no puede ser vacio")
	private Integer nuNivel;

	@NotNull(message = "El código rector no puede ser vacio")
	private Integer nuCodigoRector;

	private Integer flBloqueado;

	private Integer flEstado;

	private String txRutaLogo;

	private String txUbicacion;

	private String txInformacion;

	private String txDescripcionOrganoRector;
	  
	public UnidadDTO() {
		super();
	}

	public UnidadDTO(Integer idUnidad, @NotNull(message = "El código no puede ser vacio") Integer nuCodigo,
			@Size(min = 1, max = 5, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(min = 5, max = 70, message = "La descripción debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacio") String txDescripcion,
			@NotNull(message = "El nivel no puede ser vacio") Integer nuNivel,
			@NotNull(message = "El código rector no puede ser vacio") Integer nuCodigoRector, Integer flBloqueado,
			Integer flEstado, String txRutaLogo, String txUbicacion, String txInformacion,
			String txDescripcionOrganoRector) {
		super();
		this.idUnidad = idUnidad;
		this.nuCodigo = nuCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.nuNivel = nuNivel;
		this.nuCodigoRector = nuCodigoRector;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txRutaLogo = txRutaLogo;
		this.txUbicacion = txUbicacion;
		this.txInformacion = txInformacion;
		this.txDescripcionOrganoRector = txDescripcionOrganoRector;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Integer getNuCodigo() {
		return nuCodigo;
	}

	public void setNuCodigo(Integer nuCodigo) {
		this.nuCodigo = nuCodigo;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public String getTxDescripcion() {
		return txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public Integer getNuNivel() {
		return nuNivel;
	}

	public void setNuNivel(Integer nuNivel) {
		this.nuNivel = nuNivel;
	}

	public Integer getNuCodigoRector() {
		return nuCodigoRector;
	}

	public void setNuCodigoRector(Integer nuCodigoRector) {
		this.nuCodigoRector = nuCodigoRector;
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

	public String getTxUbicacion() {
		return txUbicacion;
	}

	public void setTxUbicacion(String txUbicacion) {
		this.txUbicacion = txUbicacion;
	}

	public String getTxInformacion() {
		return txInformacion;
	}

	public void setTxInformacion(String txInformacion) {
		this.txInformacion = txInformacion;
	}

	public String getTxDescripcionOrganoRector() {
		return txDescripcionOrganoRector;
	}

	public void setTxDescripcionOrganoRector(String txDescripcionOrganoRector) {
		this.txDescripcionOrganoRector = txDescripcionOrganoRector;
	}

	@Override
	public String toString() {
		return "UnidadDTO [idUnidad=" + idUnidad + ", nuCodigo=" + nuCodigo + ", noNombre=" + noNombre
				+ ", txDescripcion=" + txDescripcion + ", nuNivel=" + nuNivel + ", nuCodigoRector=" + nuCodigoRector
				+ ", flBloqueado=" + flBloqueado + ", flEstado=" + flEstado + ", txRutaLogo=" + txRutaLogo
				+ ", txUbicacion=" + txUbicacion + ", txInformacion=" + txInformacion + "]";
	}

}
