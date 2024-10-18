package pe.mil.fap.entity.helpers;

import java.io.Serializable;
 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter; 

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "gestionSubFase.listarEjeX", 
							   procedureName = "PKG_GESTION_SUB_FASE.SP_LISTAR_EJE_X", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}),
	@NamedStoredProcedureQuery(name = "calificarMision.listarEjeX", 
							   procedureName = "PKG_CALIFICAR_MISION.SP_LISTAR_EJE_X", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICADO", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	}),
	@NamedStoredProcedureQuery(name = "calificarMision.listarEjeXPorIdCalificacion", 
	   procedureName = "PKG_CALIFICAR_MISION.SP_LISTAR_EJE_X_POR_ID_CALIFICACION", 
	   parameters = {						
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICADO", type = Integer.class), 
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_CALIFICACION", type = Integer.class), 
					
			@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
})
})

@Entity(name = "EjeXEntity")
public class EjeXEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	  
	
	@Id  
	private Integer idMision; 
	private String coCodigo; 
	private Integer idTipoMision;
	private String coCodigoTipoMision; 
	private String noNombreTipoMision;
	private Integer idCalificacion;
	private Integer idCalificador;
	
	public EjeXEntity() {
		super();
	}
	
 
	public EjeXEntity(Integer idMision, String coCodigo, Integer idTipoMision, String coCodigoTipoMision,
			String noNombreTipoMision, Integer idCalificacion, Integer idCalificador) {
		super();
		this.idMision = idMision;
		this.coCodigo = coCodigo;
		this.idTipoMision = idTipoMision;
		this.coCodigoTipoMision = coCodigoTipoMision;
		this.noNombreTipoMision = noNombreTipoMision;
		this.idCalificacion = idCalificacion;
		this.idCalificador = idCalificador;
	}


	public Integer getIdCalificacion() {
		return idCalificacion;
	}


	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}


	public Integer getIdMision() {
		return idMision;
	}
	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}
	public String getCoCodigo() {
		return coCodigo;
	}
	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
	}
	public Integer getIdTipoMision() {
		return idTipoMision;
	}
	public void setIdTipoMision(Integer idTipoMision) {
		this.idTipoMision = idTipoMision;
	}
	public String getCoCodigoTipoMision() {
		return coCodigoTipoMision;
	}
	public void setCoCodigoTipoMision(String coCodigoTipoMision) {
		this.coCodigoTipoMision = coCodigoTipoMision;
	}
	public String getNoNombreTipoMision() {
		return noNombreTipoMision;
	}
	public void setNoNombreTipoMision(String noNombreTipoMision) {
		this.noNombreTipoMision = noNombreTipoMision;
	}

	public Integer getIdCalificador() {
		return idCalificador;
	}


	public void setIdCalificador(Integer idCalificador) {
		this.idCalificador = idCalificador;
	} 
	  
	     
}
