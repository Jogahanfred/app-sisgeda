package pe.mil.fap.mappers.bussiness.inf;

import java.util.List;

import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.model.bussiness.DetalleCalificacionDTO;
 

public interface DetalleCalificacionMapper {


	DetalleCalificacionDTO toDTO(DetalleCalificacionEntity entity);
    DetalleCalificacionEntity toEntity(DetalleCalificacionDTO dto);
    
    List<DetalleCalificacionDTO> toDTOList(List<DetalleCalificacionEntity> entityList);
    List<DetalleCalificacionEntity> toEntityList(List<DetalleCalificacionDTO> dtoList);
}
