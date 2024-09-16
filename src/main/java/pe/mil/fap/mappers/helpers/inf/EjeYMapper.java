package pe.mil.fap.mappers.helpers.inf;

import java.util.List;

import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.model.helpers.EjeYDTO; 
   
public interface EjeYMapper {

	EjeYDTO toDTO(EjeYEntity entity);
	EjeYEntity toEntity(EjeYDTO dto);

    List<EjeYDTO> toListDTO(List<EjeYEntity> listEntity);
    List<EjeYEntity> toListEntity(List<EjeYDTO> listDto);
}
