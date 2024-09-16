package pe.mil.fap.model.administration;

import java.io.Serializable;
import java.util.List; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 
import pe.mil.fap.model.bussiness.GrupoMiembroDTO;
  

public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	private Integer idGrupo;	
	
	@NotNull(message = "La unidad no puede ser vacio") 
	private Integer idUnidad;

	@Size(max = 5, message = "El código debe tener máximo {max} caracteres")
	@NotEmpty(message = "El código no puede ser vacia") 
	private String coCodigo;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio") 
	private String noNombre;
	
	@Size(max = 15, message = "La situación debe tener {max} caracteres")
	@NotEmpty(message = "La situación no puede ser vacio") 
	private String noSituacion;
	
	@NotNull(message = "El periodo no puede ser vacio") 
	private Integer nuPeriodo;

	private List<GrupoMiembroDTO> lstAlumnos;
	
	private List<Integer> lstIdsAlumnos;

	public GrupoDTO() {
		super();
	}

	public GrupoDTO(Integer idGrupo, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@Size(max = 5, message = "El código debe tener máximo {max} caracteres") @NotEmpty(message = "El código no puede ser vacia") String coCodigo,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(max = 15, message = "La situación debe tener {max} caracteres") @NotEmpty(message = "La situación no puede ser vacio") String noSituacion,
			@NotNull(message = "El periodo no puede ser vacio") Integer nuPeriodo) {
		super();
		this.idGrupo = idGrupo;
		this.idUnidad = idUnidad;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.noSituacion = noSituacion;
		this.nuPeriodo = nuPeriodo;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
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

	public String getNoSituacion() {
		return noSituacion;
	}

	public void setNoSituacion(String noSituacion) {
		this.noSituacion = noSituacion;
	}

	public Integer getNuPeriodo() {
		return nuPeriodo;
	}

	public void setNuPeriodo(Integer nuPeriodo) {
		this.nuPeriodo = nuPeriodo;
	}

	public List<GrupoMiembroDTO> getLstAlumnos() {
		return lstAlumnos;
	}

	public void setLstAlumnos(List<GrupoMiembroDTO> lstAlumnos) {
		this.lstAlumnos = lstAlumnos;
	}

	public List<Integer> getLstIdsAlumnos() {
		return lstIdsAlumnos;
	}

	public void setLstIdsAlumnos(List<Integer> lstIdsAlumnos) {
		this.lstIdsAlumnos = lstIdsAlumnos;
	}

	@Override
	public String toString() {
		return "GrupoDTO [idGrupo=" + idGrupo + ", idUnidad=" + idUnidad + ", coCodigo=" + coCodigo + ", noNombre="
				+ noNombre + ", noSituacion=" + noSituacion + ", nuPeriodo=" + nuPeriodo + ", lstAlumnos=" + lstAlumnos
				+ ", lstIdsAlumnos=" + lstIdsAlumnos + "]";
	}
	
}
