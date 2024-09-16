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
	
	public EjeXEntity() {
		super();
	}
	
	public EjeXEntity(Integer idMision, String coCodigo, Integer idTipoMision, String coCodigoTipoMision,
			String noNombreTipoMision) {
		super();
		this.idMision = idMision;
		this.coCodigo = coCodigo;
		this.idTipoMision = idTipoMision;
		this.coCodigoTipoMision = coCodigoTipoMision;
		this.noNombreTipoMision = noNombreTipoMision;
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
	  
	     
}
