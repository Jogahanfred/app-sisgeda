package pe.mil.fap.entity.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;  
import java.io.Serializable;

import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.ParameterMode;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "operacion.listar", 
								   procedureName = "PKG_OPERACION.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "operacion.listarPorIdEscuadron", 
								   procedureName = "PKG_OPERACION.SP_LISTAR_POR_ID_ESCUADRON", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),  
										
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "operacion.buscarPorId", 
								   procedureName = "PKG_OPERACION.SP_BUSCAR_POR_ID", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class),  
										
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "operacion.insertar", 
								   procedureName = "PKG_OPERACION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "operacion.actualizar", 
								   procedureName = "PKG_OPERACION.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "operacion.eliminar", 
								   procedureName = "PKG_OPERACION.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "operacion.eliminarMultiple", 
								   procedureName = "PKG_OPERACION.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})

@Entity(name = "OperacionEntity")
@Table(name = "TBL_OPERACION")
public class OperacionEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_OPERACION")
	private Integer idOperacion;

	@NotNull(message = "El escuadrón no puede ser vacio")
	@Column(name = "ID_ESCUADRON", nullable = false)
	private Integer idEscuadron; 

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;

	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;
	
	@Transient
	private String txDescripcionEscuadron;

	public OperacionEntity() {
	}
 
	public OperacionEntity(Integer idOperacion,
			@NotNull(message = "El escuadrón no puede ser vacio") Integer idEscuadron,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			Integer flBloqueado, Integer flEstado, String txDescripcionEscuadron) {
		super();
		this.idOperacion = idOperacion;
		this.idEscuadron = idEscuadron;
		this.noNombre = noNombre;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}
  

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
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

	public String getTxDescripcionEscuadron() {
		return txDescripcionEscuadron;
	}

	public void setTxDescripcionEscuadron(String txDescripcionEscuadron) {
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	} 

}
