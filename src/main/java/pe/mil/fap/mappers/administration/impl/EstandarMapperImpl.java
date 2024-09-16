package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.EstandarEntity;
import pe.mil.fap.mappers.administration.inf.EstandarMapper;
import pe.mil.fap.model.administration.EstandarDTO;

@Component
public class EstandarMapperImpl implements EstandarMapper {

	@Override
	public EstandarDTO toDTO(EstandarEntity entity) {
		return new EstandarDTO(entity.getIdEstandar(), entity.getCoCodigo(), entity.getNoNombre(), entity.getTxDescripcion(), entity.getFlEstado(), entity.getNuNivel());

	}

	@Override
	public EstandarEntity toEntity(EstandarDTO dto) {
		return new EstandarEntity(dto.getIdEstandar(), dto.getCoCodigo(), dto.getNoNombre(), dto.getTxDescripcion(), dto.getFlEstado(), dto.getNuNivel());

	}

	@Override
	public List<EstandarDTO> toListDTO(List<EstandarEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<EstandarEntity> toListEntity(List<EstandarDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
