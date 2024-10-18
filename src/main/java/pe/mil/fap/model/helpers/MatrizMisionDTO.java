package pe.mil.fap.model.helpers;

import java.util.List;

public class MatrizMisionDTO {

	private List<EjeXDTO> lstEjeX;
	private List<EjeYDTO> lstEjeY;
	private List<EjeInterseccionACalificarDTO> lstEjeInterseccion;

	public MatrizMisionDTO() {
		super();
	}

	public MatrizMisionDTO(List<EjeXDTO> lstEjeX, List<EjeYDTO> lstEjeY, List<EjeInterseccionACalificarDTO> lstEjeInterseccion) {
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

	public List<EjeInterseccionACalificarDTO> getLstEjeInterseccion() {
		return lstEjeInterseccion;
	}

	public void setLstEjeInterseccion(List<EjeInterseccionACalificarDTO> lstEjeInterseccion) {
		this.lstEjeInterseccion = lstEjeInterseccion;
	}

}
