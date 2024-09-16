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
import jakarta.validation.constraints.Size;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "bancoSubFase.listar", 
							   procedureName = "PKG_BANCO_SUB_FASE.SP_LISTAR", 
							   parameters = { 
			   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoSubFase.insertar", 
							   procedureName = "PKG_BANCO_SUB_FASE.SP_INSERTAR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoSubFase.actualizar", 
							   procedureName = "PKG_BANCO_SUB_FASE.SP_ACTUALIZAR", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_SUB_FASE", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoSubFase.eliminar", 
							   procedureName = "PKG_BANCO_SUB_FASE.SP_ELIMINAR", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_SUB_FASE", type = Integer.class), 

									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}),
	@NamedStoredProcedureQuery(name = "bancoSubFase.eliminarMultiple", 
							   procedureName = "PKG_BANCO_SUB_FASE.SP_ELIMINAR_MULTIPLE", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
					
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}) 
			
})
@Entity(name = "BancoSubFaseEntity")
@Table(name = "TBL_BANCO_FASE")
public class BancoSubFaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_BANCO_SUB_FASE")
	private Integer idBancoSubFase;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public BancoSubFaseEntity(Integer idBancoSubFase,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flEstado) {
		super();
		this.idBancoSubFase = idBancoSubFase;
		this.noNombre = noNombre;
		this.flEstado = flEstado;
	}

	public BancoSubFaseEntity() {
		super();
	}

	public Integer getIdBancoSubFase() {
		return idBancoSubFase;
	}

	public void setIdBancoSubFase(Integer idBancoSubFase) {
		this.idBancoSubFase = idBancoSubFase;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

}
