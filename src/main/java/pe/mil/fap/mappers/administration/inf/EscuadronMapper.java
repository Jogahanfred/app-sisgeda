package pe.mil.fap.mappers.administration.inf;

import java.util.List;

import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.model.administration.EscuadronDTO;

public interface EscuadronMapper {

	EscuadronDTO toDTO(EscuadronEntity entity); 
	EscuadronEntity toEntity(EscuadronDTO dto); 
	List<EscuadronDTO> toListDTO(List<EscuadronEntity> listEntity); 
	List<EscuadronEntity> toListEntity(List<EscuadronDTO> listDto);
}
