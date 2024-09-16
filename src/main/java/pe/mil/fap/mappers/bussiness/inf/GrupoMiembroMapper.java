package pe.mil.fap.mappers.bussiness.inf;

import java.util.List;

import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.model.bussiness.GrupoMiembroDTO;
 

public interface GrupoMiembroMapper {


	GrupoMiembroDTO toDTO(GrupoMiembroEntity entity);
    GrupoMiembroEntity toEntity(GrupoMiembroDTO dto);
    
    List<GrupoMiembroDTO> toDTOList(List<GrupoMiembroEntity> entityList);
    List<GrupoMiembroEntity> toEntityList(List<GrupoMiembroDTO> dtoList);
}
