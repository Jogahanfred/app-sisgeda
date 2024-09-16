package pe.mil.fap.model.helpers;

import java.io.Serializable;
import java.util.List;

public abstract class OperacionBaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idOperacion;
	private String noNombre;
	private List<ManiobraBaseDTO> lstManiobras;

	public OperacionBaseDTO() {
		super();
	}

	public static class ManiobraBaseDTO implements Serializable {

		private static final long serialVersionUID = 1L;
		private Integer idManiobra;
		private String txDescripcionBancoManiobra; 
		private Integer flTransaccion;
        private Integer nuOrden;

		public ManiobraBaseDTO() {
			super();
		}

		public Integer getIdManiobra() {
			return idManiobra;
		}

		public void setIdManiobra(Integer idManiobra) {
			this.idManiobra = idManiobra;
		}

		public String getTxDescripcionBancoManiobra() {
			return txDescripcionBancoManiobra;
		}

		public void setTxDescripcionBancoManiobra(String txDescripcionBancoManiobra) {
			this.txDescripcionBancoManiobra = txDescripcionBancoManiobra;
		}

		public Integer getFlTransaccion() {
			return flTransaccion;
		}

		public void setFlTransaccion(Integer flTransaccion) {
			this.flTransaccion = flTransaccion;
		}

		public Integer getNuOrden() {
			return nuOrden;
		}

		public void setNuOrden(Integer nuOrden) {
			this.nuOrden = nuOrden;
		}

	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	public String getNoNombre() {
		return noNombre;
	}

	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}

	public List<ManiobraBaseDTO> getLstManiobras() {
		return lstManiobras;
	}

	public void setLstManiobras(List<ManiobraBaseDTO> lstManiobras) {
		this.lstManiobras = lstManiobras;
	}

}
