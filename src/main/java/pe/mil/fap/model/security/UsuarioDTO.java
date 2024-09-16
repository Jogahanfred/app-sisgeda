package pe.mil.fap.model.security;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Integer idUsuario;
	private String noNombre;
	private String noContrasena;
	private Integer idUnidad;
	
	public UsuarioDTO() {
		super();
	}
	public UsuarioDTO(Integer idUsuario, String noNombre, String noContrasena, Integer idUnidad) {
		super();
		this.idUsuario = idUsuario;
		this.noNombre = noNombre;
		this.noContrasena = noContrasena;
		this.idUnidad = idUnidad;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNoNombre() {
		return noNombre;
	}
	public void setNoNombre(String noNombre) {
		this.noNombre = noNombre;
	}
	public String getNoContrasena() {
		return noContrasena;
	}
	public void setNoContrasena(String noContrasena) {
		this.noContrasena = noContrasena;
	}
	public Integer getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	
	@Override
	public String toString() {
		return "UsuarioDTO [idUsuario=" + idUsuario + ", noNombre=" + noNombre + ", noContrasena=" + noContrasena
				+ ", idUnidad=" + idUnidad + "]";
	}
	
	public UsuarioDTO(UsuarioDTOBuilder builder) {
		this.idUsuario = builder.idUsuario;
		this.noNombre = builder.noNombre;
		this.noContrasena = builder.noContrasena;
		this.idUnidad = builder.idUnidad;
	}
	
	public static UsuarioDTOBuilder builder() {
		return new UsuarioDTOBuilder();
	}
	
	public static class UsuarioDTOBuilder { 
		private Integer idUsuario;
		private String noNombre;
		private String noContrasena;
		private Integer idUnidad;
		
		public UsuarioDTOBuilder() {}
		public UsuarioDTOBuilder(Integer idUsuario, String noNombre, String noContrasena, Integer idUnidad) {
			super();
			this.idUsuario = idUsuario;
			this.noNombre = noNombre;
			this.noContrasena = noContrasena;
			this.idUnidad = idUnidad;
		}
		
		public UsuarioDTOBuilder idUsuario(Integer idUsuario) {
			this.idUsuario = idUsuario;
			return this;
		}
		public UsuarioDTOBuilder noNombre(String noNombre) {
			this.noNombre = noNombre;
			return this;
		}
		public UsuarioDTOBuilder noContrasena(String noContrasena) {
			this.noContrasena = noContrasena;
			return this;
		}
		public UsuarioDTOBuilder idUnidad(Integer idUnidad) {
			this.idUnidad = idUnidad;
			return this;
		}
		
		public UsuarioDTO build() {
			return new UsuarioDTO(this);
		}
		
	}

}
