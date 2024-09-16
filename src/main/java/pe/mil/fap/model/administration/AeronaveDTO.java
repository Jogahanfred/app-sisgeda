package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AeronaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idAeronave;

	@NotNull(message = "La unidad no puede ser vacio")
	private Integer idUnidad;

	@NotNull(message = "La flota no puede ser vacio")
	private Integer idFlota;

	@Size(max = 10, message = "El código de cola debe tener máximo {max} caracteres")
	@NotEmpty(message = "El codigó de cola no puede ser vacio")
	private String coNroCola;

	private Integer flEstado;

	private String txRutaImagen;
 
	private String txDescripcionUnidad;
	 
	private String txDescripcionFlota;
	
	public AeronaveDTO() {
		super();
	}  
 
	public AeronaveDTO(Integer idAeronave, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@NotNull(message = "La flota no puede ser vacio") Integer idFlota,
			@Size(max = 10, message = "El código de cola debe tener máximo {max} caracteres") @NotEmpty(message = "El codigó de cola no puede ser vacio") String coNroCola,
			Integer flEstado, String txRutaImagen, String txDescripcionUnidad, String txDescripcionFlota) {
		super();
		this.idAeronave = idAeronave;
		this.idUnidad = idUnidad;
		this.idFlota = idFlota;
		this.coNroCola = coNroCola;
		this.flEstado = flEstado;
		this.txRutaImagen = txRutaImagen;
		this.txDescripcionUnidad = txDescripcionUnidad;
		this.txDescripcionFlota = txDescripcionFlota;
	} 

	public Integer getIdAeronave() {
		return idAeronave;
	}

	public void setIdAeronave(Integer idAeronave) {
		this.idAeronave = idAeronave;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Integer getIdFlota() {
		return idFlota;
	}

	public void setIdFlota(Integer idFlota) {
		this.idFlota = idFlota;
	}

	public String getCoNroCola() {
		return coNroCola;
	}

	public void setCoNroCola(String coNroCola) {
		this.coNroCola = coNroCola;
	}

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

	public String getTxRutaImagen() {
		return txRutaImagen;
	}

	public void setTxRutaImagen(String txRutaImagen) {
		this.txRutaImagen = txRutaImagen;
	}

	public String getTxDescripcionUnidad() {
		return txDescripcionUnidad;
	}

	public void setTxDescripcionUnidad(String txDescripcionUnidad) {
		this.txDescripcionUnidad = txDescripcionUnidad;
	}

	public String getTxDescripcionFlota() {
		return txDescripcionFlota;
	}

	public void setTxDescripcionFlota(String txDescripcionFlota) {
		this.txDescripcionFlota = txDescripcionFlota;
	}
 
}
