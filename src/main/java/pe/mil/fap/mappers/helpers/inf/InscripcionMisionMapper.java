package pe.mil.fap.mappers.helpers.inf;

import java.util.List;

import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
  
public interface InscripcionMisionMapper {

	InscripcionMisionDTO toDTO(InscripcionMisionEntity entity);
	InscripcionMisionEntity toEntity(InscripcionMisionDTO dto);

    List<InscripcionMisionDTO> toListDTO(List<InscripcionMisionEntity> listEntity);
    List<InscripcionMisionEntity> toListEntity(List<InscripcionMisionDTO> listDto);
}
