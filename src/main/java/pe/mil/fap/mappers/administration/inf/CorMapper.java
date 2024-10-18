package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.CorEntity;
import pe.mil.fap.model.administration.CorDTO;

public interface CorMapper {

	CorDTO toDTO(CorEntity entity);
	CorEntity toEntity(CorDTO dto);

    List<CorDTO> toListDTO(List<CorEntity> listEntity);
    List<CorEntity> toListEntity(List<CorDTO> listDto);
}
