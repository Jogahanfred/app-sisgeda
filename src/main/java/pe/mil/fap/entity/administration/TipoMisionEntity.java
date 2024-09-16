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
		@NamedStoredProcedureQuery(name = "tipoMision.listar", 
								   procedureName = "PKG_TIPO_MISION.SP_LISTAR", 
								   parameters = { 
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "tipoMision.insertar", 
								   procedureName = "PKG_TIPO_MISION.SP_INSERTAR", 
								   parameters = {						
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class),
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "tipoMision.actualizar", 
								   procedureName = "PKG_TIPO_MISION.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_TIPO_MISION", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class), 
										
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "tipoMision.eliminar", 
								   procedureName = "PKG_TIPO_MISION.SP_ELIMINAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_TIPO_MISION", type = Integer.class), 

										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}),
		@NamedStoredProcedureQuery(name = "tipoMision.eliminarMultiple", 
								   procedureName = "PKG_TIPO_MISION.SP_ELIMINAR_MULTIPLE", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}) 
				
})
@Entity(name = "TipoMisionEntity")
@Table(name = "TBL_TIPO_MISION")
public class TipoMisionEntity implements Serializable {

	private static final long serialVersionUID = -6036989427138566658L;

	@Id
	@Column(name = "ID_TIPO_MISION")
	private Integer idTipoMision; 

	@Size(min = 4, max = 10, message = "El código de tipo misión debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código de tipoMision no puede ser vacio")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;

	@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre; 

	@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacia")
	@Column(name = "TX_DESCRIPCION", nullable = false)
	private String txDescripcion;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	public TipoMisionEntity() {
	}

	public TipoMisionEntity(Integer idTipoMision,
			@Size(min = 4, max = 10, message = "El código de tipoMision debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código de tipoMision no puede ser vacio") String coCodigo,
			@Size(min = 5, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacia") String txDescripcion,
			Integer flEstado) {
		super();
		this.idTipoMision = idTipoMision;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.flEstado = flEstado;
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

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}
 

}
