package pe.mil.fap.mappers.helpers.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.mappers.helpers.inf.InscripcionMisionMapper;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
 
@Component
public class InscripcionMisionMapperImpl implements InscripcionMisionMapper{

	@Override
	public InscripcionMisionDTO toDTO(InscripcionMisionEntity entity) {
		return new InscripcionMisionDTO(entity.getIdMision(), entity.getCoCodigo(), entity.getFlInscripcion());
	}

	@Override
	public InscripcionMisionEntity toEntity(InscripcionMisionDTO dto) {
		return new InscripcionMisionEntity(dto.getIdMision(), dto.getCoCodigo(), dto.getFlInscripcion());
	}

	@Override
	public List<InscripcionMisionDTO> toListDTO(List<InscripcionMisionEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<InscripcionMisionEntity> toListEntity(List<InscripcionMisionDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
