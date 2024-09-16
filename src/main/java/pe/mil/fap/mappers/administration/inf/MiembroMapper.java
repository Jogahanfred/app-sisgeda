package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.MiembroEntity;
import pe.mil.fap.model.administration.MiembroDTO;

public interface MiembroMapper {

	MiembroDTO toDTO(MiembroEntity entity);
	MiembroEntity toEntity(MiembroDTO dto);

    List<MiembroDTO> toListDTO(List<MiembroEntity> listEntity);
    List<MiembroEntity> toListEntity(List<MiembroDTO> listDto);
}
