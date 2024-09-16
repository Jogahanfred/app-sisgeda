package pe.mil.fap.model.helpers;

import java.io.Serializable; 
 
public class EjeYDTO implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private Integer idManiobra;
	private String noNombreManiobra;
	private Integer idOperacion;
	private String noNombreOperacion;
	
	public EjeYDTO() {
		super();
	}
	
	public EjeYDTO(Integer idManiobra, String noNombreManiobra, Integer idOperacion, String noNombreOperacion) {
		super();
		this.idManiobra = idManiobra;
		this.noNombreManiobra = noNombreManiobra;
		this.idOperacion = idOperacion;
		this.noNombreOperacion = noNombreOperacion;
	}
	
	public Integer getIdManiobra() {
		return idManiobra;
	}
	
	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}
	
	public String getNoNombreManiobra() {
		return noNombreManiobra;
	}
	
	public void setNoNombreManiobra(String noNombreManiobra) {
		this.noNombreManiobra = noNombreManiobra;
	}
	
	public Integer getIdOperacion() {
		return idOperacion;
	}
	
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}
	
	public String getNoNombreOperacion() {
		return noNombreOperacion;
	}
	
	public void setNoNombreOperacion(String noNombreOperacion) {
		this.noNombreOperacion = noNombreOperacion;
	}
	
	
}
