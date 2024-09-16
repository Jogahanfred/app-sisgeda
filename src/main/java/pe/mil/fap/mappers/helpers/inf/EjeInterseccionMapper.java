package pe.mil.fap.mappers.helpers.inf;

import java.util.List;

import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.model.helpers.EjeInterseccionDTO; 
  
public interface EjeInterseccionMapper {

	EjeInterseccionDTO toDTO(EjeInterseccionEntity entity);
	EjeInterseccionEntity toEntity(EjeInterseccionDTO dto);

    List<EjeInterseccionDTO> toListDTO(List<EjeInterseccionEntity> listEntity);
    List<EjeInterseccionEntity> toListEntity(List<EjeInterseccionDTO> listDto);
}
