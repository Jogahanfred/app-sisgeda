package pe.mil.fap.mappers.helpers.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.mappers.helpers.inf.EjeYMapper;
import pe.mil.fap.model.helpers.EjeYDTO;
 

@Component
public class EjeYMapperImpl implements EjeYMapper{

	@Override
	public EjeYDTO toDTO(EjeYEntity entity) {
		return new EjeYDTO(entity.getIdManiobra(), entity.getNoNombreManiobra(), entity.getIdOperacion(), entity.getNoNombreOperacion());
	}

	@Override
	public EjeYEntity toEntity(EjeYDTO dto) {
		return new EjeYEntity(dto.getIdManiobra(), dto.getNoNombreManiobra(), dto.getIdOperacion(), dto.getNoNombreOperacion());
	}

	@Override
	public List<EjeYDTO> toListDTO(List<EjeYEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<EjeYEntity> toListEntity(List<EjeYDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
