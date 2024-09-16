package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.model.administration.FaseDTO;

public interface FaseMapper {

	FaseDTO toDTO(FaseEntity entity);
	FaseEntity toEntity(FaseDTO dto);

    List<FaseDTO> toListDTO(List<FaseEntity> listEntity);
    List<FaseEntity> toListEntity(List<FaseDTO> listDto);
}
