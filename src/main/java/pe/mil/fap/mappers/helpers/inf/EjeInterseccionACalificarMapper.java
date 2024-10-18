package pe.mil.fap.mappers.helpers.inf;

import java.util.List;

import pe.mil.fap.entity.helpers.EjeInterseccionACalificarEntity;
import pe.mil.fap.model.helpers.EjeInterseccionACalificarDTO; 
  
public interface EjeInterseccionACalificarMapper {

	EjeInterseccionACalificarDTO toDTO(EjeInterseccionACalificarEntity entity);
	EjeInterseccionACalificarEntity toEntity(EjeInterseccionACalificarDTO dto);

    List<EjeInterseccionACalificarDTO> toListDTO(List<EjeInterseccionACalificarEntity> listEntity);
    List<EjeInterseccionACalificarEntity> toListEntity(List<EjeInterseccionACalificarDTO> listDto);
}
