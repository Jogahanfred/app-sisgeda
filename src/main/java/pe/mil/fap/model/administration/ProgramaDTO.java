package pe.mil.fap.model.administration;

import java.io.Serializable; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProgramaDTO implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private Integer idPrograma;
	
	@NotNull(message = "El escuadrón no puede ser vacio") 
	private Integer idEscuadron;
	
	@NotNull(message = "El periodo no puede ser vacio") 
	private Integer nuPeriodo;
	
	@Size(min = 3, max = 3, message = "El tipo de instrucción debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El tipo de instrucción no puede ser vacio") 
	private String noTipoInstruccion;
	
	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio") 
	private String noNombre;
	
	@Size(min = 4, max = 70, message = "La descripción del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La descripción del programa no puede ser vacio") 
	private String txDescripcion;

	@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad del programa no puede ser vacio") 
	private String txFinalidad;
	 
	private Integer flBloqueado;
 
	private Integer flEstado;
	
	private String txDescripcionEscuadron;
	
	private Integer idUnidad;
	
	public ProgramaDTO(Integer idPrograma, @NotNull(message = "El escuadrón no puede ser vacio") Integer idEscuadron,
			@NotNull(message = "El periodo no puede ser vacio") Integer nuPeriodo,
			@Size(min = 3, max = 3, message = "El tipo de instrucción debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El tipo de instrucción no puede ser vacio") String noTipoInstruccion,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(min = 4, max = 70, message = "La descripción del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La descripción del programa no puede ser vacio") String txDescripcion,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionEscuadron, Integer idUnidad) {
		super();
		this.idPrograma = idPrograma;
		this.idEscuadron = idEscuadron;
		this.nuPeriodo = nuPeriodo;
		this.noTipoInstruccion = noTipoInstruccion;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
		this.idUnidad = idUnidad;
	}

	public ProgramaDTO() {
		super();
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public Integer getNuPeriodo() {
		return nuPeriodo;
	}

	public void setNuPeriodo(Integer nuPeriodo) {
		this.nuPeriodo = nuPeriodo;
	}

	public String getNoTipoInstruccion() {
		return noTipoInstruccion;
	}

	public void setNoTipoInstruccion(String noTipoInstruccion) {
		this.noTipoInstruccion = noTipoInstruccion;
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

	public String getTxDescripcionEscuadron() {
		return txDescripcionEscuadron;
	}

	public void setTxDescripcionEscuadron(String txDescripcionEscuadron) {
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	@Override
	public String toString() {
		return "ProgramaDTO [idPrograma=" + idPrograma + ", idEscuadron=" + idEscuadron + ", nuPeriodo=" + nuPeriodo
				+ ", noTipoInstruccion=" + noTipoInstruccion + ", noNombre=" + noNombre + ", txDescripcion="
				+ txDescripcion + ", txFinalidad=" + txFinalidad + ", flBloqueado=" + flBloqueado + ", flEstado="
				+ flEstado + "]";
	}

}
