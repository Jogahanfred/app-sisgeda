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
	@NamedStoredProcedureQuery(name = "escuadron.listar", 
							   procedureName = "PKG_ESCUADRON.SP_LISTAR", 
							   parameters = { 
			   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "escuadron.listarPorIdUnidadRector", 
							   procedureName = "PKG_ESCUADRON.SP_LISTAR_POR_ID_UNIDAD_RECTOR", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 

	@NamedStoredProcedureQuery(name = "escuadron.listarPorIdUnidad", 
							   procedureName = "PKG_ESCUADRON.SP_LISTAR_POR_ID_UNIDAD", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "escuadron.buscarId", 
							   procedureName = "PKG_ESCUADRON.SP_BUSCAR_ID", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class) 
	}),
	@NamedStoredProcedureQuery(name = "escuadron.insertar", 
							   procedureName = "PKG_ESCUADRON.SP_INSERTAR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SIGLA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_LOGO", type = String.class),  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_INFORMACION", type = String.class), 
									 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "escuadron.actualizar", 
							   procedureName = "PKG_ESCUADRON.SP_ACTUALIZAR", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SIGLA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_LOGO", type = String.class),  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_INFORMACION", type = String.class), 
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "escuadron.eliminar", 
							   procedureName = "PKG_ESCUADRON.SP_ELIMINAR", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_ESCUADRON", type = Integer.class), 

									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}),
	@NamedStoredProcedureQuery(name = "escuadron.eliminarMultiple", 
							   procedureName = "PKG_ESCUADRON.SP_ELIMINAR_MULTIPLE", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
					
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}) 
			
})
@Entity(name = "EscuadronEntity")
@Table(name = "TBL_ESCUADRON")
public class EscuadronEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ESCUADRON")
	private Integer idEscuadron;
	
	@NotNull(message = "La unidad no puede ser vacio")
	@Column(name = "ID_UNIDAD", nullable = false)
	private Integer idUnidad;

	@Size(max = 5, message = "La sigla debe tener máximo {max} caracteres")
	@NotEmpty(message = "La sigla no puede ser vacia")
	@Column(name = "CO_SIGLA", nullable = false)
	private String coSigla;
	
	@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacia")
	@Column(name = "TX_DESCRIPCION", nullable = false)
	private String txDescripcion;
	
	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;
	
	@Column(name = "TX_RUTA_LOGO")
	private String txRutaLogo; 

	@Column(name = "TX_INFORMACION")
	private String txInformacion;

	@Transient
	private String txDescripcionUnidad;
	
	public EscuadronEntity() {
		super();
	}  
 
	public EscuadronEntity(Integer idEscuadron, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@Size(max = 5, message = "La sigla debe tener máximo {max} caracteres") @NotEmpty(message = "La sigla no puede ser vacia") String coSigla,
			@Size(max = 70, message = "La descripción debe tener máximo {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacia") String txDescripcion,
			Integer flBloqueado, Integer flEstado, String txRutaLogo, String txInformacion,
			String txDescripcionUnidad) {
		super();
		this.idEscuadron = idEscuadron;
		this.idUnidad = idUnidad;
		this.coSigla = coSigla;
		this.txDescripcion = txDescripcion;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txRutaLogo = txRutaLogo;
		this.txInformacion = txInformacion;
		this.txDescripcionUnidad = txDescripcionUnidad;
	}


	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCoSigla() {
		return coSigla;
	}

	public void setCoSigla(String coSigla) {
		this.coSigla = coSigla;
	}

	public String getTxDescripcion() {
		return txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
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

	public String getTxRutaLogo() {
		return txRutaLogo;
	}

	public void setTxRutaLogo(String txRutaLogo) {
		this.txRutaLogo = txRutaLogo;
	} 
	
	public String getTxInformacion() {
		return txInformacion;
	}

	public void setTxInformacion(String txInformacion) {
		this.txInformacion = txInformacion;
	}

	public String getTxDescripcionUnidad() {
		return txDescripcionUnidad;
	}

	public void setTxDescripcionUnidad(String txDescripcionUnidad) {
		this.txDescripcionUnidad = txDescripcionUnidad;
	}
 

}
