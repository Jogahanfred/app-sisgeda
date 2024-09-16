package pe.mil.fap.model.bussiness;

import java.io.Serializable;
import java.util.List;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 

public class MisionDTO implements Serializable {
 
	private static final long serialVersionUID = 1L; 
	
	private Integer idMision;

	@NotNull(message = "La sub fase no puede ser vacia") 
	private Integer idSubFase;
	
	@NotNull(message = "El tipo de misión no puede ser vacio") 
	private Integer idTipoMision;
	
	@Size(min = 1, max = 10, message = "El código debe tener entre {min} y {max} caracteres")
	@NotEmpty(message = "El código no puede ser vacio") 
	private String coCodigo; 
	
	private Integer flChequeo;
	 
	private Integer flBloqueado;
 
	private Integer flEstado;
	 
	private List<DetalleMisionDTO> lstDetalleMision;

	public MisionDTO(Integer idMision, @NotNull(message = "La sub fase no puede ser vacia") Integer idSubFase,
			@NotNull(message = "El tipo de misión no puede ser vacio") Integer idTipoMision,
			@Size(min = 1, max = 10, message = "El código debe tener entre {min} y {max} caracteres") @NotEmpty(message = "El código no puede ser vacio") String coCodigo,
			Integer flChequeo, Integer flBloqueado, Integer flEstado, List<DetalleMisionDTO> lstDetalleMision) {
		super();
		this.idMision = idMision;
		this.idSubFase = idSubFase;
		this.idTipoMision = idTipoMision;
		this.coCodigo = coCodigo;
		this.flChequeo = flChequeo;
		this.flBloqueado = flBloqueado;
		this.flEstado = flEstado;
		this.lstDetalleMision = lstDetalleMision;
	}

	public MisionDTO() {
		super();
	}

	public Integer getIdMision() {
		return idMision;
	}

	public void setIdMision(Integer idMision) {
		this.idMision = idMision;
	}

	public Integer getIdSubFase() {
		return idSubFase;
	}

	public void setIdSubFase(Integer idSubFase) {
		this.idSubFase = idSubFase;
	}

	public Integer getIdTipoMision() {
		return idTipoMision;
	}

	public void setIdTipoMision(Integer idTipoMision) {
		this.idTipoMision = idTipoMision;
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

	public List<DetalleMisionDTO> getLstDetalleMision() {
		return lstDetalleMision;
	}

	public void setLstDetalleMision(List<DetalleMisionDTO> lstDetalleMision) {
		this.lstDetalleMision = lstDetalleMision;
	}
	 
}
