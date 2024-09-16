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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "fase.listar", 
								   procedureName = "PKG_FASE.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),  
		@NamedStoredProcedureQuery(name = "fase.listarPorUnidad", 
								   procedureName = "PKG_FASE.SP_LISTAR_POR_UNIDAD", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),  
		@NamedStoredProcedureQuery(name = "fase.listarPorPrograma", 
								   procedureName = "PKG_FASE.SP_LISTAR_POR_PROGRAMA", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_PROGRAMA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),  
		@NamedStoredProcedureQuery(name = "fase.insertar", 
								   procedureName = "PKG_FASE.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_PROGRAMA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_HORA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_SUB_FASE", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "fase.actualizar", 
								   procedureName = "PKG_FASE.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_HORA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_SUB_FASE", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "fase.eliminar", 
								   procedureName = "PKG_FASE.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FASE", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "fase.eliminarMultiple", 
								   procedureName = "PKG_FASE.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "FaseEntity")
@Table(name = "TBL_FASE")
public class FaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_FASE")
	private Integer idFase;
	
	@NotNull(message = "El ID banco fase no puede ser vacio")
	@Column(name = "ID_BANCO_FASE", nullable = false)
	private Integer idBancoFase;
	
	@NotNull(message = "El programa no puede ser vacio")
	@Column(name = "ID_PROGRAMA", nullable = false)
	private Integer idPrograma;

	@NotNull(message = "El número total de horas no puede ser vacio")
	@Column(name = "NU_TOTAL_HORA", nullable = false)
	private Integer nuTotalHora;

	@NotNull(message = "El número total de subfases no puede ser vacio")
	@Column(name = "NU_TOTAL_SUB_FASE", nullable = false)
	private Integer nuTotalSubFase;
	
	@Size(min = 4, max = 200, message = "La finalidad de la fase debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad de la fase no puede ser vacio")
	@Column(name = "TX_FINALIDAD", nullable = false)
	private String txFinalidad;
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;
	
	@Transient
	private String txDescripcionFase;
	
	@Transient
	private String txDescripcionPrograma;
	
	@Transient
	private Integer idEscuadron;
	

	public FaseEntity(Integer idFase, @NotNull(message = "El ID banco fase no puede ser vacio") Integer idBancoFase,
			@NotNull(message = "El programa no puede ser vacio") Integer idPrograma,
			@NotNull(message = "El número total de horas no puede ser vacio") Integer nuTotalHora,
			@NotNull(message = "El número total de subfases no puede ser vacio") Integer nuTotalSubFase,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionFase, String txDescripcionPrograma, Integer idEscuadron) {
		super();
		this.idFase = idFase;
		this.idBancoFase = idBancoFase;
		this.idPrograma = idPrograma;
		this.nuTotalHora = nuTotalHora;
		this.nuTotalSubFase = nuTotalSubFase;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionFase = txDescripcionFase;
		this.txDescripcionPrograma = txDescripcionPrograma;
		this.idEscuadron = idEscuadron;
	}

	public FaseEntity() {
		super();
	}

	public Integer getIdFase() {
		return idFase;
	}

	public void setIdFase(Integer idFase) {
		this.idFase = idFase;
	}

	public Integer getIdBancoFase() {
		return idBancoFase;
	}

	public void setIdBancoFase(Integer idBancoFase) {
		this.idBancoFase = idBancoFase;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getNuTotalHora() {
		return nuTotalHora;
	}

	public void setNuTotalHora(Integer nuTotalHora) {
		this.nuTotalHora = nuTotalHora;
	}

	public Integer getNuTotalSubFase() {
		return nuTotalSubFase;
	}

	public void setNuTotalSubFase(Integer nuTotalSubFase) {
		this.nuTotalSubFase = nuTotalSubFase;
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

	public String getTxDescripcionFase() {
		return txDescripcionFase;
	}

	public void setTxDescripcionFase(String txDescripcionFase) {
		this.txDescripcionFase = txDescripcionFase;
	}

	public String getTxDescripcionPrograma() {
		return txDescripcionPrograma;
	}

	public void setTxDescripcionPrograma(String txDescripcionPrograma) {
		this.txDescripcionPrograma = txDescripcionPrograma;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}
	
	
}
