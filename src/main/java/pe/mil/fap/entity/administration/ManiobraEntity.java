package pe.mil.fap.entity.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;
import jakarta.persistence.Transient; 
import jakarta.validation.constraints.NotNull; 
import java.io.Serializable;

import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.ParameterMode;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "maniobra.listar", 
								   procedureName = "PKG_MANIOBRA.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "maniobra.listarPorOperacion", 
								   procedureName = "PKG_MANIOBRA.SP_LISTAR_POR_OPERACION", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "maniobra.insertar", 
								   procedureName = "PKG_MANIOBRA.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class),  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_MANIOBRA", type = Integer.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "maniobra.actualizar", 
								   procedureName = "PKG_MANIOBRA.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MANIOBRA", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_BANCO_MANIOBRA", type = Integer.class), 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_OPERACION", type = Integer.class),  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "maniobra.eliminar", 
								   procedureName = "PKG_MANIOBRA.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_MANIOBRA", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "maniobra.eliminarMultiple", 
								   procedureName = "PKG_MANIOBRA.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})

@Entity(name = "ManiobraEntity")
@Table(name = "TBL_MANIOBRA")
public class ManiobraEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_MANIOBRA")
	private Integer idManiobra;

	@NotNull(message = "La maniobra no puede ser vacia")
	@Column(name = "ID_BANCO_MANIOBRA", nullable = false)
	private Integer idBancoManiobra; 

	@NotNull(message = "La operación no puede ser vacia")
	@Column(name = "ID_OPERACION", nullable = false)
	private Integer idOperacion; 
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Transient
	private String txDescripcionOperacion;

	@Transient
	private String txDescripcionBancoManiobra;

	@Transient
	private Integer idEscuadron;

	@Transient
	private String txDescripcionEscuadron;

	public ManiobraEntity() {
	}

	public ManiobraEntity(Integer idManiobra,
			@NotNull(message = "La maniobra no puede ser vacia") Integer idBancoManiobra,
			@NotNull(message = "La operación no puede ser vacia") Integer idOperacion, Integer flBloqueado,
			Integer flEstado, String txDescripcionOperacion, String txDescripcionBancoManiobra,
			Integer idEscuadron, String txDescripcionEscuadron) {
		super();
		this.idManiobra = idManiobra;
		this.idBancoManiobra = idBancoManiobra;
		this.idOperacion = idOperacion;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionOperacion = txDescripcionOperacion;
		this.txDescripcionBancoManiobra = txDescripcionBancoManiobra;
		this.idEscuadron = idEscuadron;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdBancoManiobra() {
		return idBancoManiobra;
	}

	public void setIdBancoManiobra(Integer idBancoManiobra) {
		this.idBancoManiobra = idBancoManiobra;
	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
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

	public String getTxDescripcionOperacion() {
		return txDescripcionOperacion;
	}

	public void setTxDescripcionOperacion(String txDescripcionOperacion) {
		this.txDescripcionOperacion = txDescripcionOperacion;
	}

	public String getTxDescripcionBancoManiobra() {
		return txDescripcionBancoManiobra;
	}

	public void setTxDescripcionBancoManiobra(String txDescripcionBancoManiobra) {
		this.txDescripcionBancoManiobra = txDescripcionBancoManiobra;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public String getTxDescripcionEscuadron() {
		return txDescripcionEscuadron;
	}

	public void setTxDescripcionEscuadron(String txDescripcionEscuadron) {
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}
  

}
