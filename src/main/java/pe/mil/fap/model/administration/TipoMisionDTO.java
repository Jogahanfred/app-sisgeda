package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class TipoMisionDTO implements Serializable {
 
	private static final long serialVersionUID = 1L; 
	
	private Integer idTipoMision; 

	@Size(min = 4, max = 10, message = "El código del  tipo de misión debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código de tipoMision no puede ser vacio") 
	private String coCodigo;

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio") 
	private String noNombre; 

	@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacia") 
	private String txDescripcion;
	 
	private Integer flEstado;

	public TipoMisionDTO() {
		super();
	}

	public TipoMisionDTO(Integer idTipoMision,
			@Size(min = 4, max = 10, message = "El código de tipoMision debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código de tipoMision no puede ser vacio") String coCodigo,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacia") String txDescripcion,
			Integer flEstado) {
		super();
		this.idTipoMision = idTipoMision;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.flEstado = flEstado;
	}

	public Integer getIdTipoMision() {
		return idTipoMision;
	}

	public void setIdTipoMision(Integer idTipoMision) {
		this.idTipoMision = idTipoMision;
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
	
}
