package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class EstandarDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idEstandar;

	@Size(min = 4, max = 10, message = "El código del estándar debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código del estándar no puede ser vacio")
	private String coCodigo;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	private String noNombre;

	@Size(max = 200, message = "La descripciondebe tener {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacio")
	private String txDescripcion;

	private Integer flEstado;

	private Integer nuNivel;

	public EstandarDTO(Integer idEstandar,
			@Size(min = 4, max = 10, message = "El código del estándar debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código del estándar no puede ser vacio") String coCodigo,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(max = 200, message = "La descripciondebe tener {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacio") String txDescripcion,
			Integer flEstado, Integer nuNivel) {
		super();
		this.idEstandar = idEstandar;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.flEstado = flEstado;
		this.nuNivel = nuNivel;
	}

	public EstandarDTO() {
		super();
	}

	public Integer getIdEstandar() {
		return idEstandar;
	}

	public void setIdEstandar(Integer idEstandar) {
		this.idEstandar = idEstandar;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
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

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

	public Integer getNuNivel() {
		return nuNivel;
	}

	public void setNuNivel(Integer nuNivel) {
		this.nuNivel = nuNivel;
	}

}
