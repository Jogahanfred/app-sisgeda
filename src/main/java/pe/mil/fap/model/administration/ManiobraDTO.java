package pe.mil.fap.model.administration;

import java.io.Serializable;
 
import jakarta.validation.constraints.NotNull;

public class ManiobraDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idManiobra;

	@NotNull(message = "La maniobra no puede ser vacia")
	private Integer idBancoManiobra;

	@NotNull(message = "La operación no puede ser vacia")
	private Integer idOperacion;

	private Integer flBloqueado;

	private Integer flEstado;

	private String txDescripcionOperacion;

	private String txDescripcionBancoManiobra;
 
	private Integer idEscuadron;
 
	private String txDescripcionEscuadron;

	public ManiobraDTO() {
		super();
	}

	public ManiobraDTO(Integer idManiobra, @NotNull(message = "La maniobra no puede ser vacia") Integer idBancoManiobra,
			@NotNull(message = "La operación no puede ser vacia") Integer idOperacion, Integer flBloqueado,
			Integer flEstado, String txDescripcionOperacion, String txDescripcionBancoManiobra,
			Integer idEscuadron, String txDescripcionEscuadron) {
		super();
		this.idManiobra = idManiobra;
		this.idBancoManiobra = idBancoManiobra;
		this.idOperacion = idOperacion;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.txDescripcionOperacion = txDescripcionOperacion;
		this.txDescripcionBancoManiobra = txDescripcionBancoManiobra;
		this.idEscuadron = idEscuadron;
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdBancoManiobra() {
		return idBancoManiobra;
	}

	public void setIdBancoManiobra(Integer idBancoManiobra) {
		this.idBancoManiobra = idBancoManiobra;
	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
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

	public String getTxDescripcionOperacion() {
		return txDescripcionOperacion;
	}

	public void setTxDescripcionOperacion(String txDescripcionOperacion) {
		this.txDescripcionOperacion = txDescripcionOperacion;
	}

	public String getTxDescripcionBancoManiobra() {
		return txDescripcionBancoManiobra;
	}

	public void setTxDescripcionBancoManiobra(String txDescripcionBancoManiobra) {
		this.txDescripcionBancoManiobra = txDescripcionBancoManiobra;
	}

	public Integer getIdEscuadron() {
		return idEscuadron;
	}

	public void setIdEscuadron(Integer idEscuadron) {
		this.idEscuadron = idEscuadron;
	}

	public String getTxDescripcionEscuadron() {
		return txDescripcionEscuadron;
	}

	public void setTxDescripcionEscuadron(String txDescripcionEscuadron) {
		this.txDescripcionEscuadron = txDescripcionEscuadron;
	}

}
