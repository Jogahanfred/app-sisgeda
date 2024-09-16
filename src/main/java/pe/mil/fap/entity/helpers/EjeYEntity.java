package pe.mil.fap.entity.helpers;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "gestionSubFase.listarEjeY", 
							   procedureName = "PKG_GESTION_SUB_FASE.SP_LISTAR_EJE_Y", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	})
})

@Entity(name = "EjeYEntity")
public class EjeYEntity implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idManiobra;
	private String noNombreManiobra;
	private Integer idOperacion;
	private String noNombreOperacion;
	
	public EjeYEntity() {
		super();
	}
	
	public EjeYEntity(Integer idManiobra, String noNombreManiobra, Integer idOperacion, String noNombreOperacion) {
		super();
		this.idManiobra = idManiobra;
		this.noNombreManiobra = noNombreManiobra;
		this.idOperacion = idOperacion;
		this.noNombreOperacion = noNombreOperacion;
	}
	
	public Integer getIdManiobra() {
		return idManiobra;
	}
	
	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}
	
	public String getNoNombreManiobra() {
		return noNombreManiobra;
	}
	
	public void setNoNombreManiobra(String noNombreManiobra) {
		this.noNombreManiobra = noNombreManiobra;
	}
	
	public Integer getIdOperacion() {
		return idOperacion;
	}
	
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}
	
	public String getNoNombreOperacion() {
		return noNombreOperacion;
	}
	
	public void setNoNombreOperacion(String noNombreOperacion) {
		this.noNombreOperacion = noNombreOperacion;
	}
	
	
}
