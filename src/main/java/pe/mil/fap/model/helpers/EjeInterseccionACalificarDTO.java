package pe.mil.fap.model.helpers;

import java.io.Serializable;
import java.util.List;

import pe.mil.fap.common.enums.EstandarEnum;
import pe.mil.fap.common.enums.ManiobraEstadoEnum;
import pe.mil.fap.model.administration.RestriccionEstandarDTO; 
 
public class EjeInterseccionACalificarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCalificacion;
	private Integer idDetalleCalificacion;
	private Integer idManiobra;
	private Integer idEstandarRequerido;
	private String coCodigoEstandarRequerido;
	private Integer idEstandarObtenido;
	private String coCodigoEstandarObtenido;
	
	private String coEstadoEjecucionManiobra;

	private List<RestriccionEstandarDTO> lstRestricciones;
	
	public EjeInterseccionACalificarDTO() {
		super();
	}

	public EjeInterseccionACalificarDTO(Integer idCalificacion, Integer idDetalleCalificacion, Integer idManiobra,
			Integer idEstandarRequerido, String coCodigoEstandarRequerido, Integer idEstandarObtenido,
			String coCodigoEstandarObtenido, List<RestriccionEstandarDTO> lstRestricciones) {
		super();
		this.idCalificacion = idCalificacion;
		this.idDetalleCalificacion = idDetalleCalificacion;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.coCodigoEstandarRequerido = coCodigoEstandarRequerido;
		this.idEstandarObtenido = idEstandarObtenido;
		this.coCodigoEstandarObtenido = coCodigoEstandarObtenido;
		this.lstRestricciones = lstRestricciones;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdDetalleCalificacion() {
		return idDetalleCalificacion;
	}

	public void setIdDetalleCalificacion(Integer idDetalleCalificacion) {
		this.idDetalleCalificacion = idDetalleCalificacion;
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

	public String getCoCodigoEstandarRequerido() {
		return coCodigoEstandarRequerido;
	}

	public void setCoCodigoEstandarRequerido(String coCodigoEstandarRequerido) {
		this.coCodigoEstandarRequerido = coCodigoEstandarRequerido;
	}

	public Integer getIdEstandarObtenido() {
		return idEstandarObtenido;
	}

	public void setIdEstandarObtenido(Integer idEstandarObtenido) {
		this.idEstandarObtenido = idEstandarObtenido;
	}

	public String getCoCodigoEstandarObtenido() {
		return coCodigoEstandarObtenido;
	}

	public void setCoCodigoEstandarObtenido(String coCodigoEstandarObtenido) {
		this.coCodigoEstandarObtenido = coCodigoEstandarObtenido;
	}

	public List<RestriccionEstandarDTO> getLstRestricciones() {
		return lstRestricciones;
	}

	public void setLstRestricciones(List<RestriccionEstandarDTO> lstRestricciones) {
		this.lstRestricciones = lstRestricciones;
	}
	
	public String getCoEstadoEjecucionManiobra() {
		return coEstadoEjecucionManiobra;
	}

	public void setCoEstadoEjecucionManiobra(String coEstadoEjecucionManiobra) {
		this.coEstadoEjecucionManiobra = coEstadoEjecucionManiobra;
	}

	public void validarEstadoManiobra() {
	    int nivelRequerido = EstandarEnum.getNivelPorCodigo(coCodigoEstandarRequerido);
	    int nivelObtenido = EstandarEnum.getNivelPorCodigo(coCodigoEstandarObtenido);
	    
	    if (nivelRequerido == -1 || nivelObtenido == -1) {
	        coEstadoEjecucionManiobra = "SIN_CALIFICAR";
	        return;
	    } 

	    if (nivelObtenido < nivelRequerido) {
	        coEstadoEjecucionManiobra = ManiobraEstadoEnum.BAJO_ESTANDAR.name();
	    } else if (nivelObtenido > nivelRequerido) {
	        coEstadoEjecucionManiobra = ManiobraEstadoEnum.SOBRE_ESTANDAR.name();
	    } else {
	        coEstadoEjecucionManiobra = ManiobraEstadoEnum.NORMAL.name();
	    }
	}
 
}
