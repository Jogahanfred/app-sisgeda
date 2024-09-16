package pe.mil.fap.mappers.bussiness.inf;

import java.util.List;
 
import pe.mil.fap.entity.bussiness.CalificacionEntity; 
import pe.mil.fap.model.bussiness.CalificacionDTO;

public interface CalificacionMapper {

	CalificacionDTO toDTO(CalificacionEntity entity);
	CalificacionEntity toEntity(CalificacionDTO dto); 
	
    List<CalificacionEntity> toEntityList(List<CalificacionDTO> dtoList);
}
