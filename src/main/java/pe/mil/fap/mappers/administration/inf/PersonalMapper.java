package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.PersonalEntity; 
import pe.mil.fap.model.administration.PersonalDTO;

public interface PersonalMapper {

	PersonalDTO toDTO(PersonalEntity entity);
	PersonalEntity toEntity(PersonalDTO dto);
	
    List<PersonalDTO> toListDTO(List<PersonalEntity> listEntity);
    List<PersonalEntity> toListEntity(List<PersonalDTO> listDto);
    
}
