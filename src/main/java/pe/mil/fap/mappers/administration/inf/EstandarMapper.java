package pe.mil.fap.mappers.administration.inf;

import java.util.List;

import pe.mil.fap.entity.administration.EstandarEntity;
import pe.mil.fap.model.administration.EstandarDTO;

public interface EstandarMapper {

	EstandarDTO toDTO(EstandarEntity entity);
	EstandarEntity toEntity(EstandarDTO dto);

    List<EstandarDTO> toListDTO(List<EstandarEntity> listEntity);
    List<EstandarEntity> toListEntity(List<EstandarDTO> listDto);
}
