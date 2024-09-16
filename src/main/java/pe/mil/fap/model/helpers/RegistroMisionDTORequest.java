package pe.mil.fap.model.helpers;

import java.io.Serializable;
import java.util.List;
 
import pe.mil.fap.model.administration.SubFaseDTO;
import pe.mil.fap.model.bussiness.MisionDTO;

public class RegistroMisionDTORequest implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private SubFaseDTO subfase;
	private List<MisionDTO> lstMisiones;
	
	private List<OperacionDTORequest> lstOperaciones;
	
	public RegistroMisionDTORequest() {
		super();
	}

	public RegistroMisionDTORequest(SubFaseDTO subfase, 
									List<MisionDTO> lstMisiones,
									List<OperacionDTORequest> lstOperaciones) {
		super();
		this.subfase = subfase;
		this.lstMisiones = lstMisiones;
		this.lstOperaciones = lstOperaciones;
	}

	public SubFaseDTO getSubfase() {
		return subfase;
	}

	public void setSubfase(SubFaseDTO subfase) {
		this.subfase = subfase;
	}

	public List<MisionDTO> getLstMisiones() {
		return lstMisiones;
	}

	public void setLstMisiones(List<MisionDTO> lstMisiones) {
		this.lstMisiones = lstMisiones;
	}

	public List<OperacionDTORequest> getLstOperaciones() {
		return lstOperaciones;
	}

	public void setLstOperaciones(List<OperacionDTORequest> lstOperaciones) {
		this.lstOperaciones = lstOperaciones;
	}
 
	
}
