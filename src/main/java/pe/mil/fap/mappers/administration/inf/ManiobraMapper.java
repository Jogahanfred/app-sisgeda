package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.ManiobraEntity;
import pe.mil.fap.model.administration.ManiobraDTO;

public interface ManiobraMapper {

	ManiobraDTO toDTO(ManiobraEntity entity);
	ManiobraEntity toEntity(ManiobraDTO dto);

    List<ManiobraDTO> toListDTO(List<ManiobraEntity> listEntity);
    List<ManiobraEntity> toListEntity(List<ManiobraDTO> listDto);
}
