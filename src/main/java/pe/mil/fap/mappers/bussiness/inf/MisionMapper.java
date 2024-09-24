package pe.mil.fap.mappers.bussiness.inf;

import java.util.List; 
import pe.mil.fap.entity.bussiness.MisionEntity; 
import pe.mil.fap.model.bussiness.MisionDTO;

public interface MisionMapper {

	MisionDTO toDTO(MisionEntity entity);
	MisionEntity toEntity(MisionDTO dto); 

    List<MisionEntity> toEntityList(List<MisionDTO> dtoList);
    List<MisionDTO> toDTOList(List<MisionEntity> entityList);

	
}
