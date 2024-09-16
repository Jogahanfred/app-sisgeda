package pe.mil.fap.mappers.helpers.inf;

import java.util.List;

import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.model.helpers.EjeXDTO;
  
public interface EjeXMapper {

	EjeXDTO toDTO(EjeXEntity entity);
	EjeXEntity toEntity(EjeXDTO dto);

    List<EjeXDTO> toListDTO(List<EjeXEntity> listEntity);
    List<EjeXEntity> toListEntity(List<EjeXDTO> listDto);
}
