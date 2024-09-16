package pe.mil.fap.entity.bussiness;

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

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "mision.insertar", 
								   procedureName = "PKG_MISION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_TIPO_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CHEQUEO", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_ID_MISION", type = Integer.class)
		})
})

@Entity(name = "MisionEntity")
@Table(name = "TBL_MISION")
public class MisionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_MISION")
	private Integer idMision;

	@NotNull(message = "La sub fase no puede ser vacia")
	@Column(name = "ID_SUB_FASE", nullable = false)
	private Integer idSubFase;
	
	@NotNull(message = "El tipo de misión no puede ser vacio")
	@Column(name = "ID_TIPO_MISION", nullable = false)
	private Integer idTipoMision;
	
	@Size(min = 1, max = 10, message = "El código debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código no puede ser vacio")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;
	
	@Column(name = "FL_CHEQUEO", nullable = false)
	private Integer flChequeo;
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;
	
	@Transient
	private List<DetalleMisionEntity> lstDetalleMision;

	public MisionEntity(Integer idMision, @NotNull(message = "La sub fase no puede ser vacia") Integer idSubFase,
			@NotNull(message = "El tipo de misión no puede ser vacio") Integer idTipoMision,
			@Size(min = 1, max = 10, message = "El código debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código no puede ser vacio") String coCodigo,
			Integer flChequeo, Integer flBloqueado, Integer flEstado, List<DetalleMisionEntity> lstDetalleMision) {
		super();
		this.idMision = idMision;
		this.idSubFase = idSubFase;
		this.idTipoMision = idTipoMision;
		this.coCodigo = coCodigo;
		this.flChequeo = flChequeo;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.lstDetalleMision = lstDetalleMision;
	}

	public MisionEntity() {
		super();
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public Integer getIdSubFase() {
		return idSubFase;
	}

	public void setIdSubFase(Integer idSubFase) {
		this.idSubFase = idSubFase;
	}

	public Integer getIdTipoMision() {
		return idTipoMision;
	}

	public void setIdTipoMision(Integer idTipoMision) {
		this.idTipoMision = idTipoMision;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
	}

	public Integer getFlChequeo() {
		return flChequeo;
	}

	public void setFlChequeo(Integer flChequeo) {
		this.flChequeo = flChequeo;
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
		 
	public List<DetalleMisionEntity> getLstDetalleMision() {
		return lstDetalleMision;
	}

	public void setLstDetalleMision(List<DetalleMisionEntity> lstDetalleMision) {
		this.lstDetalleMision = lstDetalleMision;
	}

	@Override
	public String toString() {
		return "MisionEntity [idMision=" + idMision + ", idSubFase=" + idSubFase + ", idTipoMision=" + idTipoMision
				+ ", coCodigo=" + coCodigo + ", flChequeo=" + flChequeo + ", flBloqueado=" + flBloqueado + ", flEstado="
				+ flEstado + ", lstDetalleMision=" + lstDetalleMision + "]";
	}


}
