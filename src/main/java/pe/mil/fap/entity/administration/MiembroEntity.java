package pe.mil.fap.entity.administration;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "miembro.listar", 
							   procedureName = "PKG_MIEMBRO.SP_LISTAR", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ROL", type = String.class),
			   						
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.buscarPorId", 
							   procedureName = "PKG_MIEMBRO.SP_BUSCAR_ID", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MIEMBRO", type = Integer.class),
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.buscarPorNsa", 
							   procedureName = "PKG_MIEMBRO.SP_BUSCAR_NSA", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NSA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ROL", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.insertarInstructor", 
							   procedureName = "PKG_MIEMBRO.SP_INSERTAR_INSTRUCTOR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class), 
			   						@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NSA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.insertarAlumno", 
							   procedureName = "PKG_MIEMBRO.SP_INSERTAR_ALUMNO", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NSA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.deshabilitar", 
							   procedureName = "PKG_MIEMBRO.SP_DESHABILITAR", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MIEMBRO", type = Integer.class),
									
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "miembro.eliminar", 
							   procedureName = "PKG_MIEMBRO.SP_ELIMINAR", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MIEMBRO", type = Integer.class), 

									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
			
})

@Entity(name = "MiembroEntity")
@Table(name = "TBL_MIEMBRO")
public class MiembroEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_MIEMBRO")
	private Integer idMiembro;

	@NotNull(message = "La unidad no puede ser vacio")
	@Column(name = "ID_UNIDAD", nullable = false)
	private Integer idUnidad;
	
	@Size(max = 10, message = "El rol debe tener {max} caracteres")
	@NotEmpty(message = "El rol no puede ser vacio")
	@Column(name = "NO_ROL", nullable = false)
	private String noRol;
	
	@Size(max = 8, message = "El nsa debe tener {max} caracteres")
	@NotEmpty(message = "El nsa no puede ser vacio")
	@Column(name = "CO_NSA", nullable = false)
	private String coNsa;
	
	@NotNull(message = "El periodo no puede ser vacio")
	@Column(name = "NU_PERIODO", nullable = false)
	private Integer nuPeriodo;

	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public MiembroEntity() {
		super();
	}
 
	public MiembroEntity(Integer idMiembro, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@Size(max = 10, message = "El rol debe tener {max} caracteres") @NotEmpty(message = "El rol no puede ser vacio") String noRol,
			@Size(max = 8, message = "El nsa debe tener {max} caracteres") @NotEmpty(message = "El nsa no puede ser vacio") String coNsa,
			@NotNull(message = "El periodo no puede ser vacio") Integer nuPeriodo, Integer flBloqueado,
			Integer flEstado) {
		super();
		this.idMiembro = idMiembro;
		this.idUnidad = idUnidad;
		this.noRol = noRol;
		this.coNsa = coNsa;
		this.nuPeriodo = nuPeriodo;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
	} 

	public Integer getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(Integer idMiembro) {
		this.idMiembro = idMiembro;
	}

	public String getNoRol() {
		return noRol;
	}

	public void setNoRol(String noRol) {
		this.noRol = noRol;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCoNsa() {
		return coNsa;
	}

	public void setCoNsa(String coNsa) {
		this.coNsa = coNsa;
	}

	public Integer getNuPeriodo() {
		return nuPeriodo;
	}

	public void setNuPeriodo(Integer nuPeriodo) {
		this.nuPeriodo = nuPeriodo;
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

