package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.ProgramaEntity;
import pe.mil.fap.model.administration.ProgramaDTO;

public interface ProgramaMapper {

	ProgramaDTO toDTO(ProgramaEntity entity);
	ProgramaEntity toEntity(ProgramaDTO dto);

    List<ProgramaDTO> toListDTO(List<ProgramaEntity> listEntity);
    List<ProgramaEntity> toListEntity(List<ProgramaDTO> listDto);
}
