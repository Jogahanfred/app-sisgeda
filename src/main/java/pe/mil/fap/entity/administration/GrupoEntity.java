package pe.mil.fap.entity.administration;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 
import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "grupo.listar", 
							   procedureName = "PKG_GRUPO.SP_LISTAR", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}),
	@NamedStoredProcedureQuery(name = "grupo.buscarId", 
							   procedureName = "PKG_GRUPO.SP_BUSCAR_ID", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_GRUPO", type = Integer.class),
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class) 
	}),
	@NamedStoredProcedureQuery(name = "grupo.insertar", 
							   procedureName = "PKG_GRUPO.SP_INSERTAR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SITUACION", type = String.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS_MIEMBROS", type = String.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	})	
})
@Entity(name = "GrupoEntity")
@Table(name = "TBL_GRUPO")
public class GrupoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_GRUPO")
	private Integer idGrupo;	
	
	@NotNull(message = "La unidad no puede ser vacio")
	@Column(name = "ID_UNIDAD", nullable = false)
	private Integer idUnidad;

	@Size(max = 5, message = "El código debe tener máximo {max} caracteres")
	@NotEmpty(message = "El código no puede ser vacia")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;
	
	@Size(max = 15, message = "La situación debe tener {max} caracteres")
	@NotEmpty(message = "La situación no puede ser vacio")
	@Column(name = "NO_SITUACION", nullable = false)
	private String noSituacion;
	
	@NotNull(message = "El periodo no puede ser vacio")
	@Column(name = "NU_PERIODO", nullable = false)
	private Integer nuPeriodo;

	@Transient
	private List<GrupoMiembroEntity> lstAlumnos;

	public GrupoEntity() {
		super();
	}

	public GrupoEntity(Integer idGrupo, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
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

	public List<GrupoMiembroEntity> getLstAlumnos() {
		return lstAlumnos;
	}

	public void setLstAlumnos(List<GrupoMiembroEntity> lstAlumnos) {
		this.lstAlumnos = lstAlumnos;
	}
	
	
} 

