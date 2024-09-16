package pe.mil.fap.model.helpers;

import java.io.Serializable;
import java.util.List;
 
import pe.mil.fap.model.administration.RestriccionEstandarDTO; 
 
public class EjeInterseccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idDetalleMision;
	private Integer idMision;
	private Integer idManiobra;
	private Integer idEstandarRequerido;
	private String coCodigoEstandar;

	private List<RestriccionEstandarDTO> lstRestricciones;
	
	public EjeInterseccionDTO() {
		super();
	}

	public EjeInterseccionDTO(Integer idDetalleMision, Integer idMision, Integer idManiobra, Integer idEstandarRequerido, String coCodigoEstandar, List<RestriccionEstandarDTO> lstRestricciones) {
		super();
		this.idDetalleMision = idDetalleMision;
		this.idMision = idMision;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.coCodigoEstandar = coCodigoEstandar;
		this.lstRestricciones = lstRestricciones;
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

	public String getCoCodigoEstandar() {
		return coCodigoEstandar;
	}

	public void setCoCodigoEstandar(String coCodigoEstandar) {
		this.coCodigoEstandar = coCodigoEstandar;
	}

	public List<RestriccionEstandarDTO> getLstRestricciones() {
		return lstRestricciones;
	}

	public void setLstRestricciones(List<RestriccionEstandarDTO> lstRestricciones) {
		this.lstRestricciones = lstRestricciones;
	} 
	
}
