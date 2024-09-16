package pe.mil.fap.mappers.bussiness.inf;

import java.util.List;

import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.model.bussiness.DetalleMisionDTO;
 

public interface DetalleMisionMapper {


	DetalleMisionDTO toDTO(DetalleMisionEntity entity);
    DetalleMisionEntity toEntity(DetalleMisionDTO dto);
    
    List<DetalleMisionDTO> toDTOList(List<DetalleMisionEntity> entityList);
    List<DetalleMisionEntity> toEntityList(List<DetalleMisionDTO> dtoList);
}
