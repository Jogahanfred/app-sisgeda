package pe.mil.fap.entity.administration;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "personal.paginar", 
								   procedureName = "PKG_PERSONAL.SP_PAGINAR", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PAGE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_SIZE", type = Integer.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FILTER", type = String.class),
											
				   						@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 
		@NamedStoredProcedureQuery(name = "personal.listar", 
								   procedureName = "PKG_PERSONAL.SP_LISTAR", 
								   parameters = {  
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}),
		@NamedStoredProcedureQuery(name = "personal.buscarPorNsa", 
								   procedureName = "PKG_PERSONAL.SP_BUSCAR_POR_NSA", 
								   parameters = { 
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NSA", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
		}), 


		@NamedStoredProcedureQuery(name = "personal.actualizarFotografia", 
								   procedureName = "PKG_PERSONAL.SP_ACTUALIZAR_FOTOGRAFIA", 
								   parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NSA", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FOTOGRAFIA", type = String.class),
									 
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAJE", type = String.class)
		}), 
				
})
@Entity(name = "PersonalEntity")
@Table(name = "TBL_PERSONAL")
public class PersonalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer orden;
	private Integer plana;
	@Id
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
	

	public PersonalEntity() {
		super();
	}

	public PersonalEntity(Integer orden, Integer plana, String nsa, String grado, String datos, String unidadOrigen,
			String unidadParte, String dni, String especialidad, String sexo, String nacimiento, String tipoSangre, String fotografia) {
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
}