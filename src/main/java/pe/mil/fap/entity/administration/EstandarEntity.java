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
	@NamedStoredProcedureQuery(name = "estandar.listar", 
							   procedureName = "PKG_ESTANDAR.SP_LISTAR", 
							   parameters = { 
			   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}),
	@NamedStoredProcedureQuery(name = "estandar.buscarPorCodigo", 
							   procedureName = "PKG_ESTANDAR.SP_BUSCAR_POR_CODIGO", 
							   parameters = { 
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_CODIGO", type = String.class),
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_OBJECTO", type = void.class)
	})  
})
@Entity(name = "EstandarEntity")
@Table(name = "TBL_ESTANDAR")
public class EstandarEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_ESTANDAR")
	private Integer idEstandar;

	@Size(min = 4, max = 10, message = "El código del estándar debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código del estándar no puede ser vacio")
	@Column(name = "CO_CODIGO", nullable = false)
	private String coCodigo;
	
	@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "NO_NOMBRE", nullable = false)
	private String noNombre;
	
	@Size(max = 200, message = "La descripciondebe tener {max} caracteres")
	@NotEmpty(message = "La descripción no puede ser vacio")
	@Column(name = "TX_DESCRIPCION", nullable = false)
	private String txDescripcion;
	
	@Column(name = "FL_ESTADO", nullable = false)
	private Integer flEstado;

	@Column(name = "NU_NIVEL", nullable = true)
	private Integer nuNivel;

	public EstandarEntity(Integer idEstandar,
			@Size(min = 4, max = 10, message = "El código del estándar debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código del estándar no puede ser vacio") String coCodigo,
			@Size(min = 4, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El nombre no puede ser vacio") String noNombre,
			@Size(max = 200, message = "La descripciondebe tener {max} caracteres") @NotEmpty(message = "La descripción no puede ser vacio") String txDescripcion,
			Integer flEstado, Integer nuNivel) {
		super();
		this.idEstandar = idEstandar;
		this.coCodigo = coCodigo;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
		this.flEstado = flEstado;
		this.nuNivel = nuNivel;
	}

	public EstandarEntity() {
		super();
	}

	public Integer getIdEstandar() {
		return idEstandar;
	}

	public void setIdEstandar(Integer idEstandar) {
		this.idEstandar = idEstandar;
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

	public Integer getNuNivel() {
		return nuNivel;
	}

	public void setNuNivel(Integer nuNivel) {
		this.nuNivel = nuNivel;
	} 

}
