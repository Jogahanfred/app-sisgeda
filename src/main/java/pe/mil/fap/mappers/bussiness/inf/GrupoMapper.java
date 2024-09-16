package pe.mil.fap.mappers.bussiness.inf;

import java.util.List; 
import pe.mil.fap.entity.administration.GrupoEntity; 
import pe.mil.fap.model.administration.GrupoDTO;
 
public interface GrupoMapper {

	GrupoDTO toDTO(GrupoEntity entity);
	GrupoEntity toEntity(GrupoDTO dto); 

    List<GrupoDTO> toListDTO(List<GrupoEntity> listEntity);
    List<GrupoEntity> toEntityList(List<GrupoDTO> dtoList);
}
