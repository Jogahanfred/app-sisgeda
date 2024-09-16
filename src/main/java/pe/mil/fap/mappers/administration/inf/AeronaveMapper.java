package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.AeronaveEntity;
import pe.mil.fap.model.administration.AeronaveDTO;

public interface AeronaveMapper {

	AeronaveDTO toDTO(AeronaveEntity entity);
	AeronaveEntity toEntity(AeronaveDTO dto);

    List<AeronaveDTO> toListDTO(List<AeronaveEntity> listEntity);
    List<AeronaveEntity> toListEntity(List<AeronaveDTO> listDto);
}
