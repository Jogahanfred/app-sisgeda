package pe.mil.fap.mappers.administration.inf;

import java.util.List;

import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.model.administration.RestriccionEstandarDTO;

public interface RestriccionEstandarMapper {

	RestriccionEstandarDTO toDTO(RestriccionEstandarEntity entity);
	RestriccionEstandarEntity toEntity(RestriccionEstandarDTO dto);

    List<RestriccionEstandarDTO> toListDTO(List<RestriccionEstandarEntity> listEntity);
    List<RestriccionEstandarEntity> toListEntity(List<RestriccionEstandarDTO> listDto);
}
