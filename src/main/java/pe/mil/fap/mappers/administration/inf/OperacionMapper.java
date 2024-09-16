package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.OperacionEntity;
import pe.mil.fap.model.administration.OperacionDTO;

public interface OperacionMapper {

	OperacionDTO toDTO(OperacionEntity entity);
	OperacionEntity toEntity(OperacionDTO dto);

    List<OperacionDTO> toListDTO(List<OperacionEntity> listEntity);
    List<OperacionEntity> toListEntity(List<OperacionDTO> listDto);
}
