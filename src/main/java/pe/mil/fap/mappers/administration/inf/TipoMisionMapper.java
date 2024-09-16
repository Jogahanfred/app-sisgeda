package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.TipoMisionEntity;
import pe.mil.fap.model.administration.TipoMisionDTO;

public interface TipoMisionMapper {

	TipoMisionDTO toDTO(TipoMisionEntity entity);
	TipoMisionEntity toEntity(TipoMisionDTO dto);

    List<TipoMisionDTO> toListDTO(List<TipoMisionEntity> listEntity);
    List<TipoMisionEntity> toListEntity(List<TipoMisionDTO> listDto);
}
