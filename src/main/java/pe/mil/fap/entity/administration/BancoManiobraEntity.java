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
	@NamedStoredProcedureQuery(name = "bancoManiobra.listar", 
							   procedureName = "PKG_BANCO_MANIOBRA.SP_LISTAR", 
							   parameters = { 
			   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoManiobra.insertar", 
							   procedureName = "PKG_BANCO_MANIOBRA.SP_INSERTAR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoManiobra.actualizar", 
							   procedureName = "PKG_BANCO_MANIOBRA.SP_ACTUALIZAR", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_MANIOBRA", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "bancoManiobra.eliminar", 
							   procedureName = "PKG_BANCO_MANIOBRA.SP_ELIMINAR", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_MANIOBRA", type = Integer.class), 

									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}),
	@NamedStoredProcedureQuery(name = "bancoManiobra.eliminarMultiple", 
							   procedureName = "PKG_BANCO_MANIOBRA.SP_ELIMINAR_MULTIPLE", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
					
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}) 
			
})
@Entity(name = "BancoManiobraEntity")
@Table(name = "TBL_BANCO_MANIOBRA")
public class BancoManiobraEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_BANCO_MANIOBRA")
	private Integer idBancoManiobra;

	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public BancoManiobraEntity(Integer idBancoManiobra,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flEstado) {
		super();
		this.idBancoManiobra = idBancoManiobra;
		this.noNombre = noNombre;
		this.flEstado = flEstado;
	}

	public BancoManiobraEntity() {
		super();
	}

	public Integer getIdBancoManiobra() {
		return idBancoManiobra;
	}

	public void setIdBancoManiobra(Integer idBancoManiobra) {
		this.idBancoManiobra = idBancoManiobra;
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
