package pe.mil.fap.model.helpers;

import java.io.Serializable;
  
public class EjeXDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	  
	private Integer idMision; 
	private String coCodigo; 
	private Integer idTipoMision;
	private String coCodigoTipoMision; 
	private String noNombreTipoMision;
	
	public EjeXDTO() {
		super();
	}
	
	public EjeXDTO(Integer idMision, String coCodigo, Integer idTipoMision, String coCodigoTipoMision,
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
