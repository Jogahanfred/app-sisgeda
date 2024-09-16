package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.SubFaseEntity;
import pe.mil.fap.model.administration.SubFaseDTO;

public interface SubFaseMapper {

	SubFaseDTO toDTO(SubFaseEntity entity);
	SubFaseEntity toEntity(SubFaseDTO dto);

    List<SubFaseDTO> toListDTO(List<SubFaseEntity> listEntity);
    List<SubFaseEntity> toListEntity(List<SubFaseDTO> listDto);
}
