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
		@NamedStoredProcedureQuery(name = "programa.listar", 
								   procedureName = "PKG_PROGRAMA.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "programa.listarPorIdUnidad", 
								   procedureName = "PKG_PROGRAMA.SP_LISTAR_POR_ID_UNIDAD", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "programa.listarPorIdEscuadron", 
								   procedureName = "PKG_PROGRAMA.SP_LISTAR_POR_ID_ESCUADRON", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "programa.insertar", 
								   procedureName = "PKG_PROGRAMA.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO_INSTRUCCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "programa.actualizar", 
								   procedureName = "PKG_PROGRAMA.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_PROGRAMA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO_INSTRUCCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "programa.eliminar", 
								   procedureName = "PKG_PROGRAMA.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_PROGRAMA", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "programa.eliminarMultiple", 
								   procedureName = "PKG_PROGRAMA.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "ProgramaEntity")
@Table(name = "TBL_PROGRAMA")
public class ProgramaEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_PROGRAMA")
	private Integer idPrograma;
	
	@NotNull(message = "El escuadrón no puede ser vacio")
	@Column(name = "ID_ESCUADRON", nullable = false)
	private Integer idEscuadron;
	
	@NotNull(message = "El periodo no puede ser vacio")
	@Column(name = "NU_PERIODO", nullable = false)
	private Integer nuPeriodo;
	
	@Size(min = 3, max = 3, message = "El tipo de instrucción debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El tipo de instrucción no puede ser vacio")
	@Column(name = "NO_TIPO_INSTRUCCION", nullable = false)
	private String noTipoInstruccion;
	
	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;
	
	@Size(min = 4, max = 70, message = "La descripción del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La descripción del programa no puede ser vacio")
	@Column(name = "TX_DESCRIPCION", nullable = false)
	private String txDescripcion;

	@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad del programa no puede ser vacio")
	@Column(name = "TX_FINALIDAD", nullable = false)
	private String txFinalidad;
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Transient
	private String txDescripcionEscuadron;	
	
	@Transient
	private Integer idUnidad;
	
	public ProgramaEntity() {
	} 
 
	public ProgramaEntity(Integer idPrograma, @NotNull(message = "El escuadrón no puede ser vacio") Integer idEscuadron,
			@NotNull(message = "El periodo no puede ser vacio") Integer nuPeriodo,
			@Size(min = 3, max = 3, message = "El tipo de instrucción debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El tipo de instrucción no puede ser vacio") String noTipoInstruccion,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(min = 4, max = 70, message = "La descripción del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La descripción del programa no puede ser vacio") String txDescripcion,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionEscuadron, Integer idUnidad) {
		super();
		this.idPrograma = idPrograma;
		this.idEscuadron = idEscuadron;
		this.nuPeriodo = nuPeriodo;
		this.noTipoInstruccion = noTipoInstruccion;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
		this.idUnidad = idUnidad;
	}  

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public Integer getNuPeriodo() {
		return nuPeriodo;
	}

	public void setNuPeriodo(Integer nuPeriodo) {
		this.nuPeriodo = nuPeriodo;
	}

	public String getNoTipoInstruccion() {
		return noTipoInstruccion;
	}

	public void setNoTipoInstruccion(String noTipoInstruccion) {
		this.noTipoInstruccion = noTipoInstruccion;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public String getTxDescripcion() {
		return txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public String getTxFinalidad() {
		return txFinalidad;
	}

	public void setTxFinalidad(String txFinalidad) {
		this.txFinalidad = txFinalidad;
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

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	@Override
	public String toString() {
		return "ProgramaEntity [idPrograma=" + idPrograma + ", idEscuadron=" + idEscuadron + ", nuPeriodo=" + nuPeriodo
				+ ", noTipoInstruccion=" + noTipoInstruccion + ", noNombre=" + noNombre + ", txDescripcion="
				+ txDescripcion + ", txFinalidad=" + txFinalidad + ", flBloqueado=" + flBloqueado + ", flEstado="
				+ flEstado + "]";
	} 
 
}
