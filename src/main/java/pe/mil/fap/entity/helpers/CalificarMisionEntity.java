package pe.mil.fap.entity.helpers;

import java.io.Serializable;

public class CalificarMisionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer idMision;
	private String txDescripcionTipoMision;
	private String txNombreTipoMision;
	private String coCodigo;
	private Integer flChequeo;
	private Integer idCalificador;
	private Integer idCalificacion;
	
	private Double qtNota;
	private String coCalificacionVuelo;
	private String coNroCola;
	private String feProgramacion;
	private String feEjecucion;
	
	public CalificarMisionEntity() {
		super(); 
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public String getTxDescripcionTipoMision() {
		return txDescripcionTipoMision;
	}

	public void setTxDescripcionTipoMision(String txDescripcionTipoMision) {
		this.txDescripcionTipoMision = txDescripcionTipoMision;
	}

	public String getTxNombreTipoMision() {
		return txNombreTipoMision;
	}

	public void setTxNombreTipoMision(String txNombreTipoMision) {
		this.txNombreTipoMision = txNombreTipoMision;
	}

	public String getCoCodigo() {
		return coCodigo;
	}

	public void setCoCodigo(String coCodigo) {
		this.coCodigo = coCodigo;
	}

	public Integer getFlChequeo() {
		return flChequeo;
	}

	public void setFlChequeo(Integer flChequeo) {
		this.flChequeo = flChequeo;
	}

	public Integer getIdCalificador() {
		return idCalificador;
	}

	public void setIdCalificador(Integer idCalificador) {
		this.idCalificador = idCalificador;
	}

	public Integer getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Integer idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public Double getQtNota() {
		return qtNota;
	}

	public void setQtNota(Double qtNota) {
		this.qtNota = qtNota;
	}

	public String getCoCalificacionVuelo() {
		return coCalificacionVuelo;
	}

	public void setCoCalificacionVuelo(String coCalificacionVuelo) {
		this.coCalificacionVuelo = coCalificacionVuelo;
	}

	public String getCoNroCola() {
		return coNroCola;
	}

	public void setCoNroCola(String coNroCola) {
		this.coNroCola = coNroCola;
	}

	public String getFeProgramacion() {
		return feProgramacion;
	}

	public void setFeProgramacion(String feProgramacion) {
		this.feProgramacion = feProgramacion;
	}

	public String getFeEjecucion() {
		return feEjecucion;
	}

	public void setFeEjecucion(String feEjecucion) {
		this.feEjecucion = feEjecucion;
	}
	
	
}
