package pe.mil.fap.model.security;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idUsuario;
    private String noNombre;
    private String noContrasena;
    private String coNsa; 
    
    private Integer idUnidad;
    private Integer nuPeriodo;
    private Integer idEscuadron;   
    
    private List<RolDTO> lstRoles;

    public UsuarioDTO() {
        super();
    }

    public UsuarioDTO(Integer idUsuario, String noNombre, String noContrasena, String coNsa, 
                      Integer idUnidad, Integer nuPeriodo, Integer idEscuadron, List<RolDTO> lstRoles) {
        super();
        this.idUsuario = idUsuario;
        this.noNombre = noNombre;
        this.noContrasena = noContrasena;
        this.coNsa = coNsa;  
        this.idUnidad = idUnidad;
        this.nuPeriodo = nuPeriodo;
        this.idEscuadron = idEscuadron;  
        this.lstRoles = lstRoles;
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

    public String getCoNsa() { 
        return coNsa;
    }

    public void setCoNsa(String coNsa) {  
        this.coNsa = coNsa;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getNuPeriodo() {
        return nuPeriodo;
    }

    public void setNuPeriodo(Integer nuPeriodo) {
        this.nuPeriodo = nuPeriodo;
    }

    public Integer getIdEscuadron() { 
        return idEscuadron;
    }

    public void setIdEscuadron(Integer idEscuadron) {  
        this.idEscuadron = idEscuadron;
    }

    public List<RolDTO> getLstRoles() {
        return lstRoles;
    }

    public void setLstRoles(List<RolDTO> lstRoles) {
        this.lstRoles = lstRoles;
    }

    @Override
    public String toString() {
        return "UsuarioDTO [idUsuario=" + idUsuario + ", noNombre=" + noNombre + ", noContrasena=" + noContrasena
                + ", coNsa=" + coNsa + ", idUnidad=" + idUnidad + ", nuPeriodo=" + nuPeriodo + ", idEscuadron=" + idEscuadron 
                + ", lstRoles=" + lstRoles + "]";
    }

    public UsuarioDTO(UsuarioDTOBuilder builder) {
        this.idUsuario = builder.idUsuario;
        this.noNombre = builder.noNombre;
        this.noContrasena = builder.noContrasena;
        this.coNsa = builder.coNsa;  
        this.idUnidad = builder.idUnidad;
        this.nuPeriodo = builder.nuPeriodo;
        this.idEscuadron = builder.idEscuadron;  
        this.lstRoles = builder.lstRoles;
    }

    public static UsuarioDTOBuilder builder() {
        return new UsuarioDTOBuilder();
    }

    public static class UsuarioDTOBuilder {
        private Integer idUsuario;
        private String noNombre;
        private String noContrasena;
        private String coNsa;  
        private Integer idUnidad;
        private Integer nuPeriodo;
        private Integer idEscuadron;   
        private List<RolDTO> lstRoles;

        public UsuarioDTOBuilder() {}

        public UsuarioDTOBuilder(Integer idUsuario, String noNombre, String noContrasena, String coNsa, 
                                 Integer idUnidad, Integer nuPeriodo, Integer idEscuadron, List<RolDTO> lstRoles) {
            super();
            this.idUsuario = idUsuario;
            this.noNombre = noNombre;
            this.noContrasena = noContrasena;
            this.coNsa = coNsa;  
            this.idUnidad = idUnidad;
            this.nuPeriodo = nuPeriodo;
            this.idEscuadron = idEscuadron;   
            this.lstRoles = lstRoles;
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

        public UsuarioDTOBuilder coNsa(String coNsa) {  
            this.coNsa = coNsa;
            return this;
        }

        public UsuarioDTOBuilder idUnidad(Integer idUnidad) {
            this.idUnidad = idUnidad;
            return this;
        }

        public UsuarioDTOBuilder nuPeriodo(Integer nuPeriodo) {
            this.nuPeriodo = nuPeriodo;
            return this;
        }

        public UsuarioDTOBuilder idEscuadron(Integer idEscuadron) {   
            this.idEscuadron = idEscuadron;
            return this;
        }

        public UsuarioDTOBuilder lstRoles(List<RolDTO> lstRoles) {
            this.lstRoles = lstRoles;
            return this;
        }

        public UsuarioDTO build() {
            return new UsuarioDTO(this);
        }
    }
}
