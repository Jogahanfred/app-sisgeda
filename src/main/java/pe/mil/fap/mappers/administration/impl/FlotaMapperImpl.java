package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.FlotaEntity;
import pe.mil.fap.mappers.administration.inf.FlotaMapper;
import pe.mil.fap.model.administration.FlotaDTO;

@Component
public class FlotaMapperImpl implements FlotaMapper {

	@Override
	public FlotaDTO toDTO(FlotaEntity entity) {
		return new FlotaDTO(entity.getIdFlota(), entity.getNoTipoFlota(), entity.getCoCodigo(), entity.getNoNombre(), entity.getFlBloqueado(), entity.getFlEstado());

	}

	@Override
	public FlotaEntity toEntity(FlotaDTO dto) {
		return new FlotaEntity(dto.getIdFlota(), dto.getNoTipoFlota(), dto.getCoCodigo(), dto.getNoNombre(), dto.getFlBloqueado(), dto.getFlEstado());

	}

	@Override
	public List<FlotaDTO> toListDTO(List<FlotaEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<FlotaEntity> toListEntity(List<FlotaDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
