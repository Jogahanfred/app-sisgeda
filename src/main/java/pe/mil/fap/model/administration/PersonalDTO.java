package pe.mil.fap.model.administration;

import java.io.Serializable;

import jakarta.persistence.Transient;
import pe.mil.fap.common.enums.GradoEnum;

public class PersonalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer orden;
	private Integer plana;
	private String nsa;
	private String grado;
	private String datos;
	private String unidadOrigen;
	private String unidadParte;
	private String dni;
	private String especialidad;
	private String sexo;
	private String nacimiento;
	private String tipoSangre;
	private String fotografia;

	@Transient
	private String datosCompletos;

	@Transient
	private String descriGrado;
	
	@Transient
	private String inicialesDatos;

	public PersonalDTO() {
		super();
	}

	public PersonalDTO(Integer orden, Integer plana, String nsa, String grado, String datos, String unidadOrigen,
			String unidadParte, String dni, String especialidad, String sexo, String nacimiento, String tipoSangre,
			String fotografia) {
		super();
		this.orden = orden;
		this.plana = plana;
		this.nsa = nsa;
		this.grado = grado;
		this.datos = datos;
		this.unidadOrigen = unidadOrigen;
		this.unidadParte = unidadParte;
		this.dni = dni;
		this.especialidad = especialidad;
		this.sexo = sexo;
		this.nacimiento = nacimiento;
		this.tipoSangre = tipoSangre;
		this.fotografia = fotografia;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getPlana() {
		return plana;
	}

	public void setPlana(Integer plana) {
		this.plana = plana;
	}

	public String getNsa() {
		return nsa;
	}

	public void setNsa(String nsa) {
		this.nsa = nsa;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public String getUnidadOrigen() {
		return unidadOrigen;
	}

	public void setUnidadOrigen(String unidadOrigen) {
		this.unidadOrigen = unidadOrigen;
	}

	public String getUnidadParte() {
		return unidadParte;
	}

	public void setUnidadParte(String unidadParte) {
		this.unidadParte = unidadParte;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}

	public String getDatosCompletos() {
		if (datosCompletos == null) {
			if (grado != null && datos != null) {
				datosCompletos = grado + " FAP " + datos;
			} else if (grado != null) {
				datosCompletos = grado;
			} else if (datos != null) {
				datosCompletos = datos;
			} else {
				datosCompletos = "";
			}
		}
		return datosCompletos;
	}

	public String getDescriGrado() {
		if (descriGrado == null && grado != null) {
			GradoEnum g = GradoEnum.valueOf(grado);
			descriGrado = g.getDescripcion();
		}
		return descriGrado;
	}
	
	public String getInicialesDatos() {
		if (datos == null || datos.isEmpty()) {
			return "N-N";
		}

		StringBuilder iniciales = new StringBuilder();
		String[] datosPersonales = datos.split("\\s+");
		for (String dato : datosPersonales) {
			if (!dato.isEmpty()) {
				iniciales.append(Character.toUpperCase(dato.charAt(0)));
			}
		}
		inicialesDatos = iniciales.toString();
		return inicialesDatos;
	}

	@Override
	public String toString() {
		return "PersonaDTO [orden=" + orden + ", plana=" + plana + ", nsa=" + nsa + ", grado=" + grado + ", datos="
				+ datos + ", unidadOrigen=" + unidadOrigen + ", unidadParte=" + unidadParte + ", dni=" + dni
				+ ", especialidad=" + especialidad + ", sexo=" + sexo + ", nacimiento=" + nacimiento + ", tipoSangre="
				+ tipoSangre + "]";
	}

}