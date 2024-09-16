package pe.mil.fap.model.helpers;

import java.util.List;

public class MatrizSubFaseDTO {

	private List<EjeXDTO> lstEjeX;
	private List<EjeYDTO> lstEjeY;
	private List<EjeInterseccionDTO> lstEjeInterseccion;

	public MatrizSubFaseDTO() {
		super();
	}

	public MatrizSubFaseDTO(List<EjeXDTO> lstEjeX, List<EjeYDTO> lstEjeY, List<EjeInterseccionDTO> lstEjeInterseccion) {
		super();
		this.lstEjeX = lstEjeX;
		this.lstEjeY = lstEjeY;
		this.lstEjeInterseccion = lstEjeInterseccion;
	}

	public List<EjeXDTO> getLstEjeX() {
		return lstEjeX;
	}

	public void setLstEjeX(List<EjeXDTO> lstEjeX) {
		this.lstEjeX = lstEjeX;
	}

	public List<EjeYDTO> getLstEjeY() {
		return lstEjeY;
	}

	public void setLstEjeY(List<EjeYDTO> lstEjeY) {
		this.lstEjeY = lstEjeY;
	}

	public List<EjeInterseccionDTO> getLstEjeInterseccion() {
		return lstEjeInterseccion;
	}

	public void setLstEjeInterseccion(List<EjeInterseccionDTO> lstEjeInterseccion) {
		this.lstEjeInterseccion = lstEjeInterseccion;
	}

}
