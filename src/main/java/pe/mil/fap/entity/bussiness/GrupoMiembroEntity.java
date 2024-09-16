package pe.mil.fap.entity.bussiness;

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
	@NamedStoredProcedureQuery(name = "grupoMiembro.listarDetalle", 
							   procedureName = "PKG_GRUPO.SP_LISTAR_DETALLE", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_GRUPO", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	})
})
@Entity(name = "GrupoMiembroEntity")
@Table(name = "TBL_GRUPO_MIEMBRO")
public class GrupoMiembroEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_GRUPO")
	private Integer idGrupo;

	@NotNull(message = "El miembro no puede ser vacio")
	@Column(name = "ID_MIEMBRO", nullable = false)
	private Integer idMiembro;

	@Size(max = 15, message = "La situación debe tener {max} caracteres")
	@NotEmpty(message = "La situación no puede ser vacio")
	@Column(name = "NO_SITUACION", nullable = false)
	private String noSituacion;

	@Column(name = "FE_REGISTRO", nullable = true)
	private String feRegistro;

	@Column(name = "FE_ACTUALIZACION", nullable = true)
	private String feActualizacion;

	public GrupoMiembroEntity() {
		super();
	}

	public GrupoMiembroEntity(Integer idGrupo, @NotNull(message = "El miembro no puede ser vacio") Integer idMiembro,
			@Size(max = 15, message = "La situación debe tener {max} caracteres") @NotEmpty(message = "La situación no puede ser vacio") String noSituacion,
			String feRegistro, String feActualizacion) {
		super();
		this.idGrupo = idGrupo;
		this.idMiembro = idMiembro;
		this.noSituacion = noSituacion;
		this.feRegistro = feRegistro;
		this.feActualizacion = feActualizacion;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(Integer idMiembro) {
		this.idMiembro = idMiembro;
	}

	public String getNoSituacion() {
		return noSituacion;
	}

	public void setNoSituacion(String noSituacion) {
		this.noSituacion = noSituacion;
	}

	public String getFeRegistro() {
		return feRegistro;
	}

	public void setFeRegistro(String feRegistro) {
		this.feRegistro = feRegistro;
	}

	public String getFeActualizacion() {
		return feActualizacion;
	}

	public void setFeActualizacion(String feActualizacion) {
		this.feActualizacion = feActualizacion;
	}

}
