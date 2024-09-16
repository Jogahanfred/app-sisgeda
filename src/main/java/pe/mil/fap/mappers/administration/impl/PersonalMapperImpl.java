package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
 
import pe.mil.fap.entity.administration.PersonalEntity;
import pe.mil.fap.mappers.administration.inf.PersonalMapper; 
import pe.mil.fap.model.administration.PersonalDTO;
 
@Component
public class PersonalMapperImpl implements PersonalMapper {

	@Override
	public PersonalDTO toDTO(PersonalEntity entity) {
		return new PersonalDTO(entity.getOrden(), entity.getPlana(), entity.getNsa(), entity.getGrado(), entity.getDatos(), entity.getUnidadOrigen(), entity.getUnidadParte(), entity.getDni(), entity.getEspecialidad(), entity.getSexo(), entity.getNacimiento(), entity.getTipoSangre(), entity.getFotografia());
	}

	@Override
	public PersonalEntity toEntity(PersonalDTO dto) {
		return new PersonalEntity(dto.getOrden(), dto.getPlana(), dto.getNsa(), dto.getGrado(), dto.getDatos(), dto.getUnidadOrigen(), dto.getUnidadParte(), dto.getDni(), dto.getEspecialidad(), dto.getSexo(), dto.getNacimiento(), dto.getTipoSangre(), dto.getFotografia());
	}

	@Override
	public List<PersonalDTO> toListDTO(List<PersonalEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<PersonalEntity> toListEntity(List<PersonalDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}


}
