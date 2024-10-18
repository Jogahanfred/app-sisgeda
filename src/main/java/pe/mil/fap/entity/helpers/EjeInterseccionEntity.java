package pe.mil.fap.entity.helpers;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Transient;
import pe.mil.fap.entity.administration.RestriccionEstandarEntity;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "gestionSubFase.listarInterseccion", 
							   procedureName = "PKG_GESTION_SUB_FASE.SP_LISTAR_INTERSECCION", 
							   parameters = {						
									@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_ID_SUB_FASE", type = Integer.class), 
									
									@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "P_CURSOR", type = void.class)
	})
})

@Entity(name = "EjeInterseccionEntity")
public class EjeInterseccionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idDetalleMision;
	private Integer idMision;
	private Integer idManiobra;
	private Integer idEstandarRequerido;
	private String coCodigoEstandar;
	
	@Transient
	private List<RestriccionEstandarEntity> lstRestricciones;
	
	public EjeInterseccionEntity() {
		super();
	}

	public EjeInterseccionEntity(Integer idDetalleMision, Integer idMision, Integer idManiobra, Integer idEstandarRequerido, String coCodigoEstandar, List<RestriccionEstandarEntity> lstRestricciones) {
		super();
		this.idDetalleMision = idDetalleMision;
		this.idMision = idMision;
		this.idManiobra = idManiobra;
		this.idEstandarRequerido = idEstandarRequerido;
		this.coCodigoEstandar = coCodigoEstandar;
		this.lstRestricciones = lstRestricciones;
	}

	public Integer getIdDetalleMision() {
		return idDetalleMision;
	}

	public void setIdDetalleMision(Integer idDetalleMision) {
		this.idDetalleMision = idDetalleMision;
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public Integer getIdManiobra() {
		return idManiobra;
	}

	public void setIdManiobra(Integer idManiobra) {
		this.idManiobra = idManiobra;
	}

	public Integer getIdEstandarRequerido() {
		return idEstandarRequerido;
	}

	public void setIdEstandarRequerido(Integer idEstandarRequerido) {
		this.idEstandarRequerido = idEstandarRequerido;
	}

	public String getCoCodigoEstandar() {
		return coCodigoEstandar;
	}

	public void setCoCodigoEstandar(String coCodigoEstandar) {
		this.coCodigoEstandar = coCodigoEstandar;
	}

	public List<RestriccionEstandarEntity> getLstRestricciones() {
		return lstRestricciones;
	}

	public void setLstRestricciones(List<RestriccionEstandarEntity> lstRestricciones) {
		this.lstRestricciones = lstRestricciones;
	}
	
	
}
