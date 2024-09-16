package pe.mil.fap.model.bussiness;

import java.io.Serializable; 
import jakarta.validation.constraints.NotNull;

public class DetalleMisionDTO implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private Integer idDetalleMision;

	@NotNull(message = "La misión no puede ser vacia") 
	private Integer idMision;

	@NotNull(message = "La maniobra no puede ser vacia") 
	private Integer idManiobra;

	@NotNull(message = "El estandar no puede ser vacio") 
	private Integer idEstandarRequerido;

	@NotNull(message = "El orden no puede ser vacio") 
	private Integer nuOrden;
	  
	private Integer flBloqueado;
 
	private Integer flEstado;

	public DetalleMisionDTO(Integer idDetalleMision,
			@NotNull(message = "La misión no puede ser vacia") Integer idMision,
			@NotNull(message = "La maniobra no puede ser vacia") Integer idManiobra,
			@NotNull(message = "El estandar no puede ser vacio") Integer idEstandarRequerido,
			@NotNull(message = "El orden no puede ser vacio") Integer nuOrden, Integer flBloqueado, Integer flEstado) {
		super();
		this.idDetalleMision = idDetalleMision;
		this.idMision = idMision;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.nuOrden = nuOrden;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
	}

	public DetalleMisionDTO() {
		super();
	}

	public Integer getIdDetalleMision() {
		return idDetalleMision;
	}

	public void setIdDetalleMision(Integer idDetalleMision) {
		this.idDetalleMision = idDetalleMision;
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdEstandarRequerido() {
		return idEstandarRequerido;
	}

	public void setIdEstandarRequerido(Integer idEstandarRequerido) {
		this.idEstandarRequerido = idEstandarRequerido;
	}

	public Integer getNuOrden() {
		return nuOrden;
	}

	public void setNuOrden(Integer nuOrden) {
		this.nuOrden = nuOrden;
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

}
