package pe.mil.fap.mappers.administration.inf;

import java.util.List;

import pe.mil.fap.entity.administration.UnidadEntity;
import pe.mil.fap.model.administration.UnidadDTO;
 

public interface UnidadMapper {

	UnidadDTO toDTO(UnidadEntity entity);
	UnidadEntity toEntity(UnidadDTO dto);

    List<UnidadDTO> toListDTO(List<UnidadEntity> listEntity);
    List<UnidadEntity> toListEntity(List<UnidadDTO> listDto);
}
