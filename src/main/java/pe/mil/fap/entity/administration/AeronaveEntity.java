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
	@NamedStoredProcedureQuery(name = "aeronave.paginar", 
							   procedureName = "PKG_AERONAVE.SP_PAGINAR", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PAGE", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SIZE", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FILTER", type = String.class),
			   						
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}), 
	@NamedStoredProcedureQuery(name = "aeronave.buscarId", 
							   procedureName = "PKG_AERONAVE.SP_BUSCAR_ID", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_AERONAVE", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class) 
	}),
	@NamedStoredProcedureQuery(name = "aeronave.insertar", 
							   procedureName = "PKG_AERONAVE.SP_INSERTAR", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_UNIDAD", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FLOTA", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NRO_COLA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_IMAGEN", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "aeronave.actualizar", 
							   procedureName = "PKG_AERONAVE.SP_ACTUALIZAR", 
							   parameters = {
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_AERONAVE", type = Integer.class), 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_FLOTA", type = Integer.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NRO_COLA", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_RUTA_IMAGEN", type = String.class),
								 
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}), 
	@NamedStoredProcedureQuery(name = "aeronave.eliminar", 
							   procedureName = "PKG_AERONAVE.SP_ELIMINAR", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_AERONAVE", type = Integer.class), 

									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}),
	@NamedStoredProcedureQuery(name = "aeronave.eliminarMultiple", 
							   procedureName = "PKG_AERONAVE.SP_ELIMINAR_MULTIPLE", 
							   parameters = {  
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_IDS", type = String.class), 
					
									@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
	}) 
			
})
@Entity(name = "AeronaveEntity")
@Table(name = "TBL_AERONAVE")
public class AeronaveEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_AERONAVE")
	private Integer idAeronave;	
	
	@NotNull(message = "La unidad no puede ser vacio")
	@Column(name = "ID_UNIDAD", nullable = false)
	private Integer idUnidad;
	
	@NotNull(message = "La flota no puede ser vacio")
	@Column(name = "ID_FLOTA", nullable = false)
	private Integer idFlota;

	@Size(max = 10, message = "El código de cola debe tener máximo {max} caracteres")
	@NotEmpty(message = "El codigó de cola no puede ser vacio")
	@Column(name = "CO_NRO_COLA", nullable = false)
	private String coNroCola;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;
	
	@Column(name = "TX_RUTA_IMAGEN")
	private String txRutaImagen; 

	@Transient
	private String txDescripcionUnidad;
	
	@Transient
	private String txDescripcionFlota;
 
	public AeronaveEntity() {
		super();
	}  

	public AeronaveEntity(Integer idAeronave, @NotNull(message = "La unidad no puede ser vacio") Integer idUnidad,
			@NotNull(message = "La flota no puede ser vacio") Integer idFlota,
			@Size(max = 10, message = "El código de cola debe tener máximo {max} caracteres") @NotEmpty(message = "El codigó de cola no puede ser vacio") String coNroCola,
			Integer flEstado, String txRutaImagen, String txDescripcionUnidad, String txDescripcionFlota) {
		super();
		this.idAeronave = idAeronave;
		this.idUnidad = idUnidad;
		this.idFlota = idFlota;
		this.coNroCola = coNroCola;
		this.flEstado = flEstado;
		this.txRutaImagen = txRutaImagen;
		this.txDescripcionUnidad = txDescripcionUnidad;
		this.txDescripcionFlota = txDescripcionFlota;
	}
 
	public Integer getIdAeronave() {
		return idAeronave;
	}

	public void setIdAeronave(Integer idAeronave) {
		this.idAeronave = idAeronave;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Integer getIdFlota() {
		return idFlota;
	}

	public void setIdFlota(Integer idFlota) {
		this.idFlota = idFlota;
	}

	public String getCoNroCola() {
		return coNroCola;
	}

	public void setCoNroCola(String coNroCola) {
		this.coNroCola = coNroCola;
	}

	public Integer getFlEstado() {
		return flEstado;
	}

	public void setFlEstado(Integer flEstado) {
		this.flEstado = flEstado;
	}

	public String getTxRutaImagen() {
		return txRutaImagen;
	}

	public void setTxRutaImagen(String txRutaImagen) {
		this.txRutaImagen = txRutaImagen;
	}

	public String getTxDescripcionUnidad() {
		return txDescripcionUnidad;
	}

	public void setTxDescripcionUnidad(String txDescripcionUnidad) {
		this.txDescripcionUnidad = txDescripcionUnidad;
	}

	public String getTxDescripcionFlota() {
		return txDescripcionFlota;
	}

	public void setTxDescripcionFlota(String txDescripcionFlota) {
		this.txDescripcionFlota = txDescripcionFlota;
	}  
}
