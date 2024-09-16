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
		@NamedStoredProcedureQuery(name = "subFase.listar", 
								   procedureName = "PKG_SUB_FASE.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),  
		@NamedStoredProcedureQuery(name = "subFase.listarPorUnidad", 
								   procedureName = "PKG_SUB_FASE.SP_LISTAR_POR_UNIDAD", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),  
		@NamedStoredProcedureQuery(name = "subFase.listarPorFase", 
								   procedureName = "PKG_SUB_FASE.SP_LISTAR_POR_FASE", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),    
		@NamedStoredProcedureQuery(name = "subFase.buscarPorId", 
								   procedureName = "PKG_SUB_FASE.SP_BUSCAR_POR_ID", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),   
		@NamedStoredProcedureQuery(name = "subFase.filtroPorPeriodo", 
								   procedureName = "PKG_SUB_FASE.SP_FILTRO_POR_PERIODO", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "subFase.insertar", 
								   procedureName = "PKG_SUB_FASE.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_PROGRAMA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_SUB_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_HORA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_MANIOBRA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "subFase.actualizar", 
								   procedureName = "PKG_SUB_FASE.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FASE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_HORA", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_MISION", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TOTAL_MANIOBRA", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FINALIDAD", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "subFase.eliminar", 
								   procedureName = "PKG_SUB_FASE.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "subFase.eliminarMultiple", 
								   procedureName = "PKG_SUB_FASE.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "SubFaseEntity")
@Table(name = "TBL_SUB_FASE")
public class SubFaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_SUB_FASE")
	private Integer idSubFase;
	
	@NotNull(message = "El ID banco sub fase no puede ser vacio")
	@Column(name = "ID_BANCO_SUB_FASE", nullable = false)
	private Integer idBancoSubFase;
	
	@NotNull(message = "La fase no puede ser vacio")
	@Column(name = "ID_FASE", nullable = false)
	private Integer idFase;
	
	@NotNull(message = "El número total de horas no puede ser vacio")
	@Column(name = "NU_TOTAL_HORA", nullable = false)
	private Integer nuTotalHora;

	@NotNull(message = "El número total de misiones no puede ser vacio")
	@Column(name = "NU_TOTAL_MISION", nullable = false)
	private Integer nuTotalMision;
	
	@NotNull(message = "El número total de maniobras no puede ser vacio")
	@Column(name = "NU_TOTAL_MANIOBRA", nullable = false)
	private Integer nuTotalManiobra;
	
	@Size(max = 10, message = "El código debe tener {max} caracteres")
	@NotEmpty(message = "La código no puede ser vacio")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;
	
	@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La finalidad del programa no puede ser vacio")
	@Column(name = "TX_FINALIDAD", nullable = false)
	private String txFinalidad;
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Transient
	private String txDescripcionSubFase;
	
	@Transient
	private String txDescripcionFase;
	
	@Transient
	private Integer idPrograma;

	@Transient
	private String txDescripcionPrograma;

	@Transient
	private Integer idEscuadron;
	
	@Transient
	private Integer idBancoFase;
	
	public SubFaseEntity(Integer idSubFase,
			@NotNull(message = "El ID banco sub fase no puede ser vacio") Integer idBancoSubFase,
			@NotNull(message = "La fase no puede ser vacio") Integer idFase,
			@NotNull(message = "El número total de horas no puede ser vacio") Integer nuTotalHora,
			@NotNull(message = "El número total de misiones no puede ser vacio") Integer nuTotalMision,
			@NotNull(message = "El número total de maniobras no puede ser vacio") Integer nuTotalManiobra,
			@Size(max = 10, message = "El código debe tener {max} caracteres") @NotEmpty(message = "La código no puede ser vacio") String coCodigo,
			@Size(min = 4, max = 200, message = "La finalidad del programa debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La finalidad del programa no puede ser vacio") String txFinalidad,
			Integer flBloqueado, Integer flEstado, String txDescripcionSubFase, String txDescripcionFase,
			Integer idPrograma, String txDescripcionPrograma, Integer idEscuadron, Integer idBancoFase) {
		super();
		this.idSubFase = idSubFase;
		this.idBancoSubFase = idBancoSubFase;
		this.idFase = idFase;
		this.nuTotalHora = nuTotalHora;
		this.nuTotalMision = nuTotalMision;
		this.nuTotalManiobra = nuTotalManiobra;
		this.coCodigo = coCodigo;
		this.txFinalidad = txFinalidad;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionSubFase = txDescripcionSubFase;
		this.txDescripcionFase = txDescripcionFase;
		this.idPrograma = idPrograma;
		this.txDescripcionPrograma = txDescripcionPrograma;
		this.idEscuadron = idEscuadron;
		this.idBancoFase = idBancoFase;
	}

	public SubFaseEntity() {
		super();
	}

	public Integer getIdSubFase() {
		return idSubFase;
	}

	public void setIdSubFase(Integer idSubFase) {
		this.idSubFase = idSubFase;
	}

	public Integer getIdBancoSubFase() {
		return idBancoSubFase;
	}

	public void setIdBancoSubFase(Integer idBancoSubFase) {
		this.idBancoSubFase = idBancoSubFase;
	}

	public Integer getIdFase() {
		return idFase;
	}

	public void setIdFase(Integer idFase) {
		this.idFase = idFase;
	}

	public Integer getNuTotalHora() {
		return nuTotalHora;
	}

	public void setNuTotalHora(Integer nuTotalHora) {
		this.nuTotalHora = nuTotalHora;
	}

	public Integer getNuTotalMision() {
		return nuTotalMision;
	}

	public void setNuTotalMision(Integer nuTotalMision) {
		this.nuTotalMision = nuTotalMision;
	}

	public Integer getNuTotalManiobra() {
		return nuTotalManiobra;
	}

	public void setNuTotalManiobra(Integer nuTotalManiobra) {
		this.nuTotalManiobra = nuTotalManiobra;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
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

	public String getTxDescripcionSubFase() {
		return txDescripcionSubFase;
	}

	public void setTxDescripcionSubFase(String txDescripcionSubFase) {
		this.txDescripcionSubFase = txDescripcionSubFase;
	}

	public String getTxDescripcionFase() {
		return txDescripcionFase;
	}

	public void setTxDescripcionFase(String txDescripcionFase) {
		this.txDescripcionFase = txDescripcionFase;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
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

	public Integer getIdBancoFase() {
		return idBancoFase;
	}

	public void setIdBancoFase(Integer idBancoFase) {
		this.idBancoFase = idBancoFase;
	}

	@Override
	public String toString() {
		return "SubFaseEntity [idSubFase=" + idSubFase + ", idBancoSubFase=" + idBancoSubFase + ", idFase=" + idFase
				+ ", nuTotalHora=" + nuTotalHora + ", nuTotalMision=" + nuTotalMision + ", nuTotalManiobra="
				+ nuTotalManiobra + ", coCodigo=" + coCodigo + ", txFinalidad=" + txFinalidad + ", flBloqueado="
				+ flBloqueado + ", flEstado=" + flEstado + ", txDescripcionSubFase=" + txDescripcionSubFase
				+ ", txDescripcionFase=" + txDescripcionFase + ", idPrograma=" + idPrograma + ", txDescripcionPrograma="
				+ txDescripcionPrograma + ", idEscuadron=" + idEscuadron + ", idBancoFase=" + idBancoFase + "]";
	} 
}
