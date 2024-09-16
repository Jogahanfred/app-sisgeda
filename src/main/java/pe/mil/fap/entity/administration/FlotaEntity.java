package pe.mil.fap.entity.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty; 
import jakarta.validation.constraints.Size;  
import java.io.Serializable;

import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.ParameterMode;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "flota.listar", 
								   procedureName = "PKG_FLOTA.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "flota.insertar", 
								   procedureName = "PKG_FLOTA.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO_FLOTA", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "flota.actualizar", 
								   procedureName = "PKG_FLOTA.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FLOTA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO_FLOTA", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "flota.eliminar", 
								   procedureName = "PKG_FLOTA.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FLOTA", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "flota.eliminarMultiple", 
								   procedureName = "PKG_FLOTA.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "FlotaEntity")
@Table(name = "TBL_FLOTA")
public class FlotaEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_FLOTA")
	private Integer idFlota;

	@Size(min = 4, max = 20, message = "El tipo de flota debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El tipo de flota no puede ser vacio")
	@Column(name = "NO_TIPO_FLOTA", nullable = false)
	private String noTipoFlota;

	@Size(min = 4, max = 10, message = "El código de flota debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código de flota no puede ser vacio")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;

	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public FlotaEntity() {
	}

	public FlotaEntity(Integer idFlota,
			@Size(min = 4, max = 20, message = "El tipo de flota debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El tipo de flota no puede ser vacio") String noTipoFlota,
			@Size(min = 4, max = 10, message = "El código de flota debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código de flota no puede ser vacio") String coCodigo,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flBloqueado, Integer flEstado) {
		super();
		this.idFlota = idFlota;
		this.noTipoFlota = noTipoFlota;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
	}

	public Integer getIdFlota() {
		return idFlota;
	}

	public void setIdFlota(Integer idFlota) {
		this.idFlota = idFlota;
	}

	public String getNoTipoFlota() {
		return noTipoFlota;
	}

	public void setNoTipoFlota(String noTipoFlota) {
		this.noTipoFlota = noTipoFlota;
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
