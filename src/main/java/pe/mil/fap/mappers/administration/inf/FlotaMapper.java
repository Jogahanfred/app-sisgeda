package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.FlotaEntity;
import pe.mil.fap.model.administration.FlotaDTO;

public interface FlotaMapper {

	FlotaDTO toDTO(FlotaEntity entity);
	FlotaEntity toEntity(FlotaDTO dto);

    List<FlotaDTO> toListDTO(List<FlotaEntity> listEntity);
    List<FlotaEntity> toListEntity(List<FlotaDTO> listDto);
}
