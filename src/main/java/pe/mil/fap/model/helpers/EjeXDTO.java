package pe.mil.fap.model.helpers;

import java.io.Serializable;
  
public class EjeXDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	  
	private Integer idMision; 
	private String coCodigo; 
	private Integer idTipoMision;
	private String coCodigoTipoMision; 
	private String noNombreTipoMision;
	private Integer idCalificacion;
	private Integer idCalificador;
	
	private Integer flHabilitado;
	
	public EjeXDTO() {
		super();
	}
	
	public EjeXDTO(Integer idMision, String coCodigo, Integer idTipoMision, String coCodigoTipoMision,
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

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Integer getIdCalificador() {
		return idCalificador;
	}

	public void setIdCalificador(Integer idCalificador) {
		this.idCalificador = idCalificador;
	}

	public Integer getFlHabilitado() {
		return flHabilitado;
	}

	public void setFlHabilitado(Integer flHabilitado) {
		this.flHabilitado = flHabilitado;
	} 
	  
	
	     
}
