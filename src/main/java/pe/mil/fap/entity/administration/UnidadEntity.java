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
		@NamedStoredProcedureQuery(name = "unidad.listar", 
								   procedureName = "PKG_UNIDAD.SP_LISTAR", 
								   parameters = {
									    @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class) 
		}),
		@NamedStoredProcedureQuery(name = "unidad.listarPorRector", 
								   procedureName = "PKG_UNIDAD.SP_LISTAR_POR_RECTOR", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO_RECTOR", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = String.class)
		}), 
		@NamedStoredProcedureQuery(name = "unidad.buscarId", 
								   procedureName = "PKG_UNIDAD.SP_BUSCAR_ID", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class) 
		}),
		@NamedStoredProcedureQuery(name = "unidad.insertar", 
								   procedureName = "PKG_UNIDAD.SP_INSERTAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NIVEL", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO_RECTOR", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_LOGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_UBICACION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_INFORMACION", type = String.class),
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class) 
		}),	
		@NamedStoredProcedureQuery(name = "unidad.actualizar", 
								   procedureName = "PKG_UNIDAD.SP_ACTUALIZAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOMBRE", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_DESCRIPCION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NIVEL", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO_RECTOR", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_BLOQUEADO", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_LOGO", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_UBICACION", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_INFORMACION", type = String.class),
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class) 
		}),
		@NamedStoredProcedureQuery(name = "unidad.eliminar", 
								   procedureName = "PKG_UNIDAD.SP_ELIMINAR", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class) 
		}),
		@NamedStoredProcedureQuery(name = "unidad.eliminarMultiple", 
								   procedureName = "PKG_UNIDAD.SP_ELIMINAR_MULTIPLE", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class),
						
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class) 
		}) 
})
@Entity(name = "UnidadEntity")
@Table(name = "TBL_UNIDAD")
public class UnidadEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_UNIDAD")
	private Integer idUnidad;

	@NotNull(message = "El código no puede ser vacio")
	@Column(name = "NU_CODIGO", nullable = false)
	private Integer nuCodigo;

	@Size(min = 1, max = 5, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;

	@Size(min = 5, max = 70, message = "La descripción debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacio")
	@Column(name = "TX_DESCRIPCION", nullable = false)
	private String txDescripcion;

	@NotNull(message = "El nivel no puede ser vacio")
	@Column(name = "NU_NIVEL", nullable = false)
	private Integer nuNivel;

	@NotNull(message = "El código rector no puede ser vacio")
	@Column(name = "NU_CODIGO_RECTOR", nullable = false)
	private Integer nuCodigoRector;

	@Column(name = "FL_BLOQUEADO", nullable = false)
	private Integer flBloqueado;

	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Column(name = "TX_RUTA_LOGO")
	private String txRutaLogo;

	@Column(name = "TX_UBICACION")
	private String txUbicacion;

	@Column(name = "TX_INFORMACION")
	private String txInformacion;

	@Transient
	private String txDescripcionOrganoRector;

	public UnidadEntity() {
		super();
	}

	public UnidadEntity(Integer idUnidad, @NotNull(message = "El código no puede ser vacio") Integer nuCodigo,
			@Size(min = 1, max = 5, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(min = 5, max = 70, message = "La descripción debe tener entre {min} y {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacio") String txDescripcion,
			@NotNull(message = "El nivel no puede ser vacio") Integer nuNivel,
			@NotNull(message = "El código rector no puede ser vacio") Integer nuCodigoRector, Integer flBloqueado,
			Integer flEstado, String txRutaLogo, String txUbicacion, String txInformacion,
			String txDescripcionOrganoRector) {
		super();
		this.idUnidad = idUnidad;
		this.nuCodigo = nuCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.nuNivel = nuNivel;
		this.nuCodigoRector = nuCodigoRector;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txRutaLogo = txRutaLogo;
		this.txUbicacion = txUbicacion;
		this.txInformacion = txInformacion;
		this.txDescripcionOrganoRector = txDescripcionOrganoRector;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Integer getNuCodigo() {
		return nuCodigo;
	}

	public void setNuCodigo(Integer nuCodigo) {
		this.nuCodigo = nuCodigo;
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

	public Integer getNuNivel() {
		return nuNivel;
	}

	public void setNuNivel(Integer nuNivel) {
		this.nuNivel = nuNivel;
	}

	public Integer getNuCodigoRector() {
		return nuCodigoRector;
	}

	public void setNuCodigoRector(Integer nuCodigoRector) {
		this.nuCodigoRector = nuCodigoRector;
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

	public String getTxUbicacion() {
		return txUbicacion;
	}

	public void setTxUbicacion(String txUbicacion) {
		this.txUbicacion = txUbicacion;
	}

	public String getTxInformacion() {
		return txInformacion;
	}

	public void setTxInformacion(String txInformacion) {
		this.txInformacion = txInformacion;
	}

	public String getTxDescripcionOrganoRector() {
		return txDescripcionOrganoRector;
	}

	public void setTxDescripcionOrganoRector(String txDescripcionOrganoRector) {
		this.txDescripcionOrganoRector = txDescripcionOrganoRector;
	}

}
