package pe.mil.fap.model.bussiness;

import java.io.Serializable; 
import jakarta.validation.constraints.NotNull;

public class DetalleCalificacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
 
	private Integer idDetalleCalificacion;

	@NotNull(message = "La cabecera calificación no puede ser vacia") 
	private Integer idCalificacion;

	@NotNull(message = "La maniobra no puede ser vacia") 
	private Integer idManiobra; 
	
	private Integer idEstandarObtenido;

	public DetalleCalificacionDTO(Integer idDetalleCalificacion,
			@NotNull(message = "La cabecera calificación no puede ser vacia") Integer idCalificacion,
			@NotNull(message = "La maniobra no puede ser vacia") Integer idManiobra, Integer idEstandarObtenido) {
		super();
		this.idDetalleCalificacion = idDetalleCalificacion;
		this.idCalificacion = idCalificacion;
		this.idManiobra = idManiobra;
		this.idEstandarObtenido = idEstandarObtenido;
	}

	public DetalleCalificacionDTO() {
		super();
	}

	public Integer getIdDetalleCalificacion() {
		return idDetalleCalificacion;
	}

	public void setIdDetalleCalificacion(Integer idDetalleCalificacion) {
		this.idDetalleCalificacion = idDetalleCalificacion;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdEstandarObtenido() {
		return idEstandarObtenido;
	}

	public void setIdEstandarObtenido(Integer idEstandarObtenido) {
		this.idEstandarObtenido = idEstandarObtenido;
	}

}
