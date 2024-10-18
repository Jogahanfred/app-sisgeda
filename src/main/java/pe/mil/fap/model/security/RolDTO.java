package pe.mil.fap.model.security;

import java.io.Serializable;

public class RolDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idRol;
	private String noNombre;
	private String txDescripcion;

	public RolDTO() {
		super();
	}

	public RolDTO(Integer idRol, String noNombre, String txDescripcion) {
		super();
		this.idRol = idRol;
		this.noNombre = noNombre;
		this.txDescripcion = txDescripcion;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
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

}
